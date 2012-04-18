/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.controller;

import de.fwenz.cascade_and_conquer.game_logic.player.IPlayer;

/**
 * Das Interface gibt die Methoden fuer einen CascadeAndConquer Controller an.
 * 
 * @author felixwenz
 * 
 */
public interface IController {

	/**
	 * Die Methode startet ein neues Spiel.
	 * 
	 * @param xDim
	 *            Die Breite des Spielbretts.
	 * @param yDim
	 *            Die Hoehe des Spielbretts.
	 */
	public void newGame(int xDim, int yDim);

	/**
	 * Die Methode startet ein neues Spiel.
	 * 
	 * @param xDim
	 *            Die Breite des Spielbretts.
	 * @param yDim
	 *            Die Hoehe des Spielbretts.
	 * @param whitePlayer
	 *            Der Spieler mit den weissen Steinen.
	 * @param blackPlayer
	 *            Der Spieler mit den schwarzen Steinen.
	 */
	public void newGame(int xDim, int yDim, IPlayer whitePlayer,
			IPlayer blackPlayer);

	/**
	 * Die Methode setzt fest, ob das Spiel laeuft oder pausiert.
	 * 
	 * @param enabled
	 *            wahr, wenn das Spiel laufen soll.
	 */
	public void setEnabled(boolean enabled);

	/**
	 * Die Methode beendet das Spiel.
	 */
	public void quitGame();

	/**
	 * Die Methode stellt eine bestimmte Stellung auf dem Spielbrett her.
	 * 
	 * @param gameboard
	 *            Die Zeilen der Spielbrettbelegung als String-Array.
	 */
	public void setupBoard(String[] gameboard);

	/**
	 * Die Methode uebergibt dem Controller den Spieler, der fortan die weissen
	 * Steine setzen soll.
	 * 
	 * @param whitePlayer
	 *            Der Spieler, der fortan die weissen Steine setzen soll.
	 */
	public void setWhitePlayer(IPlayer whitePlayer);

	/**
	 * Die Methode uebergibt dem Controller den Spieler, der fortan die
	 * schwarzen Steine setzen soll.
	 * 
	 * @param blackPlayer
	 *            Der Spieler, der fortan die schwarzen Steine setzen soll.
	 */
	public void setBlackPlayer(IPlayer blackPlayer);

}
