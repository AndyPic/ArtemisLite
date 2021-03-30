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

	ORION("Orion Spacecraft"), SLS("Space Launch System"), EGS("Exploration Ground Systems"), GATEWAY("Gateway"),
	RESOURCE("Resource Element"), BLANK("Blank Element");

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
