package ru.hogwarts.school.dto;

import lombok.Data;
import ru.hogwarts.school.model.AppInfo;

@Data
public class AppInfoDTO {
    private String appName;
    private String appVersion;
    private String appEnvironment;


    public static AppInfoDTO fromAppInfo(AppInfo appInfo) {
        AppInfoDTO dto = new AppInfoDTO();
        dto.setAppName(AppInfo.APP_NAME);
        dto.setAppVersion(AppInfo.APP_VERSION);
        dto.setAppEnvironment(appInfo.getAppEnvironment());
        return dto;
    }
}
