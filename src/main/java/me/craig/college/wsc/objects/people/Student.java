package me.craig.college.wsc.objects.people;

import me.craig.college.wsc.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/* Created by Craig on 19/03/2021 */
public class Student extends Person< Student > {

    private long studentID;
    private String teamName;

    public Student(long studentID, String address, long telephone, String status, String teamName) {
        super(address, telephone, status);
        this.studentID = studentID;
        this.teamName = teamName;
    }

    public long getStudentID() {
        return studentID;
    }

    @Override
    public String[] toDataRow() {
        return new String[]{teamName(), String.valueOf(getStudentID()), getAddress(), getStatus(), Utils.censorPhoneNumber(String.valueOf(getTelephone()))};
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

    public Student getStudent() {
        return this;
    }
}
