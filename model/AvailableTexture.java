package model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Available Textures (AT) are textures that are available for use
 * in user's levels. Its main attributes are the 'name' of the 
 * texture, the keyboard 'key' which draws the said texture, and
 * the color of the texture. 
 * @author Roman Pusec
 *
 */
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
	
	/**
	 * Constructs the AT. 
	 * @param name The name of the texture.
	 * @param key The key this texture is denoted with. 
	 * @param color The color of the texture. 
	 */
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

	/**
	 * Returns the name of the AT. 
	 * @return name. 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the key of the AT. 
	 * @return key
	 */
	public char getKey() {
		return key;
	}

	/**
	 * Returns the color of the AT.
	 * @return color 
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns the name, key, and color of the AT. 
	 */
	public String toString()
	{
		return NAME_TAG + ": " + this.name + ", " + KEY_TAG + ": " + this.key + ", " + COLOR_TAG + ": " + this.color.getRGB();
	}
}
