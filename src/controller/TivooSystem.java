package controller;
import process.EventCalendar;
import html_output.HtmlPage;

import xmlParse.ParsingException;
import xmlParse.XmlParser;



public class TivooSystem {
	
	private EventCalendar myEvents;
	
	public TivooSystem(){
		myEvents = new EventCalendar();
	}

	public void setMyEvents(EventCalendar ec) {
	    myEvents = ec;
	}
	
	/**
	 * Loads a  cal file into the Tivoo Systems
	 */
	public void loadCal(String link){
		try {
			myEvents.addAll(new XmlParser(link).loadAndParse());
		} catch (ParsingException e) {
			System.err.println(e.getMessage());
			System.err.println("File \"" + link + "\" could not be loaded");
		}
	}
	
	public EventCalendar getEventCalendar(){
		return myEvents;
	}
	

	public String outputHtmlPage(HtmlPage page) {
	    if (myEvents.getList().isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    return page.createHTMLpage(myEvents);
	    
	    //TODO: update sorting in HtmlPage to use EventCalendar.  Also make makeHtmlOutput() accept EventCalendar instead of List<Event> events.
	}
	
}
