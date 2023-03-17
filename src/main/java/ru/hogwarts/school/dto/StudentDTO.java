package ru.hogwarts.school.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.Faculty;


@Data
public class StudentDTO {
    private Long studentId;
    private String studentName;
    private Integer studentAge;
    private Long facultyId;


    public static StudentDTO fromStudent(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentAge(student.getStudentAge());
        studentDTO.setFacultyId(student.getFaculty().getFacultyId());
        return studentDTO;
    }

    public Student toStudent() {
        Student student = new Student();
        student.setStudentId(this.getStudentId());
        student.setStudentName(this.getStudentName());
        student.setStudentAge(this.getStudentAge());
        return student;
    }
}
