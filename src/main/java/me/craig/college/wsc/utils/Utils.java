package me.craig.college.wsc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.craig.college.wsc.objects.Projects;
import me.craig.college.wsc.objects.Team;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/* Created by Craig on 26/03/2021 */
public class Utils {

    public static Random RAND = new Random();
    public static Gson GSON = typeApapters(new GsonBuilder()).setPrettyPrinting().create();

    public static GsonBuilder typeApapters(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Team.class, new Team());
        gsonBuilder.registerTypeAdapter(Projects.Project.class, new Projects.Project());
        return gsonBuilder;
    }

    public static void setTheme() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static long generateNumber(int length) {
        char[] digits = new char[length];
        digits[0] = (char) (RAND.nextInt(9) + '1');
        for (int i = 1; i < digits.length; i++) {
            digits[i] = (char) (RAND.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static Object[][] addToArray(String[] strings, Object[][] array) {
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = strings;
        return array;
    }

    public static String censorPhoneNumber(String num) {
        int length = num.length();
        String first = num.substring(0, 1);
        char last = num.charAt(num.length() - 1);
        StringBuilder censored = new StringBuilder();
        for (int i = length - 2; i > 0; i--) {
            censored.append("*");
        }
        return first + censored.toString() + last;
    }

    public static boolean isLongDetailValid(String number) {
        try {
            Long.parseLong(number);
        } catch (NumberFormatException numberFormatException) {
            Utils.showError("Please only enter numerical values! \nCheck: \n- The Student ID \n- The Phone Number");
            return false;
        }
        return true;
    }

    public static void showError(String errorMessage) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}
