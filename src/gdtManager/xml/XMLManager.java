package gdtManager.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import gdtManager.GDTLine;
import gdtManager.GDTLineFactory;
import gdtManager.exceptions.GDTException;


public class XMLManager {

	private static  DocumentBuilderFactory docBuilderfactory = null;
	private static  DocumentBuilder docBuilder = null;

	
	public XMLManager() throws GDTException{
		init();
	}
	
	public Document string2xmlDoc(InputStream is) throws GDTException {
		
		Document doc = null;
		
		try {
			doc = docBuilder.parse(is);
			// is.close();
		} catch (SAXException | IOException e) {
			// e.printStackTrace();
			throw new GDTException(e.getMessage(), e);
		}

		return doc;
	}

	private void init() throws GDTException {
		
		docBuilderfactory = DocumentBuilderFactory.newInstance();
		docBuilderfactory.setNamespaceAware(true);
		try {
			docBuilder = docBuilderfactory.newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			throw new GDTException(ex.getMessage(), ex);
		}
	}
	
	public List<GDTLine> getGDTLines(Document doc) throws GDTException{
		
		List<GDTLine> gdtLines = new ArrayList<GDTLine>();
		NodeList lines = doc.getElementsByTagName("line");
		for(int i = 0;i < lines.getLength();i++ ) {
			
			Node campo = null;
			Node value = null;
			
			Node nodo = lines.item(i);
			if(nodo.getNodeType() == Node.ELEMENT_NODE) {
				//obtengo el campo y el valor.
				NodeList campos = ((Element)nodo).getElementsByTagName("field");
				
				for(int j=0;j<campos.getLength();j++) {
					if (campos.item(j).getNodeType() == Node.ELEMENT_NODE) {
						campo = campos.item(j);
					}
				}
				NodeList valores = ((Element)nodo).getElementsByTagName("value");
				
				for(int k=0;k<valores.getLength();k++) {
					if (valores.item(k).getNodeType() == Node.ELEMENT_NODE) {
						 value = valores.item(k);
					}
				}
			}
			
			//Hacemos push de la linea SOLO si el campo y el valor no son null
			if (campo != null && value != null) {
				gdtLines.add(GDTLineFactory.buildGDTLine(campo.getFirstChild().getNodeValue(), value.getFirstChild().getNodeValue()));	
			}
			
		}
		
		return gdtLines;
		
	}
	
	public Document makeGDTXML(List<GDTLine> listGDTs) throws GDTException {
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElementNS("http://www.gdt.com", "GDT");
		Element payloadElement = doc.createElement("payload");
	
		for(GDTLine line : listGDTs) {
			Element xmlLine = doc.createElement("line");
			Element field = doc.createElement("field");
			Element value = doc.createElement("value");
			
			field.appendChild(doc.createTextNode(line.getField()));
			value.appendChild(doc.createTextNode(line.getValue()));
			
			xmlLine.appendChild(field);
			xmlLine.appendChild(value);
			
			payloadElement.appendChild(xmlLine);
		}
		
		rootElement.appendChild(payloadElement);
		doc.appendChild(rootElement);
		return doc;
	}
	
	public String document2XML(Document doc, String encode) throws GDTException{
		
		        StringWriter sw = new StringWriter();
		        TransformerFactory tf = TransformerFactory.newInstance();
		        Transformer transformer;
				try {
					transformer = tf.newTransformer();
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					throw new GDTException(e);
				}
		        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.setOutputProperty(OutputKeys.ENCODING, encode);

		        try {
					transformer.transform(new DOMSource(doc), new StreamResult(sw));
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					throw new GDTException(e);
				}
		        return sw.toString();

	}
}
