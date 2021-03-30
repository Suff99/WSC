package me.craig.college.wsc.objects.people;

import me.craig.college.wsc.Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/* Created by Craig on 19/03/2021 */
public class Student extends Person<Student> {

    private String studentID, teamName;

    public Student(String studentID, String address, long telephone, String teamName, String status) {
        super(address, telephone, status);
        this.studentID = studentID;
        this.teamName = teamName;
    }

    public String getStudentID() {
        return studentID;
    }

    @Override
    public String[] toDataRow() {
        return new String[]{teamName(), getStudentID(), getAddress(), getStatus(), Utility.censorPhoneNumber(String.valueOf(getTelephone()))};
    }

    public void setTeam(String teamName) {
        this.teamName = teamName;
    }

    public String teamName() {
        return teamName;
    }

    @Override
    public void setDataHeaders(JTable dataTable, Object[][] data) {
        dataTable.setModel(new DefaultTableModel(data, new String[]{"Team Name", "Student", "Address", "Status", "Telephone"}));
    }
}
