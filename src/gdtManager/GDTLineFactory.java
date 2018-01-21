package gdtManager;

import gdtManager.exceptions.GDTException;

public class GDTLineFactory {

	
	private GDTLineFactory() {
		
	}
	
	public static GDTLine buildGDTLine(String field, String value) throws GDTException {

		GDTLine gdtLine = null;

		gdtLine = new GDTLine(field, value);
		
		return gdtLine;
	}
	
	public static GDTLine buildGDTLine(String line) throws GDTException{
		
		
		if ( line == null ) {
			
			throw new GDTException("Error: GDT line is null");
			
		}
		
		GDTLine gdtLine = null;
		
		String field = null;
		String value  = null;
		
		//String length = null;
		
		//length = line.substring(0, 1);
		field = line.substring(2,6);
		value = line.substring(7);
		
		gdtLine = new GDTLine(field, value);
		
		return gdtLine;
		
	}
}
