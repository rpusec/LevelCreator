package view;

import java.awt.event.*;

import javax.swing.*;

import model.AvailableTexture;
import controller.Controller;

public class RemoveAvailableTexture extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JComboBox<AvailableTexture> jcbATList;
	private JButton jbRemove;
	private Controller controller;
	
	public RemoveAvailableTexture(Controller contr)
	{
		//setting up the controller
		this.controller = contr;
		
		//setting up the main jpanels
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpSouth = new JPanel();
		
		//constructing the JComboBox
		jcbATList = new JComboBox<AvailableTexture>();
		
		//constructing remove button
		jbRemove = new JButton("Remove");
		
		updateComboBox();
		checkATAmount();
		
		//adding components to the panels
		jpNorth.add(new JLabel("Remove an Available Texture from the list. "));
		jpCenter.add(new JLabel("Available Texture: "));
		jpCenter.add(jcbATList);
		jpSouth.add(jbRemove);
		
		//adding components to the frame itself
		add(jpNorth, "North");
		add(jpCenter, "Center");
		add(jpSouth, "South");
		
		//initial information
		setTitle("Remove Available Texture");
		pack();
		setSize(getPreferredSize().width+200, getPreferredSize().height);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		//adding action listener
		jbRemove.addActionListener(this);
	}
	
	private void updateComboBox()
	{
		//removes all items beforehand, so that the updated list will be set
		jcbATList.removeAllItems();
		
		//adding components to the JComboBox
		for(AvailableTexture at : controller.getAvailableTextures())
			jcbATList.addItem(at);
	}
	
	private void checkATAmount()
	{
		//jbRemove is disabled if there's no ATs left
		if(controller.getAvailableTextures().size() == 0)
			jbRemove.setEnabled(false);
		else
			jbRemove.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jbRemove)
		{
			controller.removeAvailableTexture((AvailableTexture) jcbATList.getSelectedItem());
			updateComboBox();
			checkATAmount();
		}
		
	}
	
}
