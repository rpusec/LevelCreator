package lcprogressbars;

import java.util.ArrayList;

import model.DisplayTexture;

public class LDPProgressBar extends JFProgressBar implements Runnable{

	private static final long serialVersionUID = 1L;
	private ArrayList<DisplayTexture> displayTextures;
	
	public LDPProgressBar() {}
	
	public LDPProgressBar(ArrayList<DisplayTexture> displayTextures, int maxAmountOfDTs) 
	{
		super(maxAmountOfDTs);
		setDisplayTextures(displayTextures);
	}
	
	public void setDisplayTextures(ArrayList<DisplayTexture> displayTextures)
	{
		this.displayTextures = displayTextures;
	}

	@Override
	public void run() 
	{		
		while(displayTextures.size() < progressBar.getMaximum())
		{
			progressBar.setValue(displayTextures.size());
			repaint();
		}
			dispose();
	}

}
