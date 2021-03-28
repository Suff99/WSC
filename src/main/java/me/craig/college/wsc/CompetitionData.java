package me.craig.college.wsc;

import java.util.ArrayList;

/* Created by Craig on 28/03/2021 */
public class CompetitionData {
    private static final ArrayList<Team> TEAMS = new ArrayList<>();

    public static void addTeam(Team team){
        TEAMS.add(team);
    }

    public static ArrayList< Team > getTeams() {
        return TEAMS;
    }
}
