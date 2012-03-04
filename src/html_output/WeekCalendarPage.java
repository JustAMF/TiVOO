package html_output;

import org.joda.time.DateTime;

public class WeekCalendarPage extends CalendarPage {
    public static final String PAGE_TITLE = "Week Calendar Page";
    
    public WeekCalendarPage(String path, DateTime startDate) {
        super(path, PAGE_TITLE, startDate, startDate.plusWeeks(1));
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOweekCalendarPage.html";
    }

}
