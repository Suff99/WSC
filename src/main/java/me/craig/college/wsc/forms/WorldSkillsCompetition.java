package me.craig.college.wsc.forms;

import javax.swing.*;

/* Created by Craig on 26/03/2021 */
public class WorldSkillsCompetition extends JFrame {
    private JButton resetProgramButton;
    private JButton addTeamButton;
    private JPanel mainPanel;
    private JButton quitButton;
    private JButton addJudgeButton;
    private JTable table1;

    public static WorldSkillsCompetition worldSkillsCompetition;

    public WorldSkillsCompetition() {
        addTeamButton.addActionListener(e -> AddTeamDialog.createTeamInput());
        quitButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        worldSkillsCompetition = new WorldSkillsCompetition();
        JFrame jFrame = new JFrame("World Skills Competition");
        ImageIcon imageIcon = new ImageIcon("main/resources/main/resources/world_skills_logo.jpg");
        JLabel jLabel = new JLabel(imageIcon);
        jFrame.add(jLabel);
        jFrame.setContentPane(worldSkillsCompetition.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

    }

    public static void display(String text) {
       // String currentTxt = worldSkillsCompetition.textArea1.getText().isEmpty() ? text : "\n" + text;
       // worldSkillsCompetition.textArea1.setText(currentTxt);
    }

}
