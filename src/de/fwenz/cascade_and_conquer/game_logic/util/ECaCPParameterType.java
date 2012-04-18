/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Der Aufzaehlungstyp stellt die verschiedenen CaCP-Parametertypen bereit.
 * 
 * @author felixwenz
 * 
 */
public enum ECaCPParameterType {

	/**
	 * Text einer Meldung.
	 */
	MESSAGE,

	/**
	 * Horizontale Spielbrettausdehnung.
	 */
	XDIM,

	/**
	 * Vertikale Spielbrettausdehnung.
	 */
	YDIM,

	/**
	 * Startspieler.
	 */
	BEGIN,

	/**
	 * Anfaengliche Spielbrettbelegung als aneinandergereihte Zeichenkette.
	 */
	SETUP,

	/**
	 * Name des Clients/Servers.
	 */
	USER,

	/**
	 * Spielbrettreihe als Zeichenkette.
	 */
	ROW,

	/**
	 * Horizontale Feldkoordinate.
	 */
	X,

	/**
	 * Vertikale Feldkoordinate.
	 */
	Y,

	/**
	 * Gewinner.
	 */
	WINNER;

}
