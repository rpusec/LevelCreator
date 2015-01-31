package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import controller.Controller;
import validation.Validation;
import model.AvailableTexture;

/**
 * 
 * This is the frame which starts the new project. 
 * @author Roman Pusec
 *
 */
public class StartNewProject extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField jtfName, jtfKey, jtfStageWidth, jtfStageHeight, jftDefaultAT;
	private JTextArea jtaConsole;
	private JButton jbAdd, jbFinish, jbUndo, jbReset;
	private ArrayList<AvailableTexture> availableTextures = new ArrayList<AvailableTexture>();
	private final int MAX_AT_NUM = 35; 
	private Controller controller;
	
	/**
	 * Constructs the GUI and adds the controller reference. 
	 * @param contr Controller reference. 
	 */
	public StartNewProject(Controller contr)
	{
		//setting the title
		setTitle("Start New Project");
		
		//creating main JPanels
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpSouth = new JPanel();
		jpNorth.setLayout(new BorderLayout());
		jpCenter.setLayout(new GridLayout(2,2,5,0));
		jpSouth.setLayout(new BorderLayout());
		jpNorth.setBorder(BorderFactory.createTitledBorder("Add texture"));
		jpCenter.setBorder(BorderFactory.createTitledBorder("Set width and height"));
		
		//creating subjpanels
		JPanel jpHeading = new JPanel();
		JPanel jpAddInfo = new JPanel();
		jpAddInfo.setLayout(new GridLayout(0,5,5,5));
		JPanel jpConsole = new JPanel();
		jpConsole.setBorder(BorderFactory.createTitledBorder("Console"));
		JPanel jpOptions = new JPanel();
		jpOptions.setBorder(BorderFactory.createTitledBorder("Options"));
		
		//creating textfields
		jtfName = new JTextField(5);
		jtfKey = new JTextField(1);
		jtfStageWidth = new JTextField(2);
		jtfStageHeight = new JTextField(2);
		jftDefaultAT = new JTextField(2);
		
		//creating the jbuttons
		jbAdd = new JButton("Add");
		jbFinish = new JButton("Finish");
		jbUndo = new JButton("Undo");
		jbUndo.setEnabled(false);
		jbReset = new JButton("Reset");
		
		//creating the console for jpConsole
		jtaConsole = new JTextArea(10,30);
		jtaConsole.setWrapStyleWord(true);
		jtaConsole.setLineWrap(true); 
		jtaConsole.setDisabledTextColor(Color.BLACK);
		jtaConsole.setEditable(false);
		
		//adding components to jpHeading
		jpHeading.add(new JLabel("Please insert your textures. "));
		
		//adding components to jpAddInfo
		jpAddInfo.add(new JLabel("Name: ", JLabel.CENTER));
		jpAddInfo.add(jtfName);
		jpAddInfo.add(new JLabel("Key: ", JLabel.CENTER));
		jpAddInfo.add(jtfKey);
		jpAddInfo.add(jbAdd);
		jpAddInfo.add(new JLabel());
		jpAddInfo.add(new JLabel());
		jpAddInfo.add(new JLabel("Default AT: "));
		jpAddInfo.add(jftDefaultAT);
		
		//adding components to jpConsole
		jpConsole.add(new JScrollPane(jtaConsole));
		
		//adding components to jpOptions
		jpOptions.add(jbUndo);
		jpOptions.add(jbReset);
		jpOptions.add(jbFinish);
		
		//adding components to jpNorth, jpSouth, and jpCenter
		jpNorth.add(jpHeading, "North");
		jpNorth.add(jpAddInfo, "South");
		jpCenter.add(new JLabel("Width: ", JLabel.CENTER));
		jpCenter.add(jtfStageWidth);
		jpCenter.add(new JLabel("Height: ", JLabel.CENTER));
		jpCenter.add(jtfStageHeight);
		jpSouth.add(jpConsole, "North");
		jpSouth.add(jpOptions, "South");
		
		//adding components to the frame
		add(jpNorth, "North");
		add(jpSouth, "South");
		add(jpCenter, "Center");
		
		//setting initial settings
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		//action listeners
		jbAdd.addActionListener(this);
		jbFinish.addActionListener(this);
		jbUndo.addActionListener(this);
		jbReset.addActionListener(this);
		
		this.controller = contr;
	}
	
	/**
	 * Resets all of the inputs. 
	 */
	private void resetInput()
	{
		jtaConsole.setText("");
		jtfName.setText("");
		jtfKey.setText("");
		jftDefaultAT.setText("");
		availableTextures.clear();
	}
	
	/**
	 * Prints all of the ATs. 
	 * @return List of ATs in String format. 
	 */
	private String printAvailableTextures()
	{
		String listStr = "";
		
		int counter = 1;
		for(AvailableTexture at : availableTextures)
		{
			listStr += "#" + counter + " " + at.toString() + "\n";
			counter++;
		}
		
		return listStr;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jbAdd)
		{
			if(!Controller.isKeyOriginal(jtfKey.getText().charAt(0), availableTextures))
			{
				JOptionPane.showMessageDialog(null, "Please use another key. ", "Wrong key", JOptionPane.WARNING_MESSAGE); 
				return;
			}
			else if(jtfName.getText().length() == 0 || jtfName.getText().length() > 8)
			{
				JOptionPane.showMessageDialog(null, "The name should be less or equal to 8 characters. ", "Illegal name length", JOptionPane.WARNING_MESSAGE); 
				return;
			}
			else if(availableTextures.size() == MAX_AT_NUM)
			{
				JOptionPane.showMessageDialog(null, "The number of textures should be less or equal to " + MAX_AT_NUM + ". ", "Illegal texture amount", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			availableTextures.add(new AvailableTexture(jtfName.getText(), jtfKey.getText().charAt(0), Controller.getRandomColor()));
			jtaConsole.setText(printAvailableTextures());
		}
		else if(e.getSource() == jbUndo)
		{
			availableTextures.remove(availableTextures.size()-1);
			jtaConsole.setText(printAvailableTextures());
		}
		else if(e.getSource() == jbReset)
		{
			resetInput();
		}
		else if(e.getSource() == jbFinish)
		{
			if(!Validation.validateInputLength(jtfStageHeight, 0, 3) || !Validation.validateInputLength(jtfStageWidth, 0, 3))
			{
				JOptionPane.showMessageDialog(null, "Stage width and height should be less or equal to 999. ", "Illegal stage size", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(!Validation.validateNumberFormat(jtfStageHeight) || !Validation.validateNumberFormat(jtfStageWidth))
			{
				JOptionPane.showMessageDialog(null, "Please insert an integer for the width and height of the stage. ", "Illegal stage number format", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(jftDefaultAT.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(null, "Please insert a character for your Default AT. ", "No DefaultAT character", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(!Controller.isKeyOriginal(jftDefaultAT.getText().charAt(0), availableTextures))
			{
				JOptionPane.showMessageDialog(null, "Please use another key for your Default AT. ", "Illegal DefaultAT character", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			AvailableTexture defaultAT = new AvailableTexture("default", jftDefaultAT.getText().charAt(0), Color.WHITE);
			
			controller.buildWorkingStage(
					Integer.parseInt(jtfStageHeight.getText()), 
					Integer.parseInt(jtfStageWidth.getText()),
					defaultAT,
					availableTextures,
					null);
			
			controller.notifyNewFile();
			
			dispose();
		}
		
		//constantly testing if the Undo jButton should be available
		jbUndo.setEnabled(availableTextures.size() == 0 ? false : true);
	}
}
