package [=mavenproject.groupId].service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import [=mavenproject.groupId].domain.[=mavenproject.entityName];

@Singleton
public class [=mavenproject.entityName]PseudoPersistenceService {

	private static final  Logger log = LoggerFactory.getLogger([=mavenproject.entityName]PseudoPersistenceService.class);
	
	private static Map<Long, [=mavenproject.groupId]> [=mavenproject.entityName?lower_case]Map = new ConcurrentHashMap<>();

	public [=mavenproject.entityName] persistOrUpdate([=mavenproject.entityName] [=mavenproject.entityName?lower_case]) {
		log.debug("Request to save [=mavenproject.entityName] : {}", [=mavenproject.entityName?lower_case]);
			if ([=mavenproject.entityName?lower_case].getId() != null) {
				if (![=mavenproject.entityName?lower_case]Map.containsKey([=mavenproject.entityName?lower_case].getId())) {
					throw new RuntimeException("no [=mavenproject.entityName?lower_case] with id: " + [=mavenproject.entityName?lower_case].id);
				}
				[=mavenproject.entityName?lower_case]Map.replace([=mavenproject.entityName?lower_case].getId(), [=mavenproject.entityName?lower_case]);
			} else {
				[=mavenproject.entityName?lower_case].setId(idCount++);
				[=mavenproject.entityName?lower_case]Map.put([=mavenproject.entityName?lower_case].getId(), [=mavenproject.entityName?lower_case]);
			}
		return [=mavenproject.entityName?lower_case];
	}

	/**
	 * Delete the [=mavenproject.entityName] by id.
	 *
	 * @param id the id of the entity.
	 */
	public [=mavenproject.entityName] delete(Long id) {
		log.debug("Request to delete [=mavenproject.entityName] : {}", id);
		[=mavenproject.entityName] deleted = [=mavenproject.entityName?lower_case]Map.get(id);
		[=mavenproject.entityName?lower_case]Map.remove(id);
		return deleted;
	}

	/**
	 * Get one [=mavenproject.entityName] by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	public Optional<[=mavenproject.entityName]> findOne(Long id) {
		log.debug("Request to get [=mavenproject.entityName] : {}", id);
        if([=mavenproject.entityName?lower_case]Map.containsKey(id)) {
        	return Optional.of([=mavenproject.entityName?lower_case]Map.get(id));
        }
        return Optional.empty();
	}


	/**
	 * Get all the [=mavenproject.entityName?lower_case]s.
	 * 
	 * @param page the pagination information.
	 * @return the list of entities.
	 */
	public List<[=mavenproject.entityName]> findAll() {
		log.debug("Request to get all [=mavenproject.entityName]s");
		return new ArrayList([=mavenproject.entityName?lower_case]Map.values());
	}

}
