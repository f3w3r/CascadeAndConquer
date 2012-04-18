/**
 * 
 */
package de.fwenz.cascade_and_conquer;

import de.fwenz.cascade_and_conquer.game_logic.model.Gameboard;
import de.fwenz.cascade_and_conquer.game_logic.model.IRWModel;
import de.fwenz.cascade_and_conquer.gui.CaCMainFrame;

/**
 * Die Klasse dient dem Start des "Cascade and Conquer" Spiels. Die
 * verschiedenen Programmteile werden erstellt und funktionsgemaess miteinander
 * verknuepft.
 * 
 * @author felixwenz
 * 
 */
public class CascadeAndConquer {

	/**
	 * Die Methode startet das Spiel. GUI und Programmlogik werden initialisiert
	 * und etwaig auftretende Fehler werden entsprechend behandelt.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Fehlerbehandlung
		// TODO Logik initialisieren
		// TODO GUI initialisieren
		// TODO Programmteile verknüpfen und Spiel starten

		try {
			CaCMainFrame mainFrame = new CaCMainFrame();

			// T E S T S
			String[] bulk = { "0e0e0e1w", "2b0e0e1w", "0e3b0e1w", "0e0e0e4w" };
			IRWModel board = new Gameboard(bulk);

			(mainFrame.getView()).setBoardState(board
					.getGameboardRowsAsStringArray());
			mainFrame.showBoardPanel();

			// Parsertest

			// String testCommand1 = "board(row=0ergerg)";
			// System.out.println(CaCProtocolParser
			// .parseCommandToString(CaCProtocolParser
			// .parseStringToCommand(testCommand1)));
			// String testCommand2 = "bye";
			// System.out.println(CaCProtocolParser
			// .parseCommandToString(CaCProtocolParser
			// .parseStringToCommand(testCommand2)));
			// String testCommand3 = "move (X= 0 ;Y=3)";
			// System.out.println(CaCProtocolParser
			// .parseCommandToString(CaCProtocolParser
			// .parseStringToCommand(testCommand3)));

		} catch (Exception e) {
			System.out.println("CRASH!!!");
			e.printStackTrace();
		}
	}
}
