package me.craig.college.wsc.forms;

import me.craig.college.wsc.Utility;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.objects.people.Person;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class AddStudentForm extends JDialog {
    private final Team team;
    private final JTable textArea;
    private JPanel contentPane;
    private JButton buttonCancel, buttonOK;
    private JTextField idInput, addressInput;
    private JComboBox< String > employmentChoice;
    private JFormattedTextField telephoneInput;
    private JLabel title;

    public AddStudentForm(Team team, JTable table) {
        this.team = team;
        this.textArea = table;
        for (Person.Status status : Person.Status.values()) {
            employmentChoice.addItem(status.getStatus());
        }
        defaultFormActions(team);
    }

    public static void createTeamInput(Team team, JTable jTable) {
        AddStudentForm dialog = new AddStudentForm(team, jTable);
        dialog.setIconImage(WorldSkillsCompetition.instance.getIconLocation());
        dialog.setSize(760, 350);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
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

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                telephoneInput.setText(String.valueOf(generateID()));
                addressInput.setText(Utility.RAND.nextInt(100) + (Utility.RAND.nextBoolean() ? " Evergreen Terrace" : " Totters Lane"));
                idInput.setText(String.valueOf(generateID()));
                employmentChoice.setSelectedIndex(Utility.RAND.nextInt(3));
                onOK();
            }

            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public long generateID() {
        Random rnd = new Random();
        char[] digits = new char[8];
        digits[0] = (char) (rnd.nextInt(9) + '1');
        for (int i = 1; i < digits.length; i++) {
            digits[i] = (char) (rnd.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    private void onOK() {
        if (!validateInputs()) {
            Utility.showError("Please fill out all fields.");
            return;
        }
        String telephone = telephoneInput.getText().replaceAll(" ", "");
        if (Utility.isValidLong(telephone) && Utility.isValidLong(idInput.getText())) {
            team.addStudent(Long.parseLong(idInput.getText()), addressInput.getText(), Long.parseLong(telephone), (String) employmentChoice.getSelectedItem(), team);
            WorldSkillsCompetition.insertDataToTable(textArea, team.getStudents());
            dispose();
            return;
        }
        Utility.showError("Please only enter numerical values! \nCheck: \n- The Student ID \n- The Phone Number");
    }

    public boolean validateInputs() {
        return !telephoneInput.getText().isEmpty() && !addressInput.getText().isEmpty() && !idInput.getText().isEmpty();
    }

    private void onCancel() {
        dispose();
    }
}
