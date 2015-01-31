package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
import model.*;
import controller.*;

/**
 * 
 * This is the component which is used by
 * the end user to draw a single level. Its DTs 
 * are stored in the controller.
 * @author Roman Pusec
 *
 */
public class LevelDesignPanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean multipleMode = false; //determines when to select multiple DTs
	private boolean multipleAtOnceMode = false; //determines when to select multiple DTs
	private int multipleModeKey = KeyEvent.VK_CONTROL; 
	private int multipleAtOnceKey = KeyEvent.VK_SHIFT;
	private GridBagConstraints c;
	
	/**
	 * Sets the layout, and references the initial controller. 
	 * @param contr The controller to reference
	 */
	public LevelDesignPanel(Controller contr) 
	{
		//sets the layout
		setLayout(new GridBagLayout());
		this.c = new GridBagConstraints();
		this.controller = contr;
		setFocusable(true);
		addKeyListener(this); 
	}
	
	/**
	 * Builds the panel itself based on the applied height and width. 
	 * @param maxHeight Max height.
	 * @param maxWidth Max width.
	 * @param defaultAT Default AvailableTexture. 
	 */
	public void buildPanel(int maxHeight, int maxWidth, AvailableTexture defaultAT)
	{
		//sets height and width to controller
		controller.setStageHeight(maxHeight);
		controller.setStageWidth(maxWidth);
		
		//removes all DisplayTextures
		removeAll();
		
		if(controller.getDisplayTextures() != null)
			removeAllActionListeners();
		
		//the array of displayTextures, adds it later to controller
		ArrayList<DisplayTexture> newDisplayTextures = new ArrayList<DisplayTexture>();
		
		//current values
		int currColumn = 0;
		int currRow = 0;
		
		//adds the DisplayTextures to the list
		for(int i = 0; i < maxHeight * maxWidth; i++)
		{
			if(currColumn != maxWidth)
			{
				DisplayTexture newDT = new DisplayTexture(defaultAT);
				newDT.addActionListener(this);
				newDT.addKeyListener(this);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = currColumn;
				c.gridy = currRow;
				
				add(newDT, c);
				newDisplayTextures.add(newDT);
				
				currColumn++;
			}
			else
			{
				currColumn = 0;
				currRow++;
				i--;
			}
		}
		
		validate();
		repaint();
		
		//adds the new DTs to the controller
		controller.setDisplayTextures(newDisplayTextures);
		controller.setDefaultAT(defaultAT);
	}
	
	/**
	 * Removes all ActionListeners from all DTs. 
	 */
	private void removeAllActionListeners()
	{
		for(DisplayTexture dt : controller.getDisplayTextures())
			for(ActionListener al : dt.getActionListeners())
				dt.removeActionListener(al);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() instanceof DisplayTexture)
		{
			if(!multipleMode && !multipleAtOnceMode)
				controller.removeAllFromSelected();
			
			if(!multipleAtOnceMode)
				controller.addToSelected((DisplayTexture) e.getSource(), true);
			else
				controller.selectMultipleAtOnce((DisplayTexture) e.getSource());
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == multipleModeKey) 
			multipleMode = true;
		
		if(ke.getKeyCode() == multipleAtOnceKey) 
			multipleAtOnceMode = true;
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
		if(ke.getKeyCode() == multipleModeKey) 
			multipleMode = false;
		
		if(ke.getKeyCode() == multipleAtOnceKey) 
			multipleAtOnceMode = false;
		
		//if backspace was pressed, we need to remove
		//the selected textures
		if(ke.getKeyCode() != KeyEvent.VK_BACK_SPACE)
			controller.drawTextureToStage(ke.getKeyChar());
		else
			controller.removeTexturesFromStage();
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	/**
	 * Removes all of the DisplayTextures and adds 
	 * new, updates DisplayTextures to the stage. 
	 * @param updatedStage The array of new or updated DisplayTextures. 
	 */
	public void updateStage(ArrayList<DisplayTexture> updatedStage) {
		//removes all DisplayTextures
		removeAll();
		
		if(controller.getDisplayTextures() != null)
			removeAllActionListeners();
		
		//current values
		int currColumn = 0;
		int currRow = 0;
		
		//adds the DisplayTextures to the list
		for(int i = 0; i < updatedStage.size(); i++)
		{
			if(currColumn != controller.getStageWidth())
			{
				updatedStage.get(i).addActionListener(this);
				updatedStage.get(i).addKeyListener(this);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = currColumn;
				c.gridy = currRow;
				
				add(updatedStage.get(i), c);
				
				currColumn++;
			}
			else
			{
				currColumn = 0;
				currRow++;
				i--;
			}
		}
		
		validate();
		repaint();
	}
	
}
