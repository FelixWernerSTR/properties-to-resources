package [=mavenproject.groupId];

import [=mavenproject.groupId].config.ApplicationProperties;
import [=mavenproject.groupId].config.CRLFLogConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class [=mavenproject.artifactId]App {

    private static final Logger log = LoggerFactory.getLogger([=mavenproject.artifactId]App.class);

    private final Environment env;

    public [=mavenproject.artifactId]App(Environment env) {
        this.env = env;
    }

    /**
     * Initializes [=mavenproject.artifactId].
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains("dev") &&
            activeProfiles.contains("prod")
        ) {
            log.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains("dev") &&
            activeProfiles.contains("cloud")
        ) {
            log.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication([=mavenproject.artifactId]App.class);
        Map<String, Object> defProperties = new HashMap<>();
        /*
         * The default profile to use when no other profiles are defined
         * This cannot be set in the application.yml file.
         * See https://github.com/spring-projects/spring-boot/issues/1219
         */
        defProperties.put("spring.profiles.default", "prod,api-docs");
        app.setDefaultProperties(defProperties);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
            .ofNullable(env.getProperty("server.servlet.context-path"))
            .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            "\n----------------------------------------------------------\n\t" +
            "Application '{}' is running! Access URLs:\n\t" +
            "Local: \t\t{}://localhost:{}{}\n\t" +
            "External: \t{}://{}:{}{}\n\t" +
            "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );
    }
}
