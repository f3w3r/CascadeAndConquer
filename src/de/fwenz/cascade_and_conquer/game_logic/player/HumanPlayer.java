/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.player;

import de.fwenz.cascade_and_conquer.game_logic.util.Move;

/**
 * Die Klasse stellt einen menschlichen Spieler dar, der zur Interaktion mit dem
 * Spiel Tastatur und Maus benutzt.
 * 
 * @author felixwenz
 * 
 */
public class HumanPlayer implements IPlayer {

	/**
	 * wahr, solange der Spieler am Zug ist.
	 */
	private boolean enabled = false;

	/**
	 * wahr, wenn nach Aufforderung ein Zug getaetigt wurde.
	 */
	private boolean movePerformed = false;

	/**
	 * Der juengste Zug.
	 */
	private Move lastMove = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.player.IPlayer#getMove(java.lang
	 * .String[])
	 */
	@Override
	public synchronized Move getMove(String[] state) {
		enabled = true;
		try {
			// Auf Zug warten.
			while (!movePerformed)
				wait();
			movePerformed = false;
		} catch (InterruptedException e) {
			enabled = false;
		}
		enabled = false;
		return lastMove;
	}

	/**
	 * Die Methode informiert den menschlichen Spieler ueber seinen Zug.
	 * 
	 * @param move
	 *            Der erfolgte Zug.
	 */
	public synchronized void tellMove(Move move) {
		// Pruefen, ob der Spieler am Zug ist.
		if (enabled) {
			lastMove = move;
			movePerformed = true;
			notify();
		}
	}

}
