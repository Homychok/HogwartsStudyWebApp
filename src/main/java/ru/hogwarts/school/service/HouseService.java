package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private final FacultyRepository facultyRepository;

    public HouseService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /*    private final HashMap<Long, Faculty> facultys = new HashMap<>();
        private long lastId = 0;

     */
    public Faculty createFaculty (Faculty faculty) {
/*        faculty.setFacultyId(++lastId);
        facultys.put(lastId, faculty);
        return faculty;

 */
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty (Long facultyId) {
/*        if (facultys.containsKey(facultyId)) {
            return facultys.get(facultyId);
        }
        return null;

 */
        return facultyRepository.findById(facultyId).get();
    }
    public Faculty updateFaculty(Faculty faculty) {
 /*       if (facultys.containsKey(faculty.getFacultyId())) {
            facultys.put(faculty.getFacultyId(), faculty);
            return faculty;
        }
        return null;

  */
        return facultyRepository.save(faculty);
    }
    public void deleteFaculty(Long facultyId) {
/*        if (facultys.containsKey(facultyId)) {
            return facultys.remove(facultyId);
        }return null;

 */
        facultyRepository.deleteById(facultyId);
    }
    public Collection<Faculty> getAllFacultys(String facultyColor) {
        return facultyRepository.findAll().stream().filter(faculty ->
                faculty.getFacultyColor() == facultyColor).collect(Collectors.toList());
    }
    public Collection<Faculty> getAllFacultiesByColor(String facultyColor) {
        return facultyRepository.findByFacultyColor(facultyColor);
    }
}
