import java.io.File;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;

public class RegexInterpereter {

	public static String getRenamedFilename(File file, String regex) {
		String fileName = "";

		while (regex.length() > 0) {
			String regexItem = getNextItem(regex);
			regex = regex.replace(regexItem, "");
			String parseNextItem = parseNextItem(null, regexItem);
			fileName += parseNextItem;
		}

		return fileName;
	}

	public static String getNextItem(String str) {

		String returnStr = "";
		if (str.length() > 0) {
			if (str.charAt(0) == '"') {
				// check for string start
				int indexOf = str.indexOf('"', 1);
				String subStrString = str.substring(0, indexOf + 1);
				returnStr += subStrString;
				if (str.length() > indexOf) {
					returnStr = checkNextItemForOperator(str, returnStr, subStrString);
				}
			}

			for (String item : RegexParser.identifiers) {
				if (str.length() >= item.length()) {
					String subStrIdentifier = (String) str.subSequence(0,
							item.length());
					if (subStrIdentifier.indexOf(item) != -1) {

						if (subStrIdentifier.indexOf("/d{") != -1) {
							// its a directory operator
							CharSequence subSequence = str.subSequence(0,
									str.indexOf('}') + 1);
							returnStr += subSequence;
							returnStr = checkNextItemForOperator(str, returnStr,
									subSequence.toString());
						} else {
							returnStr += item;
							returnStr = checkNextItemForOperator(str, returnStr, item);
						}
					}

				}
			}

		}
		return returnStr;
	}

	private static String parseNextItem(File file, String item) {
		String returnStr = "";
		if (item.charAt(0) == '"') {
			// string
			returnStr += item.substring(1, item.indexOf('"', 1));
			try {
				returnStr = parseItemsOperator(returnStr, item);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return returnStr;

		}

		for (String identifier : RegexParser.identifiers) {
			if (item.indexOf(identifier) != -1) {
				if (identifier == "/d{") {
					// directory
					if (item.contains("...")) {
						// all dirs
					} else {
						// one dir
					}
					// TODO dir
				} else if (identifier == "/date{curr}") {

				} else if (identifier == "/date{created}") {

				} else if (identifier == "/date{modified}") {

				}
			}
		}
		return returnStr;
	}

	private static String checkNextItemForOperator(String str, String returnStr,
			String identifier) {
	
		// check for operators
		int strLength = str.length();
		int identifierLength = identifier.length();
	
		if (strLength >= identifierLength + 1) {
			char charAt = str.charAt(identifierLength);
			if (charAt == '.') {
				for (String operator : RegexParser.operators) {
					int operatorLength = operator.length();
					if (strLength >= identifierLength + operatorLength) {
						String subStrOperator = str.substring(identifierLength,
								identifierLength + operatorLength);
						if (operator.indexOf(subStrOperator) != -1) {
	
							if (operator == ".replace") {
								// if its replace
								int endPos = identifierLength + operatorLength;
								returnStr += str.substring(identifierLength,
										str.indexOf(")", endPos) + 1);
							} else {
								returnStr += subStrOperator;
							}
						}
					}
				}
			}
		}
		return returnStr;
	}

	private static String parseItemsOperator(String returnStr,
			String itemOriginal) throws Exception {
		int indexOf = itemOriginal.indexOf('.');
		if (indexOf != -1) {
			String item = itemOriginal
					.substring(indexOf, itemOriginal.length());
			if (item.contains(".trim")) {
				return returnStr.trim();
			} else if (item.contains(".toUpperCase")) {
				return returnStr.toUpperCase();
			} else if (item.contains(".toLowerCase")) {
				return returnStr.toLowerCase();
			} else if (item.contains(".replace")) {

				int startIndex = item.indexOf("(")+2;
				System.out.println(item.charAt(startIndex));
				int endIndex = item.indexOf('"', startIndex+2);
				System.out.println(item.charAt(endIndex));
				String str1 = item.substring(startIndex, endIndex);
				
				int startIndex2 = item.indexOf(',') + 2;
				int endIndex2 = item.indexOf(")") - 1;
				String str2 = item.substring(startIndex2,
						endIndex2);
				String returnStrNew = returnStr.replace(str1, str2);
				return returnStrNew;
			} else {
				throw new Exception();
			}
		}else{
			return returnStr;
		}
	}
}
