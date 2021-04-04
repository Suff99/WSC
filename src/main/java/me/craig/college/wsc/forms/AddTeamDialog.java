package me.craig.college.wsc.forms;

import me.craig.college.wsc.data.DataHandler;
import me.craig.college.wsc.objects.College;
import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.objects.Teams;
import me.craig.college.wsc.objects.people.Person;
import me.craig.college.wsc.objects.people.Student;
import me.craig.college.wsc.utils.Utils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddTeamDialog extends JDialog {
    private final boolean test = false;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField teamName;
    private JComboBox< String > collegeChoiceBox;
    private JButton addTeamMembersButton;
    private JTable collegeMembersTable;
    private JScrollPane teamMembersPane;
    private JComboBox subjectArea;
    private Team team = new Team();

    public AddTeamDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        for (College college : College.values()) {
            collegeChoiceBox.addItem(college.getCollegeName());
        }

        for (String subject : DataHandler.getSubjects()) {
            subjectArea.addItem(subject);
        }

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        addTeamMembersButton.addActionListener(e -> {
            if (teamName.getText().isEmpty()) {
                Utils.showError("You must set a Team name!");
                return;
            }
            for (int i = 16; i > 0; i--) {
                AddStudentForm.createTeamInput(team.setTeamName(teamName.getText()), collegeMembersTable);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

            }

            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void createTeamInput() {
        AddTeamDialog dialog = new AddTeamDialog();
        dialog.setTitle("Add Team");
        dialog.setIconImage(WorldSkillsCompetition.instance.getIconLocation());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    private void onOK() {
        if (team.getStudents().isEmpty()) {
            Utils.showError("You must add Members to the Team");
            return;
        }
        team.setCollege((String) collegeChoiceBox.getSelectedItem());
        team.setArea((String) subjectArea.getSelectedItem());
        Teams.addTeam(team);
        DataHandler.teamToJson(team);
        for (DataTable< Person< Student > > genericStudent : team.getStudents()) {
            Student student = (Student) genericStudent;
            DataHandler.studentToJson(student);
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
