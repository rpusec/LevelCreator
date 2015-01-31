package filemanipulator;

import java.io.*;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.Controller;

/**
 * 
 * Class which provides Save, SaveAs, 
 * and Open capability. 
 * @author Roman Pusec
 *
 */
public class FileManipulator {
	
	private Controller contr;
	
	/**
	 * Adds the controller reference. 
	 * @param contr Controller reference. 
	 */
	public FileManipulator(Controller contr)
	{
		this.contr = contr;
	}
	
	/**
	 * Saves the file. 
	 * @param fileToSelect Target file to save. 
	 */
	public void saveFile(File fileToSelect)
	{
		try {
			XmlEncoder.saveXml(fileToSelect, contr);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the file as another file. 
	 * @return Null if the saving process was dismissed, otherwise returns the selected file. 
	 */
	public File saveFileAs()
	{
		//preparing the file chooser
		JFileChooser jFileChooser = new JFileChooser();
		
		int returnValue = jFileChooser.showSaveDialog(null);
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			final String suffix = ".xml";
			File selectedFile = jFileChooser.getSelectedFile();
			
			//if the file doesn't have an XML extension, the application adds the XML extension
			if(!selectedFile.getPath().endsWith(suffix))
				selectedFile = new File(selectedFile.getAbsolutePath()+suffix);
			
			saveFile(selectedFile);
			
			return selectedFile;
		}
		
		return null;
	}
	
	/**
	 * Opens a file. 
	 * @return Null if the file open process was dismissed, otherwise returns the selected file. 
	 */
	public File openFile()
	{
		//preparing the file chooser
		JFileChooser jFileChooser = new JFileChooser();
		
		int returnValue = jFileChooser.showOpenDialog(null);
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
			return jFileChooser.getSelectedFile();
		
		return null;
	}
		
}
