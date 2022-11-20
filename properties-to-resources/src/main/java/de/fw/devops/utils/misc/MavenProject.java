package de.fw.devops.utils.misc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO für MavenProject um Snippets wie pom.xml, .project und beliebig strukturierte Artefakte für ein Maven-Projekt zu erzeugen. einfach ein
 * "template_einSnippet.file-extension" im template-Verzeichnis zur Verfügung stellen.
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class MavenProject {
  
  String name;
  String groupId;
  String artifactId;
  String version;
  String property1;
  String property2;
  String property3;
  
  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }
  
  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * @return the groupId
   */
  public String getGroupId() {
    return groupId;
  }
  
  /**
   * @param groupId the grouptId to set
   */
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
  
  /**
   * @return the artifactId
   */
  public String getArtifactId() {
    return artifactId;
  }
  
  /**
   * @param artifactId the artifactId to set
   */
  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }
  
  /**
   * @return the property1
   */
  public String getProperty1() {
    return property1;
  }
  
  /**
   * @param property1 the property1 to set
   */
  public void setProperty1(String property1) {
    this.property1 = property1;
  }
  
  /**
   * @return the property2
   */
  public String getProperty2() {
    return property2;
  }
  
  /**
   * @param property2 the property2 to set
   */
  public void setProperty2(String property2) {
    this.property2 = property2;
  }
  
  /**
   * @return the property3
   */
  public String getProperty3() {
    return property3;
  }
  
  /**
   * @param property3 the property3 to set
   */
  public void setProperty3(String property3) {
    this.property3 = property3;
  }
  
  public static List<String> getRegex() {
    
    Class<MavenProject> aClass = MavenProject.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(MavenProject.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE_SINGLE_POJO + field.getName());
    }
    return fieldList;
  }
}
