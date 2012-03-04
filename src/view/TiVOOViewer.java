package view;

import html_output.*;
import controller.TivooSystem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TiVOOViewer extends JFrame {

	private File myFile;
	private TivooSystem myTVS;
	private JTextField myMessage;
	private JEditorPane pane;
	private JPanel panel;
	private JTextField myInstruction;
	private String myString;

	protected static JFileChooser ourChooser = new JFileChooser(System
			.getProperties().getProperty("user.dir"));

	public TiVOOViewer(String title) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = (JPanel) getContentPane();
		panel.setLayout(new BorderLayout());
		panel.add(makeMessage(), BorderLayout.SOUTH);
		setTitle(title);
		makeMenus();
		pack();

		setSize(600, 400);
		setVisible(true);
		showMessage("Welcome to TiVOO! Please load XML files from File menu");
	}

	public void setModel(TivooSystem TVS) {
		myTVS = TVS;
	}

	private JPanel makeMessage() {
		JPanel p = new JPanel(new BorderLayout());
		myMessage = new JTextField(30);
		p.setBorder(BorderFactory.createTitledBorder("message"));
		p.add(myMessage, BorderLayout.CENTER);
		return p;
	}

	private JPanel makeInstruction() {
		JPanel p = new JPanel(new BorderLayout());
		myInstruction = new JTextField(30);
		p.setBorder(BorderFactory.createTitledBorder("Instruction"));
		p.add(myInstruction, BorderLayout.CENTER);
		return p;
	}

	private void makeMenus() {
		JMenuBar bar = new JMenuBar();
		bar.add(makeFileMenu());
		bar.add(makeHelpMenu());
		setJMenuBar(bar);
	}

	private JMenu makeHelpMenu() {
		
		JMenu helpMenu = new JMenu("Help");
		
		helpMenu.add(new AbstractAction("Guide") {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(TiVOOViewer.this,
						"Please refer to the README file for detail", "Guide",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		helpMenu.add(new AbstractAction("About") {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(TiVOOViewer.this,
						"This is a course project of CS108 at Duke, Spring 2012", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		return helpMenu;
	}

	private JMenu makeFileMenu() {

		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new AbstractAction("Load File") {
			public void actionPerformed(ActionEvent ev) {
				doRead();
			}
		});

		fileMenu.add(makeFilterMenu());

		fileMenu.add(new AbstractAction("Preview Webpage") {
			public void actionPerformed(ActionEvent ev) {
				try {
					doPreview();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					JOptionPane.showMessageDialog(TiVOOViewer.this,
							e.getMessage(), "No event left!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		fileMenu.add(new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});

		return fileMenu;
	}
	
	private JMenu makeFilterMenu(){
		JMenu filterMenu = new JMenu("SelectFilter");
		
		filterMenu.add(new AbstractAction("Find Keyword In Title") {
			public void actionPerformed(ActionEvent ev) {
				doKeywordInTitleProcess();
			}
		});
		filterMenu.add(new AbstractAction("Find Keyword In Description") {
			public void actionPerformed(ActionEvent ev) {
				doKeywordInDescriptionProcess();
			}
		});

		filterMenu.add(new AbstractAction("Find Events at Time") {
			public void actionPerformed(ActionEvent ev) {
				doTimeProcess();
			}
		});
		filterMenu.add(new AbstractAction("Find Events between Time") {
			public void actionPerformed(ActionEvent ev) {
				doPeriodProcess();
			}
		});
		filterMenu.add(new AbstractAction("Reverse Events by Time") {
			public void actionPerformed(ActionEvent ev) {
				doReverseProcess();
			}
		});
		
		return filterMenu;
	}
	

	private void doKeywordInTitleProcess() {

		myString = JOptionPane.showInputDialog("Input your keyword");
		myTVS.setMyEvents(myTVS.getEventCalendar().filterByName(myString));

		showMessage("Please press Preview Webpage button to preview the page");
	}

	private void doKeywordInDescriptionProcess() {
		myString = JOptionPane.showInputDialog("Input your keyword");
		myTVS.setMyEvents(myTVS.getEventCalendar().searchCalendar(
				"description", myString));
		showMessage("Please press Preview Webpage button to preview the page");
	}

	private void doReverseProcess() {
		myTVS.setMyEvents(myTVS.getEventCalendar().reverse());
		showMessage("Please press Preview Webpage button to preview the page");
	}

	private void doPeriodProcess() {
		try {
			myString = JOptionPane
					.showInputDialog("Input your start and end time");
			String[] times = myString.split(" to ");
			DateTimeFormatter dtf = DateTimeFormat
					.forPattern("yyyyMMdd'T'HHmmss");
			DateTime start = dtf.parseDateTime(times[0]);
			DateTime end = dtf.parseDateTime(times[1]);
			myTVS.setMyEvents(myTVS.getEventCalendar().eventsBetweenTimes(
					start, end));
			
			showMessage("Please press Preview Webpage button to preview the page");

		} catch (IllegalFieldValueException e) {
			JOptionPane.showMessageDialog(TiVOOViewer.this, e.getMessage(),
					"Input Time Format does not match!",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(TiVOOViewer.this, e.getMessage(),
					"Please input your time!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void doTimeProcess() {

		try {
			myString = JOptionPane
					.showInputDialog("Input a specific time");
			DateTimeFormatter dtf = DateTimeFormat
					.forPattern("yyyyMMdd'T'HHmmss");
			DateTime t = dtf.parseDateTime(myString);
			myTVS.setMyEvents(myTVS.getEventCalendar().eventsAtTime(t));

			showMessage("Please press Preview Webpage button to preview the page");
		} catch (IllegalFieldValueException e) {
			JOptionPane.showMessageDialog(TiVOOViewer.this, e.getMessage(),
					"Input Time Format does not match!",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(TiVOOViewer.this, e.getMessage(),
					"Please input your time!", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void doPreview() throws IOException {

		pane = new JEditorPane();// create a new pane
		getContentPane().setLayout(new BorderLayout());
		// make it scroll
		getContentPane().add(new JScrollPane(pane), BorderLayout.CENTER);

		pane.setEditable(false);
		pane.setPreferredSize(new Dimension(800, 600));
		pane.addHyperlinkListener(new LinkFollower());
		pack();
		String address = myTVS.outputHtmlPage(new SortedEventsPage(System
				.getProperty("user.home") + "/Desktop/"));
		// use the address to create a file object
		File out = new File(address);
		
		// set page using HtmlPage's address.								
		pane.setPage(out.toURI().toString());
		setVisible(true);

	}

	private class LinkFollower implements HyperlinkListener {
		public void hyperlinkUpdate(HyperlinkEvent evt) {
			if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				// user clicked a link, load it and show it
				try {
					pane.setPage((evt.getURL().toString()));
				} catch (Exception e) {
					String s = evt.getURL().toString();
					JOptionPane.showMessageDialog(TiVOOViewer.this,
							"loading problem for " + s + " " + e,
							"Load Problem", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private File doRead() {

		int retval = ourChooser.showOpenDialog(null);
		if (retval != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		myFile = ourChooser.getSelectedFile();
		myTVS.loadCal(myFile.getPath());

		showMessage("Please use one or more filters to filter events or load more XML files from File menu");
		return myFile;

	}

	/**
	 * Display a text message in the view (e.g., in the small text area at the
	 * bottom of the GUI), thus a modeless message the user can ignore.
	 * @param s  is the message displayed
	 */
	public void showMessage(String s) {
		myMessage.setText(s);
	}

	public void showInstruction(String s) {
		myInstruction.setText(s);
	}

	public static void main(String[] args) {

		TiVOOViewer tvv = new TiVOOViewer("TiVOO");

		tvv.setModel(new TivooSystem());

	}

}
