package de.fw.devops.utils.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  
  static Logger logger = LogManager.getLogger(ProjectSnippetBuilder.class);
  private MavenProject mavenProject = new MavenProject();
  
  public static AbstractArtifactBuilder fromProperties(String path) {
    return new ProjectSnippetBuilder().properties(path);
  }
  
  @Override
  public void registerPojos() {
    pojoPropertiesParseUtil.registerPojo(new MavenProject(), "mavenproject", mavenProject);
  }
  
  @Override
  public String resolveNameForRessource(String templateName) {
    if (templateName.endsWith("_config.properties")) {
      return "config.properties";
    }
    if (templateName.endsWith("_deployment.properties")) {
      return "deployment.properties";
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
  public String resolveNameForTargetPath() {
    return mavenProject.getName();
  }
  
  private Pattern searchPattern = Pattern.compile("(.*)-(.*)(.properties)");
  
  @Override
  public String addPrefixPathForResource(String resorcePath) {
    try {
      if (!Files.exists(Paths.get(targetPath + "/src/main/resources/dev"))) {
        Files.createDirectories(Paths.get(targetPath + "/src/main/resources/dev"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    if (resorcePath.endsWith("deployment.properties")) {
      return "src/main/resources/dev/" + resorcePath;
    }
    if (resorcePath.contains("placeholder-replacement-config")) {
      return "src/main/resources" + resorcePath;
    }
    
    Matcher matcher = searchPattern.matcher(resorcePath);
    if (matcher.find()) {
      return "src/main/resources/dev/" + resorcePath;
    }
    
    return resorcePath;
  }
  
  @Override
  public String resolveTargetPathSuffix() {
    return pojoPropertiesParseUtil.getProperties().getProperty("targetPathSuffix");
  }
  
}
