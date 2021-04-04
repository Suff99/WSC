package me.craig.college.wsc.objects.people;

import me.craig.college.wsc.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/* Created by Craig on 19/03/2021 */
public class Teacher extends Person< Teacher > {

    private long teachingId = 0;

    public Teacher(String address, long telephone, long teachingId, String status) {
        super(address, telephone, status);
        this.teachingId = teachingId;
    }

    public long getTeachingId() {
        return teachingId;
    }

    @Override
    public String[] toDataRow() {
        return new String[]{String.valueOf(getTeachingId()), Utils.censorPhoneNumber(String.valueOf(getTelephone())), getAddress(), getStatus()};
    }

    @Override
    public void setDataHeaders(JTable dataTable, Object[][] data) {
        dataTable.setModel(new DefaultTableModel(data, new String[]{"Teaching ID", "Telephone", "Address", "Status"}));
    }
}
