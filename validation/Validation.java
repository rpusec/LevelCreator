package validation;

import javax.swing.JTextField;

public abstract class Validation {
	
	public static boolean validateInputLength(JTextField jtfInput, int numFrom, int numTo)
	{
		if(jtfInput.getText().length() >= numFrom && jtfInput.getText().length() <= numTo)
			return true;
		else
			return false;
	}
	
	public static boolean validateNumberLength(int numInput, int from, int to)
	{
		if(numInput >= from && numInput <= to)
			return true;
		else
			return false;
	}
	
	public static boolean validateNumberFormat(JTextField jtfInput)
	{
		try
		{
			Integer.parseInt(jtfInput.getText());
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		
		return true;
	}
	
}
