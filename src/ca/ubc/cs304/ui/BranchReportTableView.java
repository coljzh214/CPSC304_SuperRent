package ca.ubc.cs304.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.BranchReportModel;
import ca.ubc.cs304.model.ReportModel;

public class BranchReportTableView {

    static JScrollPane createTable(List list, String[] columnNames) {
        Object[][] array = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Object> row = (ArrayList<Object>) list.get(i);
            array[i] = row.toArray(new String[row.size()]);
        }
        JTable table = new JTable(array, columnNames);
        return new JScrollPane(table);
    }

    public BranchReportTableView(BranchReportModel reportModel, String[][] columnNames) {
        JScrollPane scrollerA = createTable(reportModel.a, columnNames[0]);
        JScrollPane scrollerB = createTable(reportModel.b, columnNames[1]);
        JScrollPane scrollerC = createTable(reportModel.c, columnNames[2]);
        JLabel tableALabel = new JLabel("IDK");
        JLabel tableBLabel = new JLabel("IDK");
        JLabel tableCLabel = new JLabel("IDK");

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
        // adding the table
        c.gridwidth = GridBagConstraints.RELATIVE;
        gb.setConstraints(scrollerC, c);
        panel.add(scrollerC);
        c.gridwidth = GridBagConstraints.REMAINDER;

        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
