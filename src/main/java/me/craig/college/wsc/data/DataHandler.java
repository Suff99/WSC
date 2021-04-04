package me.craig.college.wsc.data;


import me.craig.college.wsc.forms.WorldSkillsCompetition;
import me.craig.college.wsc.objects.Projects;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.objects.Teams;
import me.craig.college.wsc.objects.people.JudgingPanel;
import me.craig.college.wsc.objects.people.Student;
import me.craig.college.wsc.objects.people.Teacher;
import me.craig.college.wsc.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.Collection;

/* Created by Craig on 04/04/2021 */
public class DataHandler {

    private static String[] SUBJECTS = {};

    public static void subjects() throws FileNotFoundException {
        URL jsonStream = DataHandler.class.getResource("/data/subjects.json");
        SUBJECTS = Utils.GSON.fromJson(new FileReader(jsonStream.getFile()), String[].class);
    }

    public static String[] getSubjects() {
        return SUBJECTS;
    }

    public static void teamToJson(Team team) {
        String teamJson = Utils.GSON.toJson(team);
        File directory = new File("./teams/" + team.teamName());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(directory + "/" + team.teamName() + ".team");
            fileWriter.write(teamJson);
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static void judgeToJson(Teacher judge) {
        String judgeJson = Utils.GSON.toJson(judge);
        File directory = new File("./panel/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(directory + "/" + judge.getTeachingId() + ".judge");
            fileWriter.write(judgeJson);
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static void studentToJson(Student student) {
        String studentJson = Utils.GSON.toJson(student);
        File directory = new File("./teams/" + student.teamName() + "/students/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(directory + "/" + student.getStudentID() + ".student");
            fileWriter.write(studentJson);
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void readInData() throws FileNotFoundException {
        // Teams & Students
        File teamsDir = new File("./teams");
        if (!teamsDir.exists()) return;
        Collection< File > files = FileUtils.listFiles(new File("./teams"), new String[]{"team"}, true);
        for (File file : files) {
            Team team = Utils.GSON.fromJson(new FileReader(file), Team.class);
            Teams.addTeam(team);
            findStudentsBasedOnTeam(team);
            WorldSkillsCompetition.instance.unlockButtons();
            WorldSkillsCompetition.updateMainTable("Teams");
        }

        // Judges
        File judgesDir = new File("./panel");
        if (!judgesDir.exists()) return;
        Collection< File > judgesFiles = FileUtils.listFiles(judgesDir, new String[]{"judge"}, true);
        for (File file : judgesFiles) {
            Teacher judge = Utils.GSON.fromJson(new FileReader(file), Teacher.class);
            JudgingPanel.addJudge(judge);
            WorldSkillsCompetition.instance.unlockButtons();
            WorldSkillsCompetition.updateMainTable("Teams");
        }
    }

    public static void findStudentsBasedOnTeam(Team team) throws FileNotFoundException {
        File teamsDir = new File("./teams/" + team.teamName());
        if (!teamsDir.exists()) return;
        Collection< File > files = FileUtils.listFiles(teamsDir, new String[]{"student"}, true);
        for (File file : files) {
            Student student = Utils.GSON.fromJson(new FileReader(file), Student.class);
            team.addStudent(student);
            WorldSkillsCompetition.instance.unlockButtons();
            WorldSkillsCompetition.updateMainTable("Teams");
        }
    }

    public static void projectToJson(Projects.Project project) {
        String projectJson = Utils.GSON.toJson(project);
        File directory = new File("./teams/" + project.getTeam());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(directory + "/" + project.getProjectName() + ".proj");
            fileWriter.write(projectJson);
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
