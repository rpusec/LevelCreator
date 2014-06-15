package filemanipulator;

import java.io.File;

import controller.Controller;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.AvailableTexture;
import model.DisplayTexture;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XmlEncoder implements XmlElementConstants{
	
	private static DocumentBuilderFactory docFactory;
	private static DocumentBuilder docBuilder;
	private static Document doc;
	private static Element rootElement, availableTexturesRoot, defaultATItem, displayTexturesRoot, stageWidthElement, stageHeightElement;
	private static Controller controller;
	
	public static void saveXml(File selectedFile, Controller contr) throws ParserConfigurationException, TransformerException
	{
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		
		controller = contr;
		
		createRootElements();
		createATSection();
		createDTSection();
		setStageWidthHeight();
		saveResult(selectedFile);
	}
	
	private static void createRootElements()
	{
		//is our document
		doc = docBuilder.newDocument();
		
		//creating the root element
		rootElement = doc.createElement(LEVEL);
		
		//creating the AT elements
		availableTexturesRoot = doc.createElement(AVAILABLE_TEXTURES);
		defaultATItem = doc.createElement(DEFAULT_AT);
		
		//creating the DT elements
		displayTexturesRoot = doc.createElement(DISPLAY_TEXTURES);
		
		//creating the stage width and height
		stageWidthElement = doc.createElement(STAGE_WIDTH);
		stageHeightElement = doc.createElement(STAGE_HEIGHT); 
		
		//appending root elements
		doc.appendChild(rootElement);
		rootElement.appendChild(availableTexturesRoot);
		rootElement.appendChild(defaultATItem);
		rootElement.appendChild(displayTexturesRoot);
		rootElement.appendChild(stageWidthElement);
		rootElement.appendChild(stageHeightElement);
	}
	
	private static void createATSection()
	{
		for(AvailableTexture at : controller.getAvailableTextures())
		{
			//creating the items for an AT
			Element atName = doc.createElement(AT_NAME);
			Element atKey = doc.createElement(AT_KEY); 
			Element atColor = doc.createElement(AT_COLOR);
			
			//appending textnodes
			atName.appendChild(doc.createTextNode(at.getName()));
			atKey.appendChild(doc.createTextNode("" + at.getKey()));
			atColor.appendChild(doc.createTextNode("" + at.getColor().getRGB()));
			
			//creating the item element itself and appending elements
			Element availableTextureItem = doc.createElement(AVAILABLE_TEXTURE);
			availableTextureItem.appendChild(atName);
			availableTextureItem.appendChild(atKey);
			availableTextureItem.appendChild(atColor);
			
			//appending to the root AT element
			availableTexturesRoot.appendChild(availableTextureItem);
		}
		
		//creating the elements for the default AT
		Element defaultName = doc.createElement(AT_NAME);
		Element defaultKey = doc.createElement(AT_KEY); 
		Element defaultColor = doc.createElement(AT_COLOR);
		defaultName.appendChild(doc.createTextNode(controller.getDefaultAT().getName()));
		defaultKey.appendChild(doc.createTextNode("" + controller.getDefaultAT().getKey()));
		defaultColor.appendChild(doc.createTextNode("" + controller.getDefaultAT().getColor().getRGB()));
		
		//appending the default AT elements
		defaultATItem.appendChild(defaultName);
		defaultATItem.appendChild(defaultKey);
		defaultATItem.appendChild(defaultColor);
	}
	
	private static void createDTSection()
	{
		for(DisplayTexture dt : controller.getDisplayTextures())
		{
			Element displayTextureItem = doc.createElement(DISPLAY_TEXTURE);
			displayTextureItem.appendChild(doc.createTextNode("" + dt.getKey()));
			displayTexturesRoot.appendChild(displayTextureItem);
		}
	}
	
	private static void setStageWidthHeight()
	{
		stageWidthElement.appendChild(doc.createTextNode("" + controller.getStageWidth()));
		stageHeightElement.appendChild(doc.createTextNode("" + controller.getStageHeight()));
	}
	
	private static void saveResult(File selectedFile) throws TransformerException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(selectedFile);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(source, result);
	}

}
