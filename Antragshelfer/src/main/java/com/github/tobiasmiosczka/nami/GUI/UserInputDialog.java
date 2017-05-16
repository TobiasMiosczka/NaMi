package com.github.tobiasmiosczka.nami.GUI;

import com.github.tobiasmiosczka.nami.extendetjnami.TimeHelp;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserInputDialog implements ActionListener{

	abstract class Input<T>{
		private final JLabel label;
		
		public Input(JPanel parent, int index, String description) {
			label = new JLabel(description + ":");
			label.setBounds(10, 11 + (index * 30), 130,  20);
			parent.add(label);
		}
		
		public void showError(){
			label.setForeground(Color.RED);
		}
		
		public abstract boolean check();
		public abstract T getValue();
		public abstract String toString();
	}
	
	private class InputString extends Input<String>{
		final JTextField textField;
		public InputString(JPanel parent, int index, String description, String preview) {
			super(parent, index, description);
			textField = new JTextField(preview);
			textField.setBounds(150,  11 + (index * 30), 220,  20);
			parent.add(textField);
		}

		@Override
		public boolean check() {
			return true;
		}

		@Override
		public String getValue() {
			return textField.getText();
		}

		@Override
		public String toString() {
			return getValue();
		}
	}

	private class InputInteger extends Input<Integer>{
		final JTextField textField;
		public InputInteger(JPanel parent, int index, String description, int preview) {
			super(parent, index, description);
			textField = new JTextField(preview);
			textField.setBounds(150,  11 + (index * 30), 220,  20);
			parent.add(textField);
		}

		@Override
		public boolean check() {
			try {
				Integer.parseInt(textField.getText());
		    } catch(NumberFormatException e) {
		        return false;
		    }
		    return true;
		}

		@Override
		public Integer getValue() {
			return Integer.getInteger(textField.getText());
		}

		@Override
		public String toString() {
			return String.valueOf(getValue());
		}

	}
	
	private class InputDate extends Input<Date>{
		final JTextField textField;
		public InputDate(JPanel parent, int index, String description, Date preview) {
			super(parent, index, description);
			textField = new JTextField(TimeHelp.getDateString(preview));
			textField.setBounds(150,  11 + (index * 30), 220,  20);
			parent.add(textField);
		}

		@Override
		public boolean check() {
			try {
				TimeHelp.getDateFromInputInput(textField.getText());
			} catch (ParseException e) {
				return false;
			}
			return true;
		}

		@Override
		public Date getValue() {
			try {
				return TimeHelp.getDateFromInputInput(textField.getText());
			} catch (ParseException e) {
				return null;
			}
		}

		@Override
		public String toString() {
			return TimeHelp.getDateString(getValue());
		}	
	}
	
	private class InputBoolean extends Input<Boolean>{
		final JCheckBox checkBox;
		public InputBoolean(JPanel parent, int index, String description, boolean preview) {
			super(parent, index, description);
			checkBox = new JCheckBox();
			checkBox.setSelected(preview);
			checkBox.setBounds(150,  11 + (index * 30), 220,  20);
			parent.add(checkBox);
		}

		@Override
		public boolean check() {
			return true;
		}

		@Override
		public Boolean getValue() {
			return checkBox.isSelected();
		}

		@Override
		public String toString() {
			return null;
		}
		
	}
	
	private final JDialog dialog;
	private final JPanel panel;

	private final List<Input> inputList = new LinkedList<>();
	private boolean isOK;
	
	private final JButton btnOK;
	private final JButton btnCancel;
		
	/**
	 * Create the panel.
	 */
	public UserInputDialog(JFrame owner) {
		dialog = new JDialog(owner, "Optionen", true);
		dialog.setResizable(false);
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));

		isOK = false;

		panel = new JPanel();
		dialog.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panelOkCancel = new JPanel();
		dialog.getContentPane().add(panelOkCancel, BorderLayout.SOUTH);
		
		btnOK = new JButton("Fertig");
		btnOK.addActionListener(this);
		panelOkCancel.add(btnOK);
		
		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(this);
		panelOkCancel.add(btnCancel);
	}

	private void updateBounds() {
		dialog.setBounds(0, 0, 400, (inputList.size() * 30) + 80);
	}
	
	public void addStringOption(String description, String preview){
		inputList.add(new InputString(panel, inputList.size(), description, preview));
		updateBounds();
	}
	
	public void addIntegerOption(String description, int preview){
		inputList.add(new InputInteger(panel, inputList.size(), description, preview));
		updateBounds();
	}

	public void addDateOption(String description, Date preview){
		inputList.add(new InputDate(panel, inputList.size(), description, preview));
		updateBounds();
	}
	
	public void addBooleanOption(String description, boolean preview){
		inputList.add(new InputBoolean(panel, inputList.size(), description, preview));
		updateBounds();
	}
	
	public boolean showModal(){
		if(getOptionCount() == 0){
			return true;
		}
		dialog.setVisible(true);
		return isOK;
	}

	public Object getOption(int index){
		return inputList.get(index).getValue();
	}

	private boolean check(){
		boolean valid = true;
		for (Input input : inputList) {
			if(!input.check()){
				input.showError();
				valid = false;
			}
		}
		return valid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btnOK){
			if(check()){
				isOK = true;
				dialog.setVisible(false);
			}
		}
		if(source == btnCancel){
			isOK = false;
			dialog.setVisible(false);
		}
	}

	private int getOptionCount() {
		return inputList.size();
	}
}