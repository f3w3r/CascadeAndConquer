/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Die Klasse stellt eine Ausnahme dar, die im Falle eines ungueltigen Zuges
 * geworfen werden kann.
 * 
 * @author felixwenz
 * 
 */
public class InvalidMoveException extends Exception {

	/**
	 * Die Kennzahl der Klasse zur (De)serialisierung.
	 */
	private static final long serialVersionUID = 1762151835406242873L;

	/**
	 * Der Konstruktor fuer eine Ausnahme, die im Falle eines falschen Zuges
	 * auftritt.
	 * 
	 * @param message
	 *            Die Nachricht, die den Grund fuer die Ausnahme naeher
	 *            spezifiziert.
	 */
	public InvalidMoveException(String message) {
		super(message);
	}
}
