package ca.ubc.cs304.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;

import ca.ubc.cs304.model.VehicleModel;
 
public class TableView {
        // frame 
        JFrame f; 
        // Table 
        JTable j;
        JPanel p; 
      
        // Constructor 
        TableView(VehicleModel[] vModels) { 
            // Frame initiallization 
            f = new JFrame();
            p = new JPanel();
            p.setLayout(new BorderLayout());
            JLabel number = new JLabel("Total number of available vehicles: " + vModels.length);
      
            // Frame Title 
            f.setTitle("JTable Example"); 
      
            // Data to be displayed in the JTable 
            String[][] data = new String[vModels.length][11];
            for (int i = 0; i < vModels.length; i++) {
                data[i] = vModels[i].getTuple();
            }
      
            // Column Names 
            String[] columnNames = {  "vlicense",  "vid",  "make",  "model",  "year",  "color",  "odometer",
                 "status",  "vtname",  "location",  "city" }; 
      
            // Initializing the JTable 
            j = new JTable(data, columnNames); 
            j.setBounds(30, 40, 200, 300); 
      
            // adding it to JScrollPane 
            JScrollPane sp = new JScrollPane(j);
            p.add(number, "North");
            p.add(sp, "Center");
            // Frame Size
            f.getContentPane().add(p); 
            f.setSize(700, 300); 
            // Frame Visible = true 
            f.setVisible(true); 
        }
} 