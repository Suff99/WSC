package me.craig.college.wsc.objects.people;

import me.craig.college.wsc.objects.DataTable;

import java.util.ArrayList;

/* Created by Craig on 12/03/2021 */
public class Judge {

    private static final ArrayList< DataTable< Person< Teacher > > > JUDGES = new ArrayList<>();

    public static ArrayList< DataTable< Person< Teacher > > > getJudges() {
        return JUDGES;
    }

    public void addJudge(String address, int telephone, String status) {
        JUDGES.add(new Teacher(address, telephone, status));
    }

}
