package view;

import java.awt.*;
import javax.swing.*;

public class ProjectInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jlFileName, jlCapslockConfirm;
	private JTextField jtfFileName;
	
	private final String CAPSLOCK_ON_MESSAGE = "CAPSLOCK is enabled. ";
	private final String CAPSLOCK_OFF_MESSAGE = "CAPSLOCK is disabled. ";
	
	public ProjectInfoPanel()
	{
		setLayout(new BorderLayout());
		
		//JPanels
		JPanel jpWest = new JPanel();
		JPanel jpEast = new JPanel();
		
		//JLabels
		jlFileName = new JLabel("File: ");
		jlCapslockConfirm = new JLabel(CAPSLOCK_OFF_MESSAGE);
		jlCapslockConfirm.setForeground(Color.GRAY); 
		
		//JTextFields
		jtfFileName = new JTextField(10);
		jtfFileName.setEditable(false);
		
		//adding components to panels
		jpWest.add(jlFileName);
		jpWest.add(jtfFileName);
		jpEast.add(jlCapslockConfirm);
		
		//adding components to the main panel
		add(jpWest, "West");
		add(jpEast, "East");
	}
	
	public void setCurrentFilePath(String path)
	{
		jtfFileName.setText(path);
	}
	
	public void notifyCapslock(boolean isCapslockOn)
	{
		if(isCapslockOn)
		{
			jlCapslockConfirm.setText(CAPSLOCK_ON_MESSAGE); 
			jlCapslockConfirm.setForeground(Color.RED); 
		}
		else
		{
			jlCapslockConfirm.setText(CAPSLOCK_OFF_MESSAGE);
			jlCapslockConfirm.setForeground(Color.GRAY); 
		}
	}

}
