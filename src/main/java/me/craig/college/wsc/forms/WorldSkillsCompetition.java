package me.craig.college.wsc.forms;

import me.craig.college.wsc.CompetitionData;
import me.craig.college.wsc.Utility;
import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.Projects;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.objects.people.JudgingPanel;
import me.craig.college.wsc.objects.people.Person;
import me.craig.college.wsc.objects.people.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/* Created by Craig on 26/03/2021 */
public class WorldSkillsCompetition extends JFrame {
    public static WorldSkillsCompetition instance;
    public final URL iconLocation;
    private JButton addTeamButton;
    private JPanel mainPanel;
    private JButton quitButton;
    private JButton addJudgeButton;
    private JTable dataTable;
    private JComboBox< String > currentDataChoice;
    private JButton addProjectButton;
    private JButton helpButton;

    public WorldSkillsCompetition() {

        addTeamButton.addActionListener(e -> {
            AddTeamDialog.createTeamInput();
            unlockButtons();
        });

        quitButton.addActionListener(e -> System.exit(0));

        //Table Data Choice
        currentDataChoice.addItem("Teams");
        currentDataChoice.addItem("Judges");
        currentDataChoice.addItem("Students");
        currentDataChoice.addItem("Projects");
        currentDataChoice.addActionListener(e -> updateMainTable((String) currentDataChoice.getSelectedItem()));
        currentDataChoice.setEnabled(false);


        addJudgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddJudge.addJudgeInput();
                updateMainTable((String) currentDataChoice.getSelectedItem());
            }
        });
        addJudgeButton.setEnabled(false);

        addProjectButton.setEnabled(false);
        addProjectButton.addActionListener(e -> {
            AddProject.createProject();
            updateMainTable((String) currentDataChoice.getSelectedItem());
        });

        dataTable.setModel(new DefaultTableModel(null, new String[]{"Team Name", "College", "Members", "Area"}));
        dataTable.setEnabled(false);
        iconLocation = getClass().getResource("/ws-logo.png");
    }

    private void unlockButtons() {
        boolean enableProgram = !CompetitionData.getTeams().isEmpty();
        addProjectButton.setEnabled(enableProgram);
        currentDataChoice.setEnabled(enableProgram);
        addJudgeButton.setEnabled(enableProgram);
        updateMainTable((String) currentDataChoice.getSelectedItem());
    }

    public static void main(String[] args) {
        Utility.setTheme();
        instance = new WorldSkillsCompetition();
        JFrame jFrame = new JFrame("World Skills Competition");
        jFrame.setContentPane(instance.mainPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.setIconImage(instance.getIconLocation());

    }

    public static void updateMainTable(String table) {
        if (!instance.currentDataChoice.isEnabled()) return;
        switch (table) {
            case "Judges":
                insertDataToTable(instance.dataTable, JudgingPanel.getJudges());
                break;
            case "Teams":
                insertDataToTable(instance.dataTable, CompetitionData.getTeams());
                break;
            case "Students":
                ArrayList< DataTable< Person< Student > > > combinedStudents = new ArrayList<>();
                for (DataTable< Team > dataTable : CompetitionData.getTeams()) {
                    Team team = dataTable.getAsSelf();
                    ArrayList< DataTable< Person< Student > > > students = team.getStudents();
                    combinedStudents.addAll(students);
                }
                insertDataToTable(instance.dataTable, combinedStudents);
                break;
            case "Projects":
                insertDataToTable(instance.dataTable, Projects.getProjects());
                break;
            default:
                break;
        }
    }

    public static < T > void insertDataToTable(JTable jTable, List< DataTable< T > > dataTables) {
        Object[][] values = new Object[][]{};
        if (dataTables.isEmpty()) {
            Utility.showError("No Available Data");
            return;
        }
        for (DataTable< T > project : dataTables) {
            values = Utility.addToArray(project.toDataRow(), values);
            project.setDataHeaders(jTable, values);
        }
    }

    public Image getIconLocation() {
        return new ImageIcon(iconLocation).getImage();
    }
}
