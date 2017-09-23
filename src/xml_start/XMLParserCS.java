package xml_start;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * Class responsible for reading XML file and extracting relevant information, and assigning it to SimulationParameters
 * 
 * Adapted from XMLParser.java by:
 * @author Rhondu Smithwick
 * @author Robert C. Duvall 
 *
 */
public class XMLParserCS {

    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    
    /**
     * Create a parser for XML files of given type.
     */
    public XMLParserCS (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }
	
	
	/**
     * Get the data contained in this XML file as an object
     */
    public SimulationParameters getSimulation (File dataFile) {
        Element root = getRootElement(dataFile);
        System.out.println(root);
        if (! isValidFile(root, SimulationParameters.DATA_TYPE)) {
        		System.out.println("im in here baby");
            throw new XMLException("XML file does not represent %s", SimulationParameters.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        Map<String, String> results = new HashMap<>();
        for (String field : SimulationParameters.DATA_FIELDS) {
            results.put(field, getTextValue(root, field));
        }
        return new SimulationParameters(results);
    }
    
    
    
    // Get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }
    
    
    // Get value of Element's text
    private String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }
    
    // Returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }
	
	
    // Helper method to do the boilerplate code needed to make a documentBuilder.
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
	
    
    // Get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }
	
	
	
}
