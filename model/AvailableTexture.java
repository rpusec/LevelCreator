package model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AvailableTexture extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private char key;
	private Color color;
	private JPanel jpNameKey, jpColor;
	private JLabel jlNameKey;
	
	public static final String NAME_TAG = "Name";
	public static final String KEY_TAG = "Key";
	public static final String COLOR_TAG = "Color";
	
	public AvailableTexture(String name, char key, Color color)
	{
		this.name = name;
		this.key = key;
		this.color = color;
		
		jpNameKey = new JPanel();
		jpColor = new JPanel(); 
		
		jlNameKey = new JLabel(this.name + " - " + this.key);
		
		jpNameKey.add(jlNameKey);
		jpColor.setBackground(this.color);
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
		
		add(jpNameKey, "Center");
		add(jpColor, "East");
	}

	public String getName() {
		return name;
	}

	public char getKey() {
		return key;
	}

	public Color getColor() {
		return color;
	}
	
	public String toString()
	{
		return NAME_TAG + ": " + this.name + ", " + KEY_TAG + ": " + this.key + ", " + COLOR_TAG + ": " + this.color.getRGB();
	}
}
