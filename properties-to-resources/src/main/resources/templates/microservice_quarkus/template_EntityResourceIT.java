package [=mavenproject.groupId].web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import [=mavenproject.groupId].TestUtil;
import [=mavenproject.groupId].domain.[=mavenproject.entityName];
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

@QuarkusTest
public class [=mavenproject.entityName]ResourceIT {

    private static final TypeRef<[=mavenproject.entityName]> ENTITY_TYPE = new TypeRef<>() {};

    private static final TypeRef<List<[=mavenproject.entityName]>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {};

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final Date DEFAULT_GEBURTS_DATUM = new Date();
    private static final Date UPDATED_GEBURTS_DATUM = new Date();

    private static final String DEFAULT_STRASSE = "AAAAAAAAAA";
    private static final String UPDATED_STRASSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_HAUS_NUMMER = 0;
    private static final Integer UPDATED_HAUS_NUMMER = 1;

    private static final Integer DEFAULT_PLZ = 0;
    private static final Integer UPDATED_PLZ = 1;

    private static final String DEFAULT_LAND = "AAAAAAAAAA";
    private static final String UPDATED_LAND = "BBBBBBBBBB";

    String adminToken;

    [=mavenproject.entityName] [=mavenproject.entityName?lower_case];

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeAll
    static void jsonMapper() {
        RestAssured.config =
            RestAssured.config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        //this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static [=mavenproject.entityName] createEntity() {
        var [=mavenproject.entityName?lower_case] = new [=mavenproject.entityName]();
        [=mavenproject.entityName?lower_case].description = DEFAULT_DESCRIPTION;
        [=mavenproject.entityName?lower_case].name = DEFAULT_NAME;
        [=mavenproject.entityName?lower_case].vorname = DEFAULT_VORNAME;
        [=mavenproject.entityName?lower_case].geburtsDatum = DEFAULT_GEBURTS_DATUM;
        [=mavenproject.entityName?lower_case].strasse = DEFAULT_STRASSE;
        [=mavenproject.entityName?lower_case].hausNummer = DEFAULT_HAUS_NUMMER;
        [=mavenproject.entityName?lower_case].plz = DEFAULT_PLZ;
        [=mavenproject.entityName?lower_case].land = DEFAULT_LAND;
        return [=mavenproject.entityName?lower_case];
    }

    @BeforeEach
    public void initTest() {
        [=mavenproject.entityName?lower_case] = createEntity();
    }

    @Test
    public void create[=mavenproject.entityName]() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the [=mavenproject.entityName]
        [=mavenproject.entityName?lower_case] =
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([=mavenproject.entityName?lower_case])
                .when()
                .post("/api/[=mavenproject.entityName?lower_case]s")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract()
                .as(ENTITY_TYPE);

        // Validate the [=mavenproject.entityName] in the database
        var [=mavenproject.entityName?lower_case]List = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat([=mavenproject.entityName?lower_case]List).hasSize(databaseSizeBeforeCreate + 1);
        var test[=mavenproject.entityName] = [=mavenproject.entityName?lower_case]List.stream().filter(it -> [=mavenproject.entityName?lower_case].id.equals(it.id)).findFirst().get();
        assertThat(test[=mavenproject.entityName].description).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(test[=mavenproject.entityName].name).isEqualTo(DEFAULT_NAME);
        assertThat(test[=mavenproject.entityName].vorname).isEqualTo(DEFAULT_VORNAME);
        assertThat(test[=mavenproject.entityName].geburtsDatum).isEqualTo(DEFAULT_GEBURTS_DATUM);
        assertThat(test[=mavenproject.entityName].strasse).isEqualTo(DEFAULT_STRASSE);
        assertThat(test[=mavenproject.entityName].hausNummer).isEqualTo(DEFAULT_HAUS_NUMMER);
        assertThat(test[=mavenproject.entityName].plz).isEqualTo(DEFAULT_PLZ);
        assertThat(test[=mavenproject.entityName].land).isEqualTo(DEFAULT_LAND);
    }

    @Test
    public void create[=mavenproject.entityName]WithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the [=mavenproject.entityName] with an existing ID
        [=mavenproject.entityName?lower_case].id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body([=mavenproject.entityName?lower_case])
            .when()
            .post("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the [=mavenproject.entityName] in the database
        var [=mavenproject.entityName?lower_case]List = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat([=mavenproject.entityName?lower_case]List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void update[=mavenproject.entityName]() {
        // Initialize the database
        [=mavenproject.entityName?lower_case] =
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([=mavenproject.entityName?lower_case])
                .when()
                .post("/api/[=mavenproject.entityName?lower_case]s")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract()
                .as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the [=mavenproject.entityName?lower_case]
        var updated[=mavenproject.entityName] = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s/{id}", [=mavenproject.entityName?lower_case].id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .body()
            .as(ENTITY_TYPE);

        // Update the [=mavenproject.entityName?lower_case]
        updated[=mavenproject.entityName].description = UPDATED_DESCRIPTION;
        updated[=mavenproject.entityName].name = UPDATED_NAME;
        updated[=mavenproject.entityName].vorname = UPDATED_VORNAME;
        updated[=mavenproject.entityName].geburtsDatum = UPDATED_GEBURTS_DATUM;
        updated[=mavenproject.entityName].strasse = UPDATED_STRASSE;
        updated[=mavenproject.entityName].hausNummer = UPDATED_HAUS_NUMMER;
        updated[=mavenproject.entityName].plz = UPDATED_PLZ;
        updated[=mavenproject.entityName].land = UPDATED_LAND;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updated[=mavenproject.entityName])
            .when()
            .put("/api/[=mavenproject.entityName?lower_case]s/" + [=mavenproject.entityName?lower_case].id)
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the [=mavenproject.entityName] in the database
        var [=mavenproject.entityName?lower_case]List = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat([=mavenproject.entityName?lower_case]List).hasSize(databaseSizeBeforeUpdate);
        var test[=mavenproject.entityName] = [=mavenproject.entityName?lower_case]List.stream().filter(it -> updated[=mavenproject.entityName].id.equals(it.id)).findFirst().get();
        assertThat(test[=mavenproject.entityName].description).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(test[=mavenproject.entityName].name).isEqualTo(UPDATED_NAME);
        assertThat(test[=mavenproject.entityName].vorname).isEqualTo(UPDATED_VORNAME);
        assertThat(test[=mavenproject.entityName].geburtsDatum).isEqualTo(UPDATED_GEBURTS_DATUM);
        assertThat(test[=mavenproject.entityName].strasse).isEqualTo(UPDATED_STRASSE);
        assertThat(test[=mavenproject.entityName].hausNummer).isEqualTo(UPDATED_HAUS_NUMMER);
        assertThat(test[=mavenproject.entityName].plz).isEqualTo(UPDATED_PLZ);
        assertThat(test[=mavenproject.entityName].land).isEqualTo(UPDATED_LAND);
    }

    @Test
    public void updateNonExisting[=mavenproject.entityName]() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body([=mavenproject.entityName?lower_case])
            .when()
            .put("/api/[=mavenproject.entityName?lower_case]s/" + Long.MAX_VALUE)
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the [=mavenproject.entityName] in the database
        var [=mavenproject.entityName?lower_case]List = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat([=mavenproject.entityName?lower_case]List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void delete[=mavenproject.entityName]() {
        // Initialize the database
        [=mavenproject.entityName?lower_case] =
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([=mavenproject.entityName?lower_case])
                .when()
                .post("/api/[=mavenproject.entityName?lower_case]s")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract()
                .as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the [=mavenproject.entityName?lower_case]
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/[=mavenproject.entityName?lower_case]s/{id}", [=mavenproject.entityName?lower_case].id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var [=mavenproject.entityName?lower_case]List = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat([=mavenproject.entityName?lower_case]List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAll[=mavenproject.entityName]s() {
        // Initialize the database
        [=mavenproject.entityName?lower_case] =
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([=mavenproject.entityName?lower_case])
                .when()
                .post("/api/[=mavenproject.entityName?lower_case]s")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract()
                .as(ENTITY_TYPE);

        // Get all the [=mavenproject.entityName?lower_case]List
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem([=mavenproject.entityName?lower_case].id.intValue()))
            .body("description", hasItem(DEFAULT_DESCRIPTION.toString()))
            .body("name", hasItem(DEFAULT_NAME))
            .body("vorname", hasItem(DEFAULT_VORNAME))
            //.body("geburtsDatum", hasItem(TestUtil.formatDateTime(DEFAULT_GEBURTS_DATUM)))
            .body("strasse", hasItem(DEFAULT_STRASSE))
            .body("hausNummer", hasItem(DEFAULT_HAUS_NUMMER.intValue()))
            .body("plz", hasItem(DEFAULT_PLZ.intValue()))
            .body("land", hasItem(DEFAULT_LAND));
    }

    @Test
    public void get[=mavenproject.entityName]() {
        // Initialize the database
        [=mavenproject.entityName?lower_case] =
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([=mavenproject.entityName?lower_case])
                .when()
                .post("/api/[=mavenproject.entityName?lower_case]s")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract()
                .as(ENTITY_TYPE);

        var response = given() // Get the [=mavenproject.entityName?lower_case]
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s/{id}", [=mavenproject.entityName?lower_case].id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(ENTITY_TYPE);

        // Get the [=mavenproject.entityName?lower_case]
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s/{id}", [=mavenproject.entityName?lower_case].id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is([=mavenproject.entityName?lower_case].id.intValue()))
            .body("description", is(DEFAULT_DESCRIPTION.toString()))
            .body("name", is(DEFAULT_NAME))
            .body("vorname", is(DEFAULT_VORNAME))
            //.body("geburtsDatum", is(TestUtil.formatDateTime(DEFAULT_GEBURTS_DATUM)))
            .body("strasse", is(DEFAULT_STRASSE))
            .body("hausNummer", is(DEFAULT_HAUS_NUMMER.intValue()))
            .body("plz", is(DEFAULT_PLZ.intValue()))
            .body("land", is(DEFAULT_LAND));
    }

    @Test
    public void getNonExisting[=mavenproject.entityName]() {
        // Get the [=mavenproject.entityName?lower_case]
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/[=mavenproject.entityName?lower_case]s/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
