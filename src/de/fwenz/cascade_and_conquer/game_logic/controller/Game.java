/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.controller;

import de.fwenz.cascade_and_conquer.game_logic.model.Gameboard;
import de.fwenz.cascade_and_conquer.game_logic.model.IRWModel;
import de.fwenz.cascade_and_conquer.game_logic.player.IPlayer;
import de.fwenz.cascade_and_conquer.game_logic.util.ETileOccupancy;
import de.fwenz.cascade_and_conquer.game_logic.util.InvalidMoveException;
import de.fwenz.cascade_and_conquer.game_logic.util.Move;

/**
 * Die Klasse stellt ein komplettes CascadeAndConquer-Spiel dar. Realisiert wird
 * dies durch das Erben von der Klasse Thread.
 * 
 * @author felixwenz
 * 
 */
public class Game extends Thread implements IController {

	/**
	 * Der Spieler mit den weissen Steinen.
	 */
	private IPlayer whitePlayer;

	/**
	 * Der Spieler mit den schwarzen Steinen.
	 */
	private IPlayer blackPlayer;

	/**
	 * Das Spielbrett.
	 */
	private IRWModel gameboard;

	/**
	 * wahr, waehrend das Spiel laeuft.
	 */
	private boolean enabled = false;

	/**
	 * wahr, wenn das Spiel beendet werden soll.
	 */
	private boolean terminate = false;

	/**
	 * wahr, wenn der weisse Spieler am Zug ist.
	 */
	private boolean whitePlayerActive = true;

	/**
	 * Der Konstruktor fuer ein CascadeAndConquer-Spiel.
	 * 
	 * @param whitePlayer
	 *            Der Spieler mit den weissen Steinen.
	 * @param blackPlayer
	 *            Der Spieler mit den schwarzen Steinen.
	 * @param gameboard
	 *            Das Spielbrett.
	 */
	public Game(IPlayer whitePlayer, IPlayer blackPlayer, IRWModel gameboard) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		this.gameboard = gameboard;
	}

	/**
	 * Der Konstruktor fuer ein CascadeAndConquer-Spiel.
	 * 
	 * @param whitePlayer
	 *            Der Spieler mit den weissen Steinen.
	 * @param blackPlayer
	 *            Der Spieler mit den schwarzen Steinen.
	 * @param xDim
	 *            Die Breite des Spielbretts.
	 * @param yDim
	 *            Die Hoehe des Spielbretts.
	 */
	public Game(IPlayer whitePlayer, IPlayer blackPlayer, int xDim, int yDim) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		gameboard = new Gameboard(xDim, yDim);
	}

	@Override
	public void run() {
		while (!terminate) {
			while (enabled) {
				if (gameboard.isWinningSituation())
					setEnabled(false);
				else {
					if (whitePlayerActive)
						requestMoveFromPlayer(whitePlayer);
					else
						requestMoveFromPlayer(blackPlayer);
					nextPlayer();
				}
			}
		}
	}

	/**
	 * Die Methode fordert vom uebergebenen Spieler einen Zug an.
	 * 
	 * @param player
	 *            Spieler, von dem ein Zug angefordert werden soll.
	 */
	synchronized private void requestMoveFromPlayer(IPlayer player) {
		// Spielerfarbe ermitteln
		ETileOccupancy color;
		if (player == whitePlayer)
			color = ETileOccupancy.WHITE;
		else
			color = ETileOccupancy.BLACK;

		// Solange Zuege vom Spieler anfordern, bis einer gueltig war.
		boolean moveValid = false;
		while (!moveValid) {
			Move nextMove = player.getMove(gameboard
					.getGameboardRowsAsStringArray());
			try {
				gameboard.performMove(color, nextMove.getYPos(), nextMove
						.getYPos());
				moveValid = true;
			} catch (InvalidMoveException e) {
				moveValid = false;
			}
		}
	}

	/**
	 * Die Methode setzt den nächsten Spieler aktiv.
	 */
	synchronized private void nextPlayer() {
		whitePlayerActive = !whitePlayerActive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#newGame
	 * (int, int)
	 */
	@Override
	synchronized public void newGame(int xDim, int yDim) {
		gameboard = new Gameboard(xDim, yDim);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#newGame
	 * (int, int, de.fwenz.cascade_and_conquer.game_logic.player.IPlayer,
	 * de.fwenz.cascade_and_conquer.game_logic.player.IPlayer)
	 */
	@Override
	synchronized public void newGame(int xDim, int yDim, IPlayer whitePlayer,
			IPlayer blackPlayer) {
		gameboard = new Gameboard(xDim, yDim);
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#quitGame()
	 */
	@Override
	synchronized public void quitGame() {
		terminate = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#setBlackPlayer
	 * (de.fwenz.cascade_and_conquer.game_logic.player.IPlayer)
	 */
	@Override
	synchronized public void setBlackPlayer(IPlayer blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#setWhitePlayer
	 * (de.fwenz.cascade_and_conquer.game_logic.player.IPlayer)
	 */
	@Override
	synchronized public void setWhitePlayer(IPlayer whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#setupBoard
	 * (java.lang.String[])
	 */
	@Override
	synchronized public void setupBoard(String[] gameboard) {
		this.gameboard = new Gameboard(gameboard);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.controller.IController#setEnabled
	 * (boolean)
	 */
	@Override
	synchronized public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
