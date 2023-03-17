package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.HouseService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("faculty")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("{id}") // GET http://localhost:8080/faculty/3
    public ResponseEntity<Faculty> getFacultyInfo (@PathVariable Long facultyId) {
        Faculty faculty = houseService.findFaculty(facultyId);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }
    //    @GetMapping// GET http://localhost:8080/faculty
//    public ResponseEntity<Collection<Faculty>> getAllFacultys() {
//        return ResponseEntity.ok(houseService.getAllFacultys());
//    }
    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFacultiesByUniqColor(@RequestParam String facultyColor) {
        return ResponseEntity.ok(houseService.getAllFacultiesByColor(facultyColor));
//                .stream()
//                .filter(faculty -> faculty.getFacultyColor().equals(facultyColor))
//                .collect(Collectors.toList()));
    }
    @PostMapping // POST http://localhost:8080/faculty
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return houseService.createFaculty(faculty);
    }
    @PutMapping  // PUT http://localhost:8080/faculty
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = houseService.updateFaculty(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }
    @DeleteMapping("{id}") // DELETE http://localhost:8080/faculty/3
    public ResponseEntity deleteFaculty(@PathVariable Long facultyId) {
        houseService.deleteFaculty(facultyId);
/*        if (deletedFaculty == null) {
//            return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

 */
        return ResponseEntity.ok().build();
    }
}
