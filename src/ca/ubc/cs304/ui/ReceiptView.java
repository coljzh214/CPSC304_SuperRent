package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReceiptView {

 public ReceiptView(String receiptText, JFrame parent) {
    final JDialog dialog = new JDialog(parent,"TRANSACTION RECEIPT");
    JTextArea textArea = new JTextArea(100, 20);
    textArea.setEnabled(false);
    textArea.setDisabledTextColor(Color.BLACK);
    textArea.setText(receiptText);
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
    contentPane.add(textArea, BorderLayout.CENTER);
    contentPane.add(closePanel, BorderLayout.PAGE_END);
    contentPane.setOpaque(true);
    dialog.setContentPane(contentPane);
    dialog.setSize(300, 130);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);
    }
}