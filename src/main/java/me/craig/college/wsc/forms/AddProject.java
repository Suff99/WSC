package me.craig.college.wsc.forms;

import me.craig.college.wsc.CompetitionData;
import me.craig.college.wsc.Utility;
import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.Projects;
import me.craig.college.wsc.objects.Team;

import javax.swing.*;
import java.awt.event.*;

public class AddProject extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox teamDropbox;
    private JTextField projectName;

    public AddProject() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        for (DataTable< Team > team : CompetitionData.getTeams()) {
            teamDropbox.addItem(team.getAsSelf().teamName());
        }
    }

    public static void createProject() {
        AddProject dialog = new AddProject();
        dialog.setIconImage(WorldSkillsCompetition.instance.getIconLocation());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Add Project");
        dialog.setVisible(true);
    }

    private void onOK() {
        if (projectName.getText().isEmpty()) {
            Utility.showError("Please Input a Project Name!");
            return;
        }
        Projects.Project project = new Projects.Project();
        project.setProjectName(projectName.getText());
        project.setTeam((String) teamDropbox.getSelectedItem());
        Projects.addProject(project);
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
