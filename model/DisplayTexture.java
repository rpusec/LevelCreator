package model;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class DisplayTexture extends JButton {

	private static final long serialVersionUID = 1L;
	private AvailableTexture atInUse;
	private boolean isSelected = false;
	private Border defaultBorder = BorderFactory.createLineBorder(Color.GRAY);
	private final int BUTTON_SIZE = 50;
	
	public DisplayTexture(AvailableTexture availableTexture)
	{
		atInUse = availableTexture;
		setBackground(atInUse.getColor()); 
		setBorder(defaultBorder);
		setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
	}
	
	public void setAvailableTexture(AvailableTexture newAT)
	{
		atInUse = newAT;
		setBackground(atInUse.getColor()); 
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		
		if(isSelected)
			setBorder(BorderFactory.createLineBorder(Color.CYAN));
		else
			setBorder(defaultBorder);
	}
	
	public char getKey()
	{
		return atInUse.getKey();
	}

}
