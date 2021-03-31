package me.craig.college.wsc;

import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.Team;

import java.util.ArrayList;

/* Created by Craig on 28/03/2021 */
public class CompetitionData {

    private static final ArrayList< DataTable< Team > > TEAMS = new ArrayList<>();
    public static String[] SUBJECTS = new String[]{"Accounting and Finance",
            "Aeronautical and Manufacturing Engineering",
            "Agriculture and Forestry",
            "Anatomy and Physiology",
            "Anthropology",
            "Archaeology",
            "Architecture",
            "Art and Design",
            "Biological Sciences",
            "Building",
            "Business and Management Studies",
            "Chemical Engineering",
            "Chemistry",
            "Civil Engineering",
            "Classics and Ancient History",
            "Communication and Media Studies",
            "Complementary Medicine",
            "Computer Science",
            "Counselling",
            "Creative Writing",
            "Criminology",
            "Dentistry",
            "Drama Dance and Cinematics",
            "Economics",
            "Education",
            "Electrical and Electronic Engineering",
            "English",
            "Fashion",
            "Film Making",
            "Food Science",
            "Forensic Science",
            "General Engineering",
            "Geography and Environmental Sciences",
            "Geology",
            "Health And Social Care",
            "History",
            "History of Art Architecture and Design",
            "Hospitality Leisure Recreation and Tourism",
            "Information Technology",
            "Land and Property Management",
            "Law",
            "Linguistics",
            "Marketing",
            "Materials Technology",
            "Mathematics",
            "Mechanical Engineering",
            "Medical Technology",
            "Medicine",
            "Music",
            "Nursing",
            "Occupational Therapy",
            "Pharmacology and Pharmacy",
            "Philosophy",
            "Physics and Astronomy",
            "Physiotherapy",
            "Politics",
            "Psychology",
            "Robotics",
            "Social Policy",
            "Social Work",
            "Sociology",
            "Sports Science",
            "Veterinary Medicine",
            "Youth Work"
    };

    public static void addTeam(Team team) {
        TEAMS.add(team);
    }

    public static ArrayList< DataTable< Team > > getTeams() {
        return TEAMS;
    }
}
