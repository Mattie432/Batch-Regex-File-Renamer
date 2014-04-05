import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileRenamer {

	int countIterators = 0;
	int maxSize = 0;

	String root = "";
	String regex = "";
	@SuppressWarnings("rawtypes")
	ArrayList<Pair> directoryCount = new ArrayList<Pair>();

	/**
	 * Constructor for the class.
	 * 
	 * @param root : String - the root directory
	 * @param regex : String - the regex to use
	 */
	public FileRenamer(String root, String regex) {
		this.root = root;
		this.regex = regex;
	}

	/**
	 * Starts the renaming process in the root directory.
	 * 
	 * @param includeSubFolders : Boolean - search through subfolders as well
	 * @param progressBar : JProgressBar - the progressbar to update (can be null)
	 */
	public void renameFilesInDir(Boolean includeSubFolders,
			final JProgressBar progressBar) {
		progressBar.setValue(0);
		File f = new File(root);
		Iterator<File> files = FileUtils.iterateFiles(f, null,
				includeSubFolders);

		Iterator<File> temp = FileUtils
				.iterateFiles(f, null, includeSubFolders);
		while (temp.hasNext()) {
			maxSize++;
			temp.next();
			temp.remove();
		}

		while (files.hasNext()) {
			File file = files.next();

			String newFileName = RegexInterpereter.getRenamedFilename(file,
					regex, root);

			String directory = file.getParent().replace(root, "");
			int count = nextDirCount(directory);

			// System.out.println(newFileNamme + count);
			String extension = FilenameUtils.getExtension(file
					.getAbsolutePath());
			String newFilePath = file.getParent() + '\\' + newFileName + count
					+ "." + extension;
			System.err.println(newFilePath);
			file.renameTo(new File(newFilePath));

			files.remove();
			countIterators++;
			if (progressBar != null && maxSize != 0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						int value = getValue();
						progressBar.setValue(value);
					}
				});
			}
		}
	}

	/**
	 * Calculates the percentage completeion
	 * @return value : Int - percentage complete
	 */
	private int getValue() {
		return (countIterators / maxSize) * 100;
	}

	/**
	 * Gets the next count for the directory. If no directory exists then it
	 * will be added to the list and will have a count created for it.
	 * 
	 * @param dir
	 *            : String - the files directory
	 * @return count : Int - the count to append to the filename
	 */
	private int nextDirCount(String dir) {

		for (@SuppressWarnings("rawtypes")
		Pair pair : directoryCount) {
			if (pair.getLeft().equals(dir)) {
				pair.setRight(pair.getRight() + 1);
				return pair.getRight();
			}
		}

		// include new dir
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Pair newPair = new Pair(dir, 1);
		directoryCount.add(newPair);
		return newPair.getRight();

	}

}
