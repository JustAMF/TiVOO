package html_output;


import org.joda.time.DateTime;
import org.joda.time.DateTime.Property;

import process.Event;
import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;


public abstract class CalendarPage extends HtmlPage {
    DateTime myStartDate;
    DateTime myEndDate;
    String myPageTitle;
    
    public CalendarPage(String path, String pageTitle, DateTime startDate, DateTime endDate) {
        super(path);
        myStartDate = startDate;
        myEndDate = endDate;
        myPageTitle = pageTitle;
    }
    
    public Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
        
        addTitleH2(myPageTitle, body);
        
        //add events to calendar
        addCalendarEvents(events, body);
        
        html.appendChild(body);
        return html;
    }
    
    public boolean addCalendarEvents(EventCalendar events, Body body) {
        EventCalendar sortedEvents = events.sortByStartTime().eventsBetweenTimes(myStartDate, myEndDate);
       
        if (sortedEvents.getList().size() == 0) { //if no events in timeframe
            System.out.println("No events in this timeframe.");
            return false;
        }
        
        //add first date H2.
        String dayOfWeekFirstEvent = sortedEvents.getList().get(0).getStartTime().dayOfWeek().getAsText();
        String dayOfMonthFirstEvent = sortedEvents.getList().get(0).getStartTime().dayOfMonth().getAsText();
        addTitleH2((dayOfWeekFirstEvent + " " + dayOfMonthFirstEvent), body); 
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = sortedEvents.getList().get(0).getStartTime();
        
        for (Event e : sortedEvents.getList()) {
            Property eventDayOfMonth = e.getStartTime().dayOfMonth();
            Property eventMonthOfYear = e.getStartTime().monthOfYear();
            
            if (eventDayOfMonth.equals(currentDate.dayOfMonth()) && eventMonthOfYear.equals(currentDate.monthOfYear())) {
                addEventInfo(e, body);
            }
            else {
                currentDate = e.getStartTime(); //currentDate ++
                
                String strEventDayOfWeek = e.getStartTime().dayOfWeek().getAsText();
                String strEventDayOfMonth = e.getStartTime().dayOfMonth().getAsText();
                addTitleH2(strEventDayOfWeek + " " + strEventDayOfMonth, body);
                addEventInfo(e, body);
            }
        }
        return true;
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOCalendarPage.html";
    }

}
