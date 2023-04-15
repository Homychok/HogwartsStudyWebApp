package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.AppInfoDTO;
import ru.hogwarts.school.model.AppInfo;

@Service
public class AppInfoService {
    @Value("${app.env}")
    private String env;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public AppInfoDTO getAppInfo() {
        logger.info("Was invoked method for get info");
        AppInfo appInfo = new AppInfo();
        appInfo.setAppEnvironment(env);

        return AppInfoDTO.fromAppInfo(appInfo);
    }
}
