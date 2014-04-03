import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is used to check that the entered regex format is valid. It
 * parses/checks the input.
 * 
 * @author Mattie432
 * 
 */
public class RegexParser {

	/**
	 * identifiers = /date, etc operators = .replace, expr = identifiers |
	 * identifiers operators
	 * 
	 * regex = expr | expr expr
	 */
	public static ArrayList<String> identifiers = new ArrayList<String>(
			Arrays.asList("/date{curr}", "/date{created}", "/d{","/date{modified}"));
	public static ArrayList<String> operators = new ArrayList<String>(
			Arrays.asList(".replace", ".toUpperCase", ".toLowerCase", ".trim"));
	private static String error = "empty regex";

	/**
	 * Parses a string accoring to the list of operators and identifiers.
	 * Returns a boolean if the string is valid or not. If it is not then the
	 * string that has the error can be viewed by the getError() method.
	 * 
	 * @param str
	 *            : String - the string to parse
	 * @param topLevel
	 *            : Boolean - should be set to true when called
	 * @return result : Boolean - if the string is valid or not.
	 */
	public static boolean parseStringIsValidExpr(String str, Boolean topLevel) {
		error = "empty regex";
		// str.trim();
		if (str.length() == 0 && topLevel == false) {
			return true;
		} else if (str.length() == 0 && topLevel == true) {
			return false;
		} else {

			char fullstopChar = ".".toCharArray()[0];
			if (str.charAt(0) == '"') {
				// check quotations closed
				int positionOfCloseQuotation = str.substring(1, str.length())
						.indexOf('"');
				if (positionOfCloseQuotation == -1) {
					error = "Quotes not closed";
					return false;
				}

				// check string for illegal chars
				String betweenQuotes = str.substring(1,
						positionOfCloseQuotation);
				Character illegalCharacter = sanatizeString(betweenQuotes);

				if (illegalCharacter != null) {
					error = "Illegal character : " + illegalCharacter;
					return false;
				}

				if (positionOfCloseQuotation != -1) {
					String substring = str.substring(
							positionOfCloseQuotation + 2, str.length());
					if (substring.length() > 0
							&& substring.charAt(0) == fullstopChar) {
						// check for operator
						return parseStringIsValidOperators(substring);
					} else {
						return parseStringIsValidExpr(substring, false);
					}
				} else {
					error = str;
					return false;
				}
			}

			for (String identifier : identifiers) {
				try {
					String extractedIdentifier = str.substring(0,
							identifier.length()).toString();
					if (extractedIdentifier.indexOf(identifier) != -1) {

						// if its the directory identifier
						if (identifier == "/d{") {
							return parseDirectory(str.substring(
									identifier.length(), str.length())
									.toString());

							// if its followed by an operator
						} else if (str
								.substring(identifier.length(), str.length())
								.toString().length() > 0
								&& str.substring(identifier.length(),
										str.length()).toString().charAt(0) == fullstopChar) {

							// check for operator
							return parseStringIsValidOperators(str.substring(
									identifier.length(), str.length())
									.toString());
						} else {
							return parseStringIsValidExpr(
									str.substring(identifier.length(),
											str.length()).toString(), false);

						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}

			error = str;
			return false;
		}
	}

	/**
	 * Checks if the next part of the string is a valid operator.
	 * 
	 * @param str
	 *            : String - the string to check
	 * @return Boolean - the result of this string.
	 */
	private static boolean parseStringIsValidOperators(String str) {
		if (str.length() > 0 && str.charAt(0) == ".".toCharArray()[0]) {
			for (String operator : operators) {
				try {
					String substring = str.substring(0, operator.length());
					if (substring.indexOf(operator) != -1) {
						if (operator == operators.get(0)) {
							// if its replace check replace is valid
							return parseReplace(str);
						}
						return parseStringIsValidExpr(
								str.substring(operator.length(), str.length()),
								false);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

			error = str;
			return false;
		} else {
			error = str;
			return false;
		}
	}

	/**
	 * Used to check the following text after the directory operator.
	 * 
	 * @param str
	 *            : String - text after /d{
	 * @return Boolean - the validity of the string
	 */
	private static boolean parseDirectory(String str) {
		if (str.length() > 0) {
			if (str.indexOf("}") != -1) {
				int index = str.indexOf("}");
				String dirNumber = str.substring(0, index);

				// check value betweek brackets
				if (isNumeric(dirNumber) || dirNumber.indexOf("...") != -1
						&& dirNumber.lastIndexOf('.') <= 2) {

					String end = str.substring(str.indexOf("}") + 1,
							str.length());

					// check if followd by operator
					if (end.length() > 0 && end.charAt(0) == '.') {

						// check for operator
						return parseStringIsValidOperators(end);
					} else {
						return parseStringIsValidExpr(end, false);
					}
				} else {
					error = str;
					return false;
				}
			} else {
				error = str;
				return false;
			}
		} else {
			error = str;
			return false;
		}
	}

	/**
	 * This checks that the contents after the replace command is valid.
	 * 
	 * @param str
	 *            : String - the string following .replace(
	 * @return Boolean - the result of the validity of the string.
	 */
	private static boolean parseReplace(String str) {

		str = str.replace(".replace(", "");
		if (str.indexOf('"') == 0) {
			str = str.substring(1, str.length());
			String quoteAndComma = "\",";
			int indexOf = str.indexOf(quoteAndComma);
			if (indexOf != -1) {
				str = str.substring(str.indexOf(',') + 1, str.length());
			} else {
				error = str;
				return false;
			}
		} else {
			error = str;
			return false;
		}

		if (str.indexOf('"') != -1) {

			int indexOf = str.indexOf('"' + ")");
			if (indexOf != -1) {
				str = str.substring(indexOf + 2, str.length());
				return parseStringIsValidExpr(str, false);
			} else {
				error = str;
				return false;
			}

		} else {
			error = str;
			return false;
		}

	}

	/**
	 * Checks if a string is a number
	 * 
	 * @param str
	 *            : String - the string to check
	 * @return Boolean - if its a number or not
	 */
	public static boolean isNumeric(String str) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the string that contains the error. This should be called after
	 * the parseStringIsValidExpression() returns false.
	 * 
	 * @return String - the string that contains the error
	 */
	public static String getError() {
		return error;
	}

	/**
	 * Checks that the string input does not contain any illegal characters. If
	 * it does then it returns the character, if there isnt then it returns
	 * null.
	 * 
	 * @param text
	 *            : String - the string to check
	 * @return char : Character - the illegal character (null if no illegal
	 *         chars);
	 */
	public static Character sanatizeString(String text) {
		Character[] illegalChars = new Character[] { '.', '/', '?', '<', '>',
				'\\', ':', '*', '|', '"', '\n', '\r', '\t', '\0', '\f', '`',
				'\"' };

		for (Character character : illegalChars) {
			if (text.indexOf(character) != -1) {
				return character;
			}
		}
		return null;
	}

}