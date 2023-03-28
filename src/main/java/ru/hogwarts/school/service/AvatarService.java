package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class AvatarService {
    @Value("${application.avatars.folder}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    public static final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void updateAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.getStudentById(studentId).toStudent();
        logger.info("Uploading avatar for student with id: " + studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(is, 1024);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os, 1024)
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }

        AvatarDTO avatarDTO = AvatarDTO.fromAvatar(getAvatar(studentId));

        avatarDTO.setFilePath(filePath.toString());
        avatarDTO.setFileSize(file.getSize());
        avatarDTO.setMediaType(file.getContentType());
        avatarDTO.setData(file.getBytes());
        avatarDTO.setStudentId(student.getStudentId());

        Avatar avatar = avatarDTO.toAvatar();
        avatar.setStudent(student);
        avatarRepository.save(avatar);
        logger.info("Avatar for student with id: " + studentId + " upload successfully");
    }

    public Avatar getAvatar(Long studentId) {
        logger.info("Getting avatar for student with id " + studentId);
        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
