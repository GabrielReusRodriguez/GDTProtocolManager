package gdtManager;

import gdtManager.exceptions.GDTException;

public class GDTLine {

	public final static String END_LINE = "\r\n";
	private final static int END_LINE_LENGTH = END_LINE.length();
	public final static int SIZE_FIELD = 4;
	public final static int SIZE_LENGTH_LINE = 3;

	
	private int size;

	private String value="";
	private String field="";
	
	protected GDTLine(String field, String value) throws GDTException {

		if( field == null || value == null ) {
			throw new GDTException("Error: NullPointer creating GDTLine");
		}
		this.field=field;
		this.value=value;
		this.size = SIZE_LENGTH_LINE + SIZE_FIELD + this.value.length() + END_LINE_LENGTH;
	}
	

	public String toString() {
		StringBuilder line=new StringBuilder();	
		// Creo la línea
		line.append(String.format("%03d", size));
		line.append(field);
		line.append(value);
		line.append(END_LINE);
		return line.toString();
	}

	public int getSize() {
		return size;
	}
	
	public void setValue(String value){
		//Hay que actualizar el tamaño ya que el resto queda con el mismo tamano
		this.value = value;
		this.size = SIZE_LENGTH_LINE + SIZE_FIELD + this.value.length() + END_LINE_LENGTH;
	}

	public String getValue() {
		return value;
	}

	public String getField() {
		return field;
	}

}
