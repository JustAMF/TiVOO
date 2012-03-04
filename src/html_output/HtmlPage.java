package html_output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import process.Event;
import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;


/**
 *  @author Antares Yee
 */

public abstract class HtmlPage {
    private String myPath; //the path where you want the file created
    
    public HtmlPage(String path) {
        myPath = path;
    }
    
    /**
     * Creates an HTML page of specified subclass type.
     */
    public String createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, getMyFileName());
    }
    
    public abstract String getMyFileName();
    
    public abstract Html makeHtmlObject(EventCalendar events);
    
    /**
     *Create file at designated path, write Html object to file.
     */
    public String makeFile(Html html, String fileName) {
        //Create file, FileWriter, BufferedWriter for writing
        String filePath = myPath + fileName;
        File out = new File(filePath);
        
        try {
            out.createNewFile();
            FileWriter fw = new FileWriter(out);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(html.write());
            bw.close();
        } 
        catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("error creating file at " + myPath + fileName);
            return "Error";
        }
        return filePath;
    }
    
    /**
     * Create a directory given a directory name
     */
    public boolean makeDirFromPath(String dirName) {
        String dirPath = myPath + dirName;
        return new File(dirPath).mkdir();
    }
    
    /**
     * Returns fileName for an Event.
     * Removes unusable characters from fileName.
     */
    public String makeFileName(Event e) {
        String name = e.getName() + e.getStartTime().toString();
        return "/" + Integer.toString(name.hashCode()) + ".html";
    }
    
    public boolean addEventH2(Event e, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(e.getName()));
        body.appendChild(h2);
        return true;
    }
    
    /**
     * Add event start and end time to body
     */
    public boolean addEventTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add A title H2 to body
     */
    public boolean addTitleH2(String name, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(name));
        body.appendChild(h2);
        return true;
    }
    
    /**
     * Add event description of an event to body
     */
    public boolean addEventDescription(Event e, Body body) {
        body.appendChild(new Text("Details: " + e.getEventDescription()));
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add info of Event to body.
     */
     public boolean addEventInfo(Event e, Body body) {
        addEventH2(e, body);
        addEventTime(e, body);
        addEventDescription(e, body);
        body.appendChild(new Br());
        return true;
    }
}

