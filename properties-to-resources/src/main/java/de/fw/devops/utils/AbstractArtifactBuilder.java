package de.fw.devops.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author N0009271
 */
public abstract class AbstractArtifactBuilder extends SimpleFileVisitor<Path>{
  
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
  
  private FreemarkerTemplateProcessor generator = null;
  
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
   * @return name for resolveFinalPathSuffix can be null and blank
   */
  public abstract String resolveFinalPathSuffix();
  
  /**
   * @return path kann be blank but not null
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
      logger.error("propertiesPath: '{}' exists: {}", propertiesPath, Files.exists(propertiesPath));
    }
  }
  
  /**
   * @throws IOException
   */
  protected void processProperties() throws IOException {
    logger.info("with configuration: {}", this);
    pojoPropertiesParseUtil = PropertiesToPojosParseUtil.properties(ApacheCommonsConfigReader.fromProperties(propertiesPath).process());
    registerPojos();
    parseToPojos();
    
    logger.debug("parsed dataModel: {}", getDataModel());
    
    String targetPathSuffix = resolveTargetPathSuffix();
    
    //  Example:               target                        ew                                  SVISAktuelles
    //  targetPath = Paths.get(targetPath + File.separator + targetPathSuffix + File.separator + resolveFinalPathSuffix());
    if (targetPathSuffix != null) {
      targetPath = concatPaths(targetPath.toString(), targetPathSuffix, resolveFinalPathSuffix());
      createTargetPath();
      generateForAllTemplates();
    } else {
      logger.warn("skip deployment properties because no targetPathSuffix resolved {} ", propertiesPath.getFileName());
    }
  }


  protected Path concatPaths(String ... paths) {
    List<String> notNullAndBlank = new ArrayList<>();
    for(String s: paths) {
      if(s!=null && !s.isBlank()) {
        notNullAndBlank.add(s);
      }
    }
    return Paths.get(String.join(File.separator, notNullAndBlank));
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
	generator = new FreemarkerTemplateProcessor().targetPath(targetPath)
		        .interpolationSyntax(Integer.valueOf(pojoPropertiesParseUtil.getProperties()
		            .getProperty(FreemarkerTemplateProcessor.INTERPOLATION_SYNTAX, String.valueOf(freemarker.template.Configuration.LEGACY_INTERPOLATION_SYNTAX)))).templatePath(templatePath);//templatePath immer nach dem Setzen von Interpolation, wegen sofortigem init()!;
	Files.walkFileTree(templatePath, this);
  }
  
  @Override
  public FileVisitResult visitFile(Path template, BasicFileAttributes attrs) throws IOException {
    String filename = template.getFileName().toString();
    if(filename.startsWith("template")) {
        logger.info("process template: {}", template);
            if(template.subpath(templatePath.getNameCount(), template.getNameCount()).getNameCount()>1) {//wenn von einem Template der "templatePath" abgezogen kann und es bleibt ein Teil übrig. Dann soll das an targetPath angehängt werden!   
            	Path prefixForTarget = template.subpath(templatePath.getNameCount(), template.getNameCount());
            	logger.debug("prefix fuer target: {}",prefixForTarget);
            	generator.templatePath(template.getParent());
            	logger.debug("parent of prefixForTarget: {}",prefixForTarget.getParent());
                if (!Files.exists(Paths.get(targetPath+ File.separator +prefixForTarget.getParent()))) {
                    Files.createDirectories(Paths.get(targetPath+ File.separator +prefixForTarget.getParent()));
                }
                generator.generate(getDataModel(), template.getFileName().toString(),
                		prefixForTarget.getParent()+ File.separator +resolveNameForRessource(template.getFileName().toString()));
            }else{
            	if(!templatePath.equals(generator.getTemplatePath())) {
            		generator.templatePath(templatePath);
            	}
                generator.generate(getDataModel(), template.getFileName().toString(),
                        addPrefixPathForResource(resolveNameForRessource(template.getFileName().toString())));
            }

    }
    return FileVisitResult.CONTINUE;
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
	return "AbstractArtifactBuilder [propertiesPath=" + propertiesPath + ", templatePath=" + templatePath
				+ ", targetPath=" + targetPath + ", pojoPropertiesParseUtil=" + pojoPropertiesParseUtil + ", generator="
				+ generator + "]";
  }
}