package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * POJO f�r Openshift/Kubernetes-Secret
 * 
 * Sollte nur bei der Entwicklung und Test verwendet werden. In Properties und
 * Git sollten ja die Passw�rter nicht in Klartext sein. Secret-Yaml-Files
 * d�rfen auch nicht nach Nexus upgeloadet werden.
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class SecretRef {

	@Override
	public String toString() {
		return "SecretRef [name=" + name + "]";
	}

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<String> getRegex() {

		Class<SecretRef> aClass = SecretRef.class;
		Field[] fields = aClass.getDeclaredFields();

		List<String> fieldList = new ArrayList<>();

		for (Field field : fields) {
			fieldList.add(SecretRef.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE
					+ field.getName());
		}
		return fieldList;
	}
}
