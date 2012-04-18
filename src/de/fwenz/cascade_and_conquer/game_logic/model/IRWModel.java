/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

import de.fwenz.cascade_and_conquer.game_logic.util.ETileOccupancy;
import de.fwenz.cascade_and_conquer.game_logic.util.InvalidMoveException;

/**
 * Das Interface gibt die read-only Methoden fuer ein CascadeAndConquer Model
 * an.
 * 
 * @author felixwenz
 * 
 */
public interface IRWModel extends IROModel {

	/**
	 * Die Methode legt die Belegung des Feldes an der Position (x, y) fest.
	 * 
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @param occupancy
	 *            Die zu setzende Belegung.
	 * @throws InvalidMoveException
	 *             Wird geworfen, falls der Zug nicht im Rahmen der Regeln
	 *             gueltig ist.
	 */
	public void setOccupancyAt(int xPos, int yPos, ETileOccupancy occupancy)
			throws InvalidMoveException;

	/**
	 * Die Methode legt die Anzahl der Steine auf dem Feld an der Position (x,
	 * y) fest. Diese muss sich innerhalb der Parameter einer stabilen
	 * Brettsituation bewegen.
	 * 
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @param count
	 *            Die zu setzende Anzahl der Steine.
	 * @throws InvalidMoveException
	 *             Wird geworfen, falls der Zug nicht im Rahmen der Regeln
	 *             gueltig ist.
	 */
	public void setCountAt(int xPos, int yPos, int count)
			throws InvalidMoveException;

	/**
	 * Die Methode führt den Zug eines Spielers durch, sofern dieser im Rahmen
	 * der Regeln gueltig ist.
	 * 
	 * @param player
	 *            Der Spieler, der einen Stein setzen moechte.
	 * @param xPos
	 *            Die Spaltennummer des Feldes, auf das der Stein gesetzt werden
	 *            soll.
	 * @param yPos
	 *            Die Zeilennummer des Feldes, auf das der Stein gesetzt werden
	 *            soll.
	 * @throws InvalidMoveException
	 *             Wird geworfen, falls der Zug nicht im Rahmen der Regeln
	 *             gueltig ist.
	 */
	public void performMove(ETileOccupancy player, int xPos, int yPos)
			throws InvalidMoveException;

	/**
	 * Die Methode fuegt dem Spielbrett einen Beobachter hinzu, der auf
	 * Aenderungen lauscht.
	 * 
	 * @param listener
	 *            Der hinzuzufuegende Beobachter.
	 */
	public void addBoardEventListener(IBoardEventListener listener);

	/**
	 * Die Methode entfernt einen Beobachter vom Spielbrett.
	 * 
	 * @param listener
	 *            Der zu entfernende Beobachter.
	 */
	public void removeBoardEventListener(IBoardEventListener listener);
}
