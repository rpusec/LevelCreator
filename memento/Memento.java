package memento;

import java.util.ArrayList;
import java.util.Hashtable;

import model.AvailableTexture;
import model.DisplayTexture;

/**
 * 
 * Represents a single state of the array list
 * of DisplayTextures. 
 * @author Roman Pusec
 *
 */
public class Memento {
	
	private int stageWidth = 0;
	private int stageHeight = 0;
	private AvailableTexture defaultAT;
	private ArrayList<DisplayTexture> displayTextures;
	private ArrayList<AvailableTexture> availableTextures;

	/**
	 * Creates a Memento of the DisplayTexture
	 * ArrayList. 
	 * @param dts The ArrayList of DisplayTextures. 
	 */
	public Memento(ArrayList<DisplayTexture> dts, ArrayList<AvailableTexture> ats, AvailableTexture defaultAT, int stageWidth, int stageHeight)
	{	
		displayTextures = dts;
		availableTextures = ats;
		this.defaultAT = defaultAT;
		this.stageWidth = stageWidth;
		this.stageHeight = stageHeight;
	}
	
	/**
	 * Returns the state of the DT ArrayList. 
	 * @return DisplayTexture ArrayList. 
	 */
	public Hashtable<String, Object> getState()
	{
		Hashtable<String, Object> state = new Hashtable<String, Object>();
		
		state.put("stageWidth", stageWidth);
		state.put("stageHeight", stageHeight);
		state.put("defaultAT", defaultAT);
		state.put("displayTextures", displayTextures);
		state.put("availableTextures", availableTextures);
		
		return state;
	}
}
