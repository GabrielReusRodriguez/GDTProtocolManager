package gdtManager;

import java.io.InputStream;
import java.io.OutputStream;

import gdtManager.exceptions.GDTException;

public class GDTProtocolManager {

	private GDTWritter gdtWritter = null;
	private GDTReader gdtReader = null;
	
	
	public GDTProtocolManager() throws GDTException {
		gdtWritter = new GDTWritter();
		gdtReader = new GDTReader();
	}
	
	
	public void xml2gdt(InputStream inputStream, OutputStream outputStream) throws GDTException {
		
		gdtWritter.execute(inputStream, outputStream);
		
	}
	
	
	public void gdt2xml( InputStream inputStream, OutputStream outputStream ) throws GDTException{
		gdtReader.execute(inputStream, outputStream);
	}
	
	
	

	
	
}
