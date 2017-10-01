package xml_start;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * XML Writing class to save current state of simulation
 * 
 * @author ptflecha
 * 
 * Some sections adapted from WriteXMLFile.java by @mykong
 * URL: https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 *
 */
public class XMLWrite {

	DocumentBuilderFactory docFactory = null;
	DocumentBuilder docBuilder = null;
	SimulationParameters currSimObject = null;
	
	// constructor
	public XMLWrite() {
		docFactory = DocumentBuilderFactory.newInstance();
	}


	private DocumentBuilder makeDocumentBuilder() throws ParserConfigurationException {
		return docFactory.newDocumentBuilder();
	}
	
	
	// TODO add the current parameters representation method input from Simran
	public void saveState(SimulationParameters currentSim, int[][] grid) throws TransformerException, ParserConfigurationException {
		
		docBuilder = makeDocumentBuilder();
		currSimObject = currentSim;
		
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("data");
		doc.appendChild(rootElement);
		
		Attr attr = doc.createAttribute("simulation");
		attr.setValue("SimulationParameters");
		rootElement.setAttributeNode(attr);
		
		addElements(doc, rootElement, grid);
					
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String fileString = "saved_states/savedState" + getXMLFileNumber() + ".xml";
		StreamResult result = new StreamResult(new File(fileString));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);
		System.out.println("File saved!");
			
		
	}

	
    /*=============================================================================
    |
    |
    |              ADD ELEMENTS
    |
    |
    *===========================================================================*/
    
	

	// TODO: add any other info you add to XML format;
	private void addElements(Document doc, Element rootElement, int[][] grid) {
		addTitle(doc, rootElement);
		addAuthor(doc, rootElement);
		addColorScheme(doc, rootElement);
		addParams(doc, rootElement);
		addGrid(doc, rootElement, grid);
	}

	// add title
	private void addTitle(Document doc, Element rootElement) {
		Element title = doc.createElement("title");
		title.appendChild(doc.createTextNode(currSimObject.getTitle()));
		rootElement.appendChild(title);
	}

	// add author
	private void addAuthor(Document doc, Element rootElement) {
		Element author = doc.createElement("author");
		author.appendChild(doc.createTextNode(currSimObject.getAuthor()));
		rootElement.appendChild(author);
	}


	// color scheme
	private void addColorScheme(Document doc, Element rootElement) {
		Element colorScheme = doc.createElement("colorScheme");
		colorScheme.appendChild(doc.createTextNode(currSimObject.getColorSchemeString()));
		rootElement.appendChild(colorScheme);
	}

	// TODO: extra parameters
	private void addParams(Document doc, Element rootElement) {
		Element extraParams = doc.createElement("extraParams");
		extraParams.appendChild(doc.createTextNode("1 0.3"));
		rootElement.appendChild(extraParams);
	}

	// add grid
	private void addGrid(Document doc, Element rootElement, int[][] grid) {
		Element gridElement = doc.createElement("grid");
		String gridAsString = makeGridString(grid);
		gridElement.appendChild(doc.createTextNode(gridAsString));
		rootElement.appendChild(gridElement);
	}
	

	// make grid as string
	private String makeGridString(int[][] grid) {
		
		StringBuffer gridString = new StringBuffer("");
		
		for (int row = 0; row < grid.length; row++) {
			
			gridString = gridString.append(" ");
			for (int col = 0; col < grid[0].length-1; col++) {
				gridString.append(String.valueOf(grid[row][col]));
				gridString.append(" ");
			}
			gridString.append(String.valueOf(grid[row][grid[0].length-1]));
			gridString.append(";");
		}
		
		return gridString.toString();
		
	}
	
	private int getXMLFileNumber() {
    		File folder = new File("saved_states/");
    		File[] listOfFiles = folder.listFiles();
    		return listOfFiles.length;
	}
	
	
}
