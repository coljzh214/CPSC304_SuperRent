package ca.ubc.cs304.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ca.ubc.cs304.model.VehicleModel;
 
public class TableView extends JPanel {
        // frame 
        JFrame f; 
        // Table 
        JTable j; 
      
        // Constructor 
        TableView(VehicleModel[] vModels) { 
            // Frame initiallization 
            f = new JFrame();
            JLabel number = new JLabel("HI");
      
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
            f.add(number);
            f.add(sp);
            // Frame Size 
            f.setSize(700, 300); 
            // Frame Visible = true 
            f.setVisible(true); 
        }
} 