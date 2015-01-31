package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.AvailableTexture;
import controller.*;

/**
 * 
 * This is the panel which displays AvailableTextures to use. 
 * @author Roman Pusec
 *
 */
public class AvailableTexturePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private int currRow;
	private GridBagConstraints c;
	
	/**
	 * Constructs the GUI and adds the controller reference. 
	 * @param contr
	 */
	public AvailableTexturePanel(Controller contr)
	{
		this.controller = contr;
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(200, getPreferredSize().height));
		setBackground(Color.LIGHT_GRAY);
	}
	
	/**
	 * Builds the panel with the new AvailableTextures. 
	 * @param availableTextures Array of AvailableTextures. 
	 */
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
	
	/**
	 * Adds a single AT. 
	 * @param newAvailableTexture New AT. 
	 */
	public void addAvailableTexture(AvailableTexture newAvailableTexture)
	{
		addAT(newAvailableTexture);
		
		validate();
		repaint();
	}
	
	/**
	 * Removes a single AT. 
	 * @param availableTexture AT to remove. 
	 */
	public void removeAvailableTexture(AvailableTexture availableTexture)
	{
		remove(availableTexture);
		
		validate();
		repaint();
	}
	
	/**
	 * Adds a single AT. Used by the program only.  
	 * @param newAvailableTexture New AT. 
	 */
	private void addAT(AvailableTexture newAvailableTexture)
	{
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = currRow;
		
		add(newAvailableTexture, c);
		
		currRow++;
	}

}
