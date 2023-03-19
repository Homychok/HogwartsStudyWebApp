package ru.hogwarts.school.model;

import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class AppInfo {
        private String appEnv;
        private String appVersion;
        private String appEnvironment;

        public AppInfo(String appEnv, String appVersion, String appEnvironment) {
            this.appEnv = appEnv;
            this.appVersion = appVersion;
            this.appEnvironment = appEnvironment;
        }
    }

