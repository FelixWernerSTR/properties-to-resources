package de.fw.devops.utils.openshift;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.fw.devops.utils.openshift.OpenShiftDeploymentBuilder;

/**
 * 
 * @author Felix Werner
 */
public class OpenShiftDeploymentBuilderTest {
  
  /**
   * 
   * Funktionalität im Unit-Test
   * 
   * @throws IOException
   */
  @Test
  public void testProcessDeploymentProperties() throws IOException {
    
    System.setProperty("basedir", new File(".").getAbsolutePath());
    
    OpenShiftDeploymentBuilder.fromProperties("src/test/resources/openshift/example-config/dev/myapp-dev.properties")// hier fuer ein Deployment-Model
        .templatePath("src/main/resources/templates/openshift") //
        .targetPath("target") //
        .process();
    assertThat(Files.exists(Paths.get("target/openshift/dev/myApp/myApp_Deployment_v1.yaml"))).isTrue();
    assertThat(new String(Files.readAllBytes(Paths.get("target/openshift/dev/myApp/myapp_Deployment_v1.yaml")))).doesNotContain("${deployment.name}");
  }
  
  /**
   * 
   * Funktionalität im Unit-Test
   * 
   * @throws IOException
   */
  @Test
  public void testProcessDeploymentPropertiesDir() throws IOException {
    
    OpenShiftDeploymentBuilder.fromProperties("src/test/resources/openshift/example-config/dev")// hier fuer mehrere Deployment_modelle im Verzeichnis. So wird
                                                                                                // es aus Maven
        // verwendet. Siehe Maven-Profile "yaml-build"
        .templatePath("src/main/resources/templates/openshift") //
        .targetPath("target") //
        .process();
    assertThat(Files.exists(Paths.get("target/openshift/preprod/myApp/myApp_Deployment_v1.yaml"))).isTrue();
    
    assertThat(Files.exists(Paths.get("target/openshift/prod/myApp/myApp_Deployment_v1.yaml"))).isTrue();// mit
                                                                                                                 // deployment.targetPathSuffix=openshift/rt.
                                                                                                                 // Nicht empfohlen. Man braucht trotzdem die
                                                                                                                 // Systemproperties fuer wagon-maven-plugin.
                                                                                                                 // Auf
                                                                                                                 // die Art hat man redundante Konfiguration
    OpenShiftDeploymentBuilder.fromProperties("src/test/resources/openshift/example-config/sonarqube").templatePath("src/main/resources/templates/openshift") //
        .targetPath("target") //
        .process();
  }
  
}
