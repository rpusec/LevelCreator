package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.AvailableTexture;
import controller.Controller;

/**
 * 
 * Adds new Available Texture to the application. 
 * @author Roman Pusec
 *
 */
public class AddAvailableTexture extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField jtfName, jtfKey;
	private JButton jbAdd;
	private Controller controller;
	
	/**
	 * Creates the GUI and references the controller. 
	 * @param contr Controller reference. 
	 */
	public AddAvailableTexture(Controller contr)
	{
		//setting up the controller
		this.controller = contr;
		
		//creating main JPanels
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(new GridBagLayout());
		JPanel jpSouth = new JPanel();
		
		//creating components
		jtfName = new JTextField(5);
		jtfKey = new JTextField(1);
		jbAdd = new JButton("Add");
		
		//adding components to jpanels
		jpNorth.add(new JLabel("Add a new Available Texture. They're the textures that you can use on your level. "));
		jpCenter.add(new JLabel("Name: "));
		jpCenter.add(jtfName);
		jpCenter.add(new JLabel("Key: "));
		jpCenter.add(jtfKey);
		jpSouth.add(jbAdd);
		
		//adding components to the frame itself
		add(jpNorth, "North");
		add(jpCenter, "Center");
		add(jpSouth, "South");
		
		//initial information
		setTitle("Add Available Texture");
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		//adding action listener
		jbAdd.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jbAdd)
		{
			if(jtfName.getText().length() == 0 || jtfName.getText().length() > 8)
			{
				JOptionPane.showMessageDialog(null, "Please insert a name for your texture with less or equal to 8 characters. ", "Illegal Texture name. ", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(jtfKey.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(null, "Please insert a character for the Key. ", "Illegal Key value. ", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(!Controller.isKeyOriginal(jtfKey.getText().charAt(0), controller.getAvailableTextures()))
			{
				JOptionPane.showMessageDialog(null, "Please insert a different character for the Key. ", "Illegal Key value. ", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(jtfKey.getText().charAt(0) == controller.getDefaultAT().getKey())
			{
				JOptionPane.showMessageDialog(null, "Your character intersects with the default AT's character. ", "Illegal Key value. ", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			controller.addAvailableTexture(new AvailableTexture(jtfName.getText(), jtfKey.getText().charAt(0), Controller.getRandomColor())); 
		}
		
	}

}
