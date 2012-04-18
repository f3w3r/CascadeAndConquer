/**
 * 
 */
package de.fwenz.cascade_and_conquer.game_logic.util;

import java.util.LinkedList;

/**
 * Die Klasse stellt statische Methoden zum Parsen des
 * CascadeAndConquer-Protokolls (CaCP) bereit.
 * 
 * @author felixwenz
 * 
 */
public class CaCProtocolParser {

	/**
	 * Der private Konstruktor stellt sicher, dass es keine Parserobjekte gibt.
	 */
	private CaCProtocolParser() {

	}

	/**
	 * Die statische Methode generiert aus der String-Repraesentation eines
	 * Kommandos ein Kommandoobjekt.
	 * 
	 * @param command
	 *            Die String-Repraesentation des Kommandos.
	 * @return Das Kommandoobjekt.
	 * @throws InvalidCommandException
	 *             Wird geworfen, falls kein gueltiges Kommando vorliegt.
	 */
	public static CaCPCommand parseStringToCommand(String command)
			throws InvalidCommandException {
		// Fehler beim Parsen abfangen.
		try {
			// Whitespaces entfernen.
			command = command.replaceAll("\\s", "");

			// Aufteilen in Kommandotyp und Parameterliste.
			String[] commandParts = (command.replaceAll("\\)", ""))
					.split("\\(");
			String sCommandType = commandParts[0];

			// Kommandotyp parsen.
			ECaCPCommandType commandType = parseStringToCommandType(sCommandType
					.toLowerCase());

			// Parameter parsen, falls vorhanden.
			LinkedList<CaCPParameter> parameters = new LinkedList<CaCPParameter>();
			if (commandParts.length > 1) {
				String[] sParameters = commandParts[1].split(";");
				for (String sParameter : sParameters) {
					parameters.add(parseStringToParameter(sParameter));
				}
			}

			// Neues Kommando erstellen und zurueckgeben.
			return new CaCPCommand(commandType, parameters);
		} catch (InvalidCommandException e) {
			throw e;
		} catch (Exception e) {
			throw new InvalidCommandException(
					"Fehler beim Parsen des Kommandos!\n" + e.getMessage());
		}
	}

	/**
	 * Die statische Methode generiert aus einer String-Repraesentation eines
	 * Kommandotyps einen Kommandotypwert.
	 * 
	 * @param sCommandType
	 *            Die String-Repraesentation des Kommandotyps.
	 * @return Der Kommandotypwert.
	 * @throws InvalidCommandException
	 *             Wird geworfen, falls kein gueltiger Kommandotyp vorliegt.
	 */
	private static ECaCPCommandType parseStringToCommandType(String sCommandType)
			throws InvalidCommandException {
		if (sCommandType.equals("error"))
			return ECaCPCommandType.ERROR;
		else if (sCommandType.equals("help"))
			return ECaCPCommandType.HELP;
		else if (sCommandType.equals("init"))
			return ECaCPCommandType.INIT;
		else if (sCommandType.equals("initialized"))
			return ECaCPCommandType.INITIALIZED;
		else if (sCommandType.equals("board"))
			return ECaCPCommandType.BOARD;
		else if (sCommandType.equals("move"))
			return ECaCPCommandType.MOVE;
		else if (sCommandType.equals("bye"))
			return ECaCPCommandType.BYE;
		else if (sCommandType.equals("exit"))
			return ECaCPCommandType.EXIT;
		else
			throw new InvalidCommandException("Ungueltiges Kommando: "
					+ sCommandType + "!");
	}

	/**
	 * Die statische Methode generiert aus einer String-Repraesentation eines
	 * Parameters ein Parameterobjekt.
	 * 
	 * @param sParameter
	 *            Die String-Repraesentation des Parameters.
	 * @return Das Parameterobjekt.
	 * @throws InvalidCommandException
	 *             Wird geworfen, falls kein gueltiger Parameter vorliegt.
	 */
	private static CaCPParameter parseStringToParameter(String sParameter)
			throws InvalidCommandException {
		try {
			// Aufteilen in Parametertyp und -wert.
			String[] parameterParts = sParameter.split("=");
			String sParameterType = parameterParts[0];
			String value = parameterParts[1];

			// Parametertyp parsen.
			ECaCPParameterType parameterType = parseStringToParameterType(sParameterType
					.toLowerCase());

			// Neuen Parameter erstellen und zurueckgeben.
			return new CaCPParameter(parameterType, value);
		} catch (InvalidCommandException e) {
			throw e;
		} catch (Exception e) {
			throw new InvalidCommandException(
					"Fehler beim Parsen eines Parameters!");
		}
	}

	/**
	 * Die statische Methode generiert aus einer String-Repraesentation eines
	 * Parametertyps einen Parametertypwert.
	 * 
	 * @param sParameterType
	 *            Die String-Repraesentation des Kommandotyps.
	 * @return Der Parametertypwert.
	 * @throws InvalidCommandException
	 *             Wird geworfen, falls kein gueltiger Parametertyp vorliegt.
	 */
	private static ECaCPParameterType parseStringToParameterType(
			String sParameterType) throws InvalidCommandException {
		if (sParameterType.equals("message"))
			return ECaCPParameterType.MESSAGE;
		else if (sParameterType.equals("xdim"))
			return ECaCPParameterType.XDIM;
		else if (sParameterType.equals("ydim"))
			return ECaCPParameterType.YDIM;
		else if (sParameterType.equals("begin"))
			return ECaCPParameterType.BEGIN;
		else if (sParameterType.equals("setup"))
			return ECaCPParameterType.SETUP;
		else if (sParameterType.equals("user"))
			return ECaCPParameterType.USER;
		else if (sParameterType.equals("row"))
			return ECaCPParameterType.ROW;
		else if (sParameterType.equals("x"))
			return ECaCPParameterType.X;
		else if (sParameterType.equals("y"))
			return ECaCPParameterType.Y;
		else if (sParameterType.equals("winner"))
			return ECaCPParameterType.WINNER;
		else
			throw new InvalidCommandException("Ungueltiger Parameter: "
					+ sParameterType + "!");
	}

	/**
	 * Die statische Methode generiert aus einem Kommandoobjekt dessen
	 * String-Repraesentation.
	 * 
	 * @param command
	 *            Das Kommandoobjekt.
	 * @return Die String-Repraesentation des Kommandos.
	 */
	public static String parseCommandToString(CaCPCommand command) {
		// Kommandotyp hinzufuegen.
		String result = command.getType().toString().toLowerCase();
		// Parameter hinzufuegen, falls vorhanden.
		if (!command.getParameters().isEmpty()) {
			result += "(";
			for (CaCPParameter parameter : command.getParameters()) {
				result += parameter.getType().toString().toLowerCase() + "="
						+ parameter.getValue() + ";";
			}
			// Letztes Semikolon durch ')' ersetzen.
			char[] sTemp = result.toCharArray();
			sTemp[sTemp.length - 1] = ')';
			result = String.valueOf(sTemp);
		}
		return result;
	}
}
