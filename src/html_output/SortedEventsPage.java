package html_output;

import process.Event;
import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;


public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(String path) {
        super(path);
    }
    
    public Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
   
        addTitleH2("Sorted Events", body);     
        for (Event e : events.getList()) {
            addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOSortedEventsPage.html";
    }

}
