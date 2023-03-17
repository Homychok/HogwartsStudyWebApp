package com.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

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
    public  Faculty findFaculty (long facultyId) {
        return facultys.get(facultyId);
    }
    public Faculty editFaculty(Faculty faculty) {
        facultys.put(faculty.getFacultyId(), faculty);
        return faculty;
    }
    public Faculty deleteFaculty(long facultyId) {
        return facultys.remove(facultyId);
    }


}
