package me.craig.college.wsc.forms;

import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.Utility;
import me.craig.college.wsc.objects.people.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddStudentForm extends JDialog {
    private final Team team;
    private final JTable textArea;
    private JPanel contentPane;
    private JButton buttonCancel, buttonOK;
    private JTextField IDinput, addressInput;
    private JComboBox< String > comboBox1;
    private JFormattedTextField telephoneInput;
    private JLabel title;

    public AddStudentForm(Team team, JTable table) {
        this.team = team;
        this.textArea = table;
        for (Person.Status status : Person.Status.values()) {
            comboBox1.addItem(status.getStatus());
        }
        defaultFormActions(team);
    }

    private void defaultFormActions(Team team) {
        setContentPane(contentPane);
        setTitle("Add User to " + team.teamName());
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (!validateInputs()) {
            Utility.showError("Please fill out all fields.");
            return;
        }
        String telephone = telephoneInput.getText().replaceAll(" ", "");
        if (Utility.isValidNumber(telephone)) {
            team.addStudent(IDinput.getText(), addressInput.getText(), Long.parseLong(telephone), (String) comboBox1.getSelectedItem(), team);
            WorldSkillsCompetition.insertDataToTable(textArea, team.getStudents());
            dispose();
            return;
        }
        Utility.showError( "Invalid Phone Number. Only Enter Numbers.");
    }

    public boolean validateInputs() {
        return !telephoneInput.getText().isEmpty() && !addressInput.getText().isEmpty() && !IDinput.getText().isEmpty();
    }

    private void onCancel() {
        dispose();
    }

    public static void createTeamInput(Team team, JTable jTable) {
        AddStudentForm dialog = new AddStudentForm(team, jTable);
        dialog.setIconImage(WorldSkillsCompetition.instance.getIconLocation());
        dialog.setSize(760, 350);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }
}
