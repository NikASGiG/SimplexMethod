/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nikasgig.simplexmethod.service;

import com.nikasgig.simplexmethod.form.MainJFrame;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NikAS GiG
 */
public class OutputService {

    public static double[] toTable(StringBuilder result) {
        double[] table = new double[6];
        String input = result.toString();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String arrayString = matcher.group(1);
            String[] arrayValues = arrayString.split(", ");
            //double[] table = new double[arrayValues.length];
            for (int i = 0; i < arrayValues.length; i++) {
                table[i] = Double.parseDouble(arrayValues[i]);
            }
            return table;
        }
        return table;
    }
    
    public static void showResult(MainJFrame frame, double[][] result, double[] extractResults){
        int rowCount = result.length;
        int columnCount = result[0].length;
        DefaultTableModel model = new DefaultTableModel(rowCount, columnCount);
        // Заполнение модели данными из массива
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                model.setValueAt(result[i][j], i, j);
            }
        }
        frame.jTable4.setModel(model);
        
        frame.jTextField3.setText(Double.toString(extractResults[0]));
        StringBuilder temp = new StringBuilder();
        for (double el : extractResults) {
           temp.append(Double.toString(el));
        }
        frame.jTextField4.setText(temp.toString());
    }
}
