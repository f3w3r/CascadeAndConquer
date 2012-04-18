/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Der Aufzaehlungstyp stellt die verschiedenen CaCP-Kommandotypen bereit.
 * 
 * @author felixwenz
 * 
 */
public enum ECaCPCommandType {

	/**
	 * Fehlermeldung.
	 */
	ERROR,

	/**
	 * Hilfefunktion.
	 */
	HELP,

	/**
	 * Initialisierung eines neuen Spielfeldes.
	 */
	INIT,

	/**
	 * Bestaetigun einer erfolgreichen Initialisierung.
	 */
	INITIALIZED,

	/**
	 * Gibt jeweils eine Zeile der aktuellen Spielfeldsituation an.
	 */
	BOARD,

	/**
	 * Spielzug.
	 */
	MOVE,

	/**
	 * Beendet das aktuelle Spiel.
	 */
	BYE,

	/**
	 * Anfrage zum Beenden des aktuellen Spiels.
	 */
	EXIT
}
