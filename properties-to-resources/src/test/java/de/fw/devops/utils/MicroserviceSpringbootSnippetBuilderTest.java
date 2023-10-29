package de.fw.devops.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.fw.devops.utils.misc.MicroserviceSpringbootSnippetBuilder;

public class MicroserviceSpringbootSnippetBuilderTest {
	
	  /**
	   * Demo: Generiert ein MicroserviceSpringbootMavenProjekt
	   * 
	   * @throws IOException
	   */
	  @Test
	  public void testProcessProjectProperties() throws IOException {
	    
		  MicroserviceSpringbootSnippetBuilder //
	        .fromProperties("src/test/resources/snippets/datamodel/project.properties")// hier fuer ein Project-Model
	        .templatePath("src/main/resources/templates/microservice_springboot") //
	        .targetPath("target/springboot1") //
	        .processProperties();
	    
	    assertThat(new String(Files.readAllBytes(Paths.get("target/springboot1/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
	  }

}
