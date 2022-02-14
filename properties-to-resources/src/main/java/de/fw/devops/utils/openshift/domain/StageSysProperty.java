package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO für Mapping Namespace=SysProperty. SysProperty hält PathSuffix für die generierten Artefakte.
 * 
 * Beispiel: stagesysproperty.pr.mapping=myapp-dev:yaml.upload.nexus.dirSuffixDev stagesysproperty.dev.mapping=myapp-dev:yaml.upload.nexus.dirSuffixDev
 * stagesysproperty.preprod.mapping=myapp-preprod:yaml.upload.nexus.dirSuffixPreProd
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class StageSysProperty {
  
  @Override
  public String toString() {
    return "StageSysProperty [mapping=" + mapping + "]";
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
    
    Class<StageSysProperty> aClass = StageSysProperty.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(StageSysProperty.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE + field.getName());
    }
    return fieldList;
  }
}
