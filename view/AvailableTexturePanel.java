package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.AvailableTexture;
import controller.*;

public class AvailableTexturePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private int currRow;
	private GridBagConstraints c;
	
	public AvailableTexturePanel(Controller contr)
	{
		this.controller = contr;
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(200, getPreferredSize().height));
		setBackground(Color.LIGHT_GRAY);
	}
	
	public void buildPanel(ArrayList<AvailableTexture> availableTextures)
	{
		//removes all AvailableTextures
		removeAll();
		
		c = new GridBagConstraints();
		
		currRow = 0;
		
		//adds available textures to the stage
		for(AvailableTexture at : availableTextures)
			addAT(at);
		
		validate();
		repaint();
		
		controller.setAvailableTextures(availableTextures);
	}
	
	public void addAvailableTexture(AvailableTexture newAvailableTexture)
	{
		addAT(newAvailableTexture);
		
		validate();
		repaint();
	}
	
	public void removeAvailableTexture(AvailableTexture availableTexture)
	{
		remove(availableTexture);
		
		validate();
		repaint();
	}
	
	private void addAT(AvailableTexture newAvailableTexture)
	{
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = currRow;
		
		add(newAvailableTexture, c);
		
		currRow++;
	}

}
