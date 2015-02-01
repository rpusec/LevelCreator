package controller;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import filemanipulator.*;
import view.MainView;
import memento.*;
import model.*;

/**
 * 
 * This class provides the initial 
 * logic of the whole application. 
 * @author Roman Pusec
 *
 */
public class Controller {
	
	private ArrayList<DisplayTexture> displayTextures = null;
	private ArrayList<AvailableTexture> availableTextures = null;
	private ArrayList<DisplayTexture> selectedDisplayTextures = new ArrayList<DisplayTexture>();
	private AvailableTexture defaultAT = null;
	
	//stage dimensions
	private int stageWidth;
	private int stageHeight;
	
	//file manipulaition
	private File selectedFile = null;
	private FileManipulator fileManipulator;
	
	//main view reference 
	private MainView mainView;
	
	//memento design pattern
	private Originator originator;
	private Caretaker caretaker;
	
	/**
	 * Creates an instance of the FileManipulator class. 
	 */
	public Controller(MainView mainView) 
	{
		fileManipulator = new FileManipulator(this);
		this.mainView = mainView;
		originator = new Originator();
		caretaker = new Caretaker();
	}
	
	public void setDisplayTextures(ArrayList<DisplayTexture> displayTextures)
	{
		this.displayTextures = displayTextures;
	}
	
	public ArrayList<DisplayTexture> getDisplayTextures()
	{
		return this.displayTextures;
	}
	
	public void setAvailableTextures(ArrayList<AvailableTexture> availableTextures)
	{
		this.availableTextures = availableTextures;
	}
	
	public ArrayList<AvailableTexture> getAvailableTextures()
	{
		return this.availableTextures;
	}
	
	/**
	 * Sets a different default AvailableTexture. 
	 * @param defaultAT New defaultAT. 
	 */
	public void setDefaultAT(AvailableTexture defaultAT)	
	{
		if(this.defaultAT != null)
		{
			char prevDefaultKey = this.defaultAT.getKey(); //saving the previous default key
			
			//setting the new default key on every display texture which has the defaultAT
			for(DisplayTexture dt : displayTextures)
			{
				if(dt.getKey() == prevDefaultKey)
					dt.setAvailableTexture(defaultAT); //setting the local, updated key
			}
		}
			
		this.defaultAT = defaultAT; //setting the updated default key
	}
	
	public AvailableTexture getDefaultAT()
	{
		return this.defaultAT;
	}
	
	public void setStageWidth(int stageWidth)
	{
		this.stageWidth = stageWidth;
	}
	
	public int getStageWidth()
	{
		return this.stageWidth;			
	}
	
	public void setStageHeight(int stageHeight)
	{
		this.stageHeight = stageHeight;	
	}
	
	public int getStageHeight()			
	{
		return this.stageHeight;			
	}
	
	/**
	 * Adds a new AvailableTexture to the application. 
	 * @param newAvailableTexture New AvailableTexture. 
	 */
	public void addAvailableTexture(AvailableTexture newAvailableTexture) 
	{
		availableTextures.add(newAvailableTexture);
		mainView.addAvailableTexture(newAvailableTexture);
	}
	
	/**
	 * Removes an AvailableTexture from the application. 
	 * @param newAvailableTexture Target AvailableTexture. 
	 */
	public void removeAvailableTexture(AvailableTexture availableTexture)
	{
		availableTextures.remove(availableTexture);
		mainView.removeAvailableTexture(availableTexture);
	}
	
	/**
	 * Marks a single DT as selected. 
	 * @param dt Target DisplayTexture. 
	 * @param removeIfExist Removes the DT if it already is selected. 
	 */
	public void addToSelected(DisplayTexture dt, boolean removeIfExist)
	{	
		if(!dt.isSelected())
		{
			selectedDisplayTextures.add(dt);
			dt.setSelected(true);
		}
		else
			if(removeIfExist)
				removeFromSelected(dt);
	}
	
	/**
	 * Removes a single DT from the selected list. 
	 * @param dt Target DisplayTexture. 
	 */
	public void removeFromSelected(DisplayTexture dt)
	{
		selectedDisplayTextures.remove(dt);
		dt.setSelected(false);
	}
	
	/**
	 * Removes all DTs from the selected list. 
	 */
	public void removeAllFromSelected()
	{
		for(DisplayTexture dt : selectedDisplayTextures)
			dt.setSelected(false);
		
		selectedDisplayTextures.clear();
	}
	
	/**
	 * Draws the textures to the stage based on the chosen character, and based
	 * on all of the selected DTs. 
	 * @param chosenChar The keyboard character the AT is associated with. 
	 */
	public void drawTextureToStage(char chosenChar)
	{	
		if(availableTextures != null)
		{
			boolean wasStageAltered = false;
			
			//getting the appropriate AvailableTexture
			for(AvailableTexture at : availableTextures)
			{
				if(at.getKey() == chosenChar)
				{
					//appending that AvailableTexture to all selected DisplayTextures
					for(DisplayTexture dt : selectedDisplayTextures)
						dt.setAvailableTexture(at);
					
					wasStageAltered = true;
					break;
				}
			}
			
			//to avoid characters such as SHIFT, ENTER, etc. 
			if(wasStageAltered)
				registerToMemento();
		}
	}
	
	/**
	 * Removes all textures from stage. 
	 */
	public void removeTexturesFromStage()
	{
		if(defaultAT != null)
		{
			for(DisplayTexture dt : selectedDisplayTextures)
				dt.setAvailableTexture(defaultAT);
		}
	}
	
	/**
	 * Checks if the key is original, that is, if an AvailableTexture
	 * doesn't share the same key. 
	 * @param theKey The target key. 
	 * @param availableTextures The array of available textures. (Sometimes it is needed to reference ATs not a part of the controller)
	 * @return True if the key is original, false otherwise. 
	 */
	public static boolean isKeyOriginal(char theKey, ArrayList<AvailableTexture> availableTextures)
	{
		for(AvailableTexture at : availableTextures)
		{
			if(at.getKey() == theKey)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Adds a row to the stage. 
	 * @param whatPosition On what target position should the row be added. 
	 */
	public void addRow(int whatPosition)
	{
		if(displayTextures != null && defaultAT != null)
		{
			int addingPosition = (whatPosition)*stageWidth; //adds new rows from this position
			
			//adds new DisplayTextures from the adding position until the adding position + stageWidth
			for(int i = addingPosition; i < addingPosition + stageWidth; i++)
				displayTextures.add(addingPosition, new DisplayTexture(defaultAT));
			
			stageHeight++; //updates stage height due to new row
			
			mainView.updateStage(displayTextures);
		}
	}
	
	/**
	 * Removes a row from the stage. 
	 * @param whatPosition On what target position should the row be removed. 
	 */
	public void removeRow(int whatPosition)
	{
		if(displayTextures != null)
		{
			int removingPosition = (whatPosition-1)*stageWidth; //removes rows from this position
			
			//removes DisplayTextures
			for(int i = removingPosition; i < removingPosition + stageWidth; i++)
				displayTextures.remove(displayTextures.get(removingPosition));
			
			stageHeight--; //updates stage height due to removed row
			
			mainView.updateStage(displayTextures);
		}
	}
	
	/**
	 * Adds a column to the stage. 
	 * @param whatPosition On what position should the column be added. 
	 */
	public void addCol(int whatPosition)
	{
		if(displayTextures != null)
		{
			removeAllFromSelected();
			
			int currentPosition = whatPosition; //used so that we know in which row we currently should be
			
			stageWidth++; //updates the stage width due to new column
			
			//adds each new DisplayTexture on each row (creating a whole new column)
			for(int currRow = 0; currRow < stageHeight; currRow++)
			{
				displayTextures.add(currentPosition, new DisplayTexture(defaultAT));
				currentPosition += stageWidth; //getting to the next row
			}
			
			mainView.updateStage(displayTextures);
		}
	}
	
	/**
	 * Adds a column to the stage. 
	 * @param whatPosition On what position should the column be removed. 
	 */
	public void removeCol(int whatPosition)
	{
		if(displayTextures != null)
		{
			removeAllFromSelected();
			
			int currentPosition = whatPosition-1; //used so that we know in which row we currently should be
			
			stageWidth--; //updates the stage width due to removed column
			
			//removes each DisplayTexture on each row (removing a whole column)
			for(int currRow = 0; currRow < stageHeight; currRow++)
			{
				displayTextures.remove(displayTextures.get(currentPosition));
				currentPosition += stageWidth; //getting to the next row
			}
			
			mainView.updateStage(displayTextures);
		}
	}
	
	/**
	 * Selects multiple DTs at once. Selects from the 
	 * first DT in the array of selected DTs. 
	 * @param dt DT to which it should select. 
	 */
	public void selectMultipleAtOnce(DisplayTexture dt)
	{
		if(selectedDisplayTextures.size() == 0)
			addToSelected(dt, false);
		else
		{
			//desc - from which destination it should start selecting
			//endpoint - from which endpoint should it stop selecting
			int desc = displayTextures.indexOf(selectedDisplayTextures.get(0));
			int endpoint = displayTextures.indexOf(dt);
			
			//if the user selected a lower DT as the "desc", and an upper DT as "endpoint"
			//then the sides of the two variables have to be switched
			if(desc > endpoint)
			{
				int tempDesc = desc;
				desc = endpoint;
				endpoint = tempDesc;
			}
			
			if(mod(endpoint, stageWidth) < mod(desc, stageWidth))
			{
				int tempDesc = desc;
				desc -= mod(desc, stageWidth) - mod(endpoint, stageWidth);
				endpoint += mod(tempDesc, stageWidth) - mod(endpoint, stageWidth);
			}
			
			addToSelected(displayTextures.get(desc), false);
			addToSelected(displayTextures.get(endpoint), false);
			
			//if it is not the same block
			if(desc != endpoint)
			{
				for(int i = desc; i < endpoint; i++)
				{
					if(mod(desc, stageWidth) <= mod(i, stageWidth) && mod(endpoint, stageWidth) >= mod(i, stageWidth))
						addToSelected(displayTextures.get(i), false);
				}
			}
		}
	}
	
	/**
	 * Creates a new state/memento and adds 
	 * it to Caretaker. 
	 */
	public void registerToMemento()
	{
		originator.set(displayTextures, availableTextures, defaultAT, stageWidth, stageHeight);
		caretaker.addMemento(originator.createMemento());
	}
	
	/**
	 * Loads the previous memento and 
	 * restores the stage at that state. 
	 */
	public void loadPreviousMemento()
	{
		//if the previous memento exists
		if(caretaker.getCurrMementoIndex()-1 > -1)
			onRestoreMomento(caretaker.getCurrMementoIndex()-1);
	}
	
	/**
	 * Loads the following memento and 
	 * restores the stage at that stage. 
	 */
	public void loadFollowingMemento()
	{
		if(caretaker.getCurrMementoIndex() != caretaker.getMementoList().size()-1)
			onRestoreMomento(caretaker.getCurrMementoIndex()+1);
	}
	
	/**
	 * Common code used for undo/redo, that is, to 
	 * restore previous or following state/memento. 
	 * @param mementoIndex 	Determines which memento assigned by index should be restored. </br>
	 * 						For undo, use 'caretaker.getCurrMementoIndex()-1'. </br>
	 * 						For redo, use 'caretaker.getCurrMementoIndex()+1'. </br>
	 */
	private void onRestoreMomento(int mementoIndex)
	{
		//decreasing the current index
		caretaker.setCurrMementoIndex(mementoIndex);
		
		//restoring the previous memento
		originator.restoreFromMemento(caretaker.getMemento(caretaker.getCurrMementoIndex()));
		
		//restores the working stage
		mainView.buildWorkingStage(
				originator.getStageHeight(), 
				originator.getStageWidth(), 
				originator.getDefaultAT(),
				originator.getATs(),
				originator.getDTs());
	}
	
	/**
	 * Resets the attributes of the originator empties 
	 * the list of mementos in the caretaker. 
	 */
	public void resetMemento()
	{
		originator.resetAttributes();
		caretaker.emptyMementos();
	}
	
	/**
	 * If a new file is being used. 
	 */
	public void notifyNewFile()
	{
		selectedFile = null;
		mainView.notifyFileUsage("", false);
	}
	
	/**
	 * Saves the level as an XML. 
	 */
	public void saveLevel()
	{
		if(selectedFile != null)
		{
			fileManipulator.saveFile(selectedFile);
			mainView.notifyFileUsage(selectedFile.getAbsolutePath(), true);
		}
		else
			saveLevelAs();
	}
	
	/**
	 * Saves the level as a different XML file. 
	 */
	public void saveLevelAs()
	{
		File updatedFile = fileManipulator.saveFileAs();
		
		if(updatedFile != null)
		{
			selectedFile = updatedFile;
			mainView.notifyFileUsage(selectedFile.getAbsolutePath(), true);
		}
	}
	
	/**
	 * Opens a project XML file and assembles the 
	 * level based on the gathered information. 
	 */
	public void openLevel()
	{
		File chosenFile = fileManipulator.openFile();
		
		if(chosenFile != null)
		{
			XmlDecoder xmlDecoder = new XmlDecoder(chosenFile);
			xmlDecoder.decode();
			selectedFile = chosenFile;
			stageWidth = xmlDecoder.getStageWidth();
			stageHeight = xmlDecoder.getStageHeight();
			buildWorkingStage(xmlDecoder.getStageHeight(), xmlDecoder.getStageWidth(), xmlDecoder.getDefaultAT(), xmlDecoder.getAvailableTextures(), xmlDecoder.getDisplayTextures());
			mainView.notifyFileUsage(chosenFile.getAbsolutePath(), false);
			registerToMemento();
		}
	}
	
	/**
	 * Returns a random color. 
	 * @return Random color. 
	 */
	public static Color getRandomColor()
	{
		final int MAX_RANGE = 255;
		return new Color((int)Math.floor(Math.random()*MAX_RANGE), (int)Math.floor(Math.random()*MAX_RANGE), (int)Math.floor(Math.random()*MAX_RANGE));
	}
	
	/**
	 * Builds the overall stage to work on. 
	 * @param stageHeight Height of the stage. 
	 * @param stageWidth Width of the stage. 
	 * @param defaultAT The default AT. 
	 * @param availableTextures Array of AvailableTextures. 
	 * @param displayTextures Array of DisplayTextures. 
	 */
	public void buildWorkingStage(int stageHeight, int stageWidth, AvailableTexture defaultAT, ArrayList<AvailableTexture> availableTextures, ArrayList<DisplayTexture> displayTextures)
	{
		if(mainView != null)
			mainView.buildWorkingStage(stageHeight, stageWidth, defaultAT, availableTextures, displayTextures);
	}
	
	/**
	 * Checks if a number is in a certain modulo
	 * @param num the number
	 * @param mod_num modulo number
	 */
	private int mod(int num, int mod_num)
	{
		return num % mod_num;
	}
}
