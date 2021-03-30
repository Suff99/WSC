package me.craig.college.wsc.forms;

import me.craig.college.wsc.objects.College;
import me.craig.college.wsc.CompetitionData;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JComboBox comboBox1;
    private Team team = new Team();

    public AddTeamDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        for (College college : College.values()) {
            collegeChoiceBox.addItem(college.getCollegeName());
        }

        for (String subject : CompetitionData.SUBJECTS) {
            comboBox1.addItem(subject);
        }

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        addTeamMembersButton.addActionListener(e -> {
            if(teamName.getText().isEmpty()){
                Utility.showError("You must set a Team name!");
                return;
            }
            AddStudentForm.createTeamInput(team.setTeamName(teamName.getText()), collegeMembersTable);
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        collegeMembersTable.setModel(new DefaultTableModel(null, new String[]{"EC Number", "Address", "Telephone", "Employment Status"}));

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if(team.getStudents().isEmpty()){
            Utility.showError( "You must add Members to the Team");
            return;
        }
        team.setCollege((String) collegeChoiceBox.getSelectedItem());
        team.setArea((String) comboBox1.getSelectedItem());
        CompetitionData.addTeam(team);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void createTeamInput() {
        AddTeamDialog dialog = new AddTeamDialog();
        dialog.setTitle("Add Team");
        dialog.setIconImage(WorldSkillsCompetition.instance.getIconLocation());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }
}
