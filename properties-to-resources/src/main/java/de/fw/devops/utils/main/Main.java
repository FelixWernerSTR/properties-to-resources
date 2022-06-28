package de.fw.devops.utils.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fw.devops.utils.AbstractArtifactBuilder;
import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * 
 * @author Felix Werner
 */
public class Main {
  static Logger logger = LogManager.getLogger(Main.class);
  
  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    
    var commandLine = CommandLineHandler.parseCommandLineOptions("PropertiesToResourcesApp", args);
    if (commandLine == null) {
      return;
    }
    var classNameDataModelProvider = new String(
        Files.readAllBytes(Paths.get(commandLine.getOptionValue("t") + File.separator + "datamodelProvider.properties")));
    
    var object = (AbstractArtifactBuilder) PropertiesToPojosParseUtil.createObject(classNameDataModelProvider);
    
    object.properties(commandLine.getOptionValue("i")).templatePath(commandLine.getOptionValue("t")).targetPath(commandLine.getOptionValue("o")).process();
  }
  
}
