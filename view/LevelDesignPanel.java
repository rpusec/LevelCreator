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

public class LevelDesignPanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean multipleMode = false; //determines when to select multiple DTs
	private boolean multipleAtOnceMode = false; //determines when to select multiple DTs
	private int multipleModeKey = KeyEvent.VK_CONTROL; 
	private int multipleAtOnceKey = KeyEvent.VK_SHIFT;
	private GridBagConstraints c;
	private ProjectInfoPanel pip;
	
	public LevelDesignPanel(Controller contr, ProjectInfoPanel pip) 
	{
		//sets the layout
		setLayout(new GridBagLayout());
		this.c = new GridBagConstraints();
		this.controller = contr;
		this.pip = pip;
		checkCapslock();
		setFocusable(true);
		addKeyListener(this); 
	}
	
	public void buildPanel(int maxHeight, int maxWidth, AvailableTexture defaultAT)
	{
		//sets height and width to controller
		controller.setStageHeight(maxHeight);
		controller.setStageWidth(maxWidth);
		
		//removes all DisplayTextures
		removeAll();
		
		if(controller.getDisplayTextures() != null)
			removeAllActionListeners();
		
		//holds the array of displayTextures and adds it later to controller
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
		
		//adds it to the controller
		controller.setDisplayTextures(newDisplayTextures);
		controller.setDefaultAT(defaultAT);
	}
	
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
	
	private void checkCapslock()
	{
		if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
			pip.notifyCapslock(true);
		else
			pip.notifyCapslock(false);
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == multipleModeKey) 
			multipleMode = true;
		
		if(ke.getKeyCode() == multipleAtOnceKey) 
			multipleAtOnceMode = true;
		
		checkCapslock();
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
