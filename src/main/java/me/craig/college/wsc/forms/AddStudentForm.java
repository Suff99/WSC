package me.craig.college.wsc.forms;

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
                telephoneInput.setText("0131 664 " + Utils.generateNumber(4));
                addressInput.setText(Utils.RAND.nextInt(100) + (Utils.RAND.nextBoolean() ? " Evergreen Terrace" : " Totters Lane"));
                idInput.setText(String.valueOf(Utils.generateNumber(8)));
                employmentChoice.setSelectedIndex(Utils.RAND.nextInt(3));
                onOK();
            }

            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onOK() {
        if (!validateInputs()) {
            Utils.showError("Please fill out all fields.");
            return;
        }
        String telephone = telephoneInput.getText().replaceAll(" ", "");
        if (Utils.isLongDetailValid(telephone) && Utils.isLongDetailValid(idInput.getText()) && idCheck(idInput.getText())) {
            team.addStudent(Long.parseLong(idInput.getText()), addressInput.getText(), Long.parseLong(telephone), (String) employmentChoice.getSelectedItem(), team);
            WorldSkillsCompetition.insertDataToTable(textArea, team.getStudents());
            dispose();
        }
    }

    private boolean idCheck(String id) {

        // Check Already existing teams
        for (DataTable< Team > table : Teams.getTeams()) {
            for (DataTable< Person< Student > > student : table.getAsSelf().getStudents()) {
                Student stu = (Student) student.getAsSelf();
                if (String.valueOf(stu.getStudentID()).equals(id)) {
                    Utils.showError("Student with ID EC" + id + " already exists!");
                    return false;
                }
            }
        }

        // Check Pending team
        for (DataTable< Person< Student > > student : team.getStudents()) {
            Student stu = (Student) student.getAsSelf();
            if (String.valueOf(stu.getStudentID()).equals(id)) {
                Utils.showError("Student with ID EC" + id + " already exists!");
                return false;
            }
        }

        return true;
    }

    public boolean validateInputs() {
        return !telephoneInput.getText().isEmpty() && !addressInput.getText().isEmpty() && !idInput.getText().isEmpty();
    }

    private void onCancel() {
        dispose();
    }
}
