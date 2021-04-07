package me.craig.college.wsc.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import me.craig.college.wsc.data.DataHandler;
import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.Projects;
import me.craig.college.wsc.objects.Team;
import me.craig.college.wsc.objects.Teams;
import me.craig.college.wsc.objects.people.JudgingPanel;
import me.craig.college.wsc.objects.people.Person;
import me.craig.college.wsc.objects.people.Student;
import me.craig.college.wsc.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/* Created by Craig on 26/03/2021 */
public class WorldSkillsCompetition extends JFrame {
    public static WorldSkillsCompetition instance;
    public static boolean testMode = false;
    public URL iconLocation = null;
    private JButton addTeamButton;
    private JPanel mainPanel;
    private JButton quitButton;
    private JButton addJudgeButton;
    private JTable dataTable;
    private JComboBox< String > currentDataChoice;
    private JButton addProjectButton;
    private JButton helpButton;
    private JButton editProject;

    public WorldSkillsCompetition() {

        addTeamButton.addActionListener(e -> {
            AddTeamForm.createTeamInput();
            unlockButtons();
        });

        quitButton.addActionListener(e -> System.exit(0));

        helpButton.addActionListener(actionEvent -> Help.showHelp());

        //Table Data Choice
        currentDataChoice.addItem("Teams");
        currentDataChoice.addItem("Judges");
        currentDataChoice.addItem("Students");
        currentDataChoice.addItem("Projects");
        currentDataChoice.addActionListener(e -> updateMainTable((String) currentDataChoice.getSelectedItem()));
        currentDataChoice.setEnabled(false);


        addJudgeButton.addActionListener(e -> {
            AddJudgeForm.addJudgeInput();
            updateMainTable((String) currentDataChoice.getSelectedItem());
        });
        addJudgeButton.setEnabled(false);

        addProjectButton.setEnabled(false);
        addProjectButton.addActionListener(e -> {
            AddProjectForm.createProject();
            updateMainTable((String) currentDataChoice.getSelectedItem());
        });

        editProject.addActionListener(actionEvent -> EditProject.showEditor());
        editProject.setEnabled(false);

        dataTable.setModel(new DefaultTableModel(null, new String[]{"Team Name", "College", "Members", "Area"}));
        dataTable.setEnabled(false);
        iconLocation = getClass().getResource("/images/logo.png");

    }

    public static void main(String[] args) {
        Utils.setTheme();
        instance = new WorldSkillsCompetition();
        JFrame jFrame = new JFrame("World Skills Competition");
        jFrame.setContentPane(instance.mainPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.setIconImage(instance.getIconLocation());
        DataHandler.subjects();
        DataHandler.readInData();
    }

    public static void updateMainTable(String table) {
        if (!instance.currentDataChoice.isEnabled()) return;
        switch (table) {
            case "Judges":
                insertDataToTable(instance.dataTable, JudgingPanel.getJudges());
                break;
            case "Teams":
                insertDataToTable(instance.dataTable, Teams.getTeams());
                break;
            case "Students":
                ArrayList< DataTable< Person< Student > > > combinedStudents = new ArrayList<>();
                for (DataTable< Team > dataTable : Teams.getTeams()) {
                    Team team = dataTable.getAsSelf();
                    ArrayList< DataTable< Person< Student > > > students = team.getStudents();
                    combinedStudents.addAll(students);
                }
                insertDataToTable(instance.dataTable, combinedStudents);
                break;
            case "Projects":
                insertDataToTable(instance.dataTable, Projects.getProjects());
                break;
            default:
                break;
        }
    }

    public static < T > void insertDataToTable(JTable jTable, List< DataTable< T > > dataTables) {
        Object[][] values = new Object[][]{};
        if (dataTables.isEmpty()) {
            Utils.showError("No Available Data");
            return;
        }
        for (DataTable< T > project : dataTables) {
            values = Utils.addToArray(project.toDataRow(), values);
            project.setDataHeaders(jTable, values);
        }
    }

    public void unlockButtons() {
        boolean enableProgram = !Teams.getTeams().isEmpty();
        addProjectButton.setEnabled(enableProgram);
        currentDataChoice.setEnabled(enableProgram);
        addJudgeButton.setEnabled(enableProgram);
        editProject.setEnabled(enableProgram);
        updateMainTable((String) currentDataChoice.getSelectedItem());
    }

    public Image getIconLocation() {
        return new ImageIcon(iconLocation).getImage();
    }

    //===================================================================================================

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(16, 11, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(30);
        scrollPane1.setVerticalScrollBarPolicy(20);
        mainPanel.add(scrollPane1, new GridConstraints(10, 1, 4, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        dataTable = new JTable();
        scrollPane1.setViewportView(dataTable);
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(0, 8, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(1, 10, 15, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setToolTipText("Quit the Program");
        mainPanel.add(quitButton, new GridConstraints(14, 8, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(15, 8, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Unispace", Font.BOLD, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("World Skills Competition");
        mainPanel.add(label1, new GridConstraints(1, 5, 2, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new GridConstraints(1, 1, 9, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon(getClass().getResource("/images/logo.png")));
        label2.setText("");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel5, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        helpButton = new JButton();
        helpButton.setHideActionText(true);
        helpButton.setHorizontalAlignment(0);
        helpButton.setText("?");
        mainPanel.add(helpButton, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addProjectButton = new JButton();
        addProjectButton.setText("Add Project");
        mainPanel.add(addProjectButton, new GridConstraints(4, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addTeamButton = new JButton();
        addTeamButton.setText("Add Team");
        mainPanel.add(addTeamButton, new GridConstraints(4, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Table Data:");
        mainPanel.add(label3, new GridConstraints(7, 7, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentDataChoice = new JComboBox();
        mainPanel.add(currentDataChoice, new GridConstraints(7, 8, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editProject = new JButton();
        editProject.setText("Edit Project");
        mainPanel.add(editProject, new GridConstraints(5, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addJudgeButton = new JButton();
        addJudgeButton.setText("Add Judge");
        mainPanel.add(addJudgeButton, new GridConstraints(5, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
