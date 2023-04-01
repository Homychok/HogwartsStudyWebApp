package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private FacultyRepository facultyRepository;
    public static final Logger logger = LoggerFactory.getLogger(HouseService.class);
    public HouseService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /*    private final HashMap<Long, Faculty> facultys = new HashMap<>();
        private long lastId = 0;

     */
//    public Faculty createFaculty (Faculty faculty) {
///*        faculty.setFacultyId(++lastId);
//        facultys.put(lastId, faculty);
//        return faculty;
//
// */
//        return facultyRepository.save(faculty);
//    }
//    public Faculty findFaculty (Long facultyId) {
///*        if (facultys.containsKey(facultyId)) {
//            return facultys.get(facultyId);
//        }
//        return null;
//
// */
//        return facultyRepository.findById(facultyId).get();
//    }
//    public Faculty editFaculty(Faculty faculty) {
// /*       if (facultys.containsKey(faculty.getFacultyId())) {
//            facultys.put(faculty.getFacultyId(), faculty);
//            return faculty;
//        }
//        return null;
//
//  */
//        return facultyRepository.save(faculty);
//    }
//    public void deleteFaculty(long facultyId) {
///*        if (facultys.containsKey(facultyId)) {
//            return facultys.remove(facultyId);
//        }return null;
//
// */
//        facultyRepository.deleteById(facultyId);
//    }
//    public Collection<Faculty> getAllFacultys(String facultyColor) {
//        return facultyRepository.findAll().stream().filter(faculty ->
//                faculty.getFacultyColor() == facultyColor).collect(Collectors.toList());
//    }

    public FacultyDTO createFaculty(FacultyDTO facultyDTO){
        logger.info("Creating a new faculty");
        Faculty faculty = facultyDTO.toFaculty();
        Faculty createdFaculty = facultyRepository.save(faculty);
        logger.info("New faculty has been created");
        return FacultyDTO.fromFaculty(createdFaculty);
    }

    public FacultyDTO findFaculty(Long id){
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null){
            return FacultyDTO.fromFaculty(faculty);
        }
        return null;
    }

    public FacultyDTO updateFaculty(FacultyDTO facultyDTO){
        logger.info("Updating a faculty");
        Faculty faculty = facultyDTO.toFaculty();
        Faculty updatedFaculty = facultyRepository.save(faculty);
        logger.info("Faculty has been updated");
        return FacultyDTO.fromFaculty(updatedFaculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Deleting faculty with id: " + id);
        facultyRepository.deleteById(id);
        logger.info("Faculty with id: " + id + " has been deleted");
    }

    public FacultyDTO getFacultyById(Long id) {
        logger.info("Getting faculty with id: " + id);
        return FacultyDTO.fromFaculty(facultyRepository.findById(id).get());
    }
    public Collection<FacultyDTO> getFaculties() {
        logger.info("Getting all faculties");
        return facultyRepository.findAll()
                .stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList());
    }
    public Collection<FacultyDTO> getFacultyByColor(String facultyColor){
        logger.info("Getting all faculties by color: " + facultyColor);
        return facultyRepository.findByFacultyColorIgnoreCase(facultyColor)
                .stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList()); }

    public Collection<FacultyDTO> getFacultyByName (String facultyName) {
        logger.info("Getting faculty by name: " + facultyName);
        return facultyRepository.findByFacultyNameContainsIgnoreCase(facultyName).stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByFacultyId(Long id) {
        logger.info("Getting all students by faculty with id: " + id);
        List<Student> students = facultyRepository.findById(id).get().getStudents();
        List<StudentDTO> studentsDTO = new ArrayList<>();
        for(Student student : students) {
            StudentDTO studentDTO = StudentDTO.fromStudent(student);
            studentsDTO.add(studentDTO);
        }
        return studentsDTO;
    }
}
