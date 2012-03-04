package process;

import java.util.Comparator;

public class EndTimeComp implements Comparator<Event>{

	@Override
	public int compare(Event a, Event b) {
		if (a.getEndTime().compareTo(b.getEndTime()) < 0)
            return -1;
        else if (a.getEndTime().compareTo(b.getEndTime()) > 0)
            return 1;
        else {
            if (a.getStartTime().compareTo(b.getStartTime()) < 0)
                return -1;
            else if (a.getStartTime().compareTo(b.getStartTime()) > 0)
                return 1;
        }
        return 0;
	}

}
