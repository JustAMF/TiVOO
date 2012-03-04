package xmlParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import process.Event;


/**
 * @author Glenn Rivkees
 */

public class XmlParser {
	
	private String myUrl;
	
	public XmlParser(String url){
		myUrl = url;
	}

	public List<Event> loadAndParse() {
		
		Document document = xmlFileFromUrl(myUrl); 
		
		// initialize list of file types
		List<AbstractFileParser> kindsOfFiles = new ArrayList<AbstractFileParser>();
		kindsOfFiles.add(new GoogleCalFileParser());
		kindsOfFiles.add(new XMLTVCalFileParser());
		kindsOfFiles.add(new MsftCalFileParser());
		kindsOfFiles.add(new CsvCalFileParser());
		kindsOfFiles.add(new DukeCalFileParser());
		// find expression type, and then call its parser
		for (AbstractFileParser expressionKind : kindsOfFiles) {
			if (expressionKind.isThisCal(document))
				return expressionKind.parseEvents(document);
		}
		throw new ParsingException("Filetype not recognized");
	}
	
	/*
	 * Load Doc from URL, parse, and save to instance var
	 */
	public Document xmlFileFromUrl(String link) {
		Document toRetDoc;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			toRetDoc = dBuilder.parse(link);
		} catch (ParserConfigurationException e) {
			throw new ParsingException("ParserConfigurationException", e);
		} catch (SAXException e) {
			throw new ParsingException("SAXException", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParsingException("IOException", e);
		}
		toRetDoc.getDocumentElement().normalize();
		return toRetDoc;
	}
	
}
