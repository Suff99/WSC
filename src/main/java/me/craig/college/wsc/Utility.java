package me.craig.college.wsc;

import me.craig.college.wsc.objects.DataTable;
import me.craig.college.wsc.objects.people.Person;
import me.craig.college.wsc.objects.people.Student;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/* Created by Craig on 26/03/2021 */
public class Utility {

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

    public static boolean isValidNumber(String number) {
        try {
            Long.parseLong(number);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
        return true;
    }

    public static String createString(ArrayList< DataTable< Person< Student > > > allStudents) {
        StringBuilder students = new StringBuilder();
        for (DataTable< Person< Student > > dataTable : allStudents) {
            Student student = (Student) dataTable.getAsSelf();
            students.append("\n").append(student.getStudentID());
        }
        return students.toString();
    }

    public static void showError(String errorMessage){
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}
