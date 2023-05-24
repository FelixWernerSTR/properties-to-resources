package de.fw.devops.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.fw.devops.utils.misc.MicroserviceQuarkusSnippetBuilder;

public class MicroserviceQuarkusSnippetBuilderTest {
	
	  /**
	   * Demo: Generiert ein MicroserviceQuarkusMavenProjekt
	   * 
	   * @throws IOException
	   */
	  @Test
	  public void testProcessProjectProperties() throws IOException {
	    
		  MicroserviceQuarkusSnippetBuilder //
	        .fromProperties("src/test/resources/snippets/datamodel/project.properties")// hier fuer ein Project-Model
	        .templatePath("src/main/resources/templates/microservice_quarkus") //
	        .targetPath("target/5") //
	        .processProperties();
	    
	    assertThat(new String(Files.readAllBytes(Paths.get("target/5/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");

	  }

}
