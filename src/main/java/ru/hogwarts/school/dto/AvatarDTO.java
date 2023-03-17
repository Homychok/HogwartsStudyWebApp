package ru.hogwarts.school.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.model.Avatar;

@Data
@NoArgsConstructor
public class AvatarDTO {
    private Long avatarId;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    private Long studentId;

    public static AvatarDTO fromAvatar (Avatar avatar) {
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setAvatarId(avatar.getAvatarId());
        avatarDTO.setFilePath(avatar.getFilePath());
        avatarDTO.setFileSize(avatar.getFileSize());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setData(avatar.getData());
        avatarDTO.setStudentId(avatar.getStudent().getStudentId());
        return avatarDTO;
    }

    public Avatar toAvatar() {
        Avatar avatar = new Avatar();
        avatar.setAvatarId(this.getAvatarId());
        avatar.setFilePath(this.getFilePath());
        avatar.setFileSize(this.getFileSize());
        avatar.setMediaType(this.getMediaType());
        avatar.setData(this.getData());
        return avatar;
    }
}
