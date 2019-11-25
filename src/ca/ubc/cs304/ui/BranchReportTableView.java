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
        Object[][] array = new Object[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            array[i] = (Object[]) list.get(i);
        }
        JTable table = new JTable(array, columnNames);
        return new JScrollPane(table);
    }

    public BranchReportTableView(BranchReportModel reportModel, String[][] columnNames, String type) {
        JScrollPane scrollerA = createTable(reportModel.a, columnNames[0]);
        JScrollPane scrollerB = createTable(reportModel.b, columnNames[1]);
        JScrollPane scrollerC = createTable(reportModel.c, columnNames[2]);
        JLabel tableALabel;
        JLabel tableBLabel;
        JLabel tableCLabel;
        if (type == "Rental") {
            tableALabel = new JLabel("Today's Vehicle Rentals");
            tableBLabel = new JLabel("Total of Today's Vehicle Rentals by Vehicle Type");
            tableCLabel = new JLabel("Total number of Today's Vehicle Rentals");
        } else {
            tableALabel = new JLabel("Today's Vehicle Returns");
            tableBLabel = new JLabel("Total of Today's Vehicle Returns by Vehicle Type");
            tableCLabel = new JLabel("Total number of Today's Vehicle Returns");
        }

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
