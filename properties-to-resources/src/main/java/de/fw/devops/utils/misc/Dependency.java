package de.fw.devops.utils.misc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.fw.devops.utils.PropertiesToPojosParseUtil;

/**
 * 
 * @author Felix Werner
 */
public class Dependency {

	private String groupId;
	private String artifactId;
	private String version;

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId the artifactId to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public static List<String> getRegex() {

		Class<Dependency> aClass = Dependency.class;
		Field[] fields = aClass.getDeclaredFields();

		List<String> fieldList = new ArrayList<>();

		for (Field field : fields) {
			fieldList.add(Dependency.class.getSimpleName().toLowerCase() + PropertiesToPojosParseUtil.REGEX_PATTERN_CORE
					+ field.getName());
		}
		return fieldList;
	}

	@Override
	public String toString() {
		return "Dependency [groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version + "]";
	}

}
