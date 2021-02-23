/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public enum SystemType {

	SYSTEM1("System One"), SYSTEM2("System Two"), SYSTEM3("System Three"), SYSTEM4("System Four"),
	RESOURCE("Resource Square"), BLANK("Blank Square");

	// Instance Vars

	private final String name;

	// Constructors

	/**
	 * 
	 * @param name
	 */
	private SystemType(String name) {

		this.name = name;

	}

	// Methods

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
