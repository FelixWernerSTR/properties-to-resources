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
public class ConfigMap {
  
  @Override
  public String toString() {
    return "ConfigMap [nameValue=" + nameValue + "]";
  }
  
  String nameValue;
  
  public String getNameValue() {
    return nameValue;
  }
  
  public void setNameValue(String nameValue) {
    this.nameValue = nameValue;
  }
  
  public static List<String> getRegex() {
    
    Class<ConfigMap> aClass = ConfigMap.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(ConfigMap.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
  
}
