package lcprogressbars;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class JFProgressBar extends JFrame{
	
	private static final long serialVersionUID = 1L;
	protected JProgressBar progressBar;
	
	public JFProgressBar(){
		initialize(0);
	}
	
	public JFProgressBar(int maximumSize){
		initialize(maximumSize);
	}
	
	public void initialize(int maximumSize)
	{
		setTitle("");
		setSize(500, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(maximumSize);
		progressBar.setStringPainted(true);
		
		add(new JLabel("Progress... "), gbc);
		gbc.gridy = 1;
		add(progressBar, gbc);
		
		repaint();
		revalidate();
		setVisible(true);
	}
	
	public void setMaximum(int maximumSize){
		progressBar.setMaximum(maximumSize);
	}

}
