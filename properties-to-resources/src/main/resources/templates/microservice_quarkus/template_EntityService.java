package [=mavenproject.groupId].service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import [=mavenproject.groupId].domain.[=mavenproject.entityName];
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
@Transactional
public class [=mavenproject.entityName]Service {

    private final Logger log = LoggerFactory.getLogger([=mavenproject.entityName]Service.class);

    @Transactional
    public [=mavenproject.entityName] persistOrUpdate([=mavenproject.entityName] [=mavenproject.entityName?lower_case]) {
        log.debug("Request to save [=mavenproject.entityName] : {}", [=mavenproject.entityName?lower_case]);
        return [=mavenproject.entityName].persistOrUpdate([=mavenproject.entityName?lower_case]);
    }

    /**
     * Delete the [=mavenproject.entityName] by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete [=mavenproject.entityName] : {}", id);
        [=mavenproject.entityName]
            .findByIdOptional(id)
            .ifPresent([=mavenproject.entityName?lower_case] -> {
                [=mavenproject.entityName?lower_case].delete();
            });
    }

    /**
     * Get one [=mavenproject.entityName?lower_case] by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<[=mavenproject.entityName]> findOne(Long id) {
        log.debug("Request to get [=mavenproject.entityName] : {}", id);
        return [=mavenproject.entityName].findByIdOptional(id);
    }

    /**
     * Get all the [=mavenproject.entityName?lower_case]s.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<[=mavenproject.entityName]> findAll(Page page, Sort sort) {
        log.debug("Request to get all [=mavenproject.entityName]s");
        return new Paged<>([=mavenproject.entityName].findAll(sort).page(page));
    }
}
