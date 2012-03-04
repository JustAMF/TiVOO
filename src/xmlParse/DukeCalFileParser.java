package xmlParse;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import process.Event;


/**
 * @author Glenn Rivkees
 */

public class DukeCalFileParser extends AbstractFileParser {
	
	@SuppressWarnings("serial")
	public DukeCalFileParser(){
		// Mappings for xpath expressions
		super(
				new HashMap<String, String>(){{
					put("events", "//event");
					put("startTime", "./start/unformatted");
					put("endTime", "./end/unformatted");
					put("title", "./summary");
					put("location", "./location/address");
					put("description", "./description");
					put("link", "./link");}}
		);
	}
	
	public boolean isThisCal(Document doc) {
		// TODO: find way to distinguish a duke cal, right now this works because it is the last one.
		return true;
	}

	
	public Event evaluateXpath(Node nEvent, Map<String, XPathExpression> myXPathXpr) throws XPathExpressionException {
		DateTime start=getTime(myXPathXpr.get("startTime").evaluate(nEvent));
		DateTime end=getTime(myXPathXpr.get("endTime").evaluate(nEvent));	
		Event toReturnEvent = new Event(start, end);
		toReturnEvent.addFeature("title", myXPathXpr.get("title").evaluate(nEvent));
		toReturnEvent.addFeature("description", myXPathXpr.get("description").evaluate(nEvent));
		toReturnEvent.addFeature("location", myXPathXpr.get("location").evaluate(nEvent));
		toReturnEvent.addFeature("link", myXPathXpr.get("link").evaluate(nEvent));
		return toReturnEvent;
	}
	
	/**
	 * create Joda Time from a specific period (a subnode of an event like
	 * 'start' or 'end') in an event (this method is used for parsing
	 * DukeCalendar)
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		
		DateTimeFormatterBuilder myBuilder=new DateTimeFormatterBuilder().appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2);
		if(content.length()==15){
			myBuilder.appendLiteral('T').appendHourOfDay(2).appendMinuteOfHour(2).appendSecondOfMinute(2);
		}
		DateTimeFormatter myFormat=myBuilder.toFormatter();
		DateTime myTime=myFormat.parseDateTime(content);
		return myTime;
	}
	
}
