package de.fw.devops.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * erzeugt beliebige Artefakte fuer Pojos aus DataModel(Map<String, Object>) oder StringList(java.util.List<String>) nach targetPath fuer existierende Templates
 * in templatePath.
 * 
 * Siehe Methoden: generate(...)
 * 
 * Info zur Templateerstellung: https://freemarker.apache.org/
 * 
 * 
 * @author N0009271
 *
 */
public class FreemarkerTemplateProcessor {
  private static Logger logger = LogManager.getLogger(FreemarkerTemplateProcessor.class);
  
  private Configuration freemarkerConfiguration;
  private Path templatePath;
  private Path targetPath;
  
  public static final String INTERPOLATION_SYNTAX = "interpolationSyntax";
  private int interpolationSyntax = Configuration.LEGACY_INTERPOLATION_SYNTAX;
  
  /**
   * @param listName
   * @param stringList
   * @param templateFile
   * @param outputFile
   */
  public void generate(String listName, List<String> stringList, String templateFile, String outputFile) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(listName, stringList);
    generate(dataModel, templateFile, outputFile);
  }
  
  /**
   * @param dataModel
   * @param templateFile
   * @param outputFile
   */
  public void generate(Map<String, Object> dataModel, String templateFile, String outputFile) {
    logger.debug("generate artifacts...");
    Template freemarkerTemplate = getFreemarkerTemplate(templateFile);
    Path path = resolvePath(outputFile);
    processDataModel(dataModel, freemarkerTemplate, path);
    logger.debug("generating: {} finished.", path.getFileName());
  }
  
  /**
   * @param dataModel
   * @param freemarkerTemplate
   * @param path
   */
  private void processDataModel(Map<String, Object> dataModel, Template freemarkerTemplate, Path path) {
    try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName(StandardCharsets.UTF_8.toString()))) {
      freemarkerTemplate.process(dataModel, writer);
    } catch (IOException | TemplateException e) {
      logger.error("exception occured : {}", e);
      throw new RuntimeException(e);
    }
  }
  
  private Path resolvePath(String outputFile) {
    Path path = null;
    if (targetPath != null) {
      path = Paths.get(targetPath + File.separator + outputFile);
    } else {
      path = Paths.get(outputFile);// then outpuFile is absolute
    }
    logger.debug("to path: {}", path);
    return path;
  }
  
  private Template getFreemarkerTemplate(String templateFile) {
    if (freemarkerConfiguration == null) {
      init();
    }
    /* Get the template (uses cache internally) */
    try {
      return freemarkerConfiguration.getTemplate(templateFile);
    } catch (IOException e) {
    	e.printStackTrace();
      logger.warn("with configuration: {} ",this);
      logger.warn("exception occured : {}", e);
      logger.warn("generation of artifact not possible!");
      throw new RuntimeException();
    }
  }
  
  /**
   * 
   */
  protected void init() {
    /* Create and adjust the configuration singleton */
    freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_32);
    try {
      freemarkerConfiguration.setDirectoryForTemplateLoading(templatePath.toFile());
      freemarkerConfiguration.setDefaultEncoding(StandardCharsets.UTF_8.toString());
      freemarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      freemarkerConfiguration.setLogTemplateExceptions(true);
      freemarkerConfiguration.setWrapUncheckedExceptions(true);
      freemarkerConfiguration.setInterpolationSyntax(interpolationSyntax);
      logger.debug("with configuration: {} ",this);
    } catch (IOException e) {
      logger.error("error loading file {})", templatePath);
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param path
   * @return this
   */
  public FreemarkerTemplateProcessor templatePath(Path path) {
    templatePath = path;
    init();
    return this;
  }
  
  /**
   * @param path
   * @return this
   */
  public FreemarkerTemplateProcessor targetPath(Path path) {
    targetPath = path;
    return this;
  }
  
  /**
   * @param interpolationSyntax the interpolationSyntax to set
   * @return this
   */
  public FreemarkerTemplateProcessor interpolationSyntax(int interpolationSyntax) {
    this.interpolationSyntax = interpolationSyntax;
    return this;
  }

  public Path getTemplatePath() {
	return templatePath;
  }

  public Path getTargetPath() {
	return targetPath;
  }

  @Override
  public String toString() {
	return "FreemarkerTemplateProcessor [freemarkerConfiguration=" + freemarkerConfiguration + ", templatePath="
				+ templatePath + ", targetPath=" + targetPath + ", interpolationSyntax=" + interpolationSyntax + "]";
  }
  
}
