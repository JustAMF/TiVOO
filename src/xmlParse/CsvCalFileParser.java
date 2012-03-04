package xmlParse;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import process.Event;


/**
 * @author Glenn Rivkees
 */

public class CsvCalFileParser extends AbstractFileParser {
	
	@SuppressWarnings("serial")
	public CsvCalFileParser(){
		// Mappings for xpath expressions
		super(
				new HashMap<String, String>(){{
			    	put("events", "//row");
			    	put("title", "./Col1");
			    	put("description", "./Col2");
			    	put("startTime", "./Col8");
			    	put("endTime", "./Col9");
			    	put("location", "./Col15");}}
		);
	}

	public boolean isThisCal(Document doc) {
		return doc.getDocumentElement().getChildNodes().item(1).getNodeName().equals("row");
	}
	
	public Event evaluateXpath(Node nEvent, Map<String, XPathExpression> myXPathXpr) throws XPathExpressionException {
		DateTime start=getTime(myXPathXpr.get("startTime").evaluate(nEvent));
		DateTime end=getTime(myXPathXpr.get("endTime").evaluate(nEvent));
		Event toReturnEvent = new Event(start, end);
		toReturnEvent.addFeature("title", myXPathXpr.get("title").evaluate(nEvent));
		toReturnEvent.addFeature("description", myXPathXpr.get("description").evaluate(nEvent));
		toReturnEvent.addFeature("location", myXPathXpr.get("location").evaluate(nEvent));
		return toReturnEvent;
	}
	
	
	/**
	 * create Joda Time from a specific period in an event
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		DateTimeFormatter format=DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime time=format.parseDateTime(content);
		//TODO: Implement time parse
		return time;
	}

}
