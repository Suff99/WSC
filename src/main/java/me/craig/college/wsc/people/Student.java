package me.craig.college.wsc.people;

/* Created by Craig on 19/03/2021 */
public class Student extends Person {

    private String studentID = String.valueOf(0);

    public Student(String studentID, String address, long telephone, String status) {
        super(address, telephone, status);
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }
}
