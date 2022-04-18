package de.fw.maven.plugins.artifactbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import de.fw.devops.utils.AbstractArtifactBuilder;
import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * wraps functionality of properties-to-resources as maven-plugin
 */
@Mojo(name = "build", defaultPhase = LifecyclePhase.VALIDATE)
public class ArtifactBuildMojo extends AbstractMojo {
  
  @Parameter(property = "sourceDir", required = true)
  private String sourceDir;
  
  @Parameter(property = "templateDir", required = true)
  private String templateDir;
  
  @Parameter(property = "targetDir", required = true)
  private String targetDir;
  
  /**
   * The project currently being build.
   */
  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject mavenProject;
  
  /**
   * The current Maven session.
   */
  @Parameter(defaultValue = "${session}", readonly = true)
  private MavenSession mavenSession;
  
  /**
   * The Maven BuildPluginManager component.
   */
  @Component
  private BuildPluginManager pluginManager;
  
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    logParameters();
    setSystemPropertiesForBuilder();
    try {
      if (!new File(templateDir + "/datamodelProvider.properties").exists()) {
        throw new RuntimeException("datamodelProvider.properties fehlt im Pfad:" + templateDir);
      }
      String dataModelProviderClass = new String(Files.readAllBytes(Paths.get(templateDir + "/datamodelProvider.properties")));
      
      AbstractArtifactBuilder artifactBuilder = (AbstractArtifactBuilder) PropertiesToPojosParseUtil.createObject(dataModelProviderClass);
      artifactBuilder.properties(sourceDir).templatePath(templateDir).targetPath(targetDir).process();
      
    } catch (IOException e) {
      e.printStackTrace();
      getLog().error("Das Erzeugen von Resourcen fehlgeschlagen!", e);
    }
    
  }
  
  private void setSystemPropertiesForBuilder() {
    System.setProperty("basedir", mavenProject.getBasedir().getPath());
    System.setProperty("project.version", mavenProject.getVersion());
  }
  
  private void logParameters() {
    getLog().info("Received the following parameters: ");
    getLog().info(toString());
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("YamlBuildMojo [sourceDir=");
    builder.append(sourceDir);
    builder.append(", templateDir=");
    builder.append(templateDir);
    builder.append(", targetDir=");
    builder.append(targetDir);
    builder.append(", mavenProject=");
    builder.append(mavenProject);
    builder.append("]");
    return builder.toString();
  }
  
}