package html_output;

import org.joda.time.DateTime;

public class MonthCalendarPage extends CalendarPage {
    public static final String PAGE_TITLE = "Month Calendar Page";
    
    public MonthCalendarPage(String path, DateTime startDate) {
        super(path, PAGE_TITLE, startDate, startDate.plusMonths(1));
        myStartDate = startDate;
        
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOmonthCalendarPage.html";
    }

}
