package me.craig.college.wsc.objects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/* Created by Craig on 29/03/2021 */
public class Projects {

    private static final ArrayList< DataTable< Project > > projects = new ArrayList<>();

    public static void addProject(Project project) {
        projects.add(project);
    }

    public static ArrayList< DataTable< Project > > getProjects() {
        return projects;
    }

    public static class Project implements DataTable< Project > {
        private float score = 0;
        private String projectName = "";
        private String team = null;

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public float getScore() {
            return score;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        @Override
        public String[] toDataRow() {
            return new String[]{getProjectName(), getTeam(), String.valueOf(getScore())};
        }

        @Override
        public void setDataHeaders(JTable dataTable, Object[][] data) {
            dataTable.setModel(new DefaultTableModel(data, new String[]{"Project Name", "Team", "Score"}));
        }

        @Override
        public Project getAsSelf() {
            return this;
        }
    }

}
