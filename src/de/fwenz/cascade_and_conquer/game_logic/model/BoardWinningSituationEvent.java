/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

import de.fwenz.cascade_and_conquer.game_logic.util.ETileOccupancy;

/**
 * Die Klasse stellt ein Ereignis dar, dass ausgeloest wird, wenn das Spielbrett
 * eine Gewinnsituation aufweist.
 * 
 * @author felixwenz
 * 
 */
public class BoardWinningSituationEvent extends BoardEvent {

	/**
	 * Der ermittelte Gewinner.
	 */
	private final ETileOccupancy winner;

	/**
	 * Der Konstruktor fuer ein Gewinnsituationsereignis.
	 * 
	 * @param stateAtEvent
	 *            Der Zustand des Spielbretts.
	 * @param winner
	 *            Der ermittelte Gewinner.
	 */
	public BoardWinningSituationEvent(String[] stateAtEvent,
			ETileOccupancy winner) {
		super(stateAtEvent);
		this.winner = winner;
	}

	/**
	 * Die Methode gibt den ermittelten Gewinner zurueck.
	 * 
	 * @return Der ermittelte Gewinner.
	 */
	public ETileOccupancy getWinner() {
		return winner;
	}

}
