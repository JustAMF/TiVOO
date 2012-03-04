package html_output;

import org.joda.time.DateTime;

public class DayCalendarPage extends CalendarPage {
    public static final String PAGE_TITLE = "Day Calendar Page";
    
    public DayCalendarPage(String path, DateTime startDate) {
        super(path, PAGE_TITLE, startDate, startDate.plusDays(1));
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOdayCalendarPage.html";
    }

}
