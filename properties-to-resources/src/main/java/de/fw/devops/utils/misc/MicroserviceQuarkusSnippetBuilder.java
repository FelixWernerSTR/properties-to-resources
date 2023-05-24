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
public class MicroserviceQuarkusSnippetBuilder extends AbstractArtifactBuilder {
  
  private static Logger logger = LogManager.getLogger(MicroserviceQuarkusSnippetBuilder.class);
  private static Pattern configJavaClasses = Pattern.compile("(JsonbConfiguration|Constants|LocalDateProvider)(.java)");
  private static Pattern errorsJavaClasses = Pattern.compile("(BadRequestAlertException|ConstraintViolationExceptionMapper|ErrorConstants|FieldErrorVM)(.java)");
  private static Pattern vmJavaClasses = Pattern.compile("(ConfigPropsVM|EnvVM|LoggerVM|PageRequestVM|SortRequestVM)(.java)");
  private static Pattern utilJavaClasses = Pattern.compile("(HeaderUtil|PaginationUtil|ResponseUtil)(.java)");
  private static Pattern templateJavaClasses = Pattern.compile("(template_FieldErrorVM|template_ErrorConstants|"
  		+ "template_ConstraintViolationExceptionMapper|template_BadRequestAlertException|template_Paged|template_ManagementInfoService|"
  		+ "template_ManagementInfoDTO|template_LocalDateProviderTest|template_Constants|template_JsonbConfiguration|template_LocalDateProvider|"
  		+ "template_AuthoritiesConstants|template_ConfigPropsVM|template_EnvVM|template_LoggerVM|template_PageRequestVM|"
  		+ "template_SortRequestVM|template_HeaderUtil|template_PaginationUtil|template_ResponseUtil|template_AccountResource|template_ManagementInfoResource)(.java)");
  
  private MavenProject mavenProject = new MavenProject();
  
  /**
   * @param path
   * @return AbstractArtifactBuilder
   */
  public static AbstractArtifactBuilder fromProperties(String path) {
    return new MicroserviceQuarkusSnippetBuilder().properties(path);
  }
  
  @Override
  public void registerPojos() {
    pojoPropertiesParseUtil.registerPojo(new MavenProject(), "mavenproject", mavenProject);
  }
  
  @Override
  public String resolveNameForRessource(String templateName) {
	if (templateName.endsWith("_Readme.txt")) {
	  return "Readme.txt";
	}
    if (templateName.endsWith("_checkstyle.xml")) {
      return "checkstyle.xml";
    }
    if (templateName.endsWith("_application.properties")) {
        return "application.properties";
    }
    if (templateName.endsWith("_sonar-project.properties")) {
      return "sonar-project.properties";
    }
    if (templateName.endsWith("_pom.xml")) {
      return "pom.xml";
    }
    if (templateName.endsWith("_startQuarkusRun.cmd")) {
      return "startQuarkusRun.cmd";
    }
    if (templateName.endsWith("_startCmdHere.cmd")) {
        return "startCmdHere.cmd";
    }
    if (templateName.endsWith("_PropertiesMock.java")) {
        return mavenProject.getEntityName()+"PropertiesMock.java";
    }
    if (templateName.equals("template_EntityTest.java")) {
        return mavenProject.getEntityName()+"Test.java";
    }
    if (templateName.equals("template_ManagementInfoResourceTest.java")) {
        return "ManagementInfoResourceTest.java";
    }
    if (templateName.equals("template_ArchTest.java")) {
        return "ArchTest.java";
    }
    if (templateName.equals("template_TestResources.java")) {
        return "TestResources.java";
    }
    if (templateName.equals("template_TestUtil.java")) {
        return "TestUtil.java";
    }
    if (templateName.equals("template_EntityResourceIT.java")) {
        return mavenProject.getEntityName()+"ResourceIT.java";
    }
    if (templateName.equals("template_messages_de.properties")) {
        return "messages_de.properties";
    }
    if (templateName.equals("template_messages_en.properties")) {
        return "messages_en.properties";
    }
    if (templateName.equals("template_master.xml")) {
        return "master.xml";
    }
    if (templateName.equals("template_entity.csv")) {
        return mavenProject.getEntityName().toLowerCase()+".csv";
    }
    if (templateName.equals("template_blob.txt")) {
        return "blob.txt";
    }
    if (templateName.equals("template_00000000000000_initial_schema.xml")) {
        return "00000000000000_initial_schema.xml";
    }
    if (templateName.equals("template_20230308095426_added_entity_entityName.xml")) {
        return "20230308095426_added_entity_"+mavenProject.getEntityName()+".xml";
    }
    if (templateName.equals("template_default_banner.txt")) {
        return "default_banner.txt";
    }
    if (templateName.equals("template_EntityCompatibleImplicitNamingStrategy.java")) {
        return mavenProject.getEntityName()+"CompatibleImplicitNamingStrategy.java";
    }
    if (templateName.equals("template_EntityCompatiblePhysicalNamingStrategy.java")) {
        return mavenProject.getEntityName()+"CompatiblePhysicalNamingStrategy.java";
    }
    if (templateName.equals("template_EntityProperties.java")) {
        return mavenProject.getEntityName()+"Properties.java";
    }
    if (templateName.equals("template_Entity.java")) {
        return mavenProject.getEntityName()+".java";
    }
    if (templateName.equals("template_EntityService.java")) {
        return mavenProject.getEntityName()+"Service.java";
    }
    if (templateName.equals("template_ErrorConstants.java")) {
        return "ErrorConstants.java";
    }
    if (templateName.equals("template_EntityManageConfigurationEndpoint.java")) {
    	 return mavenProject.getEntityName()+"ManageConfigurationEndpoint.java";
    }
    if (templateName.equals("template_EntityResource.java")) {
   	 return mavenProject.getEntityName()+"Resource.java";
    }
    if (templateName.equals("template_EntityManagerLoggersEndpoint.java")) {
   	 return mavenProject.getEntityName()+"ManagerLoggersEndpoint.java";
    }

    
    Matcher matcher = templateJavaClasses.matcher(templateName);
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
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/config/hibernate"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/config/hibernate"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/domain"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/domain"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/security"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/security"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/service/dto"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/service/dto"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/errors"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/errors"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/vm"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/vm"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/util"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/main/java/"+toPath(mavenProject.getGroupId())+"/web/util"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/config/mock"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/config/mock"));
      }
      if (!Files.exists(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/domain"))) {
          Files.createDirectories(Paths.get(targetPath + "/src/test/java/"+toPath(mavenProject.getGroupId())+"/domain"));
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.warn("exception by addPrefixPathForResource", e);
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+"Test.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId()) +"/domain/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+"PropertiesMock.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId())+"/config/mock/" + resourcePath;
    }
    if (resourcePath.equals("LocalDateProviderTest.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId())+"/config/" + resourcePath;
    }
    if (resourcePath.equals("ManagementInfoResourceTest.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest/" + resourcePath;
    }
    if (resourcePath.equals(mavenProject.getEntityName()+"ResourceIT.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId())+"/web/rest/" + resourcePath;
    }
    if (resourcePath.equals("ArchTest.java") | resourcePath.equals("TestUtil.java") | resourcePath.equals("TestResources.java")) {
        return "src/test/java/"+toPath(mavenProject.getGroupId())+"/"+ resourcePath;
    }
    
    if (resourcePath.endsWith("CompatibleImplicitNamingStrategy.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/config/hibernate/" + resourcePath;
    }
    if (resourcePath.endsWith("CompatiblePhysicalNamingStrategy.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/config/hibernate/" + resourcePath;
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
    if (resourcePath.endsWith(mavenProject.getEntityName()+"Properties.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/config/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+".java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/domain/" + resourcePath;
    }
    if (resourcePath.endsWith("AuthoritiesConstants.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/security/" + resourcePath;
    }
    if (resourcePath.endsWith("ManagementInfoDTO.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/service/dto/" + resourcePath;
    }
    if (resourcePath.endsWith("ManagementInfoService.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/service/" + resourcePath;
    }
    if (resourcePath.endsWith("Paged.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/service/" + resourcePath;
    }
    if (resourcePath.endsWith(mavenProject.getEntityName()+"Service.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/service/" + resourcePath;
    }
    if (resourcePath.endsWith("ErrorConstants.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/errors/" + resourcePath;
    }
    if (resourcePath.endsWith("AccountResource.java") || resourcePath.endsWith("ManagementInfoResource.java") ||
    		resourcePath.endsWith("ManageConfigurationEndpoint.java") || resourcePath.endsWith("ManagerLoggersEndpoint.java") || 
    		resourcePath.endsWith("Resource.java")) {
        return "src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/" + resourcePath;
    }
    
    Matcher matcher = configJavaClasses.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/java/"+toPath(mavenProject.getGroupId())+"/config/" + resourcePath;
    }
    
    matcher = errorsJavaClasses.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/errors/" + resourcePath;
    }
    matcher = vmJavaClasses.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/java/"+toPath(mavenProject.getGroupId())+"/web/rest/vm/" + resourcePath;
    }
    matcher = utilJavaClasses.matcher(resourcePath);
    if (matcher.find()) {
      return "src/main/java/"+toPath(mavenProject.getGroupId())+"/web/util/" + resourcePath;
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
