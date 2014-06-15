package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import model.*;
import controller.*;

public class MainView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JMenu jmFile, jmOptions, jmHelp;
	private JMenuItem jmiNew, jmiSave, jmiSaveAs, jmiOpen, jmiAddRow, jmiRemoveRow, jmiAddCol, jmiRemoveCol, jmiAddAT, jmiRemoveAT, jmiChangeDefaultAT, jmiPrintLevel, jmiAbout;
	private LevelDesignPanel levelDesignPanel;
	private AvailableTexturePanel availableTexturePanel;
	private ProjectInfoPanel projectInfoPanel;
	private Controller controller;
	
	public MainView() {
		
		controller = new Controller();
		controller.addMainViewReference(this);
		projectInfoPanel = new ProjectInfoPanel();
		levelDesignPanel = new LevelDesignPanel(controller, projectInfoPanel);
		availableTexturePanel = new AvailableTexturePanel(controller);
		
		buildMenu();
		
		add(new JScrollPane(levelDesignPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), "Center");
		add(availableTexturePanel, "West");
		add(projectInfoPanel, "South");
		
		setTitle("Level Creator");
		setSize(700, 500);
		setMinimumSize(new Dimension(700, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); 
		setVisible(true); 
		
		setDefaultWorkingStage();
	}
	
	public void updateStage(ArrayList<DisplayTexture> displayTextures)
	{
		levelDesignPanel.updateStage(displayTextures);
	}
	
	public void addAvailableTexture(AvailableTexture newAvailableTexture)
	{
		availableTexturePanel.addAvailableTexture(newAvailableTexture);
	}
	
	public void removeAvailableTexture(AvailableTexture availableTexture)
	{
		availableTexturePanel.removeAvailableTexture(availableTexture);
	}
	
	public void notifyFileUsage(String path, boolean notifySave)
	{
		if(notifySave)
			JOptionPane.showMessageDialog(null, "File successfully saved to: \n" + path, "File saved successfully! ", JOptionPane.INFORMATION_MESSAGE);
		
		projectInfoPanel.setCurrentFilePath(path);
	}
	
	private void buildMenu()
	{
		//menu bar
		JMenuBar jMenuBar = new JMenuBar();
		
		//menus
		jmFile = new JMenu("File");
		jmOptions = new JMenu("Options");
		jmHelp = new JMenu("Help");
		
		//menu items
		jmiNew = new JMenuItem("New");
		jmiSave = new JMenuItem("Save");
		jmiSaveAs = new JMenuItem("Save As");
		jmiOpen = new JMenuItem("Open");
		jmiAddRow = new JMenuItem("Add row");
		jmiRemoveRow = new JMenuItem("Remove row");
		jmiAddCol = new JMenuItem("Add column");
		jmiRemoveCol = new JMenuItem("Remove column");
		jmiAddAT = new JMenuItem("Add Available Texture");
		jmiRemoveAT = new JMenuItem("Remove Available Texture");
		jmiChangeDefaultAT = new JMenuItem("Change Default AT");
		jmiPrintLevel = new JMenuItem("Print Level");
		jmiAbout = new JMenuItem("About");
		
		//adding menu items to menus
		jmFile.add(jmiNew);
		jmFile.add(new JSeparator());
		jmFile.add(jmiSave);
		jmFile.add(jmiSaveAs);
		jmFile.add(new JSeparator());
		jmFile.add(jmiOpen);
		jmOptions.add(jmiAddRow);
		jmOptions.add(jmiRemoveRow);
		jmOptions.add(new JSeparator());
		jmOptions.add(jmiAddCol);
		jmOptions.add(jmiRemoveCol);
		jmOptions.add(new JSeparator());
		jmOptions.add(jmiAddAT);
		jmOptions.add(jmiRemoveAT);
		jmOptions.add(new JSeparator());
		jmOptions.add(jmiChangeDefaultAT);
		jmOptions.add(new JSeparator());
		jmOptions.add(jmiPrintLevel);
		jmHelp.add(jmiAbout);
		
		//adding menus to the menu bar
		jMenuBar.add(jmFile);
		jMenuBar.add(jmOptions);
		jMenuBar.add(jmHelp);
		
		//setting the menu bar to the frame
		setJMenuBar(jMenuBar);
		
		//adding actionlisteners
		jmiNew.addActionListener(this);
		jmiSave.addActionListener(this);
		jmiSaveAs.addActionListener(this);
		jmiOpen.addActionListener(this);
		jmiAddRow.addActionListener(this);
		jmiRemoveRow.addActionListener(this);
		jmiAddCol.addActionListener(this);
		jmiRemoveCol.addActionListener(this);
		jmiAddAT.addActionListener(this);
		jmiRemoveAT.addActionListener(this);
		jmiChangeDefaultAT.addActionListener(this);
		jmiPrintLevel.addActionListener(this);
		jmiAbout.addActionListener(this);
	}
	
	private void setDefaultWorkingStage()
	{
		//creating the variables
		int defaultStageSize = 4;
		AvailableTexture basicDefaultAT = new AvailableTexture("default", '0', Color.WHITE);
		ArrayList<AvailableTexture> basicATs = new ArrayList<AvailableTexture>();
		
		//adding the "beginning" AvailableTextures
		basicATs.add(new AvailableTexture("grass", 'g', Color.GREEN)); 
		basicATs.add(new AvailableTexture("wall", 'w', Color.GRAY)); 
		basicATs.add(new AvailableTexture("cloud", 'c', Color.BLUE)); 
		
		//creating the stage based on this information
		buildWorkingStage(defaultStageSize, defaultStageSize, basicDefaultAT, basicATs, null);
	}
	
	public void buildWorkingStage(int stageHeight, int stageWidth, AvailableTexture defaultAT, ArrayList<AvailableTexture> availableTextures, ArrayList<DisplayTexture> displayTextures)
	{
		if(displayTextures == null)
			levelDesignPanel.buildPanel(stageHeight, stageWidth, defaultAT); 
		else
		{
			controller.setDisplayTextures(displayTextures);
			levelDesignPanel.updateStage(displayTextures); 
		}
		
		availableTexturePanel.buildPanel(availableTextures); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jmiNew)
		{
			new AddNewLevel(controller);
		}
		else if(e.getSource() == jmiSave)
		{
			controller.saveLevel();
		}
		else if(e.getSource() == jmiSaveAs)
		{
			controller.saveLevelAs();
		}
		else if(e.getSource() == jmiOpen)
		{
			controller.openLevel();
		}
		else if(e.getSource() == jmiAddRow)
		{
			new OptionsPanel(OptionsPanel.ADD_ROW_OPTION, controller, controller.getStageHeight());
		}
		else if(e.getSource() == jmiRemoveRow)
		{
			new OptionsPanel(OptionsPanel.REMOVE_ROW_OPTION, controller, controller.getStageHeight());
		}
		else if(e.getSource() == jmiAddCol)
		{
			new OptionsPanel(OptionsPanel.ADD_COL_OPTION, controller, controller.getStageWidth());
		}
		else if(e.getSource() == jmiRemoveCol)
		{
			new OptionsPanel(OptionsPanel.REMOVE_COL_OPTION, controller, controller.getStageWidth());
		}
		else if(e.getSource() == jmiAddAT)
		{
			new AddAvailableTexture(controller);
		}
		else if(e.getSource() == jmiRemoveAT)
		{
			new RemoveAvailableTexture(controller);
		}
		else if(e.getSource() == jmiChangeDefaultAT)
		{
			new ChangeDefaultAT(controller);
		}
		else if(e.getSource() == jmiPrintLevel)
		{
			new PrintNewLevel(controller);
		}
		else if(e.getSource() == jmiAbout)
		{
			JOptionPane.showMessageDialog(null, "Level Creator\nBy Roman Pusec, 2014", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
