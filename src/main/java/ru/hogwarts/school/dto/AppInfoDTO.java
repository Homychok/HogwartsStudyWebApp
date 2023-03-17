package ru.hogwarts.school.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.model.AppInfo;

    @Data
    @NoArgsConstructor
    public class AppInfoDTO {
        private String appName;
        private String appVersion;
        private String appEnvironment;

        public static AppInfoDTO fromAppInfo(AppInfo appInfo) {
            AppInfoDTO dto = new AppInfoDTO();
            dto.setAppName(appInfo.getAppName());
            dto.setAppVersion(appInfo.getAppVersion());
            dto.setAppEnvironment(appInfo.getAppEnvironment());
            return dto;
        }
}
