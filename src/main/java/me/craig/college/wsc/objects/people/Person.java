package me.craig.college.wsc.objects.people;

import me.craig.college.wsc.objects.DataTable;

import javax.swing.*;

/* Created by Craig on 19/03/2021 */
public class Person< S extends Person > implements DataTable< Person< S > > {
    private String address = "";
    private long telephone = 0;
    private String status = Status.NONE.getStatus();

    public Person(String address, long telephone, String status) {
        this.address = address;
        this.telephone = telephone;
        this.status = status;
    }

    public long getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String[] toDataRow() {
        return new String[0];
    }

    @Override
    public void setDataHeaders(JTable dataTable, Object[][] data) {

    }

    @Override
    public Person< S > getAsSelf() {
        return this;
    }


    public enum Status {
        FULL_TIME("Full Time"), PART_TIME("Part Time"), APPRENTICE("Apprentice"), NONE("None");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
