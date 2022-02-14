package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO für zusätzliche Openshift/Kubernetes-Route's. In der Regel wenn mehrere WebAnwendunegn auf einem Tomcat über ein Deployment draufkommen und separat
 * erreichbar sein müssen.
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class Route {
  
  @Override
  public String toString() {
    return "Route [path=" + path + "]";
  }
  
  String path;
  
  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }
  
  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }
  
  public static List<String> getRegex() {
    
    Class<Route> aClass = Route.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(Route.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
  
}
