package process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class Event{
	
	private Map<String, ArrayList<String>> myMap;

	public DateTime myStartTime;
	public DateTime myEndTime;
	public Interval myInterval;
	
	/*
	 * Initialize the map
	 */
	
	public Event(DateTime startTime, DateTime endTime) {
		myStartTime = startTime;
		myEndTime = endTime;
		myInterval=new Interval(startTime,endTime);
		myMap=new HashMap<String, ArrayList<String>>();
	}

	public void addFeature(String key, String[] value){		
		myMap.put(key, new ArrayList<String>(Arrays.asList(value)));
	}
	
	public void addFeature(String key, String value){		
		myMap.put(key, new ArrayList<String>(Arrays.asList(value)));
	}
	
	public boolean hasFeature(String key){
		return myMap.containsKey(key);
	}
	
	public Set<String> getKeys(){
		return myMap.keySet();
	}
	
	public ArrayList<String> getFeature(String key){
		return myMap.get(key);
	}

	public String toString() {
		
		StringBuilder myStrBuilder=new StringBuilder();
		myStrBuilder.append("Event[ ");
		for(String str: myMap.keySet()){
			
			ArrayList<String> list=myMap.get(str);
			for(String s: list){
				myStrBuilder.append(s+", ");
			}	
		}
		myStrBuilder.append(" ]\n");
		return myStrBuilder.toString();
	}

	// returns first item in title field
	public String getName(){
		return myMap.get("title").get(0);
	}
	
	
	// returns first item in description field
	public String getEventDescription(){
		if (myMap.get("description").get(0) != null)
			return myMap.get("description").get(0);
		return "(No additonal details)";
	}
	
	public DateTime getStartTime() {
		return myStartTime;
	}

	public DateTime getEndTime() {
		return myEndTime;
	}

	public Interval getInterval() {
		return myInterval;
	}


}
