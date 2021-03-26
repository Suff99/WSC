package me.craig.college.wsc;

/* Created by Craig on 26/03/2021 */
public enum College {
    SIGHTHILL("Sighthill Campus"), MILTON("Milton Campus"), GRANTON("Granton Campus"), MIDLOTHIAN("Midlothian Campus");

    private final String collegeName;

    College(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }
}
