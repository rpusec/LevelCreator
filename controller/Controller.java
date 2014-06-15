package controller;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import filemanipulator.*;
import view.MainView;
import model.*;

public class Controller {
	
	private ArrayList<DisplayTexture> displayTextures = null;
	private ArrayList<AvailableTexture> availableTextures = null;
	private ArrayList<DisplayTexture> selectedDisplayTextures = new ArrayList<DisplayTexture>();
	private AvailableTexture defaultAT = null;
	
	private int stageWidth;
	private int stageHeight;
	
	private File selectedFile = null;
	private FileManipulator fileManipulator;
	
	private MainView mainView;
	
	public Controller() 
	{
		fileManipulator = new FileManipulator(this);
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
	public AvailableTexture getDefaultAT()					{return this.defaultAT;				}
	
	public void setStageWidth(int stageWidth)				{this.stageWidth = stageWidth;		}
	public int getStageWidth()								{return this.stageWidth;			}
	
	public void setStageHeight(int stageHeight)				{this.stageHeight = stageHeight;	}
	public int getStageHeight()								{return this.stageHeight;			}
	
	public void addAvailableTexture(AvailableTexture newAvailableTexture) 
	{
		availableTextures.add(newAvailableTexture);
		mainView.addAvailableTexture(newAvailableTexture);
	}
	
	public void removeAvailableTexture(AvailableTexture availableTexture)
	{
		availableTextures.remove(availableTexture);
		mainView.removeAvailableTexture(availableTexture);
	}
	
	public void addToSelected(DisplayTexture dt)
	{	
		if(!dt.isSelected())
		{
			selectedDisplayTextures.add(dt);
			dt.setSelected(true);
		}
		else
			removeFromSelected(dt);
	}
	
	public void removeFromSelected(DisplayTexture dt)
	{
		selectedDisplayTextures.remove(dt);
		dt.setSelected(false);
	}
	
	public void removeAllFromSelected()
	{
		for(DisplayTexture dt : selectedDisplayTextures)
			dt.setSelected(false);
		
		selectedDisplayTextures.clear();
	}
	
	public void drawTextureToStage(char chosenChar)
	{	
		if(availableTextures != null)
		{
			//getting the appropriate AvailableTexture
			for(AvailableTexture at : availableTextures)
			{
				if(at.getKey() == chosenChar)
				{
					//appending that AvailableTexture to all selected DisplayTextures
					for(DisplayTexture dt : selectedDisplayTextures)
						dt.setAvailableTexture(at);
					
					break;
				}
			}
		}
	}
	
	public void removeTexturesFromStage()
	{
		if(defaultAT != null)
		{
			for(DisplayTexture dt : selectedDisplayTextures)
				dt.setAvailableTexture(defaultAT);
		}
	}
	
	public static boolean isKeyOriginal(char theKey, ArrayList<AvailableTexture> availableTextures)
	{
		for(AvailableTexture at : availableTextures)
		{
			if(at.getKey() == theKey)
				return false;
		}
		
		return true;
	}
	
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
	
	public void notifyNewFile()
	{
		selectedFile = null;
		mainView.notifyFileUsage("", false);
	}
	
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
	
	public void saveLevelAs()
	{
		File updatedFile = fileManipulator.saveFileAs(null);
		
		if(updatedFile != null)
		{
			selectedFile = updatedFile;
			mainView.notifyFileUsage(selectedFile.getAbsolutePath(), true);
		}
	}
	
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
		}
	}
	
	public static Color getRandomColor()
	{
		final int MAX_RANGE = 255;
		return new Color((int)Math.floor(Math.random()*MAX_RANGE), (int)Math.floor(Math.random()*MAX_RANGE), (int)Math.floor(Math.random()*MAX_RANGE));
	}
	
	public void addMainViewReference(MainView mainView)
	{ 
		this.mainView = mainView; 
	}
	
	public void buildWorkingStage(int stageHeight, int stageWidth, AvailableTexture defaultAT, ArrayList<AvailableTexture> availableTextures, ArrayList<DisplayTexture> displayTextures)
	{
		if(mainView != null)
			mainView.buildWorkingStage(stageHeight, stageWidth, defaultAT, availableTextures, displayTextures);
	}
}
