package csi.int033;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sap.aii.mapping.api.StreamTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;

import csi.int033.utils.Utils;

public class MessageType2File implements StreamTransformation {

	public MessageType2File() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(InputStream is, OutputStream os)
			throws StreamTransformationException {

		Document doc = Utils.newDocumentFromInputStream(is);
		GDTProtocolManager gdtManager = new GDTProtocolManager();
		//String fileName="poligrafo.txt";
		NodeList listNodes = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < listNodes.getLength(); i++) {
			Node item = listNodes.item(i);
			if ("line".equalsIgnoreCase(item.getNodeName())) {
				procesaLinea(item, gdtManager);
			}
		}
		try {
			/*FileOutputStream fos = new FileOutputStream("/devinterfaces/gabriel/"+fileName);
			fos.write(gdtManager.toString().getBytes());
			fos.close();
			os.write("".getBytes());
			*/
			os.write(gdtManager.toString().getBytes("ISO-8859-1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private void procesaLinea(Node item, GDTProtocolManager gdtManager) {

		String field = "";
		String value = "";
		NodeList listaNodos = item.getChildNodes();

		for (int j = 0; j < listaNodos.getLength(); j++) {
			Node nodo = listaNodos.item(j);
			if ("Field".equalsIgnoreCase(nodo.getNodeName())) {
				field = nodo.getFirstChild().getNodeValue().trim();
				continue;
			}
			if ("Value".equalsIgnoreCase(nodo.getNodeName())) {
				value = nodo.getFirstChild().getNodeValue().trim();
				continue;
			}

		}

		gdtManager.addPair(field, value);

	}

	@Override
	public void setParameter(Map arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputStream is = null;
		OutputStream os = null;

		try {

			is = new FileInputStream("./xml/test.xml");
			os = new FileOutputStream("./xml/test.out.xml");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		MessageType2File transform = new MessageType2File();
		try {
			transform.execute(is, os);
		} catch (StreamTransformationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
