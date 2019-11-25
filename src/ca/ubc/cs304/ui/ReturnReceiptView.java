package ca.ubc.cs304.ui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import ca.ubc.cs304.model.RentReturnModel;

public class ReturnReceiptView {

 public ReturnReceiptView(RentReturnModel rentReturnmodel) {
    JFrame parent = new JFrame();
    final JDialog dialog = new JDialog(parent,"TRANSACTION RECEIPT");
    JTextPane textPane = new JTextPane();
    textPane.setText(   
        "Rental ID: " + rentReturnmodel.getRid() + "\n\n" +
        "Date of return: " + rentReturnmodel.getReturnDate() + "\n\n" +
        "Odometer: " + rentReturnmodel.getOdometer() + "\n\n" +
        "Your Daily Rate is: " + rentReturnmodel.getRate() + "\n\n" + 
        "Your Duration is: " + rentReturnmodel.getDuration() + "\n\n" +
        "Your Total is: " + rentReturnmodel.getRate() + " X " + rentReturnmodel.getDuration() + " = " + rentReturnmodel.getValue()
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
    dialog.setSize(500, 250);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);
    }
}