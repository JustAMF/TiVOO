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

public class MsftCalFileParser extends AbstractFileParser {
	
	public final String namespace = "urn:schemas-microsoft-com:officedata";
	
	@SuppressWarnings("serial")
	public MsftCalFileParser(){
		// Mappings for xpath expressions
		super(
				new HashMap<String, String>(){{
					put("events", "//Calendar");
			    	put("title", "./Subject");
			    	put("description", "./Description");
			    	put("startTime", "./StartTime");
			    	put("startDate", "./StartDate");
			    	put("endTime", "./EndTime");
			    	put("endDate", "./EndDate");
			    	put("location", "./Location");}}
		);
	}

	public boolean isThisCal(Document doc) {
		return doc.getDocumentElement().getAttribute("xmlns:od").equals(namespace);
	}

	public Event evaluateXpath(Node nEvent, Map<String, XPathExpression> myXPathXpr) throws XPathExpressionException {
		DateTime start=getTime(myXPathXpr.get("startDate").evaluate(nEvent)+" "+myXPathXpr.get("startTime").evaluate(nEvent));
		DateTime end=getTime(myXPathXpr.get("endDate").evaluate(nEvent)+" "+myXPathXpr.get("endTime").evaluate(nEvent));
		Event toReturnEvent = new Event(start, end);
		toReturnEvent.addFeature("title", myXPathXpr.get("title").evaluate(nEvent));
		toReturnEvent.addFeature("description", myXPathXpr.get("description").evaluate(nEvent));
		toReturnEvent.addFeature("location", myXPathXpr.get("location").evaluate(nEvent));
		return toReturnEvent;
	}
	
	/**
	 * create Joda Time from a specific period
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		DateTimeFormatter format=DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss aa");
		DateTime time=format.parseDateTime(content);
		//TODO: Implement time parse
		return time;
	}

}
