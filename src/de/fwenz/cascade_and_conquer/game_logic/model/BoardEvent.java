/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

/**
 * Die Klasse stelt ein Spielbrettereignis.
 * 
 * @author felixwenz
 * 
 */
public class BoardEvent {

	/**
	 * Der Zustand des Spielbretts.
	 */
	protected final String[] stateAtEvent;

	/**
	 * Der Konstruktor für ein Spielbrettereignis.
	 * 
	 * @param stateAtEvent
	 *            Der Zustand des Spielbretts.
	 */
	public BoardEvent(String[] stateAtEvent) {
		this.stateAtEvent = stateAtEvent;
	}

	/**
	 * Die Methode gibt den Zustand des Spielbrett zurueck.
	 * 
	 * @return Der Zustand des Spielbretts.
	 */
	public String[] getStateAtEvent() {
		return stateAtEvent;
	}
}
