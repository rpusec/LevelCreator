package model;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * 
 * The display textures (DT) are AvailableTextures 
 * that are displayed on the stage. 
 * @author Roman Pusec
 *
 */
public class DisplayTexture extends JButton {

	private static final long serialVersionUID = 1L;
	private AvailableTexture atInUse;
	private boolean isSelected = false;
	private Border defaultBorder = BorderFactory.createLineBorder(Color.GRAY);
	private final int BUTTON_SIZE = 50;
	
	/**
	 * Constructs the DT. 
	 * @param availableTexture AT in use. 
	 */
	public DisplayTexture(AvailableTexture availableTexture)
	{
		atInUse = availableTexture;
		setBackground(atInUse.getColor()); 
		setBorder(defaultBorder);
		setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
	}
	
	/**
	 * Changes the AvailableTexture. 
	 * @param newAT The new AvailableTexture. 
	 */
	public void setAvailableTexture(AvailableTexture newAT)
	{
		atInUse = newAT;
		setBackground(atInUse.getColor()); 
	}

	/**
	 * Checks if it's selected. 
	 * @return True if selected, false otherwise. 
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Sets the AT as selected or unselected. 
	 * @param selected True to mark the AT as selected, false otherwise. 
	 */
	public void setSelected(boolean selected) {
		this.isSelected = selected;
		
		if(isSelected)
			setBorder(BorderFactory.createLineBorder(Color.CYAN));
		else
			setBorder(defaultBorder);
	}
	
	/**
	 * Returns the key of the DisplayTexture's AT. 
	 * @return AT key. 
	 */
	public char getKey()
	{
		return atInUse.getKey();
	}
	
	/**
	 * Returns the AT in use. 
	 * @return AvailableTexture. 
	 */
	public AvailableTexture getAT()
	{
		return atInUse;
	}
}
