/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Die Klasse beschreibt einen Parameter eines CaCP-Kommandos.
 * 
 * @author felixwenz
 * 
 */
public class CaCPParameter {

	/**
	 * Der Parametertyp.
	 */
	private final ECaCPParameterType type;

	/**
	 * Der Parameterwert (als String).
	 */
	private final String value;

	/**
	 * Der Konstruktor fuer einen CaCP-Kommandoparameter.
	 * 
	 * @param type
	 *            Der Parametertyp.
	 * @param value
	 *            Der Parameterwert.
	 */
	public CaCPParameter(ECaCPParameterType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Die Methode gibt den Parametertyp zurueck.
	 * 
	 * @return Der Parametertyp.
	 */
	public ECaCPParameterType getType() {
		return type;
	}

	/**
	 * Die Methode gibt den Parameterwert zurueck.
	 * 
	 * @return Der Parameterwert.
	 */
	public String getValue() {
		return value;
	}
}
