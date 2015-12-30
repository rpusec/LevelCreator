package view;

import java.awt.*;

import javax.swing.*;

import lcprogressbars.JFProgressBar;
import controller.Controller;

/**
 * 
 * Prints the finalized level. 
 * @author Roman Pusec 
 *
 */
public class PrintLevel extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller contr;
	private JTextArea jtaLevelDisplay;
	
	/**
	 * Creates the GUI and references the controller. 
	 * @param contr Controller reference. 
	 */
	public PrintLevel(Controller contr)
	{
		this.contr = contr;
		setLayout(new BorderLayout());
		jtaLevelDisplay = new JTextArea();
		jtaLevelDisplay.setWrapStyleWord(true);
		jtaLevelDisplay.setEditable(false);
		jtaLevelDisplay.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		add(new JScrollPane(jtaLevelDisplay), "Center");
		
		Thread tDrawLevel = new Thread(new DrawLevelAlgorithm(this));
		tDrawLevel.start();
	}
	
	class DrawLevelAlgorithm extends JFProgressBar implements Runnable{

		private static final long serialVersionUID = 1L;
		private PrintLevel plRef;
		
		public DrawLevelAlgorithm(PrintLevel plRef)
		{
			super(contr.getDisplayTextures().size() - contr.getStageHeight());
			this.plRef = plRef;
		}
		
		@Override
		public void run() {
			int maxColumns = contr.getStageWidth();
			int currColumn = 0;
			
			for(int i = 0; i < contr.getDisplayTextures().size(); i++)
			{
				if(currColumn != maxColumns)
				{
					jtaLevelDisplay.append("" + contr.getDisplayTextures().get(i).getKey());
					currColumn++;
					
					if(i < contr.getDisplayTextures().size()-1)
						jtaLevelDisplay.append(", ");
				}
				else
				{
					currColumn = 0;
					jtaLevelDisplay.append("\n"); //next row
					i--;
				}
				
				progressBar.setValue(i);
				this.repaint();
			}
			
			plRef.setSize(500, 300);
			plRef.setMinimumSize(new Dimension(500, 300));
			plRef.setLocationRelativeTo(null);
			plRef.setVisible(true);
			
			this.dispose();
		}
		
	}

}
