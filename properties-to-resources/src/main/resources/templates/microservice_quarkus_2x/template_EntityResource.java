package [=mavenproject.groupId].web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import [=mavenproject.groupId].domain.[=mavenproject.entityName];
import [=mavenproject.groupId].service.Paged;
import [=mavenproject.groupId].service.[=mavenproject.entityName]Service;
import [=mavenproject.groupId].web.rest.errors.BadRequestAlertException;
import [=mavenproject.groupId].web.rest.vm.PageRequestVM;
import [=mavenproject.groupId].web.rest.vm.SortRequestVM;
import [=mavenproject.groupId].web.util.HeaderUtil;
import [=mavenproject.groupId].web.util.PaginationUtil;
import [=mavenproject.groupId].web.util.ResponseUtil;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for managing {@link [=mavenproject.groupId].domain.[=mavenproject.entityName]}.
 */
@Path("/api/[=mavenproject.entityName?lower_case]s")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class [=mavenproject.entityName]Resource {

    private final Logger log = LoggerFactory.getLogger([=mavenproject.entityName]Resource.class);

    private static final String ENTITY_NAME = "[=mavenproject.entityName?lower_case]Manager[=mavenproject.entityName]";

    @ConfigProperty(name = "application.name")
    String applicationName;

    @Inject
    [=mavenproject.entityName]Service [=mavenproject.entityName?lower_case]Service;

    /**
     * {@code POST  /[=mavenproject.entityName?lower_case]s} : Create a new [=mavenproject.entityName?lower_case].
     *
     * @param [=mavenproject.entityName?lower_case] the [=mavenproject.entityName?lower_case] to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new [=mavenproject.entityName?lower_case], or with status {@code 400 (Bad Request)} if the [=mavenproject.entityName?lower_case] has already an ID.
     */
    @POST
    public Response create[=mavenproject.entityName](@Valid [=mavenproject.entityName] [=mavenproject.entityName?lower_case], @Context UriInfo uriInfo) {
        log.debug("REST request to save [=mavenproject.entityName] : {}", [=mavenproject.entityName?lower_case]);
        if ([=mavenproject.entityName?lower_case].id != null) {
            throw new BadRequestAlertException("A new [=mavenproject.entityName?lower_case] cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = [=mavenproject.entityName?lower_case]Service.persistOrUpdate([=mavenproject.entityName?lower_case]);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /[=mavenproject.entityName?lower_case]s} : Updates an existing [=mavenproject.entityName?lower_case].
     *
     * @param [=mavenproject.entityName?lower_case] the [=mavenproject.entityName?lower_case] to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated [=mavenproject.entityName?lower_case],
     * or with status {@code 400 (Bad Request)} if the [=mavenproject.entityName?lower_case] is not valid,
     * or with status {@code 500 (Internal Server Error)} if the [=mavenproject.entityName?lower_case] couldn't be updated.
     */
    @PUT
    @Path("/{id}")
    public Response update[=mavenproject.entityName](@Valid [=mavenproject.entityName] [=mavenproject.entityName?lower_case], @PathParam("id") Long id) {
        log.debug("REST request to update [=mavenproject.entityName] : {}", [=mavenproject.entityName?lower_case]);
        if ([=mavenproject.entityName?lower_case].id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = [=mavenproject.entityName?lower_case]Service.persistOrUpdate([=mavenproject.entityName?lower_case]);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, [=mavenproject.entityName?lower_case].id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /[=mavenproject.entityName?lower_case]s/:id} : delete the "id" [=mavenproject.entityName?lower_case].
     *
     * @param id the id of the [=mavenproject.entityName?lower_case] to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response delete[=mavenproject.entityName](@PathParam("id") Long id) {
        log.debug("REST request to delete [=mavenproject.entityName] : {}", id);
        [=mavenproject.entityName?lower_case]Service.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /[=mavenproject.entityName?lower_case]s} : get all the [=mavenproject.entityName?lower_case]s.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of [=mavenproject.entityName?lower_case]s in body.
     */
    @GET
    public Response getAll[=mavenproject.entityName]s(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.info("REST request to get a page of [=mavenproject.entityName]s");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<[=mavenproject.entityName]> result = [=mavenproject.entityName?lower_case]Service.findAll(page, sort);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }

    /**
     * {@code GET  /[=mavenproject.entityName?lower_case]s/:id} : get the "id" [=mavenproject.entityName?lower_case].
     *
     * @param id the id of the [=mavenproject.entityName?lower_case] to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the [=mavenproject.entityName?lower_case], or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")
    public Response get[=mavenproject.entityName](@PathParam("id") Long id) {
        log.debug("REST request to get [=mavenproject.entityName] : {}", id);
        Optional<[=mavenproject.entityName]> [=mavenproject.entityName?lower_case] = [=mavenproject.entityName?lower_case]Service.findOne(id);
        return ResponseUtil.wrapOrNotFound([=mavenproject.entityName?lower_case]);
    }
}
