package memento;

import java.util.ArrayList;
import java.util.Hashtable;

import model.AvailableTexture;
import model.DisplayTexture;

/**
 * 
 * Gets/sets state for the current Memento.
 * @author Roman Pusec
 *
 */
public class Originator {

	//state
	private int stageWidth = 0;
	private int stageHeight = 0;
	private AvailableTexture defaultAT = null;
	private ArrayList<DisplayTexture> displayTextures = null;
	private ArrayList<AvailableTexture> availableTextures = null;
	
	/**
	 * Saves the state of the object. 
	 * Creates an exact copy of the 
	 * ArrayList and sets it as the 
	 * value of the appropriate attribute. 
	 * @param newState The current state of the object. 
	 */
	@SuppressWarnings("unchecked")
	public void set(ArrayList<DisplayTexture> dtState, ArrayList<AvailableTexture> atState, AvailableTexture datState, int stageWidth, int stageHeight)
	{	
		Hashtable<String, Object> htResult = cloneLists(dtState, atState);
		
		defaultAT = new AvailableTexture(datState.getName(), datState.getKey(), datState.getColor());
		displayTextures = (ArrayList<DisplayTexture>) htResult.get("clonedDTs");
		availableTextures = (ArrayList<AvailableTexture>) htResult.get("clonedATs");
		this.stageWidth = stageWidth;
		this.stageHeight = stageHeight;
	}
	
	/**
	 * Creates the Memento based on the state. 
	 * @return New Memento object. 
	 */
	public Memento createMemento()
	{
		return new Memento(displayTextures, availableTextures, defaultAT, stageWidth, stageHeight);
	}
	
	/**
	 * Restores the Memento object, and restores
	 * the state. 
	 * @param memento The Memento to restore. 
	 * @return The restored Memento. 
	 */
	@SuppressWarnings("unchecked")
	public Memento restoreFromMemento(Memento memento)
	{
		Hashtable<String, Object> state = memento.getState();
		
		//clones the lists
		Hashtable<String, Object> htResult = cloneLists(
				(ArrayList<DisplayTexture>) state.get("displayTextures"), 
				(ArrayList<AvailableTexture>) state.get("availableTextures"));
		
		AvailableTexture tempDAT = (AvailableTexture) state.get("defaultAT");
		
		stageWidth = (Integer) state.get("stageWidth");
		stageHeight = (Integer) state.get("stageHeight");
		defaultAT = new AvailableTexture(tempDAT.getName(), tempDAT.getKey(), tempDAT.getColor());
		displayTextures = (ArrayList<DisplayTexture>) htResult.get("clonedDTs");
		availableTextures = (ArrayList<AvailableTexture>) htResult.get("clonedATs");
		
		return memento;
	}
	
	/**
	 * Makes an identical copy of the DisplayTextures 
	 * and the AvailableTextures ArrayLists. 
	 * @param dtState The current state of the DisplayTextures. 
	 * @param atState The current state of the AvailableTextures. 
	 * @return A Hashtable containing the DisplayTextures and AvailableTextures. 
	 */
	private Hashtable<String, Object> cloneLists(ArrayList<DisplayTexture> dtState, ArrayList<AvailableTexture> atState)
	{
		ArrayList<DisplayTexture> clonedDTs = new ArrayList<DisplayTexture>();
		ArrayList<AvailableTexture> clonedATs = new ArrayList<AvailableTexture>();
		
		//cloning DTs
		for(DisplayTexture dt : dtState)
			clonedDTs.add(new DisplayTexture(dt.getAT()));
		
		//cloning ATs and applying these cloned objects to the DTs
		for(AvailableTexture at : atState)
		{
			clonedATs.add(new AvailableTexture(at.getName(), at.getKey(), at.getColor()));
		
			for(DisplayTexture dt : dtState)
			{
				if(clonedATs.get(clonedATs.size()-1).getName() == dt.getAT().getName())
					dt.setAvailableTexture(clonedATs.get(clonedATs.size()-1));
			}
		}
		
		Hashtable<String, Object> htResult = new Hashtable<String, Object>();
		htResult.put("clonedDTs", clonedDTs);
		htResult.put("clonedATs", clonedATs);
		return htResult;
	}
	
	/**
	 * Returns the height of the stage. 
	 * @return The height of the stage. 
	 */
	public int getStageHeight()
	{
		return stageHeight;
	}
	
	/**
	 * Returns the width of the stage. 
	 * @return The width of the stage. 
	 */
	public int getStageWidth()
	{
		return stageWidth;
	}
	
	/**
	 * Returns the list of DisplayTextures.
	 * @return List of DisplayTextures. 
	 */
	public ArrayList<DisplayTexture> getDTs()
	{
		return displayTextures;
	}
	
	/**
	 * Returns the list of AvailableTextures.
	 * @return List of AvailableTextures. 
	 */
	public ArrayList<AvailableTexture> getATs()
	{
		return availableTextures;
	}
	
	/**
	 * Returns the defaultAT. 
	 * @return DefaultAT. 
	 */
	public AvailableTexture getDefaultAT()
	{
		return defaultAT;
	}
	
	/**
	 * Resets the attributes. 
	 */
	public void resetAttributes()
	{
		stageWidth = 0;
		stageHeight = 0;
		defaultAT = null;
		displayTextures = null;
		availableTextures = null;
	}
	
}
