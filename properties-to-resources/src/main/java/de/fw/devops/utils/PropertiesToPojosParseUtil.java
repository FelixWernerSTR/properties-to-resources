package de.fw.devops.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Helper-Util um POJOS beim Einlesen von Properties zu erzeugen und zu aktualisieren.
 * 
 * @author Felix Werner
 *
 */
public class PropertiesToPojosParseUtil {
  private static Logger logger = LogManager.getLogger(PropertiesToPojosParseUtil.class);
  
  @SuppressWarnings("javadoc")
  public static final String REGEX_PATTERN_CORE = "[.](.+)[.]";
  
  @SuppressWarnings("javadoc")
  public static final String REGEX_PATTERN_CORE_SINGLE_POJO = "[.]";
  
  private Properties properties;
  private Map<String, Object> pojos = null;
  private Map<String, String> pojoTypeKeyMapping = null;
  
  /**
   * factoryMethod
   * 
   * @param properties
   * @return PojoPropertiesParseUtil
   */
  public static PropertiesToPojosParseUtil properties(Properties properties) {
    return new PropertiesToPojosParseUtil(properties);
  }
  
  /**
   * Konstruktor
   */
  private PropertiesToPojosParseUtil(Properties properties) {
    this.properties = properties;
    pojos = new HashMap<>();
    pojoTypeKeyMapping = new HashMap<>();
  }
  
  /**
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }
  
  /**
   * @param <T>
   * @param pojoType
   * @param key
   * @param pojo
   */
  public <T> void registerPojo(T pojoType, String key, Object pojo) {
    pojos.put(key, pojo);
    pojoTypeKeyMapping.put(pojoType.getClass().getName(), key);
  }
  
  /**
   */
  public void parseToPojos() {
    for (Object keyObject : properties.keySet()) {
      String key = (String) keyObject;
      for (Entry<String, String> entry : pojoTypeKeyMapping.entrySet()) {
        String className = entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1, entry.getKey().length());
        if (key.startsWith(className.toLowerCase())) {
          if (pojos.get(entry.getValue()) instanceof Map) {
            Object object = createObject(entry.getKey());
            PropertiesToPojosParseUtil.parsePropertiesKey((Map) pojos.get(entry.getValue()), object, key, properties);
          } else {
            PropertiesToPojosParseUtil.parsePropertiesKeyForSinglePojo(pojos.get(entry.getValue()), key, properties);
          }
        }
      }
    }
  }
  
  /**
   * @param className
   * @return object
   */
  public static Object createObject(String className) {
    Object object = new Object();
    try {
      Class<?> clazz = Class.forName(className);
      Constructor<?> ctor;
      ctor = clazz.getConstructor();
      object = ctor.newInstance();
    } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      e.printStackTrace();
      logger.error("error createObject : {0})", e);
      throw new RuntimeException(e);
    }
    return object;
  }
  
  /**
   * delivers DataModel for FreemarkerTemplateProcessor
   * 
   * @return Map<String, Object>
   */
  public Map<String, Object> getDataModel() {
    Map<String, Object> dataModel = new HashMap<>();
    for (Entry<String, Object> entry : pojos.entrySet()) {
      if (entry.getValue() instanceof Map) {
        List<?> asList = new ArrayList<>();
        asList.addAll(((Map) entry.getValue()).values());
        dataModel.put(entry.getKey(), asList);
      } else {
        dataModel.put(entry.getKey(), entry.getValue());
      }
    }
    return dataModel;
  }
  
  /**
   * @param <T>
   * @param map
   * @param type
   * @param key
   * @param pojoProperties
   */
  public static <T> void parsePropertiesKey(Map<String, T> map, T type, String key, Properties pojoProperties) {
    List<String> regexList = getRegexFromType(type);
    for (String patternName : regexList) {
      String attributeName = patternName.substring(patternName.lastIndexOf(']') + 1, patternName.length());
      
      Pattern p = Pattern.compile(patternName);
      Matcher matcher = p.matcher(key);
      
      if (matcher.find()) {
        
        String uniqueName = matcher.group(1);
        if (key.endsWith(attributeName)) {
          modifyMap(map, type, key, pojoProperties, uniqueName, attributeName);
        }
      }
    }
  }
  
  /**
   * @param <T>
   * @param type
   * @return List<String> for fields of a type
   */
  @SuppressWarnings("unchecked")
  public static <T> List<String> getRegexFromType(T type) {
    List<String> regexList = null;
    try {
      Method method = Class.forName(type.getClass().getName()).getMethod("getRegex");
      regexList = (List<String>) method.invoke(type);
    } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
    return regexList;
  }
  
  /**
   * @param <T>
   * @param map
   * @param type
   * @param key
   * @param pojoProperties
   * @param name
   * @param attrName
   */
  private static <T> void modifyMap(Map<String, T> map, T type, String key, Properties pojoProperties, String name, String attrName) {
    attrName = attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    if (map.containsKey(name)) {
      try {
        
        Method method = Class.forName(type.getClass().getName()).getMethod("set" + attrName, String.class);
        method.invoke(map.get(name), pojoProperties.getProperty(key));
        
      } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        logger.warn("error modifyMap : {0})", e);
      }
    } else {
      try {
        Method method = Class.forName(type.getClass().getName()).getMethod("set" + attrName, String.class);
        method.invoke(type, pojoProperties.getProperty(key));
        
      } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        logger.warn("error modifyMap : {0})", e);
      }
      
      map.put(name, type);
    }
    
  }
  
  /**
   * @param <T>
   * @param type
   * @param key
   * @param properties
   */
  public static <T> void parsePropertiesKeyForSinglePojo(T type, String key, Properties properties) {
    List<String> patternNamesForFields = null;
    try {
      Method method = Class.forName(type.getClass().getName()).getMethod("getRegex");
      patternNamesForFields = (List<String>) method.invoke(type);
    } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      logger.warn("getRegex : {0})", e);
    }
    
    for (String patternName : patternNamesForFields) {
      String attributeName = patternName.substring(patternName.lastIndexOf(']') + 1, patternName.length());
      
      Pattern p = Pattern.compile(patternName);
      Matcher matcher = p.matcher(key);
      
      if (matcher.find() && key.endsWith(attributeName)) {
        modifySinglePojo(type, key, properties, attributeName);
      }
    }
  }
  
  /**
   * @param <T>
   * @param type
   * @param key
   * @param properties
   * @param attrName
   */
  private static <T> void modifySinglePojo(T type, String key, Properties properties, String attrName) {
    attrName = attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    try {
      Method method = Class.forName(type.getClass().getName()).getMethod("set" + attrName, String.class);
      method.invoke(type, properties.getProperty(key));
    } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      logger.warn("error modifyDeployment : {0})", e);
    }
    
  }
  
}
