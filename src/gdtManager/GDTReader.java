package gdtManager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.Document;

import gdtManager.exceptions.GDTException;
import gdtManager.implementations.TransformerOutputImpl;
import gdtManager.interfaces.TransformerOutputIf;
import gdtManager.xml.XMLManager;

public class GDTReader {

	private final static String ENCODE ="UTF-8";
	
	private static XMLManager xmlManager = null;
	private TransformerOutputIf transformerOutput = null;
	private Hashtable<String,StringBuffer> fields = null;
	
	public GDTReader() throws GDTException {
		
		init();
		
	}

	
	private void init() throws GDTException {
		
		transformerOutput = new TransformerOutputImpl();
		xmlManager = new XMLManager();
		fields = new Hashtable<String,StringBuffer>();
	}
	
	public void execute(InputStream is, OutputStream os) throws GDTException {
		
		List<GDTLine> listGDTs = new ArrayList<GDTLine>();
		Document doc = null;
		String docStr = null;
		
		
		String gdt = inputStream2String(is, ENCODE);
		if( gdt == null ) {
			throw new GDTException("Error getting the gdt String");
		}
		String[] gdtLines = gdt.split(GDTLine.END_LINE);
		
		for( String line : gdtLines ) {
			GDTLine gdtLine = GDTLineFactory.buildGDTLine(line);
			//listGDTs.add(gdtLine);
			//Busco en el hash la linea.
			if ( fields.containsKey(gdtLine.getField()) ) {
				StringBuffer strBuffer = fields.get(gdtLine.getField());
				strBuffer.append(gdtLine.getValue());
			}else {
				//Creo el registro.
				fields.put(gdtLine.getField(), new StringBuffer(gdtLine.getValue()));
				listGDTs.add(gdtLine);
			}
		}
		
		//Generamos la lista con los valores concatenadoss
		for(GDTLine line : listGDTs) {
			line.setValue(fields.get(line.getField()).toString());
		}
		
		//Generamos la salida
		doc = xmlManager.makeGDTXML(listGDTs);
		docStr = xmlManager.document2XML(doc, ENCODE);
		transformerOutput.execute(docStr, os, ENCODE);
		
	}

	private String inputStream2String(InputStream is, String encodeValue) throws GDTException {
		String resultado = "";
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = is.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			throw new GDTException(e);
		}
		// StandardCharsets.UTF_8.name() > JDK 7
		try {
			resultado = result.toString(encodeValue);
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			//e.printStackTrace();
			throw new GDTException(e);
		}
		
		return resultado;
	}
	
	public static void main(String[] args) {
		
		InputStream is = null;
		OutputStream os = null;
		
		GDTProtocolManager gdtProtocolManager = null;
		
		try {
			gdtProtocolManager = new GDTProtocolManager();
		} catch (GDTException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return;
		}
		
		try {
			is = new FileInputStream("C:\\Users\\gabriel\\git\\GDTProtocolManager\\xml\\output.xml");
			os = new FileOutputStream("C:\\Users\\gabriel\\git\\GDTProtocolManager\\xml\\MT.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			gdtProtocolManager.gdt2xml(is, os);
		} catch (GDTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(os != null) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}
