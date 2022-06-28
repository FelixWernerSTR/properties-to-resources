package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO fuer setEnvironemt.sh/cmd
 * 
 * @author N0009271
 *
 */
@SuppressWarnings("javadoc")
public class Environment {
  
  @Override
  public String toString() {
    return "Environment [nameValue=" + nameValue + "]";
  }
  
  String nameValue;
  
  public String getNameValue() {
    return nameValue;
  }
  
  public void setNameValue(String nameValue) {
    this.nameValue = nameValue;
  }
  
  public static List<String> getRegex() {
    
    Class<Environment> aClass = Environment.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(Environment.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
  
}
