package filemanipulator;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.AvailableTexture;
import model.DisplayTexture;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlDecoder implements XmlElementConstants {
	
	private ArrayList<DisplayTexture> displayTextures = new ArrayList<DisplayTexture>();
	private ArrayList<AvailableTexture> availableTextures = new ArrayList<AvailableTexture>();
	private AvailableTexture defaultAT = null;
	
	private int stageWidth;
	private int stageHeight;
	
	private File chosenFile;

	public XmlDecoder(File chosenFile)
	{
		this.chosenFile = chosenFile;
	}
	
	public ArrayList<DisplayTexture> getDisplayTextures()
	{
		return this.displayTextures;
	}

	public ArrayList<AvailableTexture> getAvailableTextures()
	{
		return this.availableTextures;
	}

	public AvailableTexture getDefaultAT()
	{
		return this.defaultAT;
	}
	
	public int getStageWidth()
	{
		return this.stageWidth;
	}
	
	public int getStageHeight()
	{
		return this.stageHeight;
	}
	
	public void decode()
	{
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				//Available Texture Root
				boolean atRootBool = false;

				//Available Texture Items
				boolean atItemBool = false;
				boolean atNameBool = false;
				boolean atKeyBool = false;
				boolean atColorBool = false;

				//DefaultAT
				boolean atDefaultBool = false;

				//Display Texture Root
				boolean dtRootBool = false;

				//Display Texture Item
				boolean dtItemBool = false;

				//stage width
				boolean stageWidthBool = false;
				boolean stageHeightBool = false;
				
				//availableItem parts
				String atNameStr = null;
				String atKeyStr = null;
				String atColorStr = null;
				
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

					if(qName.equals(AT_NAME))
						atNameBool = true;

					if(qName.equals(AT_KEY))
						atKeyBool = true;

					if(qName.equals(AT_COLOR))
						atColorBool = true;

					if(qName.equals(DEFAULT_AT))
						atDefaultBool = true;

					if(qName.equals(AVAILABLE_TEXTURES))
						atRootBool = true;

					if(qName.equals(AVAILABLE_TEXTURE))
						atItemBool = true;
					
					if(qName.equals(DISPLAY_TEXTURES))
						dtRootBool = true;

					if(qName.equals(DISPLAY_TEXTURE))
						dtItemBool = true;
					
					if(qName.equals(STAGE_WIDTH))
						stageWidthBool = true;

					if(qName.equals(STAGE_HEIGHT))
						stageHeightBool = true;

				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

					if(qName.equals(AT_NAME))
						atNameBool = false;

					if(qName.equals(AT_KEY))
						atKeyBool = false;

					if(qName.equals(AT_COLOR))
						atColorBool = false;

					if(qName.equals(DEFAULT_AT))
						atDefaultBool = false;

					if(qName.equals(AVAILABLE_TEXTURES))
						atRootBool = false;

					if(qName.equals(AVAILABLE_TEXTURE))
						atItemBool = false;
					
					if(qName.equals(DISPLAY_TEXTURES))
						dtRootBool = false;

					if(qName.equals(DISPLAY_TEXTURE))
						dtItemBool = false;
					
					if(qName.equals(STAGE_WIDTH))
						stageWidthBool = false;

					if(qName.equals(STAGE_HEIGHT))
						stageHeightBool = false;
				}

				public void characters(char ch[], int start, int length) throws SAXException {

					if(atRootBool || atDefaultBool)
					{
						if(atItemBool || atDefaultBool)
						{
							if(atNameBool)
								atNameStr = new String(ch, start, length);

							if(atKeyBool)
								atKeyStr = new String(ch, start, length);

							if(atColorBool)
								atColorStr = new String(ch, start, length);
							
							//if all of the AT parts aren't null, we should create a new AT and add it to the list
							if(atNameStr != null && atKeyStr != null && atColorStr != null)
							{
								//if it's DefaultAT
								if(atDefaultBool)
									defaultAT = new AvailableTexture(atNameStr, atKeyStr.charAt(0), new Color(Integer.parseInt(atColorStr)));
								else
									availableTextures.add(new AvailableTexture(atNameStr, atKeyStr.charAt(0), new Color(Integer.parseInt(atColorStr))));
								
								//resetting them back to nulls
								atNameStr = null;
								atKeyStr = null;
								atColorStr = null;
							}
						}
					}
					
					if(dtRootBool)
					{
						if(dtItemBool)
						{
							//creates the key
							char key = new String(ch, start, length).charAt(0);
							
							//determines if AT has been added
							boolean isAdded = false;
							
							//loops through list of ATs and finds the AT with the given key
							for(AvailableTexture at : availableTextures)
							{
								if(at.getKey() == key)
								{
									displayTextures.add(new DisplayTexture(at));
									isAdded = true;
									break;
								}
							}
							
							//if it hasn't been added, then we pass the defaultAT
							if(!isAdded)
								displayTextures.add(new DisplayTexture(defaultAT));
						}
					}
					
					if(stageWidthBool)
					{
						String widthStr = new String(ch, start, length);
						stageWidth = Integer.parseInt(widthStr);
					}

					if(stageHeightBool)
					{
						String heightStr = new String(ch, start, length);
						stageHeight = Integer.parseInt(heightStr);
					}

				}

			};
			
			InputStream inputStream= new FileInputStream(chosenFile);
			Reader reader = new InputStreamReader(inputStream,"UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			saxParser.parse(is, handler);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
