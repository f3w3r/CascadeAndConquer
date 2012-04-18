/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

import de.fwenz.cascade_and_conquer.game_logic.util.EChangeMode;

/**
 * Die Klasse stellt ein Aenderungsereignis eines Spielbrettes dar.
 * 
 * @author felixwenz
 * 
 */
public class BoardChangedEvent extends BoardEvent {

	/**
	 * Der Typ des Aenderungsereignisses.
	 */
	private final EChangeMode changeType;

	/**
	 * Der Konstruktor für ein Aenderungsereignis eines Spielbretts.
	 * 
	 * @param changeType
	 *            Der Typ des Aenderungsereignisses.
	 * @param stateAtEvent
	 *            Der Zustand des Spielbretts nach der Aenderung.
	 */
	public BoardChangedEvent(EChangeMode changeType, String[] stateAtEvent) {
		super(stateAtEvent);
		this.changeType = changeType;
	}

	/**
	 * Die Methode gibt den Aenderungsanlass zurueck.
	 * 
	 * @return Der Aenderungsanlass.
	 */
	public EChangeMode getChangeType() {
		return changeType;
	}
}
