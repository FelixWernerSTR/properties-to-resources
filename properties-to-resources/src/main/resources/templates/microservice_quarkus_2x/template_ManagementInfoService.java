package [=mavenproject.groupId].service.monitoring;

import [=mavenproject.groupId].config.[=mavenproject.entityName]Properties;
import [=mavenproject.groupId].service.dto.ManagementInfoDTO;
import io.quarkus.runtime.configuration.ProfileManager;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Provides information for management/info resource
 */
@ApplicationScoped
public class ManagementInfoService {

    private final [=mavenproject.entityName]Properties [=mavenproject.entityName?lower_case]Properties;

    @Inject
    public ManagementInfoService([=mavenproject.entityName]Properties [=mavenproject.entityName?lower_case]Properties) {
        this.[=mavenproject.entityName?lower_case]Properties = [=mavenproject.entityName?lower_case]Properties;
    }

    public ManagementInfoDTO getManagementInfo() {
        var info = new ManagementInfoDTO();
        if ([=mavenproject.entityName?lower_case]Properties.info().swagger().enable()) {
            info.activeProfiles.add("swagger");
        }
        info.activeProfiles.add(ProfileManager.getActiveProfile());
        info.displayRibbonOnProfiles = ProfileManager.getActiveProfile();
        return info;
    }
}
