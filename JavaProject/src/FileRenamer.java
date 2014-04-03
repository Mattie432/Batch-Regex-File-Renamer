import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;


public class FileRenamer {

	String root = "";
	String regex = "";
	public FileRenamer(String root, String regex){
		this.root = root;
		this.regex = regex;
	}
	
	public void renameFilesInDir(){
		File f = new File(root);
		Iterator<File> files = FileUtils.iterateFiles(f, null, true);
		
		while (files.hasNext()) {
			File file = files.next();
			
			String newFileNamme = RegexInterpereter.getRenamedFilename(file, regex, root);
			System.out.println(newFileNamme);
			files.remove();
		}
	}
	
	public static void RenameFile(File file){
		
	}
	
}
