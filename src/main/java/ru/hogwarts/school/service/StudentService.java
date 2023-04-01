package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {

    /*private final HashMap<Long, Student> students = new HashMap<>();
       private long lastId = 0; */
    private final StudentRepository studentRepository;
    private FacultyRepository facultyRepository;
    public static final Logger logger = LoggerFactory.getLogger(StudentService.class.getName());

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //    public Student createStudent (Student student) {
///*        student.setStudentId(++lastId);
////        students.put(lastId, student);
//       return student;
//       */
//        return studentRepository.save(student);
//    }
//    public  Student findStudent (Long studentId) {
///*        if (students.containsKey(studentId)) {
////            return students.get(studentId);
////        }
//       return null;
//        */
//        return studentRepository.findById(studentId).get();
//    }
//    public Student editStudent(Student student) {
///*        if (students.containsKey(student.getStudentId())) {
//            students.put(student.getStudentId(), student);
//            return student;
//        }
//        return null;
//
// */
//        return studentRepository.save(student);
//    }
//    public void deleteStudent(Long studentId) {
///*        if (students.containsKey(studentId)) {
//            return students.remove(studentId);
//        }return null;
//
// */
//        studentRepository.deleteById(studentId);
//    }
//    public Collection<Student> getAllStudents(int studentAge) {
//        return studentRepository.findAll().stream().filter(student -> student.getStudentAge() == studentAge
//        ).collect(Collectors.toList());
//    }
    @Autowired
    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        logger.info("Creating a new student");
        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId()).get();
        Student student = studentDTO.toStudent();
        student.setFaculty(faculty);
        Student createdStudent = studentRepository.save(student);
        logger.info("New student has been created");
        return StudentDTO.fromStudent(createdStudent);
    }

    public StudentDTO updateStudent(StudentDTO studentDTO) {
        logger.info("Updating a student");
        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId()).get();
        Student student = studentDTO.toStudent();
        student.setFaculty(faculty);
        Student updatedStudent = studentRepository.save(student);
        logger.info("Student has been updated");
        return StudentDTO.fromStudent(updatedStudent);
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with id: " + id);
        studentRepository.deleteById(id);
        logger.info("Student with id: " + id + " has been deleted");
    }

    public StudentDTO getStudentById(Long id) {
        logger.info("Getting student with id: " + id);
        return StudentDTO.fromStudent(studentRepository.findById(id).get());
    }

    public Collection<StudentDTO> getStudents(Pageable pageable) {
        logger.info("Getting all students");
        return studentRepository.findAll(pageable).getContent()
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByAge(int studentAge) {
        logger.info("Getting all students by age: " + studentAge);
        return studentRepository.findByStudentAge(studentAge)
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByAgeBetween(int studentAgeMin, int studentAgeMax) {
        logger.info("Getting all students by age between: " + studentAgeMin + "-" + studentAgeMax);
        return studentRepository.findByStudentAgeBetween(studentAgeMin, studentAgeMax)
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }

    public FacultyDTO getFacultyByStudentId(Long id) {
        logger.info("Getting faculty by student id:" + id);
        Faculty faculty = facultyRepository.findById(getStudentById(id).getFacultyId()).get();
        return FacultyDTO.fromFaculty(faculty);
    }
    public Long getSumStudentAge() {
        logger.info("Getting total students count");
        return studentRepository.getSumStudentAge();
    }

    public Long getAverageAge() {
        logger.info("Getting average age by all students");
        return studentRepository.getAverageAge();
    }

    public Collection<StudentDTO> getFiveYoungestStudents() {
        logger.info("Getting 5 youngest students");
        return studentRepository.getFiveYoungestStudents()
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }
}
