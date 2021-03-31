package me.craig.college.wsc.forms;

import me.craig.college.wsc.CompetitionData;
import me.craig.college.wsc.Utility;
import me.craig.college.wsc.objects.College;
import me.craig.college.wsc.objects.Team;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddTeamDialog extends JDialog {
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

        for (String subject : CompetitionData.SUBJECTS) {
            subjectArea.addItem(subject);
        }

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        addTeamMembersButton.addActionListener(e -> {
            if (teamName.getText().isEmpty()) {
                Utility.showError("You must set a Team name!");
                return;
            }

            //TODO Test
            for (int i = Utility.RAND.nextInt(20); i > 0; i--) {
                subjectArea.setSelectedIndex(Utility.RAND.nextInt(CompetitionData.SUBJECTS.length));
                collegeChoiceBox.setSelectedIndex(Utility.RAND.nextInt(3));
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
            Utility.showError("You must add Members to the Team");
            return;
        }
        team.setCollege((String) collegeChoiceBox.getSelectedItem());
        team.setArea((String) subjectArea.getSelectedItem());
        CompetitionData.addTeam(team);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
