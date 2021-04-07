package me.craig.college.wsc.data;


import com.google.gson.JsonObject;
import me.craig.college.wsc.forms.WorldSkillsCompetition;
import me.craig.college.wsc.objects.Projects;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.objects.Teams;
import me.craig.college.wsc.objects.people.JudgingPanel;
import me.craig.college.wsc.objects.people.Student;
import me.craig.college.wsc.objects.people.Teacher;
import me.craig.college.wsc.utils.AES;
import me.craig.college.wsc.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;

/* Created by Craig on 04/04/2021 */
public class DataHandler {

    public static String[] SUBJECTS = {};

    public static void subjects() {
        InputStream is = DataHandler.class.getResourceAsStream("/subjects.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        SUBJECTS = Utils.GSON.fromJson(br, String[].class);
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
            fileWriter.write(Objects.requireNonNull(AES.encrypt(teamJson, AES.KEY)));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static void judgeToJson(Teacher judge) {
        String judgeJson = Utils.GSON.toJson(judge);
        File directory = new File("./panel");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(directory + "/" + judge.getTeachingId() + ".judge");
            fileWriter.write(Objects.requireNonNull(AES.encrypt(judgeJson, AES.KEY)));
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
            fileWriter.write(Objects.requireNonNull(AES.encrypt(studentJson, AES.KEY)));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void readInData() {
        try {
            // Projects
            readInProjects();
            // Teams & Students
            readInTeams();
            // Judges
            readInJudges();

            WorldSkillsCompetition.instance.unlockButtons();
            WorldSkillsCompetition.updateMainTable("Teams");
        } catch (IOException e) {
            Utils.showError(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void readInTeams() throws IOException {
        File teamsDir = new File("./teams");
        if (!teamsDir.exists()) return;
        Collection< File > files = FileUtils.listFiles(new File("./teams"), new String[]{"team"}, true);
        for (File file : files) {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Team team = Utils.GSON.fromJson(AES.decrypt(content, AES.KEY), Team.class);
            Teams.addTeam(team);
            findStudentsBasedOnTeam(team);
        }
    }

    private static void readInJudges() throws IOException {
        File judgesDir = new File("./panel");
        if (!judgesDir.exists()) return;
        Collection< File > judgesFiles = FileUtils.listFiles(judgesDir, new String[]{"judge"}, true);
        for (File file : judgesFiles) {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Teacher judge = Utils.GSON.fromJson(AES.decrypt(content, AES.KEY), Teacher.class);
            JudgingPanel.addJudge(judge);
        }
    }


    private static void readInProjects() throws IOException {
        File projectDir = new File("./projects");
        if (!projectDir.exists()) return;
        Collection< File > files = FileUtils.listFiles(new File("./projects"), new String[]{"proj"}, true);
        for (File file : files) {
            System.out.println(file);
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Projects.Project project = Utils.GSON.fromJson(AES.decrypt(content, AES.KEY), Projects.Project.class);
            Projects.addProject(project);
        }
    }

    public static void findStudentsBasedOnTeam(Team team) throws IOException {
        File teamsDir = new File("./teams/" + team.teamName());
        if (!teamsDir.exists()) return;
        Collection< File > files = FileUtils.listFiles(teamsDir, new String[]{"student"}, true);
        for (File file : files) {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            JsonObject student = Utils.GSON.fromJson(AES.decrypt(content, AES.KEY), JsonObject.class);
            team.addStudent(student.get("studentID").getAsLong(), student.get("address").getAsString(), student.get("telephone").getAsLong(), student.get("status").getAsString(), student.get("teamName").getAsString());
            ;
        }
    }

    public static void projectToJson(Projects.Project project) {
        String projectJson = Utils.GSON.toJson(project);
        File directory = new File("./projects");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(directory + "/" + project.getProjectName() + ".proj");
            fileWriter.write(Objects.requireNonNull(AES.encrypt(projectJson, AES.KEY)));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteProjectFile(Projects.Project project) {
        File directory = new File("./projects");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File fileWriter = new File(directory + "/" + project.getProjectName() + ".proj");
        fileWriter.delete();
    }
}
