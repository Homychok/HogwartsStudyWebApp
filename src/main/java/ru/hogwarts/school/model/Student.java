package ru.hogwarts.school.model;

import java.util.Objects;

public class Student {
    private Long studentId;
    private String studentName;
    private int studentAge;

    public Student() {
    }

    public Student(Long studentId, String studentName, int studentAge) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentAge = studentAge;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getStudentAge() == student.getStudentAge() && Objects.equals(getStudentId(), student.getStudentId()) && Objects.equals(getStudentName(), student.getStudentName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getStudentName(), getStudentAge());
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentAge=" + studentAge +
                '}';
    }
}