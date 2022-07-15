package de.fw.devops.utils.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import de.fw.devops.utils.AbstractArtifactBuilder;

/**
 * @author  Felix Werner
 */
public class PropertiesToResourcesController {
  
  private PropertiesToResourcesView propertiesToResourcesPanel;
  
  private Set<String> dataModelPropertiesFolders = new LinkedHashSet<>();
  private Set<String> templatesFolders = new LinkedHashSet<>();
  private Set<String> outputFolders = new LinkedHashSet<>();
  
  /**
   * @param propertiesToResourcesPanel
   */
  public PropertiesToResourcesController(PropertiesToResourcesView propertiesToResourcesPanel) {
    this.propertiesToResourcesPanel = propertiesToResourcesPanel;
    // init comboboxes with default values
    dataModelPropertiesFolders.add("datamodel/project.properties");
    propertiesToResourcesPanel.setComboBox(dataModelPropertiesFolders, propertiesToResourcesPanel.getDataModelPropertiesComboBox());
    templatesFolders.add("snippets_maven_openshift");
    propertiesToResourcesPanel.setComboBox(templatesFolders, propertiesToResourcesPanel.getTemplatesComboBox());
    outputFolders.add("target");
    propertiesToResourcesPanel.setComboBox(outputFolders, propertiesToResourcesPanel.getOutputComboBox());
    registerListeners();
  }
  
  /**
   * Adds listeners to all relevant GUI components.
   */
  private void registerListeners() {
    propertiesToResourcesPanel.addOpenFolderButtonForDataModelPropertiesListener(new OpenFolderButtonForDataModelPropertiesListener());
    propertiesToResourcesPanel.addOpenFolderButtonForTemplatesListener(new OpenFolderButtonForTemplatesListener());
    propertiesToResourcesPanel.addOpenFolderButtonForOutputListener(new OpenFolderButtonForOutputListener());
    propertiesToResourcesPanel.addProcessButtonListener(new ProcessButtonListener());
  }
  
  class OpenFolderButtonForTemplatesListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      openFolder(templatesFolders, propertiesToResourcesPanel.getTemplatesComboBox());
    }
  }
  
  class OpenFolderButtonForDataModelPropertiesListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      openFolder(dataModelPropertiesFolders, propertiesToResourcesPanel.getDataModelPropertiesComboBox());
    }
  }
  
  class OpenFolderButtonForOutputListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      openFolder(outputFolders, propertiesToResourcesPanel.getOutputComboBox());
    }
  }
  
  private void openFolder(Set<String> folders, JComboBox<String> comboBox) {
    // Show a file chooser dialog
    final JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    fc.setMultiSelectionEnabled(true);
    final String folderStrings = folders.iterator().next();
    if (folderStrings != null && !folderStrings.isBlank()) {
      File lastChosenDirectory = new File(folderStrings.split(";")[0]);
      fc.setCurrentDirectory(lastChosenDirectory);
    }
    int returnVal = fc.showOpenDialog(propertiesToResourcesPanel);
    
    // Save the output to the chosen file
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File[] file = fc.getSelectedFiles();
      String paths = file[0].getAbsolutePath();
      for (int i = 1; i < file.length; i++) {
        paths = paths + ";" + file[i].getAbsolutePath();
      }
      folders = addFolder(paths, folders);
      propertiesToResourcesPanel.setComboBox(folders, comboBox);
    }
  }
  
  private Set<String> addFolder(String folder, Set<String> folders) {
    Set<String> newFileSearchFolders = new LinkedHashSet<>();
    newFileSearchFolders.add(folder);
    // Add up to 19 old search folders
    Iterator<String> it = folders.iterator();
    for (int i = 0; it.hasNext() && i < 20; i++) {
      newFileSearchFolders.add(it.next());
    }
    return newFileSearchFolders;
  }
  
  class ProcessButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      writeTextArea("");
      propertiesToResourcesPanel.getTextArea().repaint();
      try {
        AbstractArtifactBuilder artifactBuilder = AbstractArtifactBuilder
            .fromTemplatesFolder(propertiesToResourcesPanel.getTextComponent(propertiesToResourcesPanel.getTemplatesComboBox()).getText())
            .properties(propertiesToResourcesPanel.getTextComponent(propertiesToResourcesPanel.getDataModelPropertiesComboBox()).getText())
            .templatePath(propertiesToResourcesPanel.getTextComponent(propertiesToResourcesPanel.getTemplatesComboBox()).getText())
            .targetPath(propertiesToResourcesPanel.getTextComponent(propertiesToResourcesPanel.getOutputComboBox()).getText());
        
        artifactBuilder.process();
        
        StringBuilder stringBuilder = new StringBuilder("DataModel: ");
        for (String s : artifactBuilder.getDataModel().keySet()) {
          stringBuilder.append(s);
          stringBuilder.append(" ,");
        }
        
        stringBuilder.append(" succsesfully processed!");
        writeTextArea(stringBuilder.toString());
        
      } catch (IOException e1) {
        e1.printStackTrace();
        writeTextArea("Exception:" + e1.toString() + "/" + e1.getMessage());
      }
      
    }
    
    private void writeTextArea(String text) {
      propertiesToResourcesPanel.getTextArea().setText(text);
    }
    
  }
}
