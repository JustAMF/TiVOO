package xmlParse;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import process.Event;


/**
 * @author Glenn Rivkees
 */

public class XMLTVCalFileParser extends AbstractFileParser {
	
	@SuppressWarnings("serial")
	public XMLTVCalFileParser(){
		// Mappings for xpath expressions
		super(
				new HashMap<String, String>(){{
					put("events", "//programme");
					put("startTime", "./@start");
					put("endTime", "./@stop");
					put("title", "./title");
					put("channel", "./channel");
					put("description", "./desc");
					put("actors", "./credits/actor");
					put("directors", "./credits/director");
					put("writers", "./credits/writer");
					put("producers", "./credits/producer");
					put("categories", "./credits/category");
					put("rating", "./rating[@system='MPAA']/value");
					put("stars", "./star-rating/value");}}
		);
	}
	
	public boolean isThisCal(Document doc) {
		return (doc.getDoctype() != null) && doc.getDoctype().getName().equals("tv");
	}

	
	public Event evaluateXpath(Node nEvent, Map<String, XPathExpression> myXPathXpr) throws XPathExpressionException {
		DateTime start=getTime(myXPathXpr.get("startTime").evaluate(nEvent));
		DateTime end=getTime(myXPathXpr.get("endTime").evaluate(nEvent));	
		Event toReturnEvent = new Event(start, end);
		toReturnEvent.addFeature("title", myXPathXpr.get("title").evaluate(nEvent));
		toReturnEvent.addFeature("description", myXPathXpr.get("description").evaluate(nEvent));
		toReturnEvent.addFeature("channel", myXPathXpr.get("channel").evaluate(nEvent));
		toReturnEvent.addFeature("rating", myXPathXpr.get("rating").evaluate(nEvent));
		toReturnEvent.addFeature("stars", myXPathXpr.get("stars").evaluate(nEvent));
		toReturnEvent.addFeature("actors", myXPathXpr.get("actors").evaluate(nEvent).split("\n"));
		toReturnEvent.addFeature("directors", myXPathXpr.get("directors").evaluate(nEvent).split("\n"));
		toReturnEvent.addFeature("writers", myXPathXpr.get("writers").evaluate(nEvent).split("\n"));
		toReturnEvent.addFeature("producers", myXPathXpr.get("producers").evaluate(nEvent).split("\n"));
		toReturnEvent.addFeature("categories", myXPathXpr.get("categories").evaluate(nEvent).split("\n"));
		return toReturnEvent;
	}
	
	/**
	 * create Joda Time from a specific period in an event
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		DateTimeFormatter format=DateTimeFormat.forPattern("yyyyMMddHHmmss Z");
		DateTime time=format.parseDateTime(content);
		//TODO: Implement time parse
		return time;
	}
	
}
