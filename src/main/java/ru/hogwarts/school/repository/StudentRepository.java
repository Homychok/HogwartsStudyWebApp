package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStudentAge(Integer studentAge);
    List<Student> findByStudentAgeBetween(Integer studentAgeMin, Integer studentAgeMax);
    @Query(value = "SELECT SUM(sum) AS count FROM student AS sum", nativeQuery = true)
    Long getSumStudentAge();

    @Query(value = "SELECT AVG(sum.age) FROM student AS sum", nativeQuery = true)
    Long getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY age ASC LIMIT 5", nativeQuery = true)
    Collection<Student> getFiveYoungestStudents();
}
