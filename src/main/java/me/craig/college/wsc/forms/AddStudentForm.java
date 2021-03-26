package me.craig.college.wsc.forms;

import me.craig.college.wsc.Team;
import me.craig.college.wsc.Utility;
import me.craig.college.wsc.people.Person;
import me.craig.college.wsc.people.Student;

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

    public AddStudentForm(Team team, JTable textArea1) {
        this.team = team;
        this.textArea = textArea1;
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
            JOptionPane.showMessageDialog(null, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String telephone = telephoneInput.getText();
        if (Utility.isValidNumber(telephone)) {
            team.addStudent(IDinput.getText(), addressInput.getText(), Long.parseLong(telephone), (String) comboBox1.getSelectedItem());
            Object[][] values = updateTable();
            createTable(textArea, values);
            dispose();
            return;
        }
        JOptionPane.showMessageDialog(null, "Invalid Phone Number. Only Enter Numbers.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private Object[][] updateTable() {
        Object[][] values = new Object[][]{};
        for (Student student : team.getStudents()) {
            values = Utility.forceAddToArray(new String[]{student.getStudentID(), student.getAddress(), Utility.censorPhoneNumber(String.valueOf(student.getTelephone())), (String) comboBox1.getSelectedItem()}, values);
        }
        return values;
    }

    public boolean validateInputs() {
        return !telephoneInput.getText().isEmpty() && !addressInput.getText().isEmpty() && !IDinput.getText().isEmpty();
    }

    public void createTable(JTable table, Object[][] data) {
        table.setModel(new DefaultTableModel(data, new String[]{"EC Number", "Address", "Telephone", "Employment Status"}));
    }

    private void onCancel() {
        dispose();
    }

    public static void createTeamInput(Team team, JTable textArea1) {
        AddStudentForm dialog = new AddStudentForm(team, textArea1);
        dialog.setSize(760, 350);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }
}
