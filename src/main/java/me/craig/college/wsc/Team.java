package me.craig.college.wsc;

import me.craig.college.wsc.people.Student;

import java.util.ArrayList;

/* Created by Craig on 26/03/2021 */
public class Team {

    private String teamName = "";
    private final ArrayList< Student > students = new ArrayList<>();

    public Team(){

    }

    public Team setTeamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public String teamName() {
        return teamName;
    }

    public void addStudent(String ecNumber, String address, long telephone, String status){
        Student student = new Student(ecNumber, address, telephone, status);
        students.add(student);
    }

    public ArrayList< Student > getStudents() {
        return students;
    }
}
