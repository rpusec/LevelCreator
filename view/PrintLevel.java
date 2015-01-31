package view;

import java.awt.*;
import javax.swing.*;
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
		
		onDrawLvl();
		
		//initial info
		setSize(500, 300);
		setMinimumSize(new Dimension(500, 300));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Draws the level based on the info 
	 * from the controller.
	 */
	private void onDrawLvl()
	{
		int maxColumns = contr.getStageWidth();
		
		int currColumn = 0;
		
		for(int i = 0; i < contr.getDisplayTextures().size(); i++)
		{
			if(currColumn != maxColumns)
			{
				jtaLevelDisplay.append(contr.getDisplayTextures().get(i).getKey() + ", ");
				currColumn++;
			}
			else
			{
				currColumn = 0;
				jtaLevelDisplay.append("\n"); //next row
				i--;
			}
		}
	}

}
