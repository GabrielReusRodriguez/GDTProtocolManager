/**
 * 
 */
package gdtManager.implementations;

import gdtManager.exceptions.GDTException;
import gdtManager.interfaces.TransformerOutputIf;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author gabriel
 *
 */
public class TransformerOutputImpl implements TransformerOutputIf{

	public static final String DEFAULT_ENCODE = "UTF-8";
	/**
	 * 
	 */
	public TransformerOutputImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(String is, OutputStream os) throws GDTException{
	
		execute(is,os,DEFAULT_ENCODE);
	}

	@Override
	public void execute(String input, OutputStream os, String encode)
			throws GDTException {
		try {
			os.write(input.getBytes(encode));
		} catch (IOException e) {
			e.printStackTrace();
			throw new GDTException(e);
		}
		
		
	}

	

}
