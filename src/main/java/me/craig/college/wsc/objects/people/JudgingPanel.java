package me.craig.college.wsc.objects.people;

import me.craig.college.wsc.data.DataHandler;
import me.craig.college.wsc.objects.DataTable;

import java.util.ArrayList;

/* Created by Craig on 12/03/2021 */
public class JudgingPanel {

    private static final ArrayList< DataTable< Person< Teacher > > > JUDGES = new ArrayList<>();

    public static ArrayList< DataTable< Person< Teacher > > > getJudges() {
        return JUDGES;
    }

    public static void addJudge(String address, long telephone, long teachingId, String status) {
        Teacher e = new Teacher(address, telephone, teachingId, status);
        JUDGES.add(e);
        DataHandler.judgeToJson(e);
    }

    public static void addJudge(Teacher teacher) {
        JUDGES.add(teacher);
    }

}
