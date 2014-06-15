package filemanipulator;

import java.io.*;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.Controller;

public class FileManipulator {
	
	private Controller contr;
	
	public FileManipulator(Controller contr)
	{
		this.contr = contr;
	}
	
	public void saveFile(File fileToSelect)
	{
		try {
			XmlEncoder.saveXml(fileToSelect, contr);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public File saveFileAs(String contentToSave)
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
