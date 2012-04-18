/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

/**
 * Das Interface enthaelt alle Methoden, die ein Beobachter eines Spielbretts
 * bereitstellen muss.
 * 
 * @author felixwenz
 * 
 */
public interface IBoardEventListener {

	/**
	 * Die Methode wird von der Spielbrettereignisquelle benutzt, um den
	 * Beobachter ueber ein neues Ereignis zu informieren.
	 * 
	 * @param e
	 *            Das Spielbrettereignis ueber das der Beobachter informiert
	 *            werden soll.
	 */
	public void fireBoardEvent(BoardEvent e);
}
