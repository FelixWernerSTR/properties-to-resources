package de.fw.devops.utils.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fw.devops.utils.AbstractArtifactBuilder;


/**
 * 
 * @author Felix Werner
 */
public class ProjectSnippetBuilder extends AbstractArtifactBuilder {
  
  private static Logger logger = LogManager.getLogger(ProjectSnippetBuilder.class);
  private static Pattern searchPatternDev = Pattern.compile("(.*)-(dev)(.properties)");
  private static Pattern searchPatternPatch = Pattern.compile("(.*)-(preprod)(.properties)");
  private static Pattern searchPatternProd = Pattern.compile("(.*)-(prod)(.properties)");
  private MavenProject mavenProject = new MavenProject();
  
  /**
   * @param path
   * @return AbstractArtifactBuilder
   */
  public static AbstractArtifactBuilder fromProperties(String path) {
    return new ProjectSnippetBuilder().properties(path);
  }
  
  @Override
  public void registerPojos() {
    pojoPropertiesParseUtil.registerPojo(new MavenProject(), "mavenproject", mavenProject);
    pojoPropertiesParseUtil.registerPojo(new Dependency(), "dependencies", new HashMap<String, Dependency>());
  }
  
  @Override
  public String resolveNameForRessource(String templateName) {
    if (templateName.endsWith("_config.properties")) {
      return "config.properties";
    }
    if (templateName.endsWith("_deployment.properties")) {
      return "deployment.properties";
    }
    if (templateName.endsWith("_stagePathMapping.properties")) {
      return "stagePathMapping.properties";
    }
    if (templateName.endsWith("_placeholder-replacement-config.properties")) {
      return "placeholder-replacement-config.properties";
    }
    if (templateName.endsWith("_dot_project")) {
      return ".project";
    }
    if (templateName.endsWith("_pom.xml")) {
      return "pom.xml";
    }
    if (templateName.endsWith("_dot_classpath")) {
      return ".classpath";
    }
    
    return templateName.replaceFirst("template", mavenProject.getName());
  }
  
  @Override
  public String resolveFinalPathSuffix() {
    return mavenProject.getName();
  }
  
  @Override
  public String addPrefixPathForResource(String resourcePath) {
    try {
      if (!Files.exists(Paths.get(targetPath + "/src/main/resources/openshift/dev"))) {
        Files.createDirectories(Paths.get(targetPath + "/src/main/resources/openshift/dev"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/resources/openshift/patch"))) {
        Files.createDirectories(Paths.get(targetPath + "/src/main/resources/openshift/patch"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/resources/openshift/prod"))) {
        Files.createDirectories(Paths.get(targetPath + "/src/main/resources/openshift/prod"));
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.warn("exception by addPrefixPathForResource", e);
    }
    
    if (resourcePath.endsWith("deployment.properties")) {
      return "src/main/resources/openshift/" + resourcePath;
    }
    if (resourcePath.endsWith("stagePathMapping.properties")) {
      return "src/main/resources/openshift/" + resourcePath;
    }
    if (resourcePath.contains("placeholder-replacement-config")) {
      return "src/main/resources/" + resourcePath;
    }
    
    Matcher matcher = searchPatternDev.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/resources/openshift/dev/" + resourcePath;
    }
    matcher = searchPatternPatch.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/resources/openshift/patch/" + resourcePath;
    }
    matcher = searchPatternProd.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/resources/openshift/prod/" + resourcePath;
    }
    
    return resourcePath;
  }
  
  @Override
  public String resolveTargetPathSuffix() {
    return pojoPropertiesParseUtil.getProperties().getProperty("targetPathSuffix");
  }
  
}
