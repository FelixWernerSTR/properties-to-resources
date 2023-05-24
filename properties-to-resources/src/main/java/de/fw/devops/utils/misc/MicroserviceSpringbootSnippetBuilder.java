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
 * @author N0009271
 */
public class MicroserviceSpringbootSnippetBuilder extends AbstractArtifactBuilder {
  
  private static Logger logger = LogManager.getLogger(MicroserviceSpringbootSnippetBuilder.class);
  private static Pattern configJavaClasses = Pattern.compile("(ApplicationProperties|Constants|CRLFLogConverter|DateTimeFormatConfiguration|LocaleConfiguration|SecurityConfiguration|WebConfigurer|IntegrationTest)(.java)");
  private static Pattern templateConfigJavaClasses = Pattern.compile("(template_ApplicationProperties|template_Constants|template_CRLFLogConverter|template_DateTimeFormatConfiguration|template_LocaleConfiguration|template_SecurityConfiguration|template_WebConfigurer|template_IntegrationTest)(.java)");
  private MavenProject mavenProject = new MavenProject();
  
  /**
   * @param path
   * @return AbstractArtifactBuilder
   */
  public static AbstractArtifactBuilder fromProperties(String path) {
    return new MicroserviceSpringbootSnippetBuilder().properties(path);
  }
  
  @Override
  public void registerPojos() {
    pojoPropertiesParseUtil.registerPojo(new MavenProject(), "mavenproject", mavenProject);
  }
  
  @Override
  public String resolveNameForRessource(String templateName) {
	if (templateName.endsWith("_application.properties")) {
	  return "application.properties";
	}
    if (templateName.endsWith("_application-prod.properties")) {
      return "application-prod.properties";
    }
    if (templateName.endsWith("_application-prod.properties.ori")) {
        return "application-prod.properties.ori";
    }
    if (templateName.endsWith("_sonar-project.properties")) {
      return "sonar-project.properties";
    }
    if (templateName.endsWith("_pom.xml")) {
      return "pom.xml";
    }
    if (templateName.endsWith("_startSpringBootApp.cmd")) {
      return "startSpringBootApp.cmd";
    }
    if (templateName.endsWith("_startCmdHere.cmd")) {
        return "startCmdHere.cmd";
    }
    if (templateName.endsWith("ApplicationWebXml.java")) {
        return "ApplicationWebXml.java";
    }
    if (templateName.endsWith("_App.java")) {
        return mavenProject.getArtifactId()+"App.java";
    }
    if (templateName.equals("template_Entity.java")) {
        return mavenProject.getEntityName()+".java";
    }
    if (templateName.equals("template_Service.java")) {
        return mavenProject.getEntityName()+"Service.java";
    }
    if (templateName.equals("template_Resource.java")) {
        return mavenProject.getEntityName()+"Resource.java";
    }
    if (templateName.equals("template_ResourceIT.java")) {
        return mavenProject.getEntityName()+"ResourceIT.java";
    }
    if (templateName.equals("template_messages.properties")) {
        return "messages.properties";
    }
    if (templateName.equals("template_messages_en.properties")) {
        return "messages_en.properties";
    }
    if (templateName.equals("template_index.html")) {
        return "index.html";
    }
    if (templateName.equals("template_error.html")) {
        return "error.html";
    }
    if (templateName.equals("template_junit-platform.properties")) {
        return "junit-platform.properties";
    }
    if (templateName.equals("template_logback.xml")) {
        return "logback.xml";
    }
    if (templateName.equals("template_logback-spring.xml")) {
        return "logback-spring.xml";
    }
    if (templateName.equals("template_banner.txt")) {
        return "banner.txt";
    }
    if (templateName.equals("template_testcontainers.properties")) {
        return "testcontainers.properties";
    }
    if (templateName.equals("template_test_application.properties")) {
        return "application.properties";
    }
    if (templateName.equals("template_application-testdev.properties")) {
        return "application-testdev.properties";
    }
    if (templateName.equals("template_application-testprod.properties")) {
        return "application-testprod.properties";
    }
    
    Matcher matcher = templateConfigJavaClasses.matcher(templateName);
    if (matcher.find()) {
    	return templateName.replaceFirst("template_", "");
    }
    
    return templateName.replaceFirst("template_", mavenProject.getName());
  }
  
  @Override
  public String resolveFinalPathSuffix() {
    return mavenProject.getName();
  }
  
  @Override
  public String addPrefixPathForResource(String resourcePath) {
    try {
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/config"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/config"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/domain"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/domain"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/service"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/service"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest"));
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.warn("exception by addPrefixPathForResource", e);
    }

    if (resourcePath.endsWith("ApplicationWebXml.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getArtifactId()+"App.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+".java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/domain/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+"Service.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/service/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+"Resource.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/" + resourcePath;
    }
    if (resourcePath.endsWith("IntegrationTest.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId()) +"/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+"ResourceIT.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest/" + resourcePath;
    }
    
    Matcher matcher = configJavaClasses.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/java/"+toPath(mavenProject.getGroupId())+"/config/" + resourcePath;
    }
    
    return resourcePath;
  }
  
  private String toPath(String groupId) {
	return groupId.replaceAll("\\.", "/");
  }

@Override
  public String resolveTargetPathSuffix() {
    return pojoPropertiesParseUtil.getProperties().getProperty("targetPathSuffix");
  }
  
}
