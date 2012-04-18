/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Der Aufzaehlungstyp stellt die moeglichen Belegungen eines Feldes dar. Ein
 * Feld kann entweder leer sein oder vom weissen oder schwarzen Spieler belegt
 * sein.
 * 
 * @author felixwenz
 * 
 */
public enum ETileOccupancy {

	/**
	 * Feld ist leer.
	 */
	EMPTY,

	/**
	 * Feld ist vom weisse Spieler belegt.
	 */
	WHITE,

	/**
	 * Feld ist vom schwarzen Spieler belegt.
	 */
	BLACK;
}
