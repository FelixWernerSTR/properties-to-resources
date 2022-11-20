package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;


/**
 * POJO f�r Mapping Namespace=Path. StagePath PathSuffix für die generierten Artefakte.
 * 
 * Beispiel: stagepath.pr.mapping=sv-pr-svis:sv-pr
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class StagePath {
  
  @Override
  public String toString() {
    return "StagePath [mapping=" + mapping + "]";
  }
  
  /**
   * @return the mapping
   */
  public String getMapping() {
    return mapping;
  }
  
  /**
   * @param mapping the mapping to set
   */
  public void setMapping(String mapping) {
    this.mapping = mapping;
  }
  
  String mapping;
  
  public static List<String> getRegex() {
    
    Class<StagePath> aClass = StagePath.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(StagePath.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
}
