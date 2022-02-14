package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO für Openshift/Kubernetes-Secret
 * 
 * Sollte nur bei der Entwicklung und Test verwendet werden. In Properties und Git sollten ja die Passwörter nicht in Klartext sein. Secret-Yaml-Files dürfen
 * auch nicht nach Nexus upgeloadet werden.
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class Secret {
  
  String nameValue;
  
  public String getNameValue() {
    return nameValue;
  }
  
  public void setNameValue(String nameValue) {
    this.nameValue = nameValue;
  }
  
  public static List<String> getRegex() {
    
    Class<Secret> aClass = Secret.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(Secret.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
}
