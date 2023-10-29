package de.fw.devops.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.fw.devops.utils.misc.Vue3PiniaSnippetBuilder;

public class Vue3PiniaSnippetBuilderTest {
	
	  /**
	   * Demo: Generiert ein Vue3piniaMavenProjekt
	   * 
	   * @throws IOException
	   */
	  @Test
	  public void testProcessProjectProperties() throws IOException {
	    
		  Vue3PiniaSnippetBuilder //
	        .fromProperties("src/test/resources/snippets/datamodel/project.properties")// hier f�r ein Project-Model
	        .templatePath("src/main/resources/templates/vue3pinia") //
	        .targetPath("target/vue3pinia1") //
	        .processProperties();
	    
	    assertThat(new String(Files.readAllBytes(Paths.get("target/vue3pinia1/snippets/myApp/pom.xml")))).doesNotContain("[=mavenproject.name]");
	  }

}
