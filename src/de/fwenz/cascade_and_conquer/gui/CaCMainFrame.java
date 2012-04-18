/**
 * 
 */
package de.fwenz.cascade_and_conquer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.fwenz.cascade_and_conquer.game_logic.view.BoardPanel;
import de.fwenz.cascade_and_conquer.game_logic.view.IView;

/**
 * Die Klasse stellt das Hauptfenster der CascadeAndConquer-Anwendung dar. Ueber
 * ein Menü sind die weiteren Funktionalitäten zugänglich.
 * 
 * @author felixwenz
 * 
 */
public class CaCMainFrame extends JFrame implements ActionListener {

	/**
	 * Die Kennzahl der Klasse zur (De)serialisierung.
	 */
	private static final long serialVersionUID = 4021640283203300192L;

	/*----------------------------------------------------------------*/
	/* GUI-Elemente */
	/*----------------------------------------------------------------*/

	/**
	 * Begruessungsanzeige.
	 */
	private WelcomePanel pWelcome;

	/**
	 * Spielbrettanzeige.
	 */
	private BoardPanel pBoard;

	/**
	 * Menueleiste.
	 */
	private JMenuBar mb;

	/**
	 * Spiel-Menue.
	 */
	private JMenu mGame;

	/**
	 * Spiel-Menueeintrag: Neu.
	 */
	private JMenuItem miNew;

	/**
	 * Spiel-Menueeintrag: Laden.
	 */
	private JMenuItem miLoad;

	/**
	 * Spiel-Menueeintrag: Speichern.
	 */
	private JMenuItem miSave;

	/**
	 * Spiel-Menueeintrag: Speichern unter.
	 */
	private JMenuItem miSaveAs;

	/**
	 * Spiel-Menueeintrag: Beenden.
	 */
	private JMenuItem miQuit;

	/**
	 * Steuerung-Menue.
	 */
	private JMenu mControl;

	/**
	 * Steuerung-Untermenue: Uebergang.
	 */
	private JMenu mTransformation;

	/**
	 * Uebergang-Untermenue: Modus.
	 */
	private JMenu mMode;

	/**
	 * Modus-Menüeintrag: Schritt.
	 */
	private JMenuItem miStep;

	/**
	 * Modus-Menüeintrag: Stufe.
	 */
	private JMenuItem miStage;

	/**
	 * Modus-Menüeintrag: Zug.
	 */
	private JMenuItem miTurn;

	/**
	 * Uebergang-Untermenue: Darstellung.
	 */
	private JMenu mDisplay;

	/**
	 * Darstellung-Menüeintrag: Klick.
	 */
	private JMenuItem miClick;

	/**
	 * Darstellung-Menüeintrag: Intervall.
	 */
	private JMenuItem miIntervall;

	/**
	 * Info-Menü.
	 */
	private JMenu mAbout;

	/**
	 * Info-Menüeintrag: Ueber CascadeAndConquer.
	 */
	private JMenuItem miAboutCaC;

	/**
	 * Öffentlicher Konstruktor des Hauptfensters.
	 */
	public CaCMainFrame() {

		super("Cascade & Conquer");

		// Schliessmechanismus --> Beenden der Anwendung
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});

		// TODO evtl. anderen Layout-Manager auswählen
		// Hauptfenster anpassen
		setLayout(null);
		setSize(800, 590);
		setLocationRelativeTo(null);
		setResizable(false);

		// Menü initialisieren
		initMenu();

		// Begruessungsanzeige initialisieren.
		pWelcome = new WelcomePanel("Cascade & Conquer",
				"Das Brettspiel mit Ueberlauf", "v0.1-BETA");
		add(pWelcome);
		pWelcome.setVisible(true);

		// Spielbrettanzeige initialisieren.
		pBoard = new BoardPanel();
		add(pBoard);

		// Hauptfenster sichtbar machen
		setVisible(true);
	}

	/**
	 * Die Methode dient dem Initialisieren der Menüstruktur.
	 */
	private void initMenu() {

		mb = new JMenuBar();

		// Menue Spiel
		mGame = new JMenu("Spiel");
		mGame.setMnemonic('s');

		// --- Spiel-Neu
		miNew = new JMenuItem("Neu");
		miNew.setMnemonic('n');
		miNew.addActionListener(this);
		mGame.add(miNew);

		// --- Spiel-Öffnen
		miLoad = new JMenuItem("Laden");
		miLoad.setMnemonic('l');
		miLoad.addActionListener(this);
		mGame.add(miLoad);

		// --- Spiel-Speichern
		miSave = new JMenuItem("Speichern");
		miSave.setMnemonic('p');
		miSave.addActionListener(this);
		mGame.add(miSave);

		// --- Spiel-SpeichernUnter
		miSaveAs = new JMenuItem("Speichern unter...");
		miSaveAs.setMnemonic('u');
		miSaveAs.addActionListener(this);
		mGame.add(miSaveAs);

		mGame.addSeparator();

		// --- Spiel-Beenden
		miQuit = new JMenuItem("Beenden");
		miQuit.setMnemonic('e');
		miQuit.addActionListener(this);
		mGame.add(miQuit);

		mb.add(mGame);

		// Menue Steuerung
		mControl = new JMenu("Steuerung");
		mControl.setMnemonic('t');

		// --- Steuerung --- Uebergang
		mTransformation = new JMenu("Übergang");
		mTransformation.setMnemonic('g');

		// --- Steuerung --- Uebergang --- Modus
		mMode = new JMenu("Modus");
		mMode.setMnemonic('o');

		// --- Modus-Schritt
		miStep = new JMenuItem("Schritt");
		miStep.setMnemonic('i');
		miStep.addActionListener(this);
		mMode.add(miStep);

		// --- Modus-Stufe
		miStage = new JMenuItem("Stufe");
		miStage.setMnemonic('e');
		miStage.addActionListener(this);
		mMode.add(miStage);

		// --- Modus-Zug
		miTurn = new JMenuItem("Zug");
		miTurn.setMnemonic('u');
		miTurn.addActionListener(this);
		mMode.add(miTurn);

		mTransformation.add(mMode);

		// --- Steuerung --- Uebergang --- Darstellung
		mDisplay = new JMenu("Darstellung");
		mDisplay.setMnemonic('a');

		// --- Darstellung-Klick
		miClick = new JMenuItem("Klick");
		miClick.setMnemonic('i');
		miClick.addActionListener(this);
		mDisplay.add(miClick);

		// --- Darstellung-Intervall
		miIntervall = new JMenuItem("Intervall");
		miIntervall.setMnemonic('a');
		miIntervall.addActionListener(this);
		mDisplay.add(miIntervall);

		mTransformation.add(mDisplay);

		mControl.add(mTransformation);

		mb.add(mControl);

		// Menue Info
		mAbout = new JMenu("Info");
		mAbout.setMnemonic('f');
		mb.add(mAbout);

		miAboutCaC = new JMenuItem("Über Cascade & Conquer");
		miAboutCaC.setMnemonic('Ü');
		miAboutCaC.addActionListener(this);
		mAbout.add(miAboutCaC);

		// Menueleiste anzeigen
		setJMenuBar(mb);
	}

	/**
	 * Methode zum Beenden der Anwendung. Der Benutzer wird gefragt, ob er den
	 * aktuellen Bearbeitungsstand speichern möchte.
	 */
	private void quit() {
		int answer = JOptionPane.showConfirmDialog(this,
				"Sollen die Daten gespeichert werden?", "Beenden",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		// bei "Abbrechen" wird das Programm fortgesetzt
		if (answer != JOptionPane.CANCEL_OPTION) {
			if (answer == JOptionPane.YES_OPTION) {
			}
			// TODO save();
			// TODO Anwendung hier ordentlich beenden; evtl. CaCEvent...
			System.exit(0);
		}
	}

	/**
	 * Die Methode zeigt die Spielbrettanzeige an.
	 */
	public void showBoardPanel() {
		pWelcome.setVisible(false);
		pBoard.setVisible(true);
	}

	/**
	 * Die Methode liefert die Spielbrettanzeige als View.
	 * 
	 * @return Die Spielbrettanzeige als View.
	 */
	public IView getView() {
		return pBoard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource = e.getSource();

		// Ereignisquelle prüfen und entsprechend reagieren
		if (eventSource == miQuit)
			quit();
		if (eventSource == miNew) {
		}
		// TODO newGame();
		if (eventSource == miLoad) {
		}
		// TODO load();
		if (eventSource == miSave) {
		}
		// TODO save();
		if (eventSource == miSaveAs) {
		}
		// TODO saveAs();
		if (eventSource == miAboutCaC) {
			String message = "    Cascade & Conquer\n                  -"
					+ "\nDas Brettspiel mit Ueberlauf\n\n\nVersion:   0.1-BETA"
					+ "\n\nAutor:      Felix Wenz\n\n               2011";
			JOptionPane.showMessageDialog(this, message, "Info",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}
