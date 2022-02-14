package de.fw.devops.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.fw.devops.utils.misc.ProjectSnippetBuilder;

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
        .fromProperties("src/test/resources/snippets/modeldata/project.properties")// hier für ein Project-Model
        .templatePath("src/main/resources/templates/snippets_maven_openshift") //
        .targetPath("target") //
        .processProperties();
    
    assertThat(new String(Files.readAllBytes(Paths.get("target/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
  }
  
  /**
   * 
   * @throws IOException
   */
  @Test
  public void testProcessProjectPropertiesDir() throws IOException {
    
    ProjectSnippetBuilder //
        .fromProperties("src/test/resources/snippets/modeldata").templatePath("src/main/resources/templates/snippets_maven_openshift").targetPath("target") //
        .process();
    
    assertThat(new String(Files.readAllBytes(Paths.get("target/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
    assertThat(new String(Files.readAllBytes(Paths.get("target/snippets/myApp2/pom.xml")))).doesNotContain("[=mavenproject.name]");

  }
  
}
