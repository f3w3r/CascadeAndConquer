/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.player;

import de.fwenz.cascade_and_conquer.game_logic.util.Move;

/**
 * Das Interface gibt die Methoden fuer einen CascadeAndConquer-Spieler an.
 * 
 * @author felixwenz
 * 
 */
public interface IPlayer {

	/**
	 * Die Methode fordert einen Spieler dazu auf, seinen Zug zu einer
	 * uebergeben Spielsituation mitzuteilen.
	 * 
	 * @param state
	 *            Die momentante Spielsituation.
	 * 
	 * @return Der Zug des Spielers.
	 */
	public Move getMove(String[] state);
}
