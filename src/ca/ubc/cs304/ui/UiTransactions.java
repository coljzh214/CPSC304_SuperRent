package ca.ubc.cs304.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JOptionPane;

import ca.ubc.cs304.model.BranchReportModel;
import ca.ubc.cs304.model.RentReturnModel;
import ca.ubc.cs304.model.RentalModel;
import ca.ubc.cs304.model.ReportModel;
import org.jdatepicker.impl.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import ca.ubc.cs304.delegates.UiTransactionsDelegate;

public class UiTransactions extends JFrame implements ActionListener {
	private static final int TEXT_FIELD_WIDTH = 10;

	private ArrayList<JTextField> textFields = new ArrayList<>();
	private ArrayList<JFormattedTextField> formattedTextFields = new ArrayList<>();
	private ArrayList<JSpinner> timeSpinners = new ArrayList<>();
    private ArrayList<JDatePickerImpl> datePickers = new ArrayList<>();
    private ArrayList<JComboBox> comboBoxs = new ArrayList<>();

    private UiTransactionsDelegate delegate;

    public UiTransactions() {
        super("Ui Transaction");
    }

    public void showMainMenu(UiTransactionsDelegate delegate) {
        this.delegate = delegate;

		JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

		JPanel tab1 = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		String[] vehicleType = new String[] {"SUV", "Van", "Sedan"};
		this.addComboBox(tab1, " Vehicle Type: ", vehicleType, gb, c);
		this.addField(tab1, " Location: ", gb, c);
		this.addLabels(tab1,"From Date:", "To Date:", gb, c);
		this.addDatePicker(tab1, gb, c);
		this.addSubmitButton(tab1, gb, c, "ViewVehicle");

		JPanel tab2 = new JPanel();
		GridBagLayout gb2 = new GridBagLayout();
		GridBagConstraints c2 = new GridBagConstraints();
		this.addComboBox(tab2, " Vehicle Type: ", vehicleType, gb2, c2);
		this.addFormattedField(tab2, " Driver License: ", gb2, c2);
		this.addFormattedField(tab2, " Phone Number : ", gb2, c2);
		this.addLabels(tab2,"From Date:", "To Date:", gb2, c2);
		this.addDatePicker(tab2, gb2, c2);
		this.addSubmitButton(tab2, gb2, c2, "ReserveVehicle");

		JPanel tab3 = new JPanel();
		JTabbedPane tabbedPane3 = new JTabbedPane();
		JPanel tab3_1 = new JPanel();
		GridBagLayout gb3_1 = new GridBagLayout();
		GridBagConstraints c3_1 = new GridBagConstraints();
		this.addFormattedField(tab3_1, " Confirmation Number: ", gb3_1, c3_1);
		this.addField(tab3_1, " Card Name: ", gb3_1, c3_1);
		this.addFormattedField(tab3_1, " Card Number: ", gb3_1, c3_1);
		this.addLabels(tab3_1,"Expiry Date:", "", gb3_1, c3_1);
		this.addOneDatePicker(tab3_1, gb3_1, c3_1);
		this.addSubmitButton(tab3_1, gb3_1, c3_1, "RentVehicle");

		JPanel tab3_2 = new JPanel();
		GridBagLayout gb3_2 = new GridBagLayout();
		GridBagConstraints c3_2 = new GridBagConstraints();
		this.addComboBox(tab3_2, " Vehicle Type: ", vehicleType, gb3_2, c3_2);
		this.addFormattedField(tab3_2, " Driver License: ", gb3_2, c3_2);
		this.addLabels(tab3_2,"From Date:", "To Date:", gb3_2, c3_2);
		this.addDatePicker(tab3_2, gb3_2, c3_2);
		this.addField(tab3_2, " Card Name: ", gb3_2, c3_2);
		this.addFormattedField(tab3_2, " Card Number: ", gb3_2, c3_2);
		this.addLabels(tab3_2,"Expiry Date:", "", gb3_2, c3_2);
		this.addOneDatePicker(tab3_2, gb3_2, c3_2);
		this.addSubmitButton(tab3_2, gb3_2, c3_2, "RentVehicle2");

		tabbedPane3.add("With Confirmation Number", tab3_1);
		tabbedPane3.add("Without Confirmation Number", tab3_2);
		tabbedPane3.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane3.setBounds(50, 50, 400, 400);
		tab3.add(tabbedPane3);

		JPanel tab4 = new JPanel();
		GridBagLayout gb4 = new GridBagLayout();
		GridBagConstraints c4 = new GridBagConstraints();
		this.addFormattedField(tab4, " Return ID: ", gb4, c4);
		this.addFormattedField(tab4, " Odometer: ", gb4, c4);
		String[] options4 = new String[] {"Y", "N"};
		this.addComboBox(tab4, " Full Tank? ", options4, gb4, c4);
		this.addLabels(tab4,"Date:", "", gb4, c4);
		this.addOneDatePicker(tab4, gb4, c4);
		this.addSubmitButton(tab4, gb4, c4, "ReturnVehicle");

		JPanel tab5 = new JPanel();
		JTabbedPane tabbedPane5 = new JTabbedPane();
		JPanel tab5_1 = new JPanel();
		GridBagLayout gb5_1 = new GridBagLayout();
		GridBagConstraints c5_1 = new GridBagConstraints();
		String[] branch = delegate.getBranches();
		this.addComboBox(tab5_1, " Branch (Leave empty for all): ", branch, gb5_1, c5_1);
		this.addSubmitButton(tab5_1, gb5_1, c5_1, "ReportRentals");

		JPanel tab5_2 = new JPanel();
		GridBagLayout gb5_2 = new GridBagLayout();
		GridBagConstraints c5_2 = new GridBagConstraints();
		this.addComboBox(tab5_2, " Branch (Leave empty for all): ", branch, gb5_2, c5_2);
		this.addSubmitButton(tab5_2, gb5_2, c5_2, "ReportReturns");

		tabbedPane5.add("Rental Report", tab5_1);
		tabbedPane5.add("Return Report", tab5_2);
		tabbedPane5.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane5.setBounds(50, 50, 500, 400);
		tab5.add(tabbedPane5);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Vehicle", tab1);
		tabbedPane.addTab("Reserve Vehicle", tab2);
		tabbedPane.addTab("Rent Vehicle", tab3);
		tabbedPane.addTab("Return Vehicle", tab4);
		tabbedPane.addTab("Generate reports", tab5);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBounds(50, 50, 400, 400);
        contentPane.add(tabbedPane);

		// anonymous inner class for closing the window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// size the window to obtain a best fit for the components
		this.pack();

		// center the frame
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// make the window visible
		 this.setVisible(true);
    }

    /**
	 * ActionListener Methods
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        try {
			String command = e.getActionCommand();
            if (command == "ViewVehicle") {
                delegate.vehicleQuery((String) this.comboBoxs.get(0).getSelectedItem(), this.textFields.get(0).getText(), 
				this.datePickers.get(0).getJFormattedTextField().getText(), this.datePickers.get(1).getJFormattedTextField().getText());
            } else if (command == "ReserveVehicle") {
				JOptionPane.showMessageDialog(null, (String) this.comboBoxs.get(0).getSelectedItem(), "Error Message", JOptionPane.INFORMATION_MESSAGE);
            } else if (command == "RentVehicle") {
            	RentalModel m = delegate.processRentalwithReservation(Integer.parseInt(this.formattedTextFields.get(2).getText()),
						this.textFields.get(1).getText(), Integer.parseInt(this.formattedTextFields.get(3).getText()),
						this.datePickers.get(4).getJFormattedTextField().getText());
			} else if (command == "RentVehicle2") {
            	RentalModel m = delegate.processRentalwithoutReservation((String) this.comboBoxs.get(2).getSelectedItem(), Integer.parseInt(this.formattedTextFields.get(4).getText()), this.datePickers.get(5).getJFormattedTextField().getText(),
						this.datePickers.get(6).getJFormattedTextField().getText(), this.textFields.get(2).getText(), Integer.parseInt(this.formattedTextFields.get(5).getText()),
						this.datePickers.get(7).getJFormattedTextField().getText());
			} else if (command == "ReturnVehicle") {
				RentReturnModel m = delegate.processReturn(Integer.parseInt(this.formattedTextFields.get(6).getText()), (String) this.comboBoxs.get(3).getSelectedItem(),
						Integer.parseInt(this.formattedTextFields.get(7).getText()), datePickers.get(8).getJFormattedTextField().getText());
			} else if (command == "ReportRentals") {
            	String text = this.textFields.get(3).getText();
				if (text == "") {
					ReportModel m = delegate.generateRentalReport();
				} else {
					String[] branchInfo = text.split("-");
					BranchReportModel m = delegate.generateRentalReport(branchInfo[0], branchInfo[1]);
				}
			} else if (command == "ReportReturns") {
				String text = this.textFields.get(4).getText();
				if (text == "") {
					ReportModel m = delegate.generateRentalReport();
				} else {
					String[] branchInfo = text.split("-");
					BranchReportModel m = delegate.generateRentalReport(branchInfo[0], branchInfo[1]);
				}
			}
        }
        catch (Exception err) {
            JOptionPane.showMessageDialog(null, err, "Error Message", JOptionPane.INFORMATION_MESSAGE);
        }
	}

	public void addField(JPanel tab, String Field, GridBagLayout gb, GridBagConstraints c) {
		JLabel fieldID = new JLabel("Enter" + Field);
		JTextField textField = new JTextField(TEXT_FIELD_WIDTH);
		textField.setMinimumSize(textField.getPreferredSize());

		tab.setLayout(gb);

		// place the field label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(fieldID, c);
		tab.add(fieldID);

		// place the text field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(textField, c);
		tab.add(textField);

		this.textFields.add(textField);
	}

	public void addFormattedField(JPanel tab, String Field, GridBagLayout gb, GridBagConstraints c) {
		JLabel fieldID = new JLabel("Enter" + Field);
		NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		integerFieldFormatter.setMaximumFractionDigits(0);
		JFormattedTextField textField = new JFormattedTextField(integerFieldFormatter);
		textField.setColumns(TEXT_FIELD_WIDTH);
		textField.setMinimumSize(textField.getPreferredSize());

		tab.setLayout(gb);

		// place the field label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(fieldID, c);
		tab.add(fieldID);

		// place the text field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(textField, c);
		tab.add(textField);

		this.formattedTextFields.add(textField);
	}

	public void addComboBox(JPanel tab, String Field, String[] options, GridBagLayout gb, GridBagConstraints c) {
		JLabel fieldID = new JLabel("Enter" + Field);
		JComboBox<String> vehicleList = new JComboBox<>(options);
		tab.setLayout(gb);

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(fieldID, c);
		tab.add(fieldID);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(vehicleList, c);
        tab.add(vehicleList);
        
        this.comboBoxs.add(vehicleList);
	}

	public void addTimeSpinner(JPanel tab, GridBagLayout gb, GridBagConstraints c) {
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.MINUTE);

		JSpinner spinner = new JSpinner();
		spinner.setModel(model);
		spinner.setEditor(new JSpinner.DateEditor(spinner, "h:mm a"));

		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setCalendarField(Calendar.MINUTE);

		JSpinner spinner2 = new JSpinner();
		spinner2.setModel(model2);
		spinner2.setEditor(new JSpinner.DateEditor(spinner2, "h:mm a"));

		tab.setLayout(gb);

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(spinner, c);
		tab.add(spinner);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(spinner2, c);
		tab.add(spinner2);

		this.timeSpinners.add(spinner);
		this.timeSpinners.add(spinner2);
	}

	public void addOneTimeSpinner(JPanel tab, GridBagLayout gb, GridBagConstraints c) {
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.MINUTE);

		JSpinner spinner = new JSpinner();
		spinner.setModel(model);
		spinner.setEditor(new JSpinner.DateEditor(spinner, "h:mm a"));
		tab.setLayout(gb);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(spinner, c);
		tab.add(spinner);

		this.timeSpinners.add(spinner);
	}

	public void addDatePicker(JPanel tab, GridBagLayout gb, GridBagConstraints c) {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
        p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		UtilDateModel model2 = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker.setMinimumSize(datePicker.getPreferredSize());
        datePicker2.setMinimumSize(datePicker.getPreferredSize());
		tab.setLayout(gb);

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 5, 5, 0);
		gb.setConstraints(datePicker, c);
		tab.add(datePicker);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 5, 10, 0);
		gb.setConstraints(datePicker2, c);
		tab.add(datePicker2);

		this.datePickers.add(datePicker);
		this.datePickers.add(datePicker2);
	}

	public void addOneDatePicker(JPanel tab, GridBagLayout gb, GridBagConstraints c) {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
        p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setMinimumSize(datePicker.getPreferredSize());
		tab.setLayout(gb);

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 10, 0);
		gb.setConstraints(datePicker, c);
		tab.add(datePicker);

		this.datePickers.add(datePicker);
	}

	public void addSubmitButton(JPanel tab, GridBagLayout gb, GridBagConstraints c, String actionCommand) {
		JButton submitButton = new JButton("Submit");

		// place the submit button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(submitButton, c);
		tab.add(submitButton);

		// register login button with action event handler
		submitButton.addActionListener(this);
		submitButton.setActionCommand(actionCommand);
	}

	public void addLabels(JPanel tab, String label1, String label2, GridBagLayout gb, GridBagConstraints c) {
		JLabel fromDate = new JLabel(label1);
		JLabel toDate = new JLabel(label2);
		tab.setLayout(gb);

		// place the label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(fromDate, c);
		tab.add(fromDate);

		// place the field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(toDate, c);
		tab.add(toDate);
	}
}