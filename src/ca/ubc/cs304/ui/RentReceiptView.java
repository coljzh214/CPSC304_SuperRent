package ca.ubc.cs304.ui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import ca.ubc.cs304.model.RentalModel;

public class RentReceiptView {

 public RentReceiptView(RentalModel rentModel) {
    JFrame parent = new JFrame();
    final JDialog dialog = new JDialog(parent,"TRANSACTION RECEIPT");
    JTextPane textPane = new JTextPane();
    String confNoText = (rentModel.getConfNo() == -1) ? "N/A" : Integer.toString(rentModel.getConfNo());
    textPane.setText(   
        "Reservation Confirmation Number: " + rentModel.getRid() + "\n\n" +
        "Vehicle ID: " + rentModel.getVlicense() + "\n\n" +
        "Drivers License: " + rentModel.getDlicense() + "\n\n" +
        "Confirmation Number: " + confNoText + "\n\n" +
        "From Date: " + rentModel.getFromDate() + " To Date: " + rentModel.getToDate() + "\n\n" +
        "Odometer: " + rentModel.getOdometer() + "\n\n" +
        "Card Name: " + rentModel.getCardName() + "\n\n" +
        "Card Number: " + rentModel.getCardNo() + "\n\n" +
        "Card Expiry Date: " + rentModel.getExpDate() + "\n\n"
    );
    textPane.setEnabled(false);
    textPane.setDisabledTextColor(Color.BLACK);
    StyledDocument doc = textPane.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);
    
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            dialog.setVisible(false);
            dialog.dispose();
        }
    });
    JPanel closePanel = new JPanel();
    closePanel.setLayout(new BoxLayout(closePanel, BoxLayout.LINE_AXIS));
    closePanel.add(Box.createHorizontalGlue());
    closePanel.add(closeButton);
    closePanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(textPane, BorderLayout.CENTER);
    contentPane.add(closePanel, BorderLayout.PAGE_END);
    contentPane.setOpaque(true);
    dialog.setContentPane(contentPane);
    dialog.setSize(500, 350);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);
    }
}