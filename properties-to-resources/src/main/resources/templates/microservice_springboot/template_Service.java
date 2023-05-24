package [=mavenproject.groupId].service;

import [=mavenproject.groupId].domain.[=mavenproject.entityName];
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing {@link [=mavenproject.entityName]}.
 */
@Service
public class [=mavenproject.entityName]Service {

    private static final Logger log = LoggerFactory.getLogger([=mavenproject.entityName]Service.class);
    
    /**
     * Get [=mavenproject.entityName].
     *
     * @return the entity.
     */

    public [=mavenproject.entityName] get[=mavenproject.entityName]() {
        log.info("Request get HealthCheck");
        return new [=mavenproject.entityName]().name("my entity");
    }

}
