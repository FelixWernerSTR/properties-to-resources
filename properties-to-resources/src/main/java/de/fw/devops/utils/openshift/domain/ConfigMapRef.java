package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO für Openshift/Kubernetes-ConfigMap
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class ConfigMapRef {
  
  @Override
  public String toString() {
    return "ConfigMapRef [name=" + name + "]";
  }
  
  String name;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public static List<String> getRegex() {
    
    Class<ConfigMapRef> aClass = ConfigMapRef.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(ConfigMapRef.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
  
}
