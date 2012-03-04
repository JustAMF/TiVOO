package process;

import java.util.Comparator;

public class NameComp implements Comparator<Event>{

	@Override
	public int compare(Event a, Event b) {
		if (a.getName().compareTo(b.getName()) < 0)
            return -1;
        else if (a.getName().compareTo(b.getName()) > 0)
            return 1;
		return 0;
	}

}
