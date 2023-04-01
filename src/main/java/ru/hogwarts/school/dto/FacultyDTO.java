package ru.hogwarts.school.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Data
public class FacultyDTO {
    private Long id;
    private String facultyName;
    private String facultyColor;

    public static FacultyDTO fromFaculty(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setFacultyName(faculty.getFacultyName());
        facultyDTO.setFacultyColor(faculty.getFacultyColor());
        return facultyDTO;
    }

    public Faculty toFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(this.getId());
        faculty.setFacultyName(this.getFacultyName());
        faculty.setFacultyColor(this.getFacultyColor());
        return faculty;
    }
}
