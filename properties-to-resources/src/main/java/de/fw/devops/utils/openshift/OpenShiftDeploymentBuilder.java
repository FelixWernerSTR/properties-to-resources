package de.fw.devops.utils.openshift;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fw.devops.utils.AbstractArtifactBuilder;
import de.fw.devops.utils.openshift.domain.ConfigMap;
import de.fw.devops.utils.openshift.domain.ConfigMapRef;
import de.fw.devops.utils.openshift.domain.Deployment;
import de.fw.devops.utils.openshift.domain.Environment;
import de.fw.devops.utils.openshift.domain.Route;
import de.fw.devops.utils.openshift.domain.Secret;
import de.fw.devops.utils.openshift.domain.SecretRef;
import de.fw.devops.utils.openshift.domain.StageSysProperty;

/**
 * Erzeugt yaml-Deployment-Dateien von deployment-StageX.properties oder deploymentProperties/-Verzeichnis nach targetPath
 * 
 * Verzeichnis mit Freemarker-Templates ist anzugeben.
 * 
 * Verzeichnis-Suffixe sind über das "stagesysproperty-mapping" zu konfigurieren siehe @StageSysProperty oder über deployment.targetPathSuffix=some/path für
 * jedes deployment anzugeben.
 * 
 * @author Felix Werner
 *
 */
public class OpenShiftDeploymentBuilder extends AbstractArtifactBuilder {
  
  static Logger logger = LogManager.getLogger(OpenShiftDeploymentBuilder.class);
  
  public static AbstractArtifactBuilder fromProperties(String path) {
    return new OpenShiftDeploymentBuilder().properties(path);
  }
  
  Deployment deployment = null;
  Map<String, StageSysProperty> stageSysPropertiesMapping = null;
  
  @Override
  public void registerPojos() {
    deployment = new Deployment();
    stageSysPropertiesMapping = new HashMap<>();
    pojoPropertiesParseUtil.registerPojo(new Deployment(), "deployment", deployment);
    pojoPropertiesParseUtil.registerPojo(new ConfigMap(), "configmapentries", new HashMap<String, ConfigMap>());
    pojoPropertiesParseUtil.registerPojo(new Secret(), "secrets", new HashMap<String, Secret>());
    pojoPropertiesParseUtil.registerPojo(new Route(), "routes", new HashMap<String, Route>());
    pojoPropertiesParseUtil.registerPojo(new StageSysProperty(), "stagesysproperties", stageSysPropertiesMapping);
    pojoPropertiesParseUtil.registerPojo(new Environment(), "environments", new HashMap<String, Environment>());
    pojoPropertiesParseUtil.registerPojo(new ConfigMapRef(), "configmaprefs", new HashMap<String, ConfigMapRef>());
    pojoPropertiesParseUtil.registerPojo(new SecretRef(), "secretrefs", new HashMap<String, SecretRef>());
  }
  
  /**
   * @param arguments
   * @throws IOException
   */
  public static void main(String[] arguments) throws IOException {
    if (arguments == null || arguments.length < 3) {
      logger.info("Bitte deploymentProperties, templatePath, targetPath-Dateien angeben!");
    } else {
      OpenShiftDeploymentBuilder.fromProperties(arguments[0]).templatePath(arguments[1]).targetPath(arguments[2]).process();
    }
  }
  
  /**
   * @return name for Template
   */
  @Override
  public String resolveNameForRessource(String templateName) {
    return templateName.replaceFirst("template", deployment.getAppName());
  }
  
  /**
   * @return name for TargetPath
   */
  @Override
  public String resolveNameForTargetPath() {
    return deployment.getAppName();
  }
  
  /**
   * @return path
   */
  @Override
  public String resolveTargetPathSuffix() {
    if (deployment.getNamespace() == null) {
      return deployment.getTargetPathSuffix();
    }
    return System.getProperty(getSysPropertyForNamespace(deployment.getNamespace()), deployment.getTargetPathSuffix());
  }
  
  /**
   * @param namespace
   * @return String
   */
  protected String getSysPropertyForNamespace(String namespace) {
    for (Entry<String, StageSysProperty> entry : stageSysPropertiesMapping.entrySet()) {
      if (entry.getValue().getMapping().split(":")[0].equals(namespace)) {
        return entry.getValue().getMapping().split(":")[1];
      }
    }
    return "no_system_property_for_" + namespace;
  }
  
  @Override
  public String toString() {
    return "OpenShiftDeploymentBuilder [propertiesPath=" + propertiesPath + ", templatePath=" + templatePath + ", targetPath=" + targetPath + "]";
  }
  
}
