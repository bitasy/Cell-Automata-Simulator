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

	// make document builder
	private DocumentBuilder makeDocumentBuilder() throws ParserConfigurationException {
		return docFactory.newDocumentBuilder();
	}
	
	
	// saves state of current simulation
	public void saveState(SimulationParameters currentSim, int[] grid, double[] sliderParams) throws TransformerException, ParserConfigurationException {
		
		docBuilder = makeDocumentBuilder();
		currSimObject = currentSim;
		
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("data");
		doc.appendChild(rootElement);
		
		Attr attr = doc.createAttribute("simulation");
		attr.setValue("SimulationParameters");
		rootElement.setAttributeNode(attr);
		
		addElements(doc, rootElement, grid, sliderParams);
					
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

	// get how many files already exist in the directory
	private int getXMLFileNumber() {
    		File folder = new File("saved_states/");
    		File[] listOfFiles = folder.listFiles();
    		return listOfFiles.length;
	}
	
    /*=============================================================================
    |
    |
    |              ADD ELEMENTS
    |
    |
    *===========================================================================*/
    
	// add all elements to XML file
	private void addElements(Document doc, Element rootElement, int[] grid, double[] sliderExtraParams) {
		
		String[] elements = {"title", "author", "hasOutline", "initialConfig", "neighborsConsidered", "sliders", "toroidal"};
		for (String s : elements) { addTag(doc, rootElement, s); }
		addColorScheme(doc, rootElement);
		addParams(doc, rootElement, sliderExtraParams);
		addGrid(doc, rootElement, grid);
		addShape(doc, rootElement);
		
	}
	
	// add shape
	private void addShape(Document doc, Element rootElement) {
		Element shape = doc.createElement("shape");
		shape.appendChild(doc.createTextNode(currSimObject.getSimShape()));
		rootElement.appendChild(shape);
	}
	
	// add initial config
	private void addTag(Document doc, Element rootElement, String tag) {
		Element e = doc.createElement(tag);
		if (tag.equals("initialConfig")) {e.appendChild(doc.createTextNode("fixed"));}
		else {e.appendChild(doc.createTextNode(currSimObject.getXMLTag(tag)));}
		rootElement.appendChild(e);
	}

	// color scheme
	private void addColorScheme(Document doc, Element rootElement) {
		Element colorScheme = doc.createElement("colorScheme");
		colorScheme.appendChild(doc.createTextNode(currSimObject.getColorSchemeString()));
		rootElement.appendChild(colorScheme);
	}

	// extra parameters
	private void addParams(Document doc, Element rootElement, double[] sliderExtraParams) {
		Element extraParams = doc.createElement("extraParams");
		double numStates = currSimObject.getNumStates();
		StringBuffer result = new StringBuffer(String.valueOf(numStates));
		for (double d : sliderExtraParams) { result.append(" " + String.valueOf(d)); }
		extraParams.appendChild(doc.createTextNode(result.toString()));
		rootElement.appendChild(extraParams);
	}

	// add grid
	private void addGrid(Document doc, Element rootElement, int[] grid) {
		Element gridElement = doc.createElement("grid");
		String gridAsString = makeGridString(grid);
		gridElement.appendChild(doc.createTextNode(gridAsString));
		rootElement.appendChild(gridElement);
	}

	// make grid as string
	private String makeGridString(int[] grid) {
		
		if (currSimObject.getSimShape().equals("other")) {
			return gridFromList(grid);
		} else {
			return gridFromMatrix(grid);
		}
		
	}
	
	// makes grid layout as a full list (for simulations like penrose)
	private String gridFromList(int[] grid) {
		StringBuffer gridString = new StringBuffer("");
		for (int i = 0; i < grid.length-1; i++) {
			gridString.append(String.valueOf(grid[i]));
			gridString.append(" ");
		}
		gridString.append(String.valueOf(grid[grid.length-1]));
		return gridString.toString();
	}
	
	// makes matrix grid layout (for normal simulations)
	private String gridFromMatrix(int[] grid) {
		StringBuffer gridString = new StringBuffer("");
		int numCols = currSimObject.getNumCols();
		// int numRows = currSimObject.getNumRows();
		
		for (int i = 0; i < grid.length; i++) {
			gridString.append(" ");
			gridString.append(String.valueOf(grid[i]));
			if ((i+1) % numCols == 0) { gridString.append(";"); }
		}
		
		return gridString.toString();
	}
		
}
