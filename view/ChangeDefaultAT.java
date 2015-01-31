package view;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import model.AvailableTexture;
import controller.Controller;

/**
 * 
 * Changes the key of the default AT. 
 * @author Roman Pusec
 *
 */
public class ChangeDefaultAT extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JTextField jtfKey;
	private JButton jbChange;
	
	/**
	 * Assembles the GUI and references the controller.
	 * @param contr Controller reference. 
	 */
	public ChangeDefaultAT(Controller contr)
	{
		//setting up the controller
		this.controller = contr;
		
		//setting up the main JPanels
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpSouth = new JPanel();
		
		//setting the components
		jtfKey = new JTextField(1);
		jbChange = new JButton("Modify");
		
		//adding components to the jpanels
		jpNorth.add(new JLabel("Change the default Available Texture. "));
		jpCenter.add(new JLabel("New key: "));
		jpCenter.add(jtfKey);
		jpSouth.add(jbChange);
		
		//adding components to the frame itself
		add(jpNorth, "North");
		add(jpCenter, "Center");
		add(jpSouth, "South");
		
		//initial information
		setTitle("Change Default Available Texture");
		pack();
		setSize(getPreferredSize().width+200, getPreferredSize().height);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		jbChange.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jbChange)
		{
			if(!Controller.isKeyOriginal(jtfKey.getText().charAt(0), controller.getAvailableTextures()))
			{
				JOptionPane.showMessageDialog(null, "Please insert a different key. ", "Illegal key choice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			controller.setDefaultAT(new AvailableTexture("default", jtfKey.getText().charAt(0), Color.WHITE));
			JOptionPane.showMessageDialog(null, "The key was successfully modified! ", "Key modified", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		
	}
	
}
