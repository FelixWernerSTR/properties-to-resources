package [=mavenproject.groupId].web.rest;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import [=mavenproject.groupId].IntegrationTest;

/**
 * Integration tests for the {@link [=mavenproject.entityName]Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class [=mavenproject.entityName]ResourceIT {
	
	private static final Logger log = LoggerFactory.getLogger([=mavenproject.entityName]ResourceIT.class);

    private static final String DEFAULT_NAME = "my entity";

    @Autowired
    private MockMvc rest[=mavenproject.entityName]MockMvc;

    @BeforeEach
    public void initTest() {

    }
    
    @Test
    void get[=mavenproject.entityName]() throws Exception {

        // Get the [=mavenproject.entityName]
        rest[=mavenproject.entityName]MockMvc
            .perform(get("/[=mavenproject.entityName?lower_case]"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

}
