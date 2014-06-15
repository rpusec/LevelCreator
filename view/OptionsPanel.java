package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import validation.Validation;
import controller.Controller;

public class OptionsPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final String ADD_ROW_MESSAGE = "Add a new row to the stage. ";
	private final String REMOVE_ROW_MESSAGE = "Remove a row from the stage. ";
	private final String ADD_COL_MESSAGE = "Add a new column to the stage. ";
	private final String REMOVE_COL_MESSAGE = "Remove a column from the stage. ";
	
	private final String ADD_ROW_COMMAND = "Add Row";
	private final String REMOVE_ROW_COMMAND = "Remove Row";
	private final String ADD_COL_COMMAND = "Add Column";
	private final String REMOVE_COL_COMMAND = "Remove Column";
	
	public static final int ADD_ROW_OPTION = 0;
	public static final int REMOVE_ROW_OPTION = 1;
	public static final int ADD_COL_OPTION = 2;
	public static final int REMOVE_COL_OPTION = 3;
	
	private int maxPos;
	private int minPos;
	
	private JTextField jtfPosition;
	private JButton jbMaxPos, jbAddComp;
	
	private Controller controller;
	
	public OptionsPanel(int option, Controller contr, int maxPos)
	{
		this.controller = contr; 
		
		switch(option)
		{
			case ADD_ROW_OPTION : 
				buildGraphics(ADD_ROW_MESSAGE, ADD_ROW_COMMAND, maxPos);
				break;
			case REMOVE_ROW_OPTION : 
				buildGraphics(REMOVE_ROW_MESSAGE, REMOVE_ROW_COMMAND, maxPos);
				break;
			case ADD_COL_OPTION : 
				buildGraphics(ADD_COL_MESSAGE, ADD_COL_COMMAND, maxPos);
				break;
			case REMOVE_COL_OPTION : 
				buildGraphics(REMOVE_COL_MESSAGE, REMOVE_COL_COMMAND, maxPos);
				break;
		}
	}
	
	private void buildGraphics(String message, String addCommand, int maxPos)
	{
		this.maxPos = maxPos;
		
		//if we're removing rows or cols, the minPos has to be 1
		if(addCommand.substring(0, addCommand.indexOf(' ')).equals("Remove"))
			this.minPos = 1;
		else
			this.minPos = 0;
		
		//setting the components
		JLabel jlMessage = new JLabel(message, JLabel.CENTER);
		JLabel jlPosition = new JLabel("Position: ", JLabel.CENTER);
		jtfPosition = new JTextField(3);
		jbMaxPos = new JButton("Last position");
		jbMaxPos.addActionListener(this);
		jbAddComp = new JButton(addCommand);
		jbAddComp.addActionListener(this);
		jbAddComp.setActionCommand(addCommand);
		
		//jpanels
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(new GridBagLayout());
		JPanel jpSouth = new JPanel();
		
		//adding components to jpanels
		jpNorth.add(jlMessage);
		jpCenter.add(jlPosition);
		jpCenter.add(jtfPosition);
		jpCenter.add(jbMaxPos);
		jpSouth.add(jbAddComp);
		
		//adding jpanels to the frame
		add(jpNorth, "North");
		add(jpCenter, "Center");
		add(jpSouth, "South");
		
		validate();
		
		//initial info
		setTitle(addCommand + " Window");
		pack();
		setSize(getSize().width+200, getSize().height);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	private boolean notifyNullRowsCols()
	{
		if(maxPos <= 1)
		{
			JOptionPane.showMessageDialog(null, "You can't delete the last row/column. ", "Illegal row/column range", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
			return true;
	}
	
	private boolean validateInput(String command)
	{
		if(!Validation.validateNumberFormat(jtfPosition))
		{
			JOptionPane.showMessageDialog(null, "Please insert an integer. ", "Illegal number format", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!Validation.validateNumberLength(Integer.parseInt(jtfPosition.getText()), minPos, maxPos))
		{
			JOptionPane.showMessageDialog(null, "Please insert a number from " + minPos + " to " + maxPos + ". ", "Illegal number range", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jbMaxPos)
		{
			jtfPosition.setText("" + maxPos);
		}
		else if(validateInput(e.getActionCommand()))
		{
			if(e.getActionCommand().equals(ADD_ROW_COMMAND))
			{
				controller.addRow(Integer.parseInt(jtfPosition.getText()));
				maxPos = controller.getStageHeight();
			}
			else if(e.getActionCommand().equals(REMOVE_ROW_COMMAND))
			{
				if(notifyNullRowsCols())
				{
					controller.removeRow(Integer.parseInt(jtfPosition.getText()));
					maxPos = controller.getStageHeight();
				}
			}
			else if(e.getActionCommand().equals(ADD_COL_COMMAND))
			{
				controller.addCol(Integer.parseInt(jtfPosition.getText()));
				maxPos = controller.getStageWidth();
			}
			else if(e.getActionCommand().equals(REMOVE_COL_COMMAND))
			{
				if(notifyNullRowsCols())
				{
					controller.removeCol(Integer.parseInt(jtfPosition.getText()));
					maxPos = controller.getStageWidth();
				}
			}
		}
	}
}
