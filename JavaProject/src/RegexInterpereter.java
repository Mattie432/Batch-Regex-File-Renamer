import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;

public class RegexInterpereter {

	public static String getRenamedFilename(File file, String regex, String root) {
		String fileName = "";

		while (regex.length() > 0) {
			String regexItem = getNextItem(regex);
			regex = regex.replace(regexItem, "");
			String parseNextItem = parseNextItem(file, regexItem, root);
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
					returnStr = checkNextItemForOperator(str, returnStr,
							subStrString);
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
							returnStr = checkNextItemForOperator(str,
									returnStr, subSequence.toString());
						} else {
							returnStr += item;
							returnStr = checkNextItemForOperator(str,
									returnStr, item);
						}
					}

				}
			}

		}
		return returnStr;
	}

	private static String parseNextItem(File file, String item, String root) {
		String returnStr = "";

		if (item.length() > 0) {
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
							if (file == null) {
								// example file
								returnStr += "Directory3_Directory2_Directory1";
								return returnStr;
							} else {
								// real file
								String parent = file.getParent() + '\\';
								String temp = parent.replace(root + '\\', "");
								temp = temp.replace('\\', '_');
								int indexOf = temp.indexOf('_');
								int length = temp.length();
								if(temp.indexOf('_') != -1 && indexOf == length-1){
									temp = temp.substring(0, length -1);
								}
								returnStr += temp;
								return returnStr;
							}
						} else {
							// one dir
							if (file == null) {
								// example
								String temp = "/Directory1/Directory2/Directory3/";
								int indexOf1 = item.indexOf("{") + 1;
								int indexOf2 = item.indexOf("}");
								String substring = item.substring(indexOf1,
										indexOf2);
								int dirNum = Integer.parseInt(substring);

								while (dirNum > 1) {
									if (temp.lastIndexOf("/") != -1
											&& temp.lastIndexOf("/") == temp
													.length() - 1) {
										// remove final / if its at the end
										temp = temp.substring(0,
												temp.lastIndexOf("/"));
									}

									if (temp.lastIndexOf("/") != -1) {
										// remove last dir
										temp = temp.substring(0,
												temp.lastIndexOf("/"));
									}
									dirNum -= 1;
								}

								if (temp.lastIndexOf("/") != -1
										&& temp.lastIndexOf("/") == temp
												.length() - 1) {
									// remove final / if its at the end
									temp = temp.substring(0,
											temp.lastIndexOf("/"));
								}
								temp = temp.substring(
										temp.lastIndexOf("/") + 1,
										temp.length());
								
								if(temp.indexOf('_') != -1 && temp.indexOf('_') == temp.length()-1){
									temp = temp.substring(0, temp.length() -1);
								}

								returnStr += temp;
								return returnStr;

							} else {
								// file
								String parent = file.getParent() + '\\';
								String temp = parent.replace(root + "\\", "");

								int indexOf1 = item.indexOf("{") + 1;
								int indexOf2 = item.indexOf("}");
								String substring = item.substring(indexOf1,
										indexOf2);
								int dirNum = Integer.parseInt(substring);

								while (dirNum > 1) {
									if (temp.lastIndexOf("/") != -1
											&& temp.lastIndexOf("/") == temp
													.length() - 1) {
										// remove final / if its at the end
										temp = temp.substring(0,
												temp.lastIndexOf("/"));
									}

									if (temp.lastIndexOf("/") != -1) {
										// remove last dir
										temp = temp.substring(0,
												temp.lastIndexOf("/"));
									}
									dirNum -= 1;
								}

								if (temp.lastIndexOf("/") != -1
										&& temp.lastIndexOf("/") == temp
												.length() - 1) {
									// remove final / if its at the end
									temp = temp.substring(0,
											temp.lastIndexOf("/"));
								}
								temp = temp.substring(
										temp.lastIndexOf("/") + 1,
										temp.length());
								temp = temp.replace('\\', '_');
								
								if(temp.indexOf('_') != -1 && temp.indexOf('_') == temp.length()-1){
									temp = temp.substring(0, temp.length() -1);
								}
								returnStr += temp;
								
								return returnStr;

							}
						}
					} else if (identifier == "/date{curr}") {
						DateFormat dateFormat = new SimpleDateFormat(
								"dd-MM-YYYY");
						Date date = new Date();
						returnStr += dateFormat.format(date);
						return returnStr;
					} else if (identifier == "/date{created}") {

						if (file == null) {
							// example
							returnStr += "01-02-03";
							return returnStr;
						} else {
							Path p = Paths.get(file.getPath());
							BasicFileAttributes view;
							try {
								view = Files.getFileAttributeView(p,
										BasicFileAttributeView.class,
										LinkOption.NOFOLLOW_LINKS)
										.readAttributes();
								String dateTime = view.creationTime()
										.toString();
								dateTime = dateTime.substring(0,
										dateTime.indexOf("T"));

								// Convert to local format
								DateFormat outputDateFormat = new SimpleDateFormat();

								DateFormat inputDateFormat = new SimpleDateFormat(
										"yyyy-MM-dd");

								String formattedDate = outputDateFormat
										.format(inputDateFormat.parse(dateTime));
								formattedDate = formattedDate.substring(0,
										formattedDate.indexOf(" "));
								formattedDate = formattedDate.replace("/", "-");

								//System.out.println(formattedDate);

								returnStr += formattedDate;
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}

						}

					} else if (identifier == "/date{modified}") {

						if (file == null) {
							// example
							returnStr += "04-05-06";
							return returnStr;
						} else {

							Path p = Paths.get(file.getPath());
							BasicFileAttributes view;
							try {
								view = Files.getFileAttributeView(p,
										BasicFileAttributeView.class,
										LinkOption.NOFOLLOW_LINKS)
										.readAttributes();
								String dateTime = view.lastModifiedTime()
										.toString();
								dateTime = dateTime.substring(0,
										dateTime.indexOf("T"));

								// Convert to local format
								DateFormat outputDateFormat = new SimpleDateFormat();

								DateFormat inputDateFormat = new SimpleDateFormat(
										"yyyy-MM-dd");

								String formattedDate = outputDateFormat
										.format(inputDateFormat.parse(dateTime));
								formattedDate = formattedDate.substring(0,
										formattedDate.indexOf(" "));
								formattedDate = formattedDate.replace("/", "-");
								//System.out.println(formattedDate);

								returnStr += formattedDate;
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}

						}

					}
				}
			}
		}
		return returnStr;
	}

	private static String checkNextItemForOperator(String str,
			String returnStr, String identifier) {

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

				int startIndex = item.indexOf("(") + 2;
				System.out.println(item.charAt(startIndex));
				int endIndex = item.indexOf('"', startIndex + 2);
				System.out.println(item.charAt(endIndex));
				String str1 = item.substring(startIndex, endIndex);

				int startIndex2 = item.indexOf(',') + 2;
				int endIndex2 = item.indexOf(")") - 1;
				String str2 = item.substring(startIndex2, endIndex2);
				String returnStrNew = returnStr.replace(str1, str2);
				return returnStrNew;
			} else {
				throw new Exception();
			}
		} else {
			return returnStr;
		}
	}
}
