/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

import java.util.LinkedList;

/**
 * Die Klasse bechreibt ein Kommando des CaC-Protokolls.
 * 
 * @author felixwenz
 * 
 */
public class CaCPCommand {

	/**
	 * Der Kommandotyp.
	 */
	private final ECaCPCommandType type;

	/**
	 * Die Parameterliste.
	 */
	private final LinkedList<CaCPParameter> parameters = new LinkedList<CaCPParameter>();

	/**
	 * Der Konstruktor fuer ein CaCP-Kommando.
	 * 
	 * @param type
	 *            Der Kommandotyp.
	 * @param parameters
	 *            Die Parameterliste.
	 */
	public CaCPCommand(ECaCPCommandType type,
			LinkedList<CaCPParameter> parameters) {
		this.type = type;
		// Finalisieren der Parameterliste.
		for (CaCPParameter param : parameters) {
			this.parameters.add(param);
		}
	}

	/**
	 * Die Methode liefert den Kommandotyp.
	 * 
	 * @return Der Kommandotyp.
	 */
	public ECaCPCommandType getType() {
		return type;
	}

	/**
	 * Die Methode liefert die Parameterliste.
	 * 
	 * @return Die Parameterliste.
	 */
	public LinkedList<CaCPParameter> getParameters() {
		return parameters;
	}
}
