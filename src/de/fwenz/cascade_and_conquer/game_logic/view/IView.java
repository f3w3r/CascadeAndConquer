/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.view;

import de.fwenz.cascade_and_conquer.game_logic.model.IBoardEventListener;
import de.fwenz.cascade_and_conquer.game_logic.util.EChangeMode;

/**
 * Das Interface beinhaltet die Methoden, die ein View zur Verfuegung stellen
 * muss.
 * 
 * @author felixwenz
 * 
 */
public interface IView extends IBoardEventListener {

	/**
	 * Die Methode legt den Uebergangsmodus der Anzeige fest.
	 * 
	 * @param changeMode
	 *            Der zu waehlende Uebergangsmodus.
	 */
	public void setChangeMode(EChangeMode changeMode);

	/**
	 * Die Methode legt die Uebergangsdarstellung der Anzeige fest.
	 * 
	 * @param changeDisplay
	 *            Die zu waehlende Uebergangsdarstellung.
	 */
	public void setChangeDisplay(int changeDisplay);

	/**
	 * Die Methode liefert den momentanen Skalierungsfaktor.
	 * 
	 * @return Der Skalierungsfaktor.
	 */
	public double getScale();

	/**
	 * Die Methode setzt den Brettzustand gemaess des uebergebenen Parameters.
	 * 
	 * @param boardState
	 *            Der zu setzende Brettzustand.
	 */
	public void setBoardState(String[] boardState);
}
