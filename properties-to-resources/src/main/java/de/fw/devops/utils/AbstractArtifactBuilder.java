package de.fw.devops.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Felix Werner
 */
public abstract class AbstractArtifactBuilder {
  
  static Logger logger = LogManager.getLogger(AbstractArtifactBuilder.class);
  /**
   * Path to properties file(s)
   */
  protected Path propertiesPath;
  /**
   * Path to dir of freemarker templates
   */
  protected Path templatePath = Paths.get("templates");
  /**
   * Path to output
   */
  protected Path targetPath = Paths.get("output");
  /**
   * util parsing properties to registered pojos
   */
  protected PropertiesToPojosParseUtil pojoPropertiesParseUtil = null;
  
  /**
   * 
   */
  public AbstractArtifactBuilder() {
  }
  
  /**
   * 
   */
  public abstract void registerPojos();
  
  /**
   * 
   */
  public void parseToPojos() {
    pojoPropertiesParseUtil.parseToPojos();
  }
  
  /**
   * @param templateName
   * @return name for Template
   */
  public abstract String resolveNameForRessource(String templateName);
  
  /**
   * @return name for TargetPath
   */
  public abstract String resolveNameForTargetPath();
  
  /**
   * @return path
   */
  public abstract String resolveTargetPathSuffix();
  
  /**
   * @param path
   * @return this
   */
  public AbstractArtifactBuilder properties(String path) {
    propertiesPath = Paths.get(path);
    return this;
  }
  
  /**
   * @param path
   * @return this
   */
  public AbstractArtifactBuilder templatePath(String path) {
    templatePath = Paths.get(path);
    return this;
  }
  
  /**
   * @param path
   * @return this
   */
  public AbstractArtifactBuilder targetPath(String path) {
    targetPath = Paths.get(path);
    return this;
  }
  
  /**
   * @throws IOException
   */
  public void process() throws IOException {
    if (Files.exists(propertiesPath) && Files.isDirectory(propertiesPath)) {
      processPropertiesDir();
    } else if (Files.exists(propertiesPath) && !Files.isDirectory(propertiesPath)) {
      processProperties();
    } else {
      logger.error("propertiesPath exists: {}", Files.exists(propertiesPath));
    }
  }
  
  /**
   * @throws IOException
   */
  protected void processProperties() throws IOException {
    logger.info("config: {}", this);
    pojoPropertiesParseUtil = PropertiesToPojosParseUtil.properties(ApacheCommonsConfigReader.fromProperties(propertiesPath).process());
    registerPojos();
    parseToPojos();
    
    logger.debug("parsed dataModel: {}", getDataModel());
    
    String targetSuffix = resolveTargetPathSuffix();
    
    if (targetSuffix != null) {
      targetPath = Paths.get(targetPath + File.separator + targetSuffix + File.separator + resolveNameForTargetPath());
      createTargetPath();
      generateForAllTemplates();
    } else {
      logger.warn("skip deployment properties because no targetPathSuffix resolved {} ", propertiesPath.getFileName());
    }
  }
  
  /**
   * @return Map<String, Object>
   */
  public Map<String, Object> getDataModel() {
    return pojoPropertiesParseUtil.getDataModel();
  }
  
  /**
   * @throws IOException
   */
  protected void processPropertiesDir() throws IOException {
    try (Stream<Path> stream = Files.list(propertiesPath)) {
      List<Path> deploymentProperties = stream.filter(c -> c.getFileName().toString().endsWith(".properties")).collect(Collectors.toList());
      for (Path depl : deploymentProperties) {
        ((AbstractArtifactBuilder) PropertiesToPojosParseUtil.createObject(this.getClass().getName())).properties(depl.toString())
            .templatePath(templatePath.toString())
            .targetPath(targetPath.toString())
            .processProperties();
      }
    }
  }
  
  /**
   * @throws IOException
   */
  protected void createTargetPath() throws IOException {
    if (!Files.exists(targetPath)) {
      Files.createDirectories(targetPath);
    }
  }
  
  /**
   * @throws IOException
   */
  protected void generateForAllTemplates() throws IOException {
    FreemarkerTemplateProcessor generator = new FreemarkerTemplateProcessor().targetPath(targetPath).templatePath(templatePath)
        .interpolationSyntax(Integer.valueOf(pojoPropertiesParseUtil.getProperties().getProperty(FreemarkerTemplateProcessor.INTERPOLATION_SYNTAX,
            String.valueOf(freemarker.template.Configuration.LEGACY_INTERPOLATION_SYNTAX))));
    try (Stream<Path> stream = Files.list(templatePath)) {
      List<Path> templates = stream.filter(c -> c.getFileName().toString().startsWith("template")).collect(Collectors.toList());
      for (Path template : templates) {
        logger.info("process template: {}", template);
        generator.generate(getDataModel(), template.getFileName().toString(),
            addPrefixPathForResource(resolveNameForRessource(template.getFileName().toString())));
      }
    }
  }
  
  /**
   * @param resourcePath
   * @return prefixPath+resourcePath
   */
  public String addPrefixPathForResource(String resourcePath) {
    return resourcePath;
  }
  
  /**
   * @param className
   * @return AbstractArtifactBuilder
   */
  public static AbstractArtifactBuilder createArtifactBuilder(String className) {
    return (AbstractArtifactBuilder) PropertiesToPojosParseUtil.createObject(className);
  }
  
  /**
   * @param templatesFolder
   * @return AbstractArtifactBuilder
   * @throws IOException
   */
  public static AbstractArtifactBuilder fromTemplatesFolder(String templatesFolder) throws IOException {
    return createArtifactBuilder(resolveClassnameFromTemplatesFolder(templatesFolder));
  }
  
  private static String resolveClassnameFromTemplatesFolder(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path + File.separator + "datamodelProvider.properties")));
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AbstractArtifactBuilder [propertiesPath=");
    builder.append(propertiesPath);
    builder.append(", templatePath=");
    builder.append(templatePath);
    builder.append(", targetPath=");
    builder.append(targetPath);
    builder.append("]");
    return builder.toString();
  }
  
}