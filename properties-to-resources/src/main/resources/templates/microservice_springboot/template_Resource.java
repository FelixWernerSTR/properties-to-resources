package [=mavenproject.groupId].web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import [=mavenproject.groupId].domain.[=mavenproject.entityName];
import [=mavenproject.groupId].service.[=mavenproject.entityName]Service;

/**
 * REST controller for managing {@link [=mavenproject.groupId].domain.[=mavenproject.entityName]}.
 */
@RestController
@RequestMapping("/api")
public class [=mavenproject.entityName]Resource {

    private final Logger log = LoggerFactory.getLogger([=mavenproject.entityName]Resource.class);

    private final [=mavenproject.entityName]Service [=mavenproject.entityName?uncap_first]Service;


    public [=mavenproject.entityName]Resource([=mavenproject.entityName]Service [=mavenproject.entityName?uncap_first]Service) {
        this.[=mavenproject.entityName?uncap_first]Service = [=mavenproject.entityName?uncap_first]Service;
    }
    
    /**
     * {@code GET  /[=mavenproject.entityName?lower_case]}
     *
     */
    @GetMapping("/[=mavenproject.entityName?lower_case]")
    public ResponseEntity<[=mavenproject.entityName]> get[=mavenproject.entityName]() {
    	log.debug("REST request to get all [=mavenproject.entityName]");
        return new ResponseEntity([=mavenproject.entityName?uncap_first]Service.get[=mavenproject.entityName](),HttpStatus.OK);
    }

}
