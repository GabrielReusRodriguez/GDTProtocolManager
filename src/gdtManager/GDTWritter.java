package gdtManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.w3c.dom.Document;

import gdtManager.enumerations.ContentFields;
import gdtManager.exceptions.GDTException;
import gdtManager.implementations.TransformerOutputImpl;
import gdtManager.interfaces.TransformerOutputIf;
import gdtManager.xml.XMLManager;

public class GDTWritter {

	protected List<GDTLine> lineas;
	protected GDTLine msgSizeLine = null;

	private TransformerOutputIf transformerOutput = null;

	private String encode ="ISO-8859-1";
	
	private static final String MSG_SIZE_FORMAT = "%05d";

	public GDTWritter() {
		//lineas = new ArrayList<GDTLine>();
		transformerOutput = new TransformerOutputImpl();
	}

	public GDTWritter(TransformerOutputIf transformer) {

		this.transformerOutput = transformer;
		//lineas = new ArrayList<GDTLine>();
	}
	
	public GDTWritter(TransformerOutputIf transformer,String encode) {

		this.transformerOutput = transformer;
		//lineas = new ArrayList<GDTLine>();
		this.encode = encode;
	}

	
	private void serialize(OutputStream outputStream) throws GDTException {
		transformerOutput.execute(
				this.makeString(),
				outputStream,
				this.encode);
	}

	private String makeString() throws GDTException{

		StringBuilder strB = new StringBuilder();
		setMsgSize();
		for (GDTLine linea : lineas) {
			strB.append(linea.toString());
		}
		return strB.toString();
	}

	private void setMsgSize() throws GDTException{
		
		if (msgSizeLine == null){
			//msgSizeLine = new GDTLine("8100", String.format(MSG_SIZE_FORMAT, 0));
			msgSizeLine = GDTLineFactory.buildGDTLine(ContentFields.GDT_FILE_SIZE.contentField(), String.format(MSG_SIZE_FORMAT, 0));//new GDTLine(ContentFields.GDT_FILE_SIZE.contentField(), String.format(MSG_SIZE_FORMAT, 0));
			lineas.add(1, msgSizeLine);
		}

		StringBuilder strB = new StringBuilder();
		for (GDTLine linea : lineas) {
			strB.append(linea.toString());
		}
		int file_size = 0;
		try {
			file_size = strB.toString().getBytes(this.encode).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			file_size = strB.toString().getBytes().length;
		}
		msgSizeLine.setValue(String.format(MSG_SIZE_FORMAT, file_size));

	}
	
	public void execute(InputStream is,OutputStream os) throws GDTException {
		
		//Obtengo el documento
				Document xmlDoc = null;
				XMLManager xmlManager = new XMLManager();
				
				xmlDoc = xmlManager.string2xmlDoc(is);
				this.lineas = xmlManager.getGDTLines(xmlDoc);
				this.serialize(os);
		
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
			is = new FileInputStream("C:\\Users\\gabriel\\git\\GDTProtocolManager\\xml\\input.xml");
			os = new FileOutputStream("C:\\Users\\gabriel\\git\\GDTProtocolManager\\xml\\output.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			gdtProtocolManager.xml2gdt(is, os);
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
