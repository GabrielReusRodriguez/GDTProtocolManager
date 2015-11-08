package gdtManager.interfaces;

import gdtManager.exceptions.GDTException;

import java.io.InputStream;
import java.io.OutputStream;

public interface TransformerOutputIf {
	
	//public void execute(InputStream is, OutputStream os) throws GDTException;
	//public void execute(InputStream is, OutputStream os,String encode) throws GDTException;
	public void execute(String input, OutputStream os)  throws GDTException;
	public void execute(String input, OutputStream os, String encode)  throws GDTException;
	
	
}
