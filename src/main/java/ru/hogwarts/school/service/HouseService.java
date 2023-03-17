package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private FacultyRepository facultyRepository;

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
        Faculty faculty = facultyDTO.toFaculty();
        Faculty createdFaculty = facultyRepository.save(faculty);
        return FacultyDTO.fromFaculty(createdFaculty);
    }

    public FacultyDTO findFaculty(Long facultyId){
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null){
            return FacultyDTO.fromFaculty(faculty);
        }
        return null;
    }

    public FacultyDTO updateFaculty(FacultyDTO facultyDTO){
        Faculty faculty = facultyDTO.toFaculty();
        return FacultyDTO.fromFaculty(facultyRepository.save(faculty));
    }

    public void deleteFaculty(Long facultyId){
        facultyRepository.deleteById(facultyId);
    }
    public FacultyDTO getFacultyById(Long facultyId) {
        return FacultyDTO.fromFaculty(facultyRepository.findById(facultyId).get());
    }
    public Collection<FacultyDTO> getFaculties() {
        return facultyRepository.findAll()
                .stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList());
    }
    public Collection<FacultyDTO> getFacultyByColor(String facultyColor){
        return facultyRepository.findByFacultyColorIgnoreCase(facultyColor)
                .stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList()); }

    public Collection<FacultyDTO> getFacultyByName (String facultyName) {
        return facultyRepository.findByFacultyNameContainsIgnoreCase(facultyName).stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByFacultyId(Long facultyId) {
        List<Student> students = facultyRepository.findById(facultyId).get().getStudents();
        List<StudentDTO> studentsDTO = new ArrayList<>();
        for(Student student : students) {
            StudentDTO studentDTO = StudentDTO.fromStudent(student);
            studentsDTO.add(studentDTO);
        }
        return studentsDTO;
    }
}
