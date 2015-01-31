package validation;

import javax.swing.JTextField;

/**
 * 
 * Used for input validation on certain components. 
 * @author Roman Pusec
 *
 */
public abstract class Validation {
	
	/**
	 * Validates the input length. 
	 * @param jtfInput Target JTextField value.
	 * @param numFrom From which number should the length be legal. 
	 * @param numTo To which number should the length be legal. 
	 * @return True if input is legal, false otherwise. 
	 */
	public static boolean validateInputLength(JTextField jtfInput, int numFrom, int numTo)
	{
		if(jtfInput.getText().length() >= numFrom && jtfInput.getText().length() <= numTo)
			return true;
		else
			return false;
	}
	
	/**
	 * Validates the length of a certain number. 
	 * @param numInput Target number. 
	 * @param from From which number should the length be legal
	 * @param to To which number should the length be legal
	 * @return True if input is legal, false otherwise. 
	 */
	public static boolean validateNumberLength(int numInput, int from, int to)
	{
		if(numInput >= from && numInput <= to)
			return true;
		else
			return false;
	}
	
	/**
	 * Validates the number format of a certain JTextField. 
	 * @param jtfInput Target JTextField value. 
	 * @return True if input is legal, false otherwise. 
	 */
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
