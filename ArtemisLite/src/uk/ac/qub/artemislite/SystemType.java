/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * list of all possible systems that an element can be part off
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public enum SystemType {

	ORION("Orion Spacecraft"), SLS("Space Launch System"), EGS("Exploration Ground Systems"), GATEWAY("Gateway"),
	RESOURCE("Recruitment"), BLANK("Holiday");

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
