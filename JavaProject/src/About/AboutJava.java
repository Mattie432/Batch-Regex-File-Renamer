package About;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class AboutJava extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					AboutJava frame = new AboutJava();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AboutJava() {
		setType(Type.UTILITY);
		setTitle("About");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtpnSoftwareCreatedAnd = new JTextArea();
		txtpnSoftwareCreatedAnd.setBackground(UIManager.getColor("menu"));
		txtpnSoftwareCreatedAnd.setWrapStyleWord(true);
		txtpnSoftwareCreatedAnd.setLineWrap(true);
		txtpnSoftwareCreatedAnd.setAutoscrolls(false);
		txtpnSoftwareCreatedAnd.setEditable(false);
		txtpnSoftwareCreatedAnd.setText("Software created and coded my Mattie432. \u00A92014\r\n\r\nThe free software programs provided by Mattie432 may be freely distributed, provided that no charge above the cost of distribution is levied, and that the disclaimer below is always attached to it. The programs are provided as is without any guarantees or warranty.\r\n\r\nAlthough the author has attempted to find and correct any bugs in the free software programs, the author is not responsible for any damage or losses of any kind caused by the use or misuse of the programs. The author is under no obligation to provide support, service, corrections, or upgrades to the free software programs.\r\n");
		panel_1.add(txtpnSoftwareCreatedAnd, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JSeparator separator = new JSeparator();
		panel_2.add(separator);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		
		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4);
		
		JLabel lblNewLabel = new JLabel("<html>\r\nPlease feel free to conatct<br>\r\nme on the following media:<br>\r\n<br>\r\nOpen source software can be<br>\r\nfound on my GitHub<br>\r\n\r\n</html>");
		panel_4.add(lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		panel_5.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JLabel lblTwitter = new JLabel("<html>\r\n<a href=\"https://twitter.com/mattie432\">https://twitter.com/mattie432</a>\r\n</html>");
		panel_3.add(lblTwitter);
		lblTwitter.setBorder(new EmptyBorder(0, 0, 2, 0));
		lblTwitter.setIcon(new ImageIcon(AboutJava.class.getResource("/About/images/twitter.png")));
		lblTwitter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblGithub = new JLabel("<html>\r\n<a href=\"https://github.com/Mattie432\">https://github.com/Mattie432</a>\r\n</html>");
		panel_3.add(lblGithub);
		lblGithub.setBorder(new EmptyBorder(0, 0, 2, 0));
		lblGithub.setIcon(new ImageIcon(AboutJava.class.getResource("/About/images/Apps-github-icon.png")));
		lblGithub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblFacebook = new JLabel("<html>\r\n<a href=\"https://www.facebook.com/Mattie432\">https://www.facebook.com/Mattie432</a>\r\n</html>");
		panel_3.add(lblFacebook);
		lblFacebook.setBorder(new EmptyBorder(0, 0, 2, 0));
		lblFacebook.setIcon(new ImageIcon(AboutJava.class.getResource("/About/images/facebook-icon.png")));
		lblFacebook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblWebsite = new JLabel("<html>\r\n<a href=\"https://mattie432.com\">https://mattie432.com</a>\r\n</html>");
		panel_3.add(lblWebsite);
		lblWebsite.setBorder(new EmptyBorder(0, 0, 2, 0));
		lblWebsite.setIcon(new ImageIcon(AboutJava.class.getResource("/About/images/Web-HTML-icon.png")));
		lblWebsite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblEmail = new JLabel("<html>\r\n<a href=\"\">mattie432@icloud.com</a>\r\n</html>");
		panel_3.add(lblEmail);
		lblEmail.setBorder(new EmptyBorder(0, 0, 2, 0));
		lblEmail.setIcon(new ImageIcon(AboutJava.class.getResource("/About/images/Mail-icon.png")));
		lblEmail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblWebsite.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() > 0) {
		    	  if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                try {
		                		URI uri = new URI("https://mattie432.com");
		                        desktop.browse(uri);
		                } catch (IOException ex) {
		                        // do nothing
		                } catch (URISyntaxException ex) {
							//do nothing
						}
		        } else {
		               //do nothing
		        }

		      }
		   }
		});
		lblFacebook.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() > 0) {
		    	  if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                try {
		                		URI uri = new URI("https://www.facebook.com/Mattie432");
		                        desktop.browse(uri);
		                } catch (IOException ex) {
		                        // do nothing
		                } catch (URISyntaxException ex) {
							//do nothing
						}
		        } else {
		               //do nothing
		        }

		      }
		   }
		});
		lblGithub.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() > 0) {
		    	  if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                try {
		                		URI uri = new URI("https://github.com/Mattie432");
		                        desktop.browse(uri);
		                } catch (IOException ex) {
		                        // do nothing
		                } catch (URISyntaxException ex) {
							//do nothing
						}
		        } else {
		               //do nothing
		        }

		      }
		   }
		});
		lblTwitter.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() > 0) {
			    	  if (Desktop.isDesktopSupported()) {
			                Desktop desktop = Desktop.getDesktop();
			                try {
			                		URI uri = new URI("https://twitter.com/mattie432");
			                        desktop.browse(uri);
			                } catch (IOException ex) {
			                        // do nothing
			                } catch (URISyntaxException ex) {
								//do nothing
							}
			        } else {
			               //do nothing
			        }

			      }
			   }
			});
		lblEmail.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() > 0) {
			    	  if (Desktop.isDesktopSupported()) {
			                Desktop desktop = Desktop.getDesktop();
			                try {
			                		URI uri = new URI("mailto:mattie432@icloud.com");
			                        desktop.browse(uri);
			                } catch (IOException ex) {
			                        // do nothing
			                } catch (URISyntaxException ex) {
								//do nothing
							}
			        } else {
			               //do nothing
			        }

			      }
			   }
			});
		

		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		
	}
	
}
