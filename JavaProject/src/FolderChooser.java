import java.io.File;

import javax.swing.JFileChooser;

/**
 * Folder chooser for selecting a folder using the JFileChooser dialogue.
 * 
 * @author Mattie432
 * 
 */
public class FolderChooser {

	private static JFileChooser chooser;
	private static String choosertitle = "Choose a root folder";

	/**
	 * Asks the user to select a folder.
	 * 
	 * @return folder : File - folder selected (returns null if none selected)
	 */
	public static File getFolderChooser() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else {
			return null;
		}
	}

}
