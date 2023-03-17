package ru.hogwarts.school.model;

import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class AppInfo {
        private String appName;
        private String appVersion;
        private String appEnvironment;

        public AppInfo(String appName, String appVersion, String appEnvironment) {
            this.appName = appName;
            this.appVersion = appVersion;
            this.appEnvironment = appEnvironment;
        }
    }

