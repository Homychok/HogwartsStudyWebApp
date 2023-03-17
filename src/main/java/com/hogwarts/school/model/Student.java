package com.hogwarts.school.model;

import java.util.Objects;

public class Student {
    private Long studentId;
    private String name;
    private int age;

    public Student() {
    }
    public Student(Long studentId, String name, int age) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getAge() == student.getAge() && Objects.equals(getStudentId(), student.getStudentId()) && Objects.equals(getName(), student.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getName(), getAge());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + studentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
