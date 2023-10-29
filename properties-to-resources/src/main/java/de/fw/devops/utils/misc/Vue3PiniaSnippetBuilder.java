package de.fw.devops.utils.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fw.devops.utils.AbstractArtifactBuilder;


/**
 * Funktionsweise:
 * Iteriert im Template-Verzeichnis-Baum und verarbeitet alle Templates("template_x"). 
 * In der Hook-Methode: resolveNameForRessource wird der Zielname der Resource "berechnet"
 * In der Hook-Methode: addPrefixPathForResource wird der Prefix-Pfad der Resource "berechnet"
 * 
 * 
 * @author Felix Werner
 */
public class Vue3PiniaSnippetBuilder extends AbstractArtifactBuilder {
  
  private static Logger logger = LogManager.getLogger(Vue3PiniaSnippetBuilder.class);

  private static Pattern templatesCutPrefixOnly = Pattern.compile("template_embedded-tomcat.launch|template_env.d.ts|"
	  		+ "template_index.html|template_package.json|template_pom.xml|template_README.md|"
	  		+ "template_startConsoleWithNodeNpmSupport.cmd|template_tsconfig.app.json|template_tsconfig.json|template_tsconfig.node.json|template_vite.config.ts|"
	  		+ "template_App.ts|template_App.vue|template_main.ts|template_base.css|template_main.css|"
	  		+ "template_web.xml|template_logo.svg|template_favicon.ico|template_index.ts|template_AboutView.vue|template_.env.production|template_.env.development");
  
  private MavenProject mavenProject = new MavenProject();
  
  /**
   * @param path
   * @return AbstractArtifactBuilder
   */
  public static AbstractArtifactBuilder fromProperties(String path) {
    return new Vue3PiniaSnippetBuilder().properties(path);
  }
  
  @Override
  public void registerPojos() {
    pojoPropertiesParseUtil.registerPojo(new MavenProject(), "mavenproject", mavenProject);
  }
  
  @Override
  public String resolveNameForRessource(String templateName) {

    Matcher matcher = templatesCutPrefixOnly.matcher(templateName);
    if (matcher.find()) {
    	return templateName.replaceFirst("template_", "");
    }
    
    return templateName.replaceFirst("template_Entity", mavenProject.getEntityName());
  }
  
  @Override
  public String resolveFinalPathSuffix() {
    return mavenProject.getName();
  }
  
  @Override
  public String addPrefixPathForResource(String resourcePath) {    
    return resourcePath;
  }
  
  private String toPath(String groupId) {
	return groupId.replaceAll("\\.", "/");
  }

@Override
  public String resolveTargetPathSuffix() {
    return pojoPropertiesParseUtil.getProperties().getProperty("targetPathSuffix");
  }
  
}
