package csi.int033;

public class GDTLine {

	private final static String END_LINE = "\r\n";
	private final static int END_LINE_LENGTH = END_LINE.length();
	public final static int SIZE_FIELD = 4;
	public final static int SIZE_LENGTH_LINE = 3;

	
	private int size;
	private String value="";
	private String field="";
	
	public GDTLine(String field, String value) throws NullPointerException {

		this.field=field;
		this.value=value;
		//this.size = 3 + this.field.length() + this.value.length() + END_LINE_LENGTH;
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
		this.value = value;
	}

}
