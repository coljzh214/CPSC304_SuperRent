package ca.ubc.cs304.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import ca.ubc.cs304.model.ReportModel;
 
public class ReportTableView {

    static JScrollPane createTable(List list, String[] columnNames) {
        Object[][] array = new Object[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            array[i] = (Object[]) list.get(i);
        }
        JTable table = new JTable(array, columnNames);
        return new JScrollPane(table);
    }

    public ReportTableView(ReportModel reportModel) {
        String[] columnNames = {  "vlicense",  "vid",  "make",  "model",  "year",  "color",  "odometer",
                 "status",  "vtname",  "location",  "city" };
        JScrollPane scrollerA = createTable(reportModel.a, columnNames);
        JScrollPane scrollerB = createTable(reportModel.b, columnNames);
        JScrollPane scrollerC = createTable(reportModel.c, columnNames);
        JScrollPane scrollerD = createTable(reportModel.d, columnNames);
        JLabel tableALabel = new JLabel("IDK");
        JLabel tableBLabel = new JLabel("IDK");
        JLabel tableCLabel = new JLabel("IDK");
        JLabel tableDLabel = new JLabel("IDK");

        JPanel panel = new JPanel();
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout(gb);
        // adding the labels
        c.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(tableALabel, c);
        panel.add(tableALabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(tableBLabel, c);
        panel.add(tableBLabel);
        // adding the table
        c.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(scrollerA, c);
        panel.add(scrollerA);
        c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(scrollerB, c);
        panel.add(scrollerB);
        // adding the labels
        c.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(tableCLabel, c);
        panel.add(tableCLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(tableDLabel, c);
        panel.add(tableDLabel);
        // adding the table
        c.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(scrollerC, c);
        panel.add(scrollerC);
        c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(scrollerD, c);
        panel.add(scrollerD);

        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}