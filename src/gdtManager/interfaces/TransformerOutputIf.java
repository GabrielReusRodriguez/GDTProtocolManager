package gdtManager.interfaces;

import java.io.OutputStream;

import gdtManager.exceptions.GDTException;

public interface TransformerOutputIf {
	
	//public void execute(InputStream is, OutputStream os) throws GDTException;
	//public void execute(InputStream is, OutputStream os,String encode) throws GDTException;
	public void execute(String input, OutputStream os)  throws GDTException;
	public void execute(String input, OutputStream os, String encode)  throws GDTException;
	
	
}
