package process;

import org.joda.time.DateTime;

public class TimeFilter implements CalendarFilter{
	DateTime myTime;
	
	TimeFilter(DateTime time){
		myTime = time;
	}

	@Override
	public boolean filter(Event e) {
		return e.getInterval().contains(myTime);
	}

}
