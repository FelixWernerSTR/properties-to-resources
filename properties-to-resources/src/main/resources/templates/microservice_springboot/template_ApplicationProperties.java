package [=mavenproject.groupId].config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to [=mavenproject.artifactId].
 * <p>
 * Properties are configured in the {@code application.properties} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private final CorsConfiguration cors = new CorsConfiguration();

	public CorsConfiguration getCors() {
		return cors;
	}

}
