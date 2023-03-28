package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.AppInfo;

@RestController
public class InfoController {
    @Value("${app.env}")
    private String appEnv;
    @Value("${app.version}")
    private String appVersion;
    @Value("${app.name}")
    private String appName;
    @GetMapping("/appInfo")
    public ResponseEntity<AppInfo> getThisInfo() {
        AppInfo appInfoThisInfo = new AppInfo(appEnv, appVersion, appName);
        return ResponseEntity.ok(appInfoThisInfo);
    }
}
