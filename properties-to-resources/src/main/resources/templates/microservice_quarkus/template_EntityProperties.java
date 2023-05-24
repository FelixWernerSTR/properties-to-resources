package [=mavenproject.groupId].config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "[=mavenproject.entityName?lower_case]")
public interface [=mavenproject.entityName]Properties {
    Info info();

    interface Info {
        Swagger swagger();

        interface Swagger {
            Boolean enable();
        }
    }
}
