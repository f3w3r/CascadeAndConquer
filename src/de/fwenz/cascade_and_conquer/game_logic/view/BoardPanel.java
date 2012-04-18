/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

import de.fwenz.cascade_and_conquer.game_logic.model.BoardChangedEvent;
import de.fwenz.cascade_and_conquer.game_logic.model.BoardEvent;
import de.fwenz.cascade_and_conquer.game_logic.model.BoardWinningSituationEvent;
import de.fwenz.cascade_and_conquer.game_logic.player.HumanPlayer;
import de.fwenz.cascade_and_conquer.game_logic.util.EChangeMode;
import de.fwenz.cascade_and_conquer.game_logic.util.Move;

/**
 * Die Klasse stellt den View des CascadeAndConquer-Spiels, eine
 * Spielbrettanzeige, als Panel in einer GUI dar.
 * 
 * @author felixwenz
 * 
 */
public class BoardPanel extends JPanel implements IView, MouseListener,
		Runnable {

	/**
	 * Die Kennzahl der Klasse zur (De)serialisierung.
	 */
	private static final long serialVersionUID = 4478613832469727609L;

	/**
	 * Die Breite des Spielbretts.
	 */
	private int xDim;

	/**
	 * Die Hoehe des Spielbretts.
	 */
	private int yDim;

	/**
	 * Der aktuelle Zustand des Spielbretts.
	 */
	private String[] boardState;

	/**
	 * Die Warteschlange der anzuzeigenden Aenderungen.
	 */
	Queue<BoardEvent> displayEventQueue = new LinkedList<BoardEvent>();

	/**
	 * Die Schleife, die den Ereignispool abarbeitet.
	 */
	private Thread displayEventLoop;

	/**
	 * wahr, wenn alle das Zugendeereignis in der Warteschlange steht.
	 */
	private boolean moveComplete = false;

	/**
	 * Der Uebergangsmodus in dem Aenderungen dargestellt werden.
	 */
	private EChangeMode changeMode;

	/**
	 * Die Uebergangsdarstellung; 0: Leertaste, >0: Intervall in Millisekunden.
	 */
	private int changeDisplay;

	/**
	 * Der Skalierungsfaktor.
	 */
	private double scale;

	/**
	 * Die Liste der menschlichen Spieler, denen Zuege gemeldet werden sollen.
	 */
	private LinkedList<HumanPlayer> humanPlayers = new LinkedList<HumanPlayer>();

	/**
	 * Der Konstruktor fuer eine Spielbrettanzeige.
	 */
	public BoardPanel() {
		super();
		xDim = 0;
		yDim = 0;
		changeMode = EChangeMode.BY_MOVE;
		changeDisplay = 0;
		boardState = null;

		displayEventLoop = new Thread(this);
		displayEventLoop.start();

		setLayout(null);
		setSize(800, 450);
		setBounds(80, 45, 400, 400);
		setVisible(false);
	}

	/**
	 * Der Konstruktor fuer eine Spielbrettanzeige.
	 * 
	 * @param boardState
	 *            Der initiale Brettzustand.
	 */
	public BoardPanel(String[] boardState) {
		this();
		setBoardState(boardState);
	}

	/**
	 * Der Konstruktor fuer eine Spielbrettanzeige.
	 * 
	 * @param boardState
	 *            Der initiale Brettzustand.
	 * @param changeMode
	 *            Der initiale Uebergangsmodus.
	 * @param changeDisplay
	 *            Die initiale Uebergangsdarstellung.
	 */
	public BoardPanel(String[] boardState, EChangeMode changeMode,
			int changeDisplay) {
		this(boardState);
		this.changeMode = changeMode;
		this.changeDisplay = changeDisplay;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		transformToUserSpace(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawBoard(g2);
	}

	/**
	 * Die Methode transformiert die virtuellen Dimensionen des Brettes auf die
	 * tatsaechlichen Bildschirmmasse (in Pixel) eines Grafikobjektes.
	 * 
	 * @param g2
	 *            Das zu transformierende Grafikobjekt.
	 */
	private void transformToUserSpace(Graphics2D g2) {
		double xScale = getWidth() / (60.0 * xDim);
		double yScale = getHeight() / (60.0 * yDim);
		scale = Math.min(xScale, yScale);
		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		g2.transform(at);
	}

	/**
	 * Die Methode zeichnet das Brett in ein Grafikobjekt.
	 * 
	 * @param g2
	 *            Das Grafikobjekt, auf das gezeichnet werden soll.
	 */
	private void drawBoard(Graphics2D g2) {
		drawGrid(g2);
		if (boardState != null)
			drawTiles(g2);
	}

	/**
	 * Die Methode zeichnet das Gitter des Spielbretts.
	 * 
	 * @param g2
	 *            Das Grafikobjekt, auf das gezeichnet werden soll.
	 */
	private void drawGrid(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, xDim * 60 - 1, yDim * 60 - 1);
		g2.setColor(Color.BLACK);
		// Rahmen zeichnen
		g2.drawRect(0, 0, xDim * 60 - 1, yDim * 60 - 1);
		// vertikale Gitterlinien zeichnen
		for (int i = 0; i < xDim; i++)
			g2.drawLine(i * 60, 0, i * 60, yDim * 60);
		// horizontale Gitterlinien zeichnen
		for (int i = 0; i < yDim; i++)
			g2.drawLine(0, i * 60, xDim * 60, i * 60);
	}

	/**
	 * Die Methode zeichnet die Steine gemaess der Brettbelegung auf die Felder.
	 * 
	 * @param g2
	 *            Das Grafikobjekt, auf das gezeichnet werden soll.
	 */
	private void drawTiles(Graphics2D g2) {
		// ueber die Felder des Spielbretts iterieren.
		for (int i = 0; i < yDim; i++) {
			char[] row = boardState[i].toCharArray();
			for (int j = 0; j < xDim; j++) {
				// nordwestlichsten Punktkoordinaten des Feldes ermitteln.
				int xPos = j * 60;
				int yPos = i * 60;
				// Feldbelegung ermitteln.
				char owner = row[2 * j + 1];
				// Anzahl der Steine auf dem Feld ermitteln.
				int count = Integer.valueOf(String.valueOf(row[2 * j]));

				// Steine (falls vorhanden) auf Feld zeichnen.
				if (owner != 'e')
					drawTile(xPos, yPos, owner, count, g2);
			}
		}
	}

	/**
	 * Die Methode zeichnet ein Feld gemaess der uebergebenen Parameter.
	 * 
	 * @param xPos
	 *            Die x-Koordinate des Feldes.
	 * @param yPos
	 *            Die y-Koordinate des Feldes.
	 * @param owner
	 *            Der Spieler, dessen Steine auf dem Feld liegen.
	 * @param count
	 *            Die Anzahl der Steine auf dem Feld.
	 * @param g2
	 *            Das Grafikobjekt, auf das gezeichnet werden soll.
	 */
	private void drawTile(int xPos, int yPos, char owner, int count,
			Graphics2D g2) {
		// Farbe fuer Steine ermitteln.
		if (owner == 'w')
			g2.setColor(Color.WHITE);
		if (owner == 'b')
			g2.setColor(Color.BLACK);

		// Anzahl der Steine auf dem Feld feststellen und zeichnen.
		switch (count) {
		case 1:
			g2.fillOval(xPos + 24, yPos + 24, 12, 12);
			break;
		case 2:
			g2.fillOval(xPos + 12, yPos + 24, 12, 12);
			g2.fillOval(xPos + 36, yPos + 24, 12, 12);
			break;
		case 3:
			g2.fillOval(xPos + 24, yPos + 12, 12, 12);
			g2.fillOval(xPos + 12, yPos + 36, 12, 12);
			g2.fillOval(xPos + 36, yPos + 36, 12, 12);
			break;
		case 4:
			g2.fillOval(xPos + 12, yPos + 12, 12, 12);
			g2.fillOval(xPos + 36, yPos + 12, 12, 12);
			g2.fillOval(xPos + 12, yPos + 36, 12, 12);
			g2.fillOval(xPos + 36, yPos + 36, 12, 12);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.view.IView#setBoardState(java
	 * .lang.String[])
	 */
	@Override
	public void setBoardState(String[] boardState) {
		if (boardState == null) {
			xDim = 0;
			yDim = 0;
		} else {
			this.boardState = boardState;
			// Konsistenz zwischen x-/yDim und Brettstellung gewaehrleisten.
			xDim = boardState[0].length() / 2;
			yDim = boardState.length;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.view.IView#setChangeDisplay(int)
	 */
	@Override
	public void setChangeDisplay(int changeDisplay) {
		this.changeDisplay = changeDisplay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fwenz.cascade_and_conquer.game_logic.view.IView#setChangeMode(de.fwenz
	 * .cascade_and_conquer.game_logic.model.EChangeMode)
	 */
	@Override
	public void setChangeMode(EChangeMode changeMode) {
		this.changeMode = changeMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.fwenz.cascade_and_conquer.game_logic.model.IBoardEventListener#
	 * fireBoardEvent(de.fwenz.cascade_and_conquer.game_logic.model.BoardEvent)
	 */
	@Override
	public void fireBoardEvent(BoardEvent e) {
		if (e instanceof BoardWinningSituationEvent) {
			// TODO Gewinnsituation behandeln --> Thread unterbrechen; Spielende
		} else if (e instanceof BoardChangedEvent) {
			// Auswaehlen, welche Aenderungsereignisse in die
			// Anzeigewarteschlange aufgenommen werden sollen.
			if (((BoardChangedEvent) e).getChangeType() == EChangeMode.BY_MOVE)
				moveComplete = true;
			if (changeMode.compareTo(((BoardChangedEvent) e).getChangeType()) <= 0)
				displayEventQueue.offer(e);
				
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// Hier sollen, falls vorhanden, die Spielbrettaenderungen im
		// Ereignispool abgearbeitet werden.
		while (true) {
			// TODO

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fwenz.cascade_and_conquer.game_logic.view.IView#getScale()
	 */
	@Override
	public double getScale() {
		return scale;
	}

	/**
	 * Die Methode fuegt der Liste der menschlichen Spieler den uebergebenen
	 * hinzu.
	 * 
	 * @param player
	 *            Der menschliche Spieler, der hinzugefuegt werden soll.
	 */
	public void addHumanPlayer(HumanPlayer player) {
		humanPlayers.add(player);
	}

	/**
	 * Die Methode entfernt aus der Liste der menschlichen Spieler den
	 * uebergebenen.
	 * 
	 * @param player
	 *            Der menschliche Spieler, der entfernt werden soll.
	 */
	public void removeHumanPlayer(HumanPlayer player) {
		humanPlayers.remove(player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int) (e.getX() / (scale * 60));
		int yPos = (int) (e.getY() / (scale * 60));
		Move move = new Move(xPos, yPos);
		for (HumanPlayer humanPlayer : humanPlayers) {
			humanPlayer.tellMove(move);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
