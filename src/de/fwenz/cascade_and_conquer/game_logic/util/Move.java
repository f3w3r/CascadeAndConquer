/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

/**
 * Die Klasse stellt einen Zug eines Spielers dar.
 * 
 * @author felixwenz
 * 
 */
public class Move {

	/**
	 * Die x-Koordinate des Feldes auf dem der Zug erfolgt.
	 */
	private final int xPos;

	/**
	 * Die y-Koordinate des Feldes auf dem der Zug erfolgt.
	 */
	private final int yPos;

	/**
	 * Der Konstruktor fuer einen Zug.
	 * 
	 * @param xPos
	 *            Die x-Koordinate des Feldes auf dem der Zug erfolgt.
	 * @param yPos
	 *            Die y-Koordinate des Feldes auf dem der Zug erfolgt.
	 */
	public Move(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Die Methode liefert die x-Koordinate des Zuges.
	 * 
	 * @return Die x-Koordinate.
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * Die Methode liefert die y-Koordinate des Zuges.
	 * 
	 * @return Die y-Koordinate.
	 */
	public int getYPos() {
		return yPos;
	}
}
