package gdtManager;

import gdtManager.exceptions.GDTException;
import gdtManager.interfaces.TransformerOutputIf;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GDTProtocolManager {

	protected List<GDTLine> lineas;
	protected GDTLine msgSizeLine = null;

	private TransformerOutputIf transformerOutput = null;

	private String encode ="ISO-8859-1";
	
	private static final String MSG_SIZE_FORMAT = "%05d";

	public GDTProtocolManager() {

		lineas = new ArrayList<GDTLine>();
	}

	public GDTProtocolManager(TransformerOutputIf transformer) {

		this.transformerOutput = transformer;
		lineas = new ArrayList<GDTLine>();
	}
	
	public GDTProtocolManager(TransformerOutputIf transformer,String encode) {

		this.transformerOutput = transformer;
		lineas = new ArrayList<GDTLine>();
		this.encode = encode;
	}

	public void addPair(String field, String value) throws NullPointerException {

		lineas.add(new GDTLine(field, value));
	}

	public void serialize(OutputStream outputStream) throws GDTException {
		transformerOutput.execute(
				this.toString(),
				outputStream,
				this.encode);
	}

	public String toString() {

		StringBuilder strB = new StringBuilder();
		setMsgSize();
		for (GDTLine linea : lineas) {
			strB.append(linea.toString());
		}
		return strB.toString();
	}

	private void setMsgSize() {
		
		if (msgSizeLine == null){
			msgSizeLine = new GDTLine("8100", String.format(MSG_SIZE_FORMAT, 0));
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

}
