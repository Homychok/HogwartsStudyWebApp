package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;

@Service
public class HouseService {
    private final HashMap<Long, Faculty> facultys = new HashMap<>();
    private long lastId = 0;
    public Faculty createFaculty (Faculty faculty) {
        faculty.setFacultyId(++lastId);
        facultys.put(lastId, faculty);
        return faculty;
    }
    public Faculty findFaculty (long facultyId) {
        if (facultys.containsKey(facultyId)) {
            return facultys.get(facultyId);
        }
        return null;
    }
    public Faculty editFaculty(Faculty faculty) {
        if (facultys.containsKey(faculty.getFacultyId())) {
            facultys.put(faculty.getFacultyId(), faculty);
            return faculty;
        }
        return null;
    }
    public Faculty deleteFaculty(long facultyId) {
        if (facultys.containsKey(facultyId)) {
            return facultys.remove(facultyId);
        }return null;
    }
    public Collection<Faculty> getAllFacultys() {
        return facultys.values();
    }
}
