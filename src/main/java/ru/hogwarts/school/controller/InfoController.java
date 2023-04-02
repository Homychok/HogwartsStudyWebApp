package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.dto.AppInfoDTO;
import ru.hogwarts.school.model.AppInfo;
import ru.hogwarts.school.service.AppInfoService;

@RequestMapping("/info")
@RestController
public class InfoController {
    private final AppInfoService appInfoService;

    public InfoController(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @GetMapping("/appInfo")
    public ResponseEntity<AppInfoDTO>  appInfo() {
        return ResponseEntity.ok(appInfoService.getAppInfo());
    }
}
