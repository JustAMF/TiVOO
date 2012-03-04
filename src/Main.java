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
import view.TiVOOViewer;

public class Main {
	
	public static void main (String args[]) {
		TiVOOViewer tvv = new TiVOOViewer("TiVOO");

		tvv.setModel(new TivooSystem());
	}
}
