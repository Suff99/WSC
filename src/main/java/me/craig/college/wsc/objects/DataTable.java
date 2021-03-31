package me.craig.college.wsc.objects;

import javax.swing.*;

/* Created by Craig on 29/03/2021 */
public interface DataTable< T > {

    String[] toDataRow();

    void setDataHeaders(JTable dataTable, Object[][] data);

    T getAsSelf();
}
