package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import model.*;
import controller.*;

/**
 * 
 * This is the main class which incorporates 
 * certain other 'View' classes such as LevelDesignPanel
 * AvailableTexturesPanel, and ProjectInfoPanel. 
 * @author Roman Pusec
 *
 */
public class MainView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JMenu jmFile, jmOptions, jmHelp;
	private JMenuItem 	jmiNew, jmiSave, jmiSaveAs, jmiOpen, 
						jmiAddRow, jmiRemoveRow, jmiAddCol, 
						jmiRemoveCol, jmiAddAT, jmiRemoveAT, 
						jmiChangeDefaultAT, jmiPrintLevel, 
						jmiAbout, jmiUndo, jmiRedo;
	private LevelDesignPanel levelDesignPanel;
	private AvailableTexturePanel availableTexturePanel;
	private ProjectInfoPanel projectInfoPanel;
	private Controller controller;
	
	/**
	 * Constructs the view, builds the menu,
	 * adds custom made panels, instantiates the 
	 * controller. 
	 */
	public MainView() {
		
		setLookAndFeel();
		setFocusable(true);
		requestFocusInWindow();
		
		controller = new Controller(this);
		projectInfoPanel = new ProjectInfoPanel();
		levelDesignPanel = new LevelDesignPanel(controller);
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
		
		checkCapslock();
		
		setDefaultWorkingStage();
		
		JPanel mvContent = (JPanel) getContentPane();
		
		//application uses key bindings to check for pressed keys
		mvContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "saveFileMapKey");
		mvContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "saveFileAsMapKey");
		mvContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "openFileMapKey");
		mvContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_CAPS_LOCK, 0), "onCapslock");
		
		mvContent.getActionMap().put("saveFileMapKey", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveLevel();
			}
		});
		
		mvContent.getActionMap().put("saveFileAsMapKey", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveLevelAs();
			}
		});
		
		mvContent.getActionMap().put("openFileMapKey", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openLevel();
			}
		});
		
		mvContent.getActionMap().put("onCapslock", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				checkCapslock();
			}
		});
		
		//added to check if CAPSLOCK was activated when the frame was out of focus
		WindowAdapter wAdapter = new WindowAdapter(){
			public void windowGainedFocus(WindowEvent e){
				checkCapslock();
			}
		};
		
		this.addWindowListener(wAdapter);
		this.addWindowFocusListener(wAdapter);
	}
	
	private void setLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether or not CAPSLOCK had been activated. 
	 */
	private void checkCapslock()
	{
		if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
			projectInfoPanel.notifyCapslock(true);
		else
			projectInfoPanel.notifyCapslock(false);
	}
	
	/**
	 * Updates the stage with new/updated DisplayTextures. 
	 * @param displayTextures Array of new and updated DisplayTextures. 
	 */
	public void updateStage(ArrayList<DisplayTexture> displayTextures)
	{
		levelDesignPanel.updateStage(displayTextures);
	}
	
	/**
	 * Adds an AvailableTexture to the appropriate panel. 
	 * @param newAvailableTexture New AvailableTexture. 
	 */
	public void addAvailableTexture(AvailableTexture newAvailableTexture)
	{
		availableTexturePanel.addAvailableTexture(newAvailableTexture);
	}
	
	/**
	 * Removes an AvailableTexture. 
	 * @param availableTexture Target AvailableTexture. 
	 */
	public void removeAvailableTexture(AvailableTexture availableTexture)
	{
		availableTexturePanel.removeAvailableTexture(availableTexture);
	}
	
	/**
	 * Notifies the application that a project file is being used. 
	 * @param path Path of the project file. 
	 * @param notifySave Should the application notify the user that the file had been saved. 
	 */
	public void notifyFileUsage(String path, boolean notifySave)
	{
		if(notifySave)
			JOptionPane.showMessageDialog(null, "File successfully saved to: \n" + path, "File saved successfully! ", JOptionPane.INFORMATION_MESSAGE);
		
		projectInfoPanel.setCurrentFilePath(path);
	}
	
	/**
	 * Builds the menu. 
	 */
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
		jmiUndo = new JMenuItem("Undo");
		jmiRedo = new JMenuItem("Redo");
		
		//adding menu items to menus
		jmFile.add(jmiNew);
		jmFile.add(new JSeparator());
		jmFile.add(jmiSave);
		jmFile.add(jmiSaveAs);
		jmFile.add(new JSeparator());
		jmFile.add(jmiOpen);
		jmOptions.add(jmiUndo);
		jmOptions.add(jmiRedo);
		jmOptions.add(new JSeparator());
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
		
		//adding action listeners
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
		jmiUndo.addActionListener(this);
		jmiRedo.addActionListener(this);
	}
	
	/**
	 * It basically creates the available textures and display
	 * textures which you see once you've opened the application. 
	 */
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
		buildWorkingStage(defaultStageSize, defaultStageSize, basicDefaultAT, basicATs, null, false);
		controller.registerToMemento();
	}
	
	/**
	 * Builds the overall stage to work on the levels. 
	 * @param stageHeight The height of the stage.
	 * @param stageWidth The width of the stage. 
	 * @param defaultAT The default AvailableTexture. 
	 * @param availableTextures Array of ATs.
	 * @param displayTextures Array of DTs. 
	 */
	public void buildWorkingStage(int stageHeight, int stageWidth, AvailableTexture defaultAT, ArrayList<AvailableTexture> availableTextures, ArrayList<DisplayTexture> displayTextures, boolean displayPB)
	{
		//setting the dimensions
		controller.setStageHeight(stageHeight);
		controller.setStageWidth(stageWidth);
		
		//if the display textures are null, the application should
		//draw new, empty display textures based on the applied height/width
		if(displayTextures == null)
			levelDesignPanel.buildPanel(stageHeight, stageWidth, defaultAT, displayPB); 
		else
		{
			controller.setDisplayTextures(displayTextures); 
			levelDesignPanel.updateStage(displayTextures); 
		}
		
		//builds the available textures
		availableTexturePanel.buildPanel(availableTextures);
	}
	
	public void buildWorkingStage(int stageHeight, int stageWidth, AvailableTexture defaultAT, ArrayList<AvailableTexture> availableTextures, ArrayList<DisplayTexture> displayTextures)
	{
		buildWorkingStage(stageHeight, stageWidth, defaultAT, availableTextures, displayTextures, true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jmiNew)
		{
			new StartNewProject(controller);
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
			new OptionsPanel(OptionsPanel.ADD_ROW_FUNCTION, controller, controller.getStageHeight());
		}
		else if(e.getSource() == jmiRemoveRow)
		{
			new OptionsPanel(OptionsPanel.REMOVE_ROW_FUNCTION, controller, controller.getStageHeight());
		}
		else if(e.getSource() == jmiAddCol)
		{
			new OptionsPanel(OptionsPanel.ADD_COL_FUNCTION, controller, controller.getStageWidth());
		}
		else if(e.getSource() == jmiRemoveCol)
		{
			new OptionsPanel(OptionsPanel.REMOVE_COL_FUNCTION, controller, controller.getStageWidth());
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
			new PrintLevel(controller);
		}
		else if(e.getSource() == jmiAbout)
		{
			JOptionPane.showMessageDialog(null, "Level Creator\nBy Roman Pusec, 2014", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource() == jmiUndo)
		{
			controller.loadPreviousMemento();
		}
		else if(e.getSource() == jmiRedo)
		{
			controller.loadFollowingMemento();
		}
	}
}
