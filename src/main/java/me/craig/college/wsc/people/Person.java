package me.craig.college.wsc.people;

/* Created by Craig on 19/03/2021 */
public class Person {
    private String address = "";
    private long telephone = 0;
    private String status = Status.NONE.getStatus();

    public Person(String address, long telephone, String status) {
        this.address = address;
        this.telephone = telephone;
        this.status = status;
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

    public long getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
