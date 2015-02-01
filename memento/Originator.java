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
	public void set(ArrayList<DisplayTexture> dtState, ArrayList<AvailableTexture> atState, AvailableTexture datState, int stageWidth, int stageHeight)
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
		
		defaultAT = new AvailableTexture(datState.getName(), datState.getKey(), datState.getColor());
		displayTextures = clonedDTs;
		availableTextures = clonedATs;
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
		
		stageWidth = (Integer) state.get("stageWidth");
		stageHeight = (Integer) state.get("stageHeight");
		defaultAT = (AvailableTexture) state.get("defaultAT");
		displayTextures = (ArrayList<DisplayTexture>) state.get("displayTextures");
		availableTextures = (ArrayList<AvailableTexture>) state.get("availableTextures");
		
		return memento;
	}
	
	public int getStageHeight()
	{
		return stageHeight;
	}
	
	public int getStageWidth()
	{
		return stageWidth;
	}
	
	public ArrayList<DisplayTexture> getDTs()
	{
		return displayTextures;
	}
	
	public ArrayList<AvailableTexture> getATs()
	{
		return availableTextures;
	}
	
	public AvailableTexture getDefaultAT()
	{
		return defaultAT;
	}
	
}
