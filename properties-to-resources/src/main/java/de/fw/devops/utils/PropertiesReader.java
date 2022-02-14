package de.fw.devops.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author N0008246
 */
public class PropertiesReader {
  private static final Logger logger = LogManager.getLogger(PropertiesReader.class.getName());
  
  private PropertiesReader() {
  }
  
  /**
   * @param propertyFile
   * @return Properties
   */
  public static synchronized Properties read(Path propertyFile) {
    Properties properties = new Properties();
    
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(propertyFile.toFile()), StandardCharsets.UTF_8))) {
      properties.load(in);
    } catch (IOException | SecurityException e) {
      logger.warn("error reading file: {1} exception: {2} ", propertyFile, e);
    }
    
    return properties;
  }
  
}
