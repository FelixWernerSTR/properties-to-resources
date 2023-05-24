package [=mavenproject.groupId];

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}.
 * This will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 */

public class ApplicationWebXml extends SpringBootServletInitializer {
	 private static final Logger log = LoggerFactory.getLogger(ApplicationWebXml.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
    	log.info("configure");
        // set a default to use when no profile is configured.
        Map<String, Object> defProperties = new HashMap<>();
        /*
         * The default profile to use when no other profiles are defined
         * This cannot be set in the application.yml file.
         * See https://github.com/spring-projects/spring-boot/issues/1219
         */
        defProperties.put("spring.profiles.default", "prod");
        applicationBuilder.application().setDefaultProperties(defProperties);
        return applicationBuilder.sources([=mavenproject.artifactId]App.class);
    }
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	log.info("onStartup");
        servletContext.setInitParameter("spring.profiles.active", "prod"); //ohne kommt es zu Fehlermeldung in "Standalone-Tomcat"(in embedded nicht): Caused by: java.lang.IllegalArgumentException: Invalid profile []: must contain text at org.springframework.core.env.AbstractEnvironment.validateProfile(AbstractEnvironment.java:432)
    	super.onStartup(servletContext);
    }
}
