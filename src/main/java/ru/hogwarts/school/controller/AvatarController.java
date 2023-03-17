package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.AvatarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/student/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAvatar(@PathVariable Long avatarId,
                                               @RequestParam MultipartFile avatarDTO) throws IOException {
        if(!avatarService.getExtension(avatarDTO.getOriginalFilename()).equals("png")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File isn't \".png\" format!");
        }
        avatarService.updateAvatar(avatarId, avatarDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{avatarId}/preview")
    public ResponseEntity<byte[]> downloadAvatar (@PathVariable Long avatarId) {
        AvatarDTO avatarDTO = AvatarDTO.fromAvatar(avatarService.getAvatar(avatarId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarDTO.getMediaType()));
        headers.setContentLength(avatarDTO.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatarDTO.getData());
    }

    @GetMapping("/student/{avatarId}")
    public void downloadAvatar(@PathVariable Long avatarId, HttpServletResponse response) throws IOException {
        AvatarDTO avatarDTO = AvatarDTO.fromAvatar(avatarService.getAvatar(avatarId));

        Path path = Path.of(avatarDTO.getFilePath());

        try(InputStream inputStream = Files.newInputStream(path);
            OutputStream outputStream = response.getOutputStream()
        ){
            response.setStatus(200);
            response.setContentType(avatarDTO.getMediaType());
            response.setContentLength((int) avatarDTO.getFileSize());
            inputStream.transferTo(outputStream);
        }
    }

}
