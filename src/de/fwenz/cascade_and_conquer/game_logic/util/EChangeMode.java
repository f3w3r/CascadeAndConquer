/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Der Aufzaehlungstyp beschreibt die Art eines Aenderungsereignisses.
 * 
 * @author felixwenz
 * 
 */
public enum EChangeMode {

	/**
	 * Aenderungsereignis eines Schrittes; hier fand der Ueberlauf eines Feldes
	 * statt.
	 */
	BY_STEP,

	/**
	 * Aenderungsereignis einer Stufe; hier fanden die Ueberlaeufe aller Felder
	 * genau einmal statt.
	 */
	BY_STAGE,

	/**
	 * Aenderungsereignis eines Zuges; hier fanden alle Ueberlaeufe aller Felder
	 * solange statt, bis sich eine stabile Endsituation eingestellt hat.
	 */
	BY_MOVE;
}
