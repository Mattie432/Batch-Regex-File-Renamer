import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


public class FileRenamer {

	String root = "";
	String regex = "";
	@SuppressWarnings("rawtypes")
	ArrayList<Pair> directoryCount = new ArrayList<Pair>();
	
	
	public FileRenamer(String root, String regex){
		this.root = root;
		this.regex = regex;
	}
	
	public void renameFilesInDir(Boolean includeSubFolders){
		File f = new File(root);
		Iterator<File> files = FileUtils.iterateFiles(f, null, includeSubFolders);
		
		while (files.hasNext()) {
			File file = files.next();
			
			String newFileName = RegexInterpereter.getRenamedFilename(file, regex, root);
			
			String directory = file.getParent().replace(root, "");
			int count = nextDirCount(directory);
			
			//System.out.println(newFileNamme + count);
			String extension = FilenameUtils.getExtension(file.getAbsolutePath());
			String newFilePath = file.getParent() + '\\' + newFileName + count + "." + extension;
			System.err.println(newFilePath);
			//file.renameTo(new File(newFilePath));
			
			files.remove();
		}
	}
	
	public static void RenameFile(File file){
		
	}
	
	/**
	 * Gets the next count for the directory. If no directory exists then it will
	 * be added to the list and will have a count created for it.
	 * @param dir : String - the files directory
	 * @return count : Int - the count to append to the filename
	 */
	private int nextDirCount(String dir){
	
		for(@SuppressWarnings("rawtypes") Pair pair : directoryCount){
			if(pair.getLeft().equals(dir)){
				pair.setRight(pair.getRight() +1);
				return pair.getRight();
			}
		}
		
		//include new dir
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Pair newPair = new Pair(dir, 1);
		directoryCount.add(newPair);
		return newPair.getRight();
	
	}
	
}
