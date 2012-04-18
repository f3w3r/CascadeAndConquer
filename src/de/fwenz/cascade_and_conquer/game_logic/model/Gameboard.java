/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.model;

import java.util.LinkedList;
import java.util.Queue;

import de.fwenz.cascade_and_conquer.game_logic.util.EChangeMode;
import de.fwenz.cascade_and_conquer.game_logic.util.ETileOccupancy;
import de.fwenz.cascade_and_conquer.game_logic.util.InvalidMoveException;

/**
 * Die Klasse stellt das Spielbrett für das CascadeAndConquer-Spiel dar.
 * 
 * @author felixwenz
 * 
 */
public class Gameboard implements IRWModel {

	/**
	 * Die innere Klasse stellt ein Feld auf dem CascadeAndConquer-Spielbrett
	 * dar.
	 * 
	 * @author felixwenz
	 * 
	 */
	private class Tile {

		/**
		 * Die Spaltennummer des Feldes.
		 */
		private final int xPos;

		/**
		 * Die Reihennummer des Feldes.
		 */
		private final int yPos;

		/**
		 * Die momentante Belegung des Feldes.
		 */
		private ETileOccupancy occupancy = ETileOccupancy.EMPTY;

		/**
		 * Die Anzahl der Steine auf dem Feld.
		 */
		private int count = 0;

		/**
		 * Der Konstruktor fuer ein Feld.
		 * 
		 * @param xPos
		 *            Die Spaltennummer des Feldes.
		 * @param yPos
		 *            Die Reihennummer des Feldes.
		 */
		protected Tile(int xPos, int yPos) {
			this.xPos = xPos;
			this.yPos = yPos;
		}

		/**
		 * @return the xPos
		 */
		public int getXPos() {
			return xPos;
		}

		/**
		 * @return the yPos
		 */
		public int getYPos() {
			return yPos;
		}

		/**
		 * Die Methode legt den Besitzer des Feldes fest.
		 * 
		 * @param occupancy
		 *            the occupancy to set
		 */
		protected void setOccupancy(ETileOccupancy occupancy) {
			this.occupancy = occupancy;
		}

		/**
		 * Die Methode gibt den Spieler zurueck, dessen Steine auf dem Feld
		 * liegen.
		 * 
		 * @return the occupancy
		 */
		protected ETileOccupancy getOccupancy() {
			return occupancy;
		}

		/**
		 * Die Methode legt die Anzahl der Steine auf dem Feld fest.
		 * 
		 * @param count
		 *            the count to set
		 */
		protected void setCount(int count) {
			this.count = count;
		}

		/**
		 * Die Methode gibt die Anzahl der Steine auf dem Feld zurueck.
		 * 
		 * @return the count
		 */
		protected int getCount() {
			return count;
		}

		/**
		 * Die Methode erhoeht di Anzahl der Steine auf dem Feld um 1.
		 */
		protected void incCount() {
			count++;
		}
	}

	/**
	 * Die Breite des Spielbretts.
	 */
	private final int XDIM;

	/**
	 * Die Hoehe des Spielbretts.
	 */
	private final int YDIM;

	/**
	 * Das Spielbrett.
	 */
	private Tile[][] board;

	/**
	 * Die Warteschlange fuer Felder, die auf Ueberlauf geprueft werden muessen.
	 */
	private Queue<Tile> overflowQueue = new LinkedList<Tile>();

	/**
	 * Die Liste der Spielbrettbeobachter.
	 */
	private LinkedList<IBoardEventListener> listeners = new LinkedList<IBoardEventListener>();

	/**
	 * Der Konstruktor fuer ein CascadeAndConquer-Spielbrett.
	 * 
	 * @param xDim
	 *            Die Breite des Spielbretts.
	 * @param yDim
	 *            Die Hoehe des Spielbretts.
	 */
	public Gameboard(int xDim, int yDim) {
		YDIM = yDim;
		XDIM = xDim;

		// Das Spielbrett wird, anders als ueblich, durch (y, x)-Indizes
		// angesprochen werden muessen.
		board = new Tile[YDIM][XDIM];

		// Initialisieren des Spielbrettarrays.
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Tile(j, i);
			}
		}
	}

	/**
	 * Der Konstruktor fuer ein CascadeAndConquer-Spielbrett. Die Dimensionen,
	 * sowie die einzelnen Feldbelegungen werden aus uebergebenen reihenweisen
	 * Stringarray eingelesen.
	 * 
	 * @param boardRows
	 *            Das reihenweise Stringarray mit der Anfangsbelegung des
	 *            Bretts.
	 */
	public Gameboard(String[] boardRows) {
		// die Brettbreite und -hoehe werden aus dem Stringarray ermittelt.
		this(boardRows[0].length() / 2, boardRows.length);
		// reihenweises Einlesen der Brettstellung aus dem String-Array.
		for (int i = 0; i < YDIM; i++) {
			char[] row = boardRows[i].toCharArray();
			for (int j = 0; j < XDIM; j++) {
				switch (row[2 * j + 1]) {
				case 'e':
					board[i][j].setOccupancy(ETileOccupancy.EMPTY);
					break;
				case 'w':
					board[i][j].setOccupancy(ETileOccupancy.WHITE);
					break;
				case 'b':
					board[i][j].setOccupancy(ETileOccupancy.BLACK);
					break;
				default:
					break;
				}
				board[i][j].setCount(Integer
						.valueOf(String.valueOf(row[2 * j])));
			}
		}
		// Beobachter informieren, falls eine Gewinnsituation eingelesen wurde.
		if (isWinningSituation())
			informBoardListeners(new BoardWinningSituationEvent(
					getGameboardRowsAsStringArray(), board[0][0].getOccupancy()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.fwenz.cascade_and_conquer.game_logic.model.IROModel#
	 * getGameboardRowsAsStringArray
	 * (de.fwenz.cascade_and_conquer.util.isWinningSituation)
	 */
	@Override
	public String[] getGameboardRowsAsStringArray() {
		String[] result = new String[YDIM];

		for (int i = 0; i < board.length; i++) {
			result[i] = "";
			for (Tile tile : board[i]) {
				result[i] += tile.count;
				switch (tile.getOccupancy()) {
				case EMPTY:
					result[i] += "e";
					break;
				case WHITE:
					result[i] += "w";
					break;
				case BLACK:
					result[i] += "b";
					break;
				default:
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Die Methode veranlasst das Spielbrett dazu seine Beobachter ueber die
	 * juengste Stellungsaenderung zu informieren.
	 * 
	 * @param e
	 *            Das Ereignis, dass Informationen ueber die Aenderungen
	 *            enthaelt.
	 */
	private void informBoardListeners(BoardEvent e) {
		for (IBoardEventListener l : listeners) {
			l.fireBoardEvent(e);
		}
	}

	/**
	 * Die Methode ueberfuehrt das Spielbrett nach einem Zug wieder in einen
	 * stabilen und konsistenten Zustand. Hierzu werden die Feldueberlaeufe
	 * berechnet und die sich ergebenden Kaskaden durchgefuehrt.
	 */
	private void performCascadeTransaction() {
		while (!overflowQueue.isEmpty()) {
			Tile probeTile = overflowQueue.poll();
			// auf Kaskadenstufenende pruefen, ggf. Beobachter informieren.
			if (probeTile == null)
				informBoardListeners(new BoardChangedEvent(
						EChangeMode.BY_STAGE, getGameboardRowsAsStringArray()));
			else {
				// abhaengig von der Lage des Feldes auf Ueberlauf pruefen.
				switch (probeTile.getCount()) {
				case 4:
					overflow(probeTile);
					break;
				case 3:
					if (isEdgeTile(probeTile))
						overflow(probeTile);
					break;
				case 2:
					if (isCornerTile(probeTile))
						overflow(probeTile);
					break;
				default:
					break;
				}
			}
			// falls Gewinnstellung: Beobachter informieren, Ueberlaufpruefliste
			// leeren
			if (isWinningSituation()) {
				informBoardListeners(new BoardWinningSituationEvent(
						getGameboardRowsAsStringArray(), board[0][0]
								.getOccupancy()));
				overflowQueue.clear();
			}
		}
		if (!isWinningSituation())
			// Beobachter ueber Endzustand nach Zug informieren
			informBoardListeners(new BoardChangedEvent(EChangeMode.BY_MOVE,
					getGameboardRowsAsStringArray()));
	}

	/**
	 * Die Methode fuehrt einen Ueberlauf von dem uebergebenen Feld auf dessen
	 * Nachbarfelder aus.
	 * 
	 * @param overflowTile
	 *            Das Feld, das ueberlaufen soll.
	 */
	private void overflow(Tile overflowTile) {
		int overflowY = overflowTile.getYPos();
		int overflowX = overflowTile.getXPos();
		// Steine vom uebergelaufenen Feld entfernen
		overflowTile.setCount(0);

		// auf Nachbarfelder pruefen; Eigentuemer aendern; Stein hinzufuegen; in
		// Ueberlaufprüfliste haengen.

		// Nachbar im Norden.
		if (isValidTilePos(overflowY - 1, overflowX)) {
			board[overflowY - 1][overflowX].setOccupancy(overflowTile
					.getOccupancy());
			board[overflowY - 1][overflowX].incCount();
			overflowQueue.offer(board[overflowY - 1][overflowX]);
		}
		// Nachbar im Westen.
		if (isValidTilePos(overflowY, overflowX - 1)) {
			board[overflowY][overflowX - 1].setOccupancy(overflowTile
					.getOccupancy());
			board[overflowY][overflowX - 1].incCount();
			overflowQueue.offer(board[overflowY][overflowX - 1]);
		}
		// Nachbar im Osten.
		if (isValidTilePos(overflowY, overflowX + 1)) {
			board[overflowY][overflowX + 1].setOccupancy(overflowTile
					.getOccupancy());
			board[overflowY][overflowX + 1].incCount();
			overflowQueue.offer(board[overflowY][overflowX + 1]);
		}
		// Nachbar im Sueden.
		if (isValidTilePos(overflowY + 1, overflowX)) {
			board[overflowY + 1][overflowX].setOccupancy(overflowTile
					.getOccupancy());
			board[overflowY + 1][overflowX].incCount();
			overflowQueue.offer(board[overflowY + 1][overflowX]);
		}

		// Beobachter ueber Kaskadenschrittende informieren.
		informBoardListeners(new BoardChangedEvent(EChangeMode.BY_STEP,
				getGameboardRowsAsStringArray()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.fwenz.cascade_and_conquer.game_logic.model.IROModel#
	 * isWinningSituation (de.fwenz.cascade_and_conquer.util.isWinningSituation)
	 */
	@Override
	public boolean isWinningSituation() {
		for (Tile[] row : board) {
			for (Tile tile : row) {
				if (!(board[0][0].getOccupancy() == tile.getOccupancy()))
					return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.fwenz.cascade_and_conquer.game_logic.model.IRWModel#
	 * addBoardChangedEventListener
	 * (de.fwenz.cascade_and_conquer.util.IBoardEventListener)
	 */
	@Override
	public void addBoardEventListener(IBoardEventListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.fwenz.cascade_and_conquer.game_logic.model.IRWModel#
	 * removeBoardChangedEventListener
	 * (de.fwenz.cascade_and_conquer.util.IBoardEventListener)
	 */
	@Override
	public void removeBoardEventListener(IBoardEventListener listener) {
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.model.IRWModel#performMove(de
	 * .fwenz. cascade_and_conquer.util.ETileOccupancy, int, int)
	 */
	@Override
	public void performMove(ETileOccupancy player, int xPos, int yPos)
			throws InvalidMoveException {
		// pruefen, ob der Spieler auf das angegebene Feld setzen darf.
		if ((board[yPos][xPos].getOccupancy() == player)
				|| (board[yPos][xPos].getOccupancy() == ETileOccupancy.EMPTY)) {
			// Stein setzen.
			board[yPos][xPos].setOccupancy(player);
			board[yPos][xPos].incCount();
			// Feld in die Kaskadeschlange einfuegen; Stufenende anhaengen.
			overflowQueue.offer(board[yPos][xPos]);
			overflowQueue.offer(null);
			// Kaskadentransaktion durchfuehren.
			performCascadeTransaction();
		} else {
			throw new InvalidMoveException(
					"Das Feld ist bereits vom gegnerischen Spieler besetzt!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.model.IRWModel#setCountAt(int,
	 * int, int)
	 */
	@Override
	public void setCountAt(int xPos, int yPos, int count)
			throws InvalidMoveException {
		// auf ungueltige Parameter pruefen:
		// Feld nicht innerhlab der Brettgrenzen.
		if (!isValidTilePos(yPos, xPos))
			throw new InvalidMoveException("Kein gueltiges Feld!");
		// Anzahl der Steine passt nicht auf das angegebene Feld.
		else if (!isValidCount(yPos, xPos, count))
			throw new InvalidMoveException(
					"Das angegebene Feld kann in einem stabilen Zustand nicht so viele Steine aufnehmen!");
		// Feld ist von keinem Spieler besetzt.
		else if ((count > 0)
				&& (board[yPos][xPos].getOccupancy() == ETileOccupancy.EMPTY))
			throw new InvalidMoveException("Feld ist neutral!");
		// gueltigen Parameter behandeln.
		else {
			board[yPos][xPos].setCount(count);
			// wird ein Feld leer, so erhaelt es neutralen Status.
			if (count == 0)
				board[yPos][xPos].setOccupancy(ETileOccupancy.EMPTY);
		}
	}

	/**
	 * Die Methode gibt an, ob in einem stabilen Zustand auf das uebergebene
	 * Feld die uebergebene Anzahl von Steinen passt.
	 * 
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @param count
	 *            Die Anzahl an Steinen.
	 * @return wahr, falls Anzahl an Steinen stabil auf das uebergene Feld
	 *         passt.
	 */
	private boolean isValidCount(int yPos, int xPos, int count) {
		return !((count < 0) || (count > 3)
				|| ((count > 2) && (isEdgeTile(yPos, xPos))) || ((count > 1) && isCornerTile(
				yPos, xPos)));
	}

	/**
	 * Die Methode gibt an, ob es sich bei dem Feld mit den angegebenen
	 * Koordinaten um ein Eckfeld handelt.
	 * 
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @return wahr, falls das Feld ein Eckfeld ist.
	 */
	private boolean isCornerTile(int yPos, int xPos) {
		return (((yPos == 0) || (yPos == YDIM - 1)) && ((xPos == 0) || (xPos == XDIM - 1)));
	}

	/**
	 * Die Methode gibt an, ob es sich bei dem Feld mit den angegebenen
	 * Koordinaten um ein Eckfeld handelt.
	 * 
	 * @param tile
	 *            Das zu pruefende Feld.
	 * @return wahr, falls das Feld ein Eckfeld ist.
	 */
	private boolean isCornerTile(Tile tile) {
		return isCornerTile(tile.getYPos(), tile.getXPos());
	}

	/**
	 * Die Methode gibt an, ob es sich bei dem Feld mit den angegebenen
	 * Koordinaten um ein Randfeld handelt.
	 * 
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * @return wahr, falls das Feld ein Randfeld ist.
	 */
	private boolean isEdgeTile(int yPos, int xPos) {
		return ((yPos == 0) || (yPos == YDIM - 1) || (xPos == 0) || (xPos == XDIM - 1));
	}

	/**
	 * Die Methode gibt an, ob es sich bei dem Feld mit den angegebenen
	 * Koordinaten um ein Randfeld handelt.
	 * 
	 * @param tile
	 *            Das zu pruefende Feld.
	 * @return wahr, falls das Feld ein Randfeld ist.
	 */
	private boolean isEdgeTile(Tile tile) {
		return isEdgeTile(tile.getYPos(), tile.getXPos());
	}

	/**
	 * Die Methode gibt an, ob sich die x- und y-Koordinaten innerhalb der
	 * Brettgrenzen befinden.
	 * 
	 * @param yPos
	 *            Die Reihennummer des Feldes.
	 * @param xPos
	 *            Die Spaltennummer des Feldes.
	 * 
	 * @return wahr, falls die Feldkoordinaten innerhalb der Brettgrenzen
	 *         liegen.
	 */
	private boolean isValidTilePos(int yPos, int xPos) {
		return ((yPos >= 0) && (yPos < YDIM) && (xPos >= 0) && (xPos < XDIM));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.model.IRWModel#setOccupancyAt
	 * (int, int, de.fwenz.cascade_and_conquer.util.ETileOccupancy)
	 */
	@Override
	public void setOccupancyAt(int xPos, int yPos, ETileOccupancy occupancy)
			throws InvalidMoveException {
		if (!isValidTilePos(yPos, xPos))
			throw new InvalidMoveException("Kein gueltiges Feld!");
		// alle Steine sollen entfernt werden, wenn das Feld neutral wird.
		if (occupancy == ETileOccupancy.EMPTY)
			board[yPos][xPos].setCount(0);
		board[yPos][xPos].setOccupancy(occupancy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.model.IROModel#getCountAt(int,
	 * int)
	 */
	@Override
	public int getCountAt(int xPos, int yPos) {
		if (isValidTilePos(yPos, xPos))
			return board[yPos][xPos].getCount();
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.model.IROModel#getOccupancyAt
	 * (int, int)
	 */
	@Override
	public ETileOccupancy getOccupancyAt(int xPos, int yPos) {
		if (isValidTilePos(yPos, xPos))
			return board[yPos][xPos].getOccupancy();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fwenz.cascade_and_conquer.game_logic.model.IROModel#getXDim()
	 */
	@Override
	public int getXDim() {
		return XDIM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fwenz.cascade_and_conquer.game_logic.model.IROModel#getYDim()
	 */
	@Override
	public int getYDim() {
		return YDIM;
	}
}
