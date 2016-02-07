package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import lcprogressbars.LDPProgressBar;
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
	
	public void buildPanel(int maxHeight, int maxWidth, AvailableTexture defaultAT)
	{
		buildPanel(maxHeight, maxWidth, defaultAT, true);
	}
	
	/**
	 * Builds the panel itself based on the applied height and width. 
	 * @param maxHeight Max height.
	 * @param maxWidth Max width.
	 * @param defaultAT Default AvailableTexture. 
	 */
	public void buildPanel(int maxHeight, int maxWidth, AvailableTexture defaultAT, boolean displayPB)
	{
		//sets height and width to controller
		controller.setStageHeight(maxHeight);
		controller.setStageWidth(maxWidth);
		controller.setDefaultAT(defaultAT);
		
		//removes all DisplayTextures
		removeAll();
		
		if(controller.getDisplayTextures() != null)
			removeAllActionListeners();
		
		//the array of displayTextures, adds it later to controller
		ArrayList<DisplayTexture> newDisplayTextures = new ArrayList<DisplayTexture>();
		controller.setDisplayTextures(newDisplayTextures);
		
		if(displayPB)
		{
			Thread tLoadingProgress = new Thread(new LDPProgressBar(newDisplayTextures, controller.getStageHeight() * controller.getStageWidth()));
			tLoadingProgress.start();
		}
		
		drawDTs(newDisplayTextures, true, displayPB);
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
			if(!isMultipleMode() && !multipleAtOnceMode)
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
			setMultipleMode(true);
		
		if(ke.getKeyCode() == multipleAtOnceKey) 
			multipleAtOnceMode = true;
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
		if(ke.getKeyCode() == multipleModeKey) 
			setMultipleMode(false);
		
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
	public void updateStage(ArrayList<DisplayTexture> updatedDTs) {
		//removes all DisplayTextures
		removeAll();
		
		if(controller.getDisplayTextures() != null)
			removeAllActionListeners();
		
		drawDTs(updatedDTs, false, true);
	}
	
	private void drawDTs(ArrayList<DisplayTexture> updatedDTs, boolean areDTsNew, boolean displayPB)
	{
		Thread tDDT = new Thread(new DrawDisplayTextures(updatedDTs, areDTsNew, this, this));
		tDDT.start();
	}
	
	public boolean isMultipleMode() {
		return multipleMode;
	}

	public void setMultipleMode(boolean multipleMode) {
		this.multipleMode = multipleMode;
	}

	class DrawDisplayTextures implements Runnable{
		
		private ArrayList<DisplayTexture> targetDTs;
		private boolean areDTsNew;
		private ActionListener dtActionListener;
		private KeyListener dtKeyListener;
		
		public DrawDisplayTextures(
				ArrayList<DisplayTexture> targetDTs, 
				boolean areDTsNew,
				ActionListener dtActionListener,
				KeyListener dtKeyListener){
			this.targetDTs = targetDTs;
			this.areDTsNew = areDTsNew;
			this.dtActionListener = dtActionListener;
			this.dtKeyListener = dtKeyListener;
		}
		
		@Override
		public void run() {
			int currColumn = 0;
			int currRow = 0;
			
			int size = targetDTs.size();
			
			if(areDTsNew)
			{
				size = controller.getStageHeight() * controller.getStageWidth();
			}
			
			//adds the DisplayTextures to the list
			for(int i = 0; i < size; i++)
			{
				if(currColumn != controller.getStageWidth())
				{
					if(!areDTsNew)
					{
						targetDTs.get(i).addActionListener(dtActionListener);
						targetDTs.get(i).addKeyListener(dtKeyListener);
					}
					else
					{
						DisplayTexture newDisplayTexture = new DisplayTexture(controller.getDefaultAT());
						newDisplayTexture.addActionListener(dtActionListener);
						newDisplayTexture.addKeyListener(dtKeyListener);
						targetDTs.add(newDisplayTexture);
					}
					
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = currColumn;
					c.gridy = currRow;
					
					if(!areDTsNew)
						add(targetDTs.get(i), c);
					else
						add(targetDTs.get(targetDTs.size()-1), c);
					
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
}
