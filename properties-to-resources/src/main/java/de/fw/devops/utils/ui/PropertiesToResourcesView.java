package de.fw.devops.utils.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

/**
 * @author  Felix Werner
 */
public class PropertiesToResourcesView extends JPanel {
  private final JButton btnProcess;
  private final JComboBox<String> dataModelPropertiesComboBox;
  private final JComboBox<String> templatesComboBox;
  private final JButton btnOpenFolderTemplates;
  private final JButton btnStop;
  private JButton btnOpenFolderDataModelProperties;
  private JLabel labelOutput;
  private JComboBox<String> outputComboBox;
  private JButton btnOpenFolderOutput;
  private JEditorPane textArea;
  
  /**
   * Create the dialog.
   */
  public PropertiesToResourcesView() {
    setBounds(100, 100, 602, 339);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
    gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 28 };
    gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0, 0, 1.0, 0.0, 0.0 };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
    setLayout(gridBagLayout);
    
    JLabel labelDataModelProperties = new JLabel("data model properties");
    GridBagConstraints gbc_labelDataModelProperties = new GridBagConstraints();
    gbc_labelDataModelProperties.insets = new Insets(6, 5, 5, 5);
    gbc_labelDataModelProperties.anchor = GridBagConstraints.NORTHEAST;
    gbc_labelDataModelProperties.gridx = 0;
    gbc_labelDataModelProperties.gridy = 0;
    add(labelDataModelProperties, gbc_labelDataModelProperties);
    
    dataModelPropertiesComboBox = new JComboBox<>();
    dataModelPropertiesComboBox.setEditable(true);
    GridBagConstraints gbc_dataModelPropertiesComboBox = new GridBagConstraints();
    gbc_dataModelPropertiesComboBox.gridwidth = 4;
    gbc_dataModelPropertiesComboBox.insets = new Insets(6, 0, 5, 5);
    gbc_dataModelPropertiesComboBox.fill = GridBagConstraints.HORIZONTAL;
    gbc_dataModelPropertiesComboBox.gridx = 1;
    gbc_dataModelPropertiesComboBox.gridy = 0;
    add(dataModelPropertiesComboBox, gbc_dataModelPropertiesComboBox);
    
    btnOpenFolderDataModelProperties = new JButton("...");
    btnOpenFolderDataModelProperties.setPreferredSize(new Dimension(20, 20));
    btnOpenFolderDataModelProperties.setMinimumSize(new Dimension(20, 20));
    GridBagConstraints gbc_btnOpenFolderDataModelProperties = new GridBagConstraints();
    gbc_btnOpenFolderDataModelProperties.insets = new Insets(0, 0, 5, 5);
    gbc_btnOpenFolderDataModelProperties.gridx = 5;
    gbc_btnOpenFolderDataModelProperties.gridy = 0;
    add(btnOpenFolderDataModelProperties, gbc_btnOpenFolderDataModelProperties);
    
    JLabel lblFolders = new JLabel("templates");
    lblFolders.setHorizontalAlignment(SwingConstants.RIGHT);
    GridBagConstraints gbc_lblFolders = new GridBagConstraints();
    gbc_lblFolders.insets = new Insets(2, 5, 5, 5);
    gbc_lblFolders.anchor = GridBagConstraints.EAST;
    gbc_lblFolders.gridx = 0;
    gbc_lblFolders.gridy = 1;
    add(lblFolders, gbc_lblFolders);
    
    templatesComboBox = new JComboBox<>();
    templatesComboBox.setEditable(true);
    GridBagConstraints gbc_templatesComboBox = new GridBagConstraints();
    gbc_templatesComboBox.gridwidth = 4;
    gbc_templatesComboBox.insets = new Insets(2, 0, 5, 5);
    gbc_templatesComboBox.fill = GridBagConstraints.HORIZONTAL;
    gbc_templatesComboBox.gridx = 1;
    gbc_templatesComboBox.gridy = 1;
    add(templatesComboBox, gbc_templatesComboBox);
    
    btnOpenFolderTemplates = new JButton("...");
    GridBagConstraints gbc_btnOpenFolderTemplates = new GridBagConstraints();
    gbc_btnOpenFolderTemplates.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnOpenFolderTemplates.insets = new Insets(2, 0, 5, 5);
    gbc_btnOpenFolderTemplates.gridx = 5;
    gbc_btnOpenFolderTemplates.gridy = 1;
    btnOpenFolderTemplates.setMinimumSize(new Dimension(20, 20));
    btnOpenFolderTemplates.setPreferredSize(new Dimension(20, 20));
    add(btnOpenFolderTemplates, gbc_btnOpenFolderTemplates);
    
    btnProcess = new JButton("process");
    GridBagConstraints gbc_btnProcess = new GridBagConstraints();
    gbc_btnProcess.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnProcess.insets = new Insets(6, 5, 5, 0);
    gbc_btnProcess.gridx = 6;
    gbc_btnProcess.gridy = 0;
    add(btnProcess, gbc_btnProcess);
    
    btnStop = new JButton("Stop");
    btnStop.setEnabled(false);
    GridBagConstraints gbc_btnStop = new GridBagConstraints();
    gbc_btnStop.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnStop.insets = new Insets(0, 5, 5, 0);
    gbc_btnStop.gridx = 6;
    gbc_btnStop.gridy = 1;
    add(btnStop, gbc_btnStop);
    
    labelOutput = new JLabel("output");
    GridBagConstraints gbc_labelOutput = new GridBagConstraints();
    gbc_labelOutput.anchor = GridBagConstraints.EAST;
    gbc_labelOutput.insets = new Insets(0, 0, 5, 5);
    gbc_labelOutput.gridx = 0;
    gbc_labelOutput.gridy = 2;
    add(labelOutput, gbc_labelOutput);
    
    outputComboBox = new JComboBox<>();
    outputComboBox.setEditable(true);
    GridBagConstraints gbc_outputComboBox = new GridBagConstraints();
    gbc_outputComboBox.gridwidth = 4;
    gbc_outputComboBox.insets = new Insets(0, 0, 5, 5);
    gbc_outputComboBox.fill = GridBagConstraints.HORIZONTAL;
    gbc_outputComboBox.gridx = 1;
    gbc_outputComboBox.gridy = 2;
    add(outputComboBox, gbc_outputComboBox);
    
    btnOpenFolderOutput = new JButton("...");
    btnOpenFolderOutput.setPreferredSize(new Dimension(20, 20));
    btnOpenFolderOutput.setMinimumSize(new Dimension(20, 20));
    GridBagConstraints gbc_btnOpenFolderOutput = new GridBagConstraints();
    gbc_btnOpenFolderOutput.insets = new Insets(0, 0, 5, 5);
    gbc_btnOpenFolderOutput.gridx = 5;
    gbc_btnOpenFolderOutput.gridy = 2;
    add(btnOpenFolderOutput, gbc_btnOpenFolderOutput);
    
    textArea = new JEditorPane("text/html", "");

    GridBagConstraints gbc_textArea = new GridBagConstraints();
    gbc_textArea.gridheight = 4;
    gbc_textArea.gridwidth = 7;
    gbc_textArea.insets = new Insets(0, 0, 5, 0);
    gbc_textArea.fill = GridBagConstraints.BOTH;
    gbc_textArea.gridx = 0;
    gbc_textArea.gridy = 5;
    add(textArea, gbc_textArea);
  }
  
  public JComboBox getDataModelPropertiesComboBox() {
    return dataModelPropertiesComboBox;
  }
  
  public JComboBox getTemplatesComboBox() {
    return templatesComboBox;
  }
  
  public JComboBox getOutputComboBox() {
    return outputComboBox;
  }
  
  public void addComboBoxListener(ActionListener actLst, JComboBox comboBox) {
    comboBox.addActionListener(actLst);
  }
  
  /**
   * Sets the data model combo box.
   * 
   * @param valueStrings The previous search strings to set
   */
  public void setComboBox(Set<String> valueStrings, JComboBox comboBox) {
    comboBox.removeAllItems();
    for (String valueString : valueStrings) {
      comboBox.addItem(valueString);
    }
    // Select the text of the selected item, so one key pressed overwrites it.
    if (comboBox.getSelectedItem() != null) {
      JTextComponent component = getTextComponent(comboBox);
      component.setSelectionStart(0);
      component.setSelectionEnd(((String) comboBox.getSelectedItem()).length());
    }
  }
  
  public JTextComponent getTextComponent(JComboBox comboBox) {
    JTextComponent component = (JTextComponent) comboBox.getEditor().getEditorComponent();
    return component;
  }
  
  // Open Folder button listener
  public void addOpenFolderButtonForDataModelPropertiesListener(ActionListener actLst) {
    btnOpenFolderDataModelProperties.addActionListener(actLst);
  }
  
  // Open Folder button listener
  public void addOpenFolderButtonForTemplatesListener(ActionListener actLst) {
    btnOpenFolderTemplates.addActionListener(actLst);
  }
  
  // Open Folder button listener
  public void addOpenFolderButtonForOutputListener(ActionListener actLst) {
    btnOpenFolderOutput.addActionListener(actLst);
  }
  
  // Find All button listener
  public void addProcessButtonListener(ActionListener actLst) {
    btnProcess.addActionListener(actLst);
  }
  
  public JButton getStopButton() {
    return btnStop;
  }
  
  // Stop Search button listener
  public void addStopButtonListener(ActionListener actLst) {
    btnStop.addActionListener(actLst);
  }
  
  public JEditorPane getTextArea() {
    return textArea;
  }
  
}
