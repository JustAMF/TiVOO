package process;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class EventsWithinTimeFilter implements CalendarFilter{
	Interval myInterval;
	
	EventsWithinTimeFilter(DateTime start, DateTime end){
		myInterval = new Interval(start,end);
	}

	@Override
	public boolean filter(Event e) {
		return (myInterval.contains(e.getStartTime()) || myInterval.contains(e.getEndTime()));
	}
}
