package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import process.Event;


/**
 * @author Glenn Rivkees
 * 
 * @info Default Map Names
 * 		title
 * 		description
 * 		location
 * 		link
 */

public abstract class AbstractFileParser {
	
	private final Map<String, String> myXpathExprStrings;
	private Map<String, XPathExpression> myXPathXpr;
	
	public AbstractFileParser(Map<String, String> inXpathExprStrings){
		myXpathExprStrings = inXpathExprStrings;
	}
	
	public abstract boolean isThisCal(Document document);
	
	/*
	 * Compile Xpath expressions from concrete calendar
	 */
	public Map<String, XPathExpression> compileXpath(Map<String, String> pathXpr){
		XPath xpath = XPathFactory.newInstance().newXPath();
		Map<String, XPathExpression> compiledExpressions = new HashMap<String, XPathExpression>();
		// Xpath compilation
		try {
			// Runs through expressions, compiles, and puts in xpr map
			for (String element: pathXpr.keySet()){
				compiledExpressions.put(element, xpath.compile(pathXpr.get(element)));
			}
		} catch (XPathExpressionException e) {
			throw new ParsingException("XPath expression failed to compile and/or evaluate", e);
		}
		return compiledExpressions;
	}
	
	public NodeList getEventNodeList(Document doc) {
		try {
			return (NodeList) myXPathXpr.get("events").evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new ParsingException("XPath expression failed to evaluate", e);
		}
	}

	public List<Event> parseEvents(Document doc){
		// Compile Xpath expressions and store in map
		myXPathXpr = compileXpath(myXpathExprStrings);
		// get list of event nodes
		NodeList myEvents = getEventNodeList(doc);
		// List of Events
		ArrayList<Event> toReturnEvents = new ArrayList<Event>();
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			nEvent.getParentNode().removeChild(nEvent);
			// run xpaths and make event
			try {			
				toReturnEvents.add(evaluateXpath(nEvent, myXPathXpr)) ;
			} catch (XPathExpressionException e) {
				throw new ParsingException("Event Xpath Parsing did not evaluate correctly", e);
			}
			
		}
		return toReturnEvents;
	}
	
	public abstract Event evaluateXpath(Node nEvent, Map<String, XPathExpression> myXPathXpr) throws XPathExpressionException;
	
}