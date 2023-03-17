package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.dto.AppInfoDTO;
import ru.hogwarts.school.model.AppInfo;

@RestController
public class InfoController {
    @Value("${app.env}")
    private String appEnv;

    @GetMapping("/appInfo")
    public AppInfoDTO getAppInfo() {
        AppInfo appInfo = new AppInfo("hogwarts", "0.0.1", appEnv);
        return AppInfoDTO.fromAppInfo(appInfo);
    }
}
