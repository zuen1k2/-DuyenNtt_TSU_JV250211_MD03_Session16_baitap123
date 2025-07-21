package session16_exercise;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    public Student() {
    }
    public Student(int studentId, String fullName, LocalDate dateOfBirth, String email) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void display (){
        System.out.printf("| Student ID: %5d | Full Name: %20s | DateOfBirth: %15s | Email: %20s\n",studentId,fullName,dateOfBirth,email);
    }
}
