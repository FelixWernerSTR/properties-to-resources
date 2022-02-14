package de.fw.devops.utils;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Liest Properties ein mit Features wie: - Variablen-Interpolation - System und Environment-Variablen - zusätzlichen Properties können mit
 * "include=x.properties" referenziert werden
 * 
 * @author Felix Werner
 *
 */
public class ApacheCommonsConfigReader {
  private static Logger logger = LogManager.getLogger(ApacheCommonsConfigReader.class);
  
  private Path propertiesFilePath;
  
  /**
   * @param path
   * @return this
   */
  protected ApacheCommonsConfigReader properties(Path path) {
    propertiesFilePath = path;
    return this;
  }
  
  private static ApacheCommonsConfigReader instance;
  
  /**
   * @param path
   * @return ApacheCommonsConfigReader
   */
  public static ApacheCommonsConfigReader fromProperties(Path path) {
    if (instance == null) {
      instance = new ApacheCommonsConfigReader().properties(path);
      return instance;
    } else {
      return instance.properties(path);
    }
  }
  
  /**
   * @return Properties
   */
  public Properties process() {
    Properties properties = new Properties();
    Parameters params = new Parameters();
    // Read data from this file
    FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
        .configure(params.fileBased().setFile(propertiesFilePath.toFile()));
    try {
      Configuration config = builder.getConfiguration();
      // config contains all properties read from the file
      Iterator<String> i = config.getKeys();
      while (i.hasNext()) {
        String key = i.next();
        properties.put(key, config.getString(key));
      }
    } catch (ConfigurationException e) {
      logger.warn("error config propertiesFilePath: {})", propertiesFilePath);
      e.printStackTrace();
    }
    return properties;
  }
  
}
