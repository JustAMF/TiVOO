package html_output;

import org.joda.time.DateTime;

import process.Event;
import process.EventCalendar;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;



/**
 *@author Antares Yee
 */

public class SummaryPage extends HtmlPage {

    public SummaryPage(String path) {
        super(path);
    }

    
    /**
     * Create Html object with 1 week summary of events.  
     * Html object contains list of days of week/day of month, 
     * and their Events.  Each Event is hyperlinked to detail page.
     * Includes Event start and end times.
     */
    public Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
        events = events.sortByStartTime(); //sort events chronologically
        
        
        addTitleH2((events.getList().get(0).getStartTime().dayOfWeek().getAsText() + " " + events.getList().get(0).getStartTime().dayOfMonth().getAsText()), body); //add first date H2.
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = events.getList().get(0).getStartTime();
        DateTime lastCalendarDate = currentDate.plusDays(7); //last date to include in calendar
        for (Event e : events.getList()) { 
            if (e.getStartTime().dayOfMonth().equals(currentDate.dayOfMonth())) {
                addEventInfo(e, body);
            }
            else {
                //if more than 7 days after first date on calendar, finished.
                if (e.getStartTime().compareTo(lastCalendarDate) > 0) {
                    break;  
                }
                currentDate = e.getStartTime(); //currentDate ++
                
                addTitleH2(e.getStartTime().dayOfWeek().getAsText() + " " + e.getStartTime().dayOfMonth().getAsText(), body);
                addEventInfo(e, body);
            }
        }
            
        html.appendChild(body);
        return html;
    }
    
    /**
     * Add info of Event to body. Name of event is a hyperlink.
     */
    public boolean addEventInfo(Event e, Body body) {
        addEventLink(e, body);
        body.appendChild(new Br()); //add </br>
        
        addEventTime(e, body);
        body.appendChild(new Br());
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add an A object to body
     */
    private boolean addEventLink(Event e, Body body) {
        A eventNameLink = new A();
        eventNameLink.setHref(DetailPage.DETAIL_DIR_PATH + makeFileName(e));
        eventNameLink.appendChild(new Text(e.getName()));
        
        body.appendChild(eventNameLink);
        return true;
    }


    @Override
    public String getMyFileName() {
        
        return "/TiVOOSummaryPage.html";
    }
}