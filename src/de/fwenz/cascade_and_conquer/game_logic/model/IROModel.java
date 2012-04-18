/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

import de.fwenz.cascade_and_conquer.game_logic.util.ETileOccupancy;

/**
 * Das Interface gibt die read-write Methoden fuer ein CascadeAndConquer Model
 * an.
 * 
 * @author felixwenz
 * 
 */
public interface IROModel {

	/**
	 * Die Methode gibt die Breite des Spielfelds zurueck.
	 * 
	 * @return Die Breite des Spielfelds.
	 */
	public int getXDim();

	/**
	 * Die Methode gibt die Hoehe des Spielfelds zurueck.
	 * 
	 * @return Die Hoehe des Spielfelds.
	 */
	public int getYDim();

	/**
	 * Die Methode gibt die Belegung des Feldes an der Position (x, y) zurueck.
	 * 
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @return Die Belegung des Feldes an der Position (x, y).
	 */
	public ETileOccupancy getOccupancyAt(int xPos, int yPos);

	/**
	 * Die Methode gibt die Anzahl der Steine auf dem Feld an der Position (x,
	 * y) zurueck.
	 * 
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @return Die Anzahl der Steine auf dem Feld an der Postion (x, y).
	 */
	public int getCountAt(int xPos, int yPos);

	/**
	 * Die Methode ermittelt, ob das Brett eine Gewinnstellung aufweist.
	 * 
	 * @return wahr, falls eine Gewinnstellung vorliegt.
	 */
	public boolean isWinningSituation();

	/**
	 * Die Methode liefert das Spielbrett als String-Array mit den einzelnen
	 * Reihen. Die Feldbelegungen werden in der Form "'Anz. Steine''Belegung'"
	 * ohne Separator aneinander gereiht.
	 * 
	 * @return Die reihenweise Stringrepraesentation des Spielbretts.
	 */
	public String[] getGameboardRowsAsStringArray();
}
