package ca.ubc.cs304.ui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import ca.ubc.cs304.model.RentalModel;

public class RentReceiptView {

 public RentReceiptView() {
    JFrame parent = new JFrame();
    final JDialog dialog = new JDialog(parent,"TRANSACTION RECEIPT");
    JTextPane textPane = new JTextPane();
    textPane.setText(   
        "Reservation Confirmation Number: " + "rentModel.getRid()" + "\n\n" +
        "Vehicle ID: " + "rentModel.getVlicense()" + "\n\n" +
        "Drivers License: " + "rentModel.getDlicense()" + "\n\n" +
        "Cell Phone: " + "rentModel.getConfNo()" + "\n\n" +
        "From Date: " + "rentmodel.getFromDate()" + " To Date: " + "rentmodel.getToDate()" + "\n\n" +
        "From Time: " + "rentmodel.getFromTime()" + " To Time: " + "rentmodel.getToTime()" + "\n\n" +
        "Odometer: " + "rentmodel.getOdometer()" + "\n\n" +
        "Card Name: " + "rentmodel.getCardName()" + "\n\n" +
        "Card Number: " + "rentmodel.getCardNo()" + "\n\n" +
        "Exipry Date: " + "rentmodel.getExpDate()" + "\n\n"
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