package process;
import java.util.*;

import org.joda.time.DateTime;



public class EventCalendar {
	private List<Event> myList;
	
	public EventCalendar(){
		myList = new ArrayList<Event>();
	}
	
	public EventCalendar(List <Event> list){
	    myList = list;
	}
	
	public void addEvent(Event event){
		myList.add(event);
	}
	
	public void addAll(List <Event> list){
		myList.addAll(list);
	}
	
	public void removeAll(List <Event> list){
		myList.removeAll(list);
	}
	
	public List <Event> getList(){
		return myList;
	}
	
	public EventCalendar filter(CalendarFilter filter){
		EventCalendar searchresults = new EventCalendar();
		for(Event e : myList){
			if(filter.filter(e)){
				searchresults.addEvent(e);
			}
		}
		return searchresults;
	}
	
	public EventCalendar removeAllContaining(String category, String keyword){
		EventCalendar searchresults = new EventCalendar(myList);
		EventCalendar toberemoved = searchresults.searchCalendar(category, keyword);
		searchresults.removeAll(toberemoved.getList());
		return searchresults;
	}
	
	public Set<String> findSearchableTerms(){
		Set<String> returnset = new HashSet<String>();
		for(Event e : myList){
			returnset.addAll(e.getKeys());
		}
		return returnset;
	}
	
	public EventCalendar findAllContaining(String category, String [] keywords){
		EventCalendar searchresults = new EventCalendar();
		for(String word : keywords){
			searchresults.addAll(searchCalendar(category, word).getList());
			}
		return searchresults;
	}
	
	public EventCalendar searchCalendar(String category, String keyword){
		return filter(new KeyWordFilter(category, keyword));
	}
	
	
	public EventCalendar filterByName(String keyword){
		return this.searchCalendar("title", keyword);
	}
	
	public EventCalendar eventsAtTime(DateTime time){
		return filter(new TimeFilter(time));
	}
	
	public EventCalendar eventsBetweenTimes(DateTime begin, DateTime end){
	    return filter(new EventsWithinTimeFilter(begin, end));
	}
	
	/**
     * Returns a list of conflicting events.
     */
    public List<Event> getConflictingEvents() {
        List<Event> conflicting = new ArrayList<Event>();
        TreeSet<Event> used = new TreeSet<Event>();
        
        //loop over all events, see if they conflict with each other
        for (Event e1 : myList) {
            for (Event e2 : myList) {
                if (e1.getInterval().overlap(e2.getInterval()) != null)  {
                    if (! used.contains(e1)) {
                        conflicting.add(e1);
                        used.add(e1);
                    }
                    if (! used.contains(e2)) {
                        conflicting.add(e2);
                        used.add(e2);
                    }   
                }
            }
        }
        
        return conflicting;
    }
	
	public EventCalendar sortByStartTime(){
		EventCalendar sortedevents = new EventCalendar(myList);
		Collections.sort(sortedevents.getList(), new TimeComp());
		return sortedevents;
	}
	
	public EventCalendar sortByEndTime(){
		EventCalendar sortedevents = new EventCalendar(myList);
		Collections.sort(sortedevents.getList(), new EndTimeComp());
		return sortedevents;
	}
	
	public EventCalendar sortByName(){
		EventCalendar sortedevents = new EventCalendar(myList);
		Collections.sort(sortedevents.getList(), new NameComp());
		return sortedevents;
	}
	
	public EventCalendar reverse(){
		EventCalendar sortedevents = new EventCalendar(myList);
		Collections.reverse(sortedevents.getList());
		return sortedevents;
	}

}
