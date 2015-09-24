package csi.int033;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GDTProtocolManager {

	private List<GDTLine> lineas;
	private GDTLine fileSizeLine;

	private static final String FILE_SIZE_FORMAT = "%05d";

	public GDTProtocolManager() {

		lineas = new ArrayList<GDTLine>();
	}

	public void addPair(String field, String value) throws NullPointerException {

		lineas.add(new GDTLine(field, value));
	}

	public String toString() {
		
		StringBuilder strB = new StringBuilder();
		setFileSize();
		for (GDTLine linea : lineas) {
			strB.append(linea.toString());
		}
		return strB.toString();
	}


	private void setFileSize() {
/*
		int file_size = 0;
		
		//file_size += this.fileSizeLine.getSize();
		for (GDTLine linea : lineas) {
			file_size += linea.getSize();
		}
		fileSizeLine.setValue(				String.format(	FILE_SIZE_FORMAT,
								GDTLine.SIZE_LENGTH_LINE + GDTLine.SIZE_FIELD + 5 + file_size
								));
		// Tengo que añadir al tamaño del fichero el tamaño de la propia línea
		// que marca el tamaño
		// Cada línea que marca el tamaño de fichero es (sin espacios):
		// 014810012345\r\n
		//Añado la linea de tamaño de fichero en la segunda línea.
		lineas.add(1, fileSizeLine);
*/
		
		
		fileSizeLine = new GDTLine("8100",String.format(FILE_SIZE_FORMAT,0));
		
		lineas.add(1, fileSizeLine);
		
		StringBuilder strB = new StringBuilder();
		for (GDTLine linea : lineas) {
			strB.append(linea.toString());
		}
		int file_size =0;
		try {
			file_size = strB.toString().getBytes("ISO-8859-1").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			file_size = strB.toString().getBytes().length;
		}
		fileSizeLine.setValue(String.format(FILE_SIZE_FORMAT,file_size));

		
	}

}
