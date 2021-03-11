/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * User defined exception for when player resources fall below zero 
 * (as a result of a forfeit of resources)
 * @author David Finlay Student Number: 40312100
 *
 */
public class BankruptException extends RuntimeException {
	
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructor with exception message argument
	 * @param s is the exception message displayed
	 */
	public BankruptException(String bankruptMsg) 
    { 
        // Call constructor of parent Exception 
        super(bankruptMsg); 
    } 

}
