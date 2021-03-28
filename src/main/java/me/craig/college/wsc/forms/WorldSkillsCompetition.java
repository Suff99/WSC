package me.craig.college.wsc.forms;

import me.craig.college.wsc.CompetitionData;
import me.craig.college.wsc.Team;
import me.craig.college.wsc.Utility;
import me.craig.college.wsc.people.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/* Created by Craig on 26/03/2021 */
public class WorldSkillsCompetition extends JFrame {
    private JButton addTeamButton;
    private JPanel mainPanel;
    private JButton quitButton;
    private JButton addJudgeButton;
    private JTable dataTable;
    private JComboBox< String > currentDataChoice;

    public static WorldSkillsCompetition worldSkillsCompetition;

    public WorldSkillsCompetition() {

        addTeamButton.addActionListener(e -> {
            AddTeamDialog.createTeamInput();
            updateTable((String) Objects.requireNonNull(currentDataChoice.getSelectedItem()));
        });
        quitButton.addActionListener(e -> System.exit(0));

        currentDataChoice.addItem("Teams");
        currentDataChoice.addItem("Judges");
        currentDataChoice.addItem("Students");
        currentDataChoice.addActionListener(e -> updateTable((String) Objects.requireNonNull(currentDataChoice.getSelectedItem())));

        addJudgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addJudgeButton.setEnabled(false);

        dataTable.setModel(new DefaultTableModel(null, new String[]{"Team Name", "College", "Members", "Area"}));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        worldSkillsCompetition = new WorldSkillsCompetition();
        JFrame jFrame = new JFrame("World Skills Competition");
        jFrame.setContentPane(worldSkillsCompetition.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    public static void updateTable(String table) {
        switch (table) {
            case "Judges":
                judgesTable(worldSkillsCompetition.dataTable);
                break;
            case "Teams":
                teamsTable(worldSkillsCompetition.dataTable);
                break;
            case "Students":
                studentsTable(worldSkillsCompetition.dataTable);
                break;
            default:
                break;
        }
    }

    private static void studentsTable(JTable dataTable) {
        Object[][] values = new Object[][]{};
        for (Team team : CompetitionData.getTeams()) {
            for (Student student : team.getStudents()) {
                values = Utility.addToArray(new String[]{team.teamName(), student.getStudentID(), student.getAddress(), student.getStatus(), Utility.censorPhoneNumber(String.valueOf(student.getTelephone()))}, values);
            }
        }
        dataTable.setModel(new DefaultTableModel(values, new String[]{"Team Name", "Student", "Address", "Status", "Telephone"}));
    }

    private static void teamsTable(JTable dataTable) {
        Object[][] values = new Object[][]{};
        for (Team team : CompetitionData.getTeams()) {
            values = Utility.addToArray(new String[]{team.teamName(), team.college(), Utility.createString(team.getStudents()), team.area()}, values);
        }
        dataTable.setModel(new DefaultTableModel(values, new String[]{"Team Name", "College", "Members", "Area"}));
    }

    private static void judgesTable(JTable dataTable) {
        Object[][] values = new Object[][]{};
        for (Team team : CompetitionData.getTeams()) {
            values = Utility.addToArray(new String[]{team.teamName(), team.college(), Utility.createString(team.getStudents()), team.area()}, values);
        }
        dataTable.setModel(new DefaultTableModel(values, new String[]{"Team Name", "College", "Members", "Area"}));
    }

}
