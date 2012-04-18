/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Die Klasse stellt eine Ausnahme dar, die bei der Verarbeitung eines
 * ungueltigen Kommandos geworfen wird.
 * 
 * @author felixwenz
 * 
 */
public class InvalidCommandException extends Exception {

	/**
	 * Die Kennzahl der Klasse zur (De)serialisierung.
	 */
	private static final long serialVersionUID = -6378623837427230807L;

	/**
	 * Der Konstruktor fuer eine Ausnahme, die im Falle eines ungueltigen
	 * Kommandos auftritt.
	 * 
	 * @param message
	 *            Die Nachricht, die den Grund fuer die Ausnahme naeher
	 *            spezifiziert.
	 */
	public InvalidCommandException(String message) {
		super(message);
	}
}
