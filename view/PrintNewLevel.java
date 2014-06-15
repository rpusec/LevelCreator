package view;

import java.awt.*;
import javax.swing.*;
import controller.Controller;

public class PrintNewLevel extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller contr;
	private JTextArea jtaLevelDisplay;
	
	public PrintNewLevel(Controller contr)
	{
		this.contr = contr;
		setLayout(new BorderLayout());
		jtaLevelDisplay = new JTextArea();
		jtaLevelDisplay.setWrapStyleWord(true);
		jtaLevelDisplay.setEditable(false);
		jtaLevelDisplay.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		add(new JScrollPane(jtaLevelDisplay), "Center");
		
		setLevelDisplay();
		
		//initial info
		setSize(500, 300);
		setMinimumSize(new Dimension(500, 300));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setLevelDisplay()
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
