/**
 * 
 */
package de.fwenz.cascade_and_conquer.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * Die Klasse stellt ein Panel mit einer Ladeanzeige zur Verfügung.
 * 
 * @author felixwenz
 * 
 */
public class ProgressPanel extends JPanel {

	/**
	 * Die Kennzahl der Klasse zur (De)serialisierung.
	 */
	private static final long serialVersionUID = 5153894692361456305L;

	/**
	 * Die Beschriftung des Ladebalkens.
	 */
	private JLabel lProgress;

	/**
	 * Der Ladebalken.
	 */
	private JProgressBar progressBar;

	/**
	 * Öffentlicher Konstruktor für eine Ladebalkenanzeige.
	 */
	public ProgressPanel() {

		super();

		setLayout(new BorderLayout());
		setSize(800, 450);
		setBounds(80, 45, 900, 450);
		setVisible(false);

		lProgress = new JLabel("Ladevorgang läuft...");
		lProgress.setFont(new Font(Font.SERIF, Font.BOLD, 40));
		lProgress.setHorizontalTextPosition(SwingConstants.CENTER);
		add(lProgress, BorderLayout.NORTH);

		progressBar = new JProgressBar();
		progressBar.setSize(300, 50);
		progressBar.setStringPainted(true);
		progressBar.setVisible(true);
		add(progressBar, BorderLayout.CENTER);
	}

	/**
	 * Die Methode lässt den Ladebalken eine Tätigkeit darstellen.
	 */
	public void startProgress() {
		progressBar.setIndeterminate(true);
	}

	/**
	 * Die Methode lässt den Ladebalken stoppen.
	 */
	public void stopProgress() {
		progressBar.setIndeterminate(false);
	}
}
