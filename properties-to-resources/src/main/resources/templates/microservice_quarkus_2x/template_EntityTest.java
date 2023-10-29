package [=mavenproject.groupId].domain;

import static org.assertj.core.api.Assertions.assertThat;

import [=mavenproject.groupId].TestUtil;
import org.junit.jupiter.api.Test;

public class [=mavenproject.entityName]Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier([=mavenproject.entityName].class);
        [=mavenproject.entityName] [=mavenproject.entityName?lower_case]1 = new [=mavenproject.entityName]();
        [=mavenproject.entityName?lower_case]1.id = 1L;
        [=mavenproject.entityName] [=mavenproject.entityName?lower_case]2 = new [=mavenproject.entityName]();
        [=mavenproject.entityName?lower_case]2.id = [=mavenproject.entityName?lower_case]1.id;
        assertThat([=mavenproject.entityName?lower_case]1).isEqualTo([=mavenproject.entityName?lower_case]2);
        [=mavenproject.entityName?lower_case]2.id = 2L;
        assertThat([=mavenproject.entityName?lower_case]1).isNotEqualTo([=mavenproject.entityName?lower_case]2);
        [=mavenproject.entityName?lower_case]1.id = null;
        assertThat([=mavenproject.entityName?lower_case]1).isNotEqualTo([=mavenproject.entityName?lower_case]2);
    }
}
