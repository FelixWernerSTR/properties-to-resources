package de.fw.devops.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.fw.devops.utils.misc.ProjectSnippetBuilder;
import de.fw.devops.utils.AbstractArtifactBuilder;
import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * 
 * @author Felix Werner
 */
public class ProjectSnippetBuilderTest {
  
  /**
   * Demo: Generiert Snippets für ein MavenProjekt
   * 
   * @throws IOException
   */
  @Test
  public void testProcessProjectProperties() throws IOException {
    
    ProjectSnippetBuilder //
        .fromProperties("src/test/resources/snippets/datamodel/project.properties")// hier für ein Project-Model
        .templatePath("src/main/resources/templates/snippets_maven_openshift") //
        .targetPath("target/1") //
        .processProperties();
    
    assertThat(new String(Files.readAllBytes(Paths.get("target/1/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
  }
  
  /**
   * 
   * @throws IOException
   */
  @Test
  public void testProcessProjectPropertiesDir() throws IOException {
    
    ProjectSnippetBuilder //
        .fromProperties("src/test/resources/snippets/datamodel").templatePath("src/main/resources/templates/snippets_maven_openshift").targetPath("target/2") //
        .process();
    
    assertThat(new String(Files.readAllBytes(Paths.get("target/2/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
    assertThat(new String(Files.readAllBytes(Paths.get("target/2/snippets/myApp2/pom.xml")))).doesNotContain("[=mavenproject.name]");

  }
  
  /**
   * 
   * @throws IOException
   */
  @Test
  public void testProcessProjectPropertiesClassForName() throws IOException {
    
    AbstractArtifactBuilder artifactBuilder = (AbstractArtifactBuilder) PropertiesToPojosParseUtil.createObject("de.fw.devops.utils.misc.ProjectSnippetBuilder");
    
    artifactBuilder.properties("src/test/resources/snippets/datamodel").templatePath("src/main/resources/templates/snippets_maven_openshift").targetPath("target/3")
        .process();
    
    assertThat(new String(Files.readAllBytes(Paths.get("target/3/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
    assertThat(new String(Files.readAllBytes(Paths.get("target/3/snippets/myApp2/pom.xml")))).doesNotContain("[=mavenproject.name]");
    
  }
  
}
