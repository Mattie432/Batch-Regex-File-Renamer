import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import org.apache.commons.io.FileUtils;
import org.jcp.xml.dsig.internal.dom.ApacheData;

import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class UserInterface {

	private JFrame frmBatchRegexFile;
	private JTextField txtRootFolder;
	protected File rootFolder;
	private JTextField txtRegex;
	JLabel lblRegexParse = new JLabel("file.ext");
	JLabel lblAfterRegexParse = new JLabel("After Regex parse: ");
	private DocumentListener docListener;
	private JButton btnStart = new JButton("Start");
	public JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.frmBatchRegexFile.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBatchRegexFile = new JFrame();
		frmBatchRegexFile.setTitle("Batch regex file renamer");
		frmBatchRegexFile.setBounds(100, 100, 535, 314);
		frmBatchRegexFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBatchRegexFile.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmBatchRegexFile.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel rootFolderPanel = new JPanel();
		panel.add(rootFolderPanel);
		rootFolderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblRootFolder = new JLabel("Root Folder:");
		rootFolderPanel.add(lblRootFolder);

		txtRootFolder = new JTextField();
		txtRootFolder.setEditable(false);
		rootFolderPanel.add(txtRootFolder);
		txtRootFolder.setColumns(25);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rootFolder = FolderChooser.getFolderChooser();
				txtRootFolder.setText(rootFolder.toString());
				checkInputRegex();
			}
		});
		rootFolderPanel.add(btnBrowse);

		JSeparator separator = new JSeparator();
		panel.add(separator);

		JPanel regexFolderPanel = new JPanel();
		panel.add(regexFolderPanel);
		regexFolderPanel.setLayout(new BoxLayout(regexFolderPanel,
				BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Regex expressions:");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		regexFolderPanel.add(label);

		JPanel panel_4 = new JPanel();
		regexFolderPanel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.WEST);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_1 = new JLabel("/d{n} : n'th directory file is in");
		lblNewLabel_1.setBorder(new EmptyBorder(0, 2, 2, 2));
		lblNewLabel_1.setSize(new Dimension(0, 18));
		lblNewLabel_1.setMinimumSize(new Dimension(182, 18));
		lblNewLabel_1.setPreferredSize(new Dimension(182, 18));
		panel_5.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("/d{...} : All directories to root");
		lblNewLabel_2.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_5.add(lblNewLabel_2);

		JLabel lblString = new JLabel(
				"\"sometext\" : String between quotations");
		lblString.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_5.add(lblString);

		JLabel lbldatecurrTodays = new JLabel("/date{curr} : Todays date");
		lbldatecurrTodays.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_5.add(lbldatecurrTodays);

		JLabel lbldatecreatedDate = new JLabel(
				"/date{created} : Date file created");
		lbldatecreatedDate.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_5.add(lbldatecreatedDate);

		JLabel lbldatemodifiedDate = new JLabel(
				"/date{modified} : Date file modified");
		lbldatemodifiedDate.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_5.add(lbldatemodifiedDate);

		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));

		JLabel lblreplacestrstrReaplace = new JLabel(
				".replace{\"str1\",\"str2\") : Reaplace occurances");
		lblreplacestrstrReaplace.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_6.add(lblreplacestrstrReaplace);

		JLabel lbltouppercaseChanges = new JLabel(
				".toUpperCase : Changes to upper case");
		lbltouppercaseChanges.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_6.add(lbltouppercaseChanges);

		JLabel lbltolowercaseChanges = new JLabel(
				".toLowerCase : Changes to lower case");
		lbltolowercaseChanges.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_6.add(lbltolowercaseChanges);

		JLabel lbltrimRemoves = new JLabel(
				".trim : Remove leading & trailing spaces");
		lbltrimRemoves.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel_6.add(lbltrimRemoves);
		
		JSeparator separator_2 = new JSeparator();
		regexFolderPanel.add(separator_2);

		JPanel panel_1 = new JPanel();
		regexFolderPanel.add(panel_1);

		JLabel lblNewLabel = new JLabel("File example:");
		panel_1.add(lblNewLabel);

		JLabel lblFileExample = new JLabel(
				"root/Subdirectory1/Subdirectory2/Subdirectory3/file.ext");
		panel_1.add(lblFileExample);

		JPanel panel_2 = new JPanel();
		regexFolderPanel.add(panel_2);

		JLabel lblRegex = new JLabel("Regex:");
		panel_2.add(lblRegex);

		docListener = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkInputRegex();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkInputRegex();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

		};

		txtRegex = new JTextField();

		txtRegex.getDocument().addDocumentListener(docListener);

		txtRegex.setText("\"image\"/date{created}");
		panel_2.add(txtRegex);
		txtRegex.setColumns(30);

		JPanel panel_3 = new JPanel();
		regexFolderPanel.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_3.add(lblAfterRegexParse);

		lblRegexParse.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(lblRegexParse);

		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1);

		JPanel optionsPanel = new JPanel();
		panel.add(optionsPanel);

		final JCheckBox chckbxSearchSubdirectories = new JCheckBox(
				"Search sub-directories");
		optionsPanel.add(chckbxSearchSubdirectories);

		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int reply = JOptionPane.showConfirmDialog(null,
						"Are you sure you wish to rename all flies in the directory: \n"
								+ txtRootFolder.getText(),
						"Confirm rename operation", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					FileRenamer fileRenamer = new FileRenamer(txtRootFolder
							.getText(), txtRegex.getText());
					fileRenamer.renameFilesInDir(chckbxSearchSubdirectories
							.isSelected(), progressBar);
				}

			}
		});

		btnStart.setEnabled(false);
		panel_7.add(btnStart, BorderLayout.EAST);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		panel_7.add(progressBar, BorderLayout.CENTER); 

		frmBatchRegexFile.pack();
	}

	private void checkInputRegex() {

		boolean result = RegexParser.parseStringIsValidExpr(txtRegex.getText(),
				true);
		if (result) {
			System.out.println(result);
			lblAfterRegexParse.setText("After regex parse: ");
			String textRootFolder = txtRootFolder.getText();
			lblRegexParse.setText(RegexInterpereter.getRenamedFilename(null,
					txtRegex.getText(), textRootFolder));

			if (textRootFolder != null && !textRootFolder.equals("")) {
				btnStart.setEnabled(true);
			}

		} else {
			System.out.println("false: " + RegexParser.getError());
			lblAfterRegexParse.setText("Error with: ");
			lblRegexParse.setText(RegexParser.getError());
			btnStart.setEnabled(false);
		}
	}
}
