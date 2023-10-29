package [=mavenproject.groupId].config.mock;

import [=mavenproject.groupId].config.[=mavenproject.entityName]Properties;
import io.quarkus.test.Mock;
import io.smallrye.config.SmallRyeConfig;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.eclipse.microprofile.config.Config;
import org.mockito.Mockito;

@ApplicationScoped
public class [=mavenproject.entityName]PropertiesMock {

    @Inject
    Config config;

    @Produces
    @ApplicationScoped
    @Mock
    [=mavenproject.entityName]Properties properties() {
    	[=mavenproject.entityName]Properties [=mavenproject.entityName?lower_case]Properties = config.unwrap(SmallRyeConfig.class).getConfigMapping([=mavenproject.entityName]Properties.class);
    	[=mavenproject.entityName]Properties spy[=mavenproject.entityName]Properties = Mockito.spy([=mavenproject.entityName?lower_case]Properties);

    	[=mavenproject.entityName]Properties.Info spyInfo = Mockito.spy([=mavenproject.entityName?lower_case]Properties.info());
    	[=mavenproject.entityName]Properties.Info.Swagger spySwagger = Mockito.spy([=mavenproject.entityName?lower_case]Properties.info().swagger());
        Mockito.when(spy[=mavenproject.entityName]Properties.info()).thenReturn(spyInfo);
        Mockito.when(spy[=mavenproject.entityName]Properties.info().swagger()).thenReturn(spySwagger);

        return spy[=mavenproject.entityName]Properties;
    }
}
