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
import de.fw.devops.utils.openshift.domain.StagePath;

/**
 * Erzeugt yaml-Deployment-Dateien von deployment-StageX.properties oder deploymentProperties/-Verzeichnis nach targetPath
 * 
 * Verzeichnis mit Freemarker-Templates ist anzugeben.
 * 
 * Verzeichnis-Suffixe sind über das "stagepath-mapping" zu konfigurieren siehe @StagePath oder ueber deployment.targetPathSuffix=some/path für jedes deployment
 * anzugeben.
 * 
 * @author Felix Werner
 *
 */
public class OpenShiftDeploymentBuilder extends AbstractArtifactBuilder {
  
	  private static Logger logger = LogManager.getLogger(OpenShiftDeploymentBuilder.class);
	  
	  private Deployment deployment = null;
	  private static Map<String, StagePath> stagePathMapping = new HashMap<>();
	  
	  /**
	   * @param path
	   * @return AbstractArtifactBuilder
	   */
	  public static AbstractArtifactBuilder fromProperties(String path) {
	    return new OpenShiftDeploymentBuilder().properties(path);
	  }
	  
	  /**
	   * @return the deployment
	   */
	  public Deployment getDeployment() {
	    return deployment;
	  }
	  
	  /**
	   * @return the stageSysPropertiesMapping
	   */
	  public static Map<String, StagePath> getStagePathMapping() {
	    return stagePathMapping;
	  }
	  
	  @Override
	  public void registerPojos() {
	    deployment = new Deployment();
	    pojoPropertiesParseUtil.registerPojo(new Deployment(), "deployment", deployment);
	    pojoPropertiesParseUtil.registerPojo(new ConfigMap(), "configmapentries", new HashMap<String, ConfigMap>());
	    pojoPropertiesParseUtil.registerPojo(new Secret(), "secrets", new HashMap<String, Secret>());
	    pojoPropertiesParseUtil.registerPojo(new Route(), "routes", new HashMap<String, Route>());
	    pojoPropertiesParseUtil.registerPojo(new StagePath(), "stagepaths", stagePathMapping);
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
		logger.info("templateName/deployment.getAppName(): {}/{}", deployment.getAppName(),templateName);
	    return templateName.replaceFirst("template", deployment.getAppName());
	  }
	  
	  /**
	   * @return name for TargetPath
	   */
	  @Override
	  public String resolveFinalPathSuffix() {
	    return deployment.getAppName();
	  }
	  
	  /**
	   * @return path
	   */
	  @Override
	  public String resolveTargetPathSuffix() {
	    if (deployment.getTargetPathSuffix() != null) {
	      return deployment.getTargetPathSuffix();
	    }
	    logger.debug("path: {} for namespace: {}", getPathForNamespace(deployment.getNamespace()), deployment.getNamespace());
	    return getPathForNamespace(deployment.getNamespace());
	  }
	  
	  /**
	   * @param namespace
	   * @return String
	   */
	  protected String getPathForNamespace(String namespace) {
	    for (Entry<String, StagePath> entry : stagePathMapping.entrySet()) {
	      if (entry.getValue().getMapping().split(":")[0].equals(namespace)) {
	        return entry.getValue().getMapping().split(":")[1];
	      }
	    }
	    return null;
	  }
	  
	  @Override
	  public void parseToPojos() {
	    stagePathMapping.clear();
	    super.parseToPojos();
	    if (stagePathMapping.isEmpty()) {
	      createDefaultStagePathsMapping();
	    }
	  }
	  
	  private void createDefaultStagePathsMapping() {
	    
	    var s = new StagePath();
	    s.setMapping("myapp-dev:openshift/dev");
	    stagePathMapping.put("stagepath.dev.mapping", s);
	        
	    s = new StagePath();
	    s.setMapping("myapp-preprod:openshift/preprod");
	    stagePathMapping.put("stagepath.preprod.mapping", s);
	        
	    s = new StagePath();
	    s.setMapping("myapp-prod:openshift/prod");
	    stagePathMapping.put("stagepath.prod.mapping", s);

	  }
	  
	  @Override
	  public String toString() {
	    return "OpenShiftDeploymentBuilder [propertiesPath=" + propertiesPath + ", templatePath=" + templatePath + ", targetPath=" + targetPath + "]";
	  }
  
}
