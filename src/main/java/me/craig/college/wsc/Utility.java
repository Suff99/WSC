package me.craig.college.wsc;

import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.people.Person;
import me.craig.college.wsc.objects.people.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/* Created by Craig on 26/03/2021 */
public class Utility {

    public static Random RAND = new Random();

    public static void setTheme(){
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
        char first = num.charAt(0);
        char last = num.charAt(num.length() - 1);
        StringBuilder censored = new StringBuilder();
        for (int i = length - 2; i > 0; i--) {
            censored.append("*");
        }
        return first + censored.toString() + last;
    }

    public static boolean isValidLong(String number) {
        try {
            Long.parseLong(number);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
        return true;
    }

    public static String readableStudent(ArrayList< DataTable< Person< Student > > > allStudents) {
        StringBuilder students = new StringBuilder();
        for (DataTable< Person< Student > > dataTable : allStudents) {
            Student student = (Student) dataTable.getAsSelf();
            students.append("\n").append(student.getStudentID());
        }
        return students.toString();
    }

    public static void showError(String errorMessage) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}
