package ca.ubc.cs304.ui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import ca.ubc.cs304.model.ReservationModel;

public class ReserveReceiptView {

 public ReserveReceiptView(ReservationModel reserveModel) {
    JFrame parent = new JFrame();
    final JDialog dialog = new JDialog(parent,"Reservation Receipt");
    JTextPane textPane = new JTextPane();
    textPane.setText(   
        "Reservation Confirmation Number: " + reserveModel.getConfNo() + "\n\n" +
        "Vehicle Type: " + reserveModel.getVtname() + "\n\n" +
        "Drivers License: " + reserveModel.getDlicense() + "\n\n" +
        "From Date: " + reserveModel.getFromDate() + " To Date: " + reserveModel.getToDate() + "\n\n"
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