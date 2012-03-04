import html_output.ConflictingEventsPage;
import html_output.DayCalendarPage;
import html_output.DetailPage;
import html_output.MonthCalendarPage;
import html_output.SortedEventsPage;
import html_output.SummaryPage;
import html_output.WeekCalendarPage;


import org.joda.time.DateTime;

import controller.TivooSystem;

import process.EventCalendar;

public class Main {
	
	public static void main (String args[]) {
		TivooSystem s = new TivooSystem();
		s.loadCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/dukecal.xml");
		//s.loadCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");
		//s.loadCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/DukeBasketBall.xml");
		//s.loadCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/NFL.xml");
		//s.loadCal("tv.xml");
		//s.setMyEvents(s.getEventCalendar().filterByName("Jets"));
		//s.outputHtmlPage(new SummaryPage(System.getProperty("user.home") + "/Desktop/"));
		//s.outputHtmlPage(new DetailPage(System.getProperty("user.home") + "/Desktop/"));
		s.outputHtmlPage(new SortedEventsPage(System.getProperty("user.home") + "/Desktop/"));
		//s.outputHtmlPage(new ConflictingEventsPage(System.getProperty("user.home") + "/Desktop/"));
		//s.outputHtmlPage(new DayCalendarPage(System.getProperty("user.home") + "/Desktop/", new DateTime(2012, 1, 16, 0, 0)));
		//s.outputHtmlPage(new WeekCalendarPage(System.getProperty("user.home") + "/Desktop/", new DateTime(2012, 1, 16, 0, 0)));
		//s.outputHtmlPage(new MonthCalendarPage(System.getProperty("user.home") + "/Desktop/", new DateTime(2012, 1, 16, 0, 0)));
		//new DateTime(2011, 9, 17, 0, 0)
		//System.out.println(s.myEvents.toString());
		//DateTime dt=new DateTime("2011-11-16T13:30:00");
		//s.filterByTime(dt);
	}
}
