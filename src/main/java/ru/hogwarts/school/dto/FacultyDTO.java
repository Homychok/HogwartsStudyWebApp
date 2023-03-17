package ru.hogwarts.school.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Data
public class FacultyDTO {
    private Long facultyId;
    private String facultyName;
    private String facultyColor;

    public static FacultyDTO fromFaculty(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyId(faculty.getFacultyId());
        facultyDTO.setFacultyName(faculty.getFacultyName());
        facultyDTO.setFacultyColor(faculty.getFacultyColor());
        return facultyDTO;
    }

    public Faculty toFaculty() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(this.getFacultyId());
        faculty.setFacultyName(this.getFacultyName());
        faculty.setFacultyColor(this.getFacultyColor());
        return faculty;
    }
}
