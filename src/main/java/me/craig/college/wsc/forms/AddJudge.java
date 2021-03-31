package me.craig.college.wsc.forms;

import me.craig.college.wsc.Utility;
import me.craig.college.wsc.objects.people.JudgingPanel;
import me.craig.college.wsc.objects.people.Person;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddJudge extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel, buttonOK;
    private JTextField idInput, addressInput;
    private JComboBox< String > employmentChoice;
    private JFormattedTextField telephoneInput;
    private JLabel title;

    public AddJudge() {
        employmentChoice.addItem(Person.Status.FULL_TIME.getStatus());
        employmentChoice.addItem(Person.Status.PART_TIME.getStatus());
        defaultFormActions();
    }

    public static void addJudgeInput() {
        AddJudge dialog = new AddJudge();
        dialog.setIconImage(WorldSkillsCompetition.instance.getIconLocation());
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void defaultFormActions() {
        setContentPane(contentPane);
        setTitle("Add JudgingPanel");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                telephoneInput.setText("0131 664 " + Utility.generateNumber(4));
                addressInput.setText(Utility.RAND.nextInt(100) + (Utility.RAND.nextBoolean() ? " Evergreen Terrace" : " Totters Lane"));
                idInput.setText(String.valueOf(Utility.generateNumber(8)));
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
            Utility.showError("Please fill out all fields.");
            return;
        }
        String telephone = telephoneInput.getText().replaceAll(" ", "");
        if (Utility.isValidLong(telephone) && Utility.isValidLong(idInput.getText())) {
            JudgingPanel.addJudge(addressInput.getText(), Long.parseLong(telephone), Long.parseLong(idInput.getText()), (String) employmentChoice.getSelectedItem());
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
