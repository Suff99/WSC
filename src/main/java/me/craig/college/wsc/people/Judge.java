package me.craig.college.wsc.people;

import java.util.ArrayList;

/* Created by Craig on 12/03/2021 */
public class Judge {

    private final ArrayList< Teachers > JUDGES = new ArrayList<>();

    public void addJudge(String address, int telephone, String status){
        JUDGES.add(new Teachers(address, telephone, status));
    }

    public ArrayList< Teachers > getJudges() {
        return JUDGES;
    }

}
