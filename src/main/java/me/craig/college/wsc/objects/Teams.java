package me.craig.college.wsc.objects;

import java.util.ArrayList;

/* Created by Craig on 28/03/2021 */
public class Teams {

    private static final ArrayList< DataTable< Team > > TEAMS = new ArrayList<>();

    public static void addTeam(Team team) {
        TEAMS.add(team);
    }

    public static ArrayList< DataTable< Team > > getTeams() {
        return TEAMS;
    }
}
