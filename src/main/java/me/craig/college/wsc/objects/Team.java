package me.craig.college.wsc.objects;

import com.google.gson.InstanceCreator;
import me.craig.college.wsc.objects.people.Person;
import me.craig.college.wsc.objects.people.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* Created by Craig on 26/03/2021 */
public class Team implements DataTable< Team >, InstanceCreator< Team > {
    private final transient ArrayList< DataTable< Person< Student > > > students = new ArrayList<>();
    private String teamName = "";
    private String college = "";
    private String area = "";

    public Team setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public Team setCollege(String college) {
        this.college = college;
        return this;
    }

    public String college() {
        return college;
    }

    public String teamName() {
        return teamName;
    }

    public void addStudent(long ecNumber, String address, long telephone, String status, Team team) {
        Student student = new Student(ecNumber, address, telephone, status, team.teamName());
        students.add(student);
    }

    public void addStudent(long ecNumber, String address, long telephone, String status, String teamName) {
        Student student = new Student(ecNumber, address, telephone, status, teamName);
        students.add(student);
    }

    public ArrayList< DataTable< Person< Student > > > getStudents() {
        return students;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String area() {
        return area;
    }

    @Override
    public String[] toDataRow() {
        return new String[]{teamName(), college(), String.valueOf(getStudents().size()), area()};
    }

    @Override
    public void setDataHeaders(JTable dataTable, Object[][] data) {
        dataTable.setModel(new DefaultTableModel(data, new String[]{"Team Name", "College", "Members", "Area"}));
    }

    @Override
    public Team getAsSelf() {
        return this;
    }

    @Override
    public Team createInstance(Type type) {
        return new Team();
    }
}
