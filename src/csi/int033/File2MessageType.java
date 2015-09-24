package csi.int033;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.sap.aii.mapping.api.StreamTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;

import csi.int033.utils.PoligrafoException;
import csi.int033.utils.Utils;

public class File2MessageType implements StreamTransformation {

	public File2MessageType() {
		// TODO Auto-generated constructor stub
	}

	private boolean pdfFound = false;
	private boolean vkgidFound = false;
	private final static String ID_CAMPO_PDF = "6305";
	private final static String ID_CAMPO_VKGID = "6333";

	@Override
	public void execute(InputStream is, OutputStream os)
			throws StreamTransformationException {

		String mensaje = "";
		StringBuffer respuesta = new StringBuffer();
		try {
			mensaje = Utils.newStringFromInputStream(is);
			inicializaRespuesta(respuesta);
			String[] lineas = mensaje.split("\n");
			for (String linea : lineas) {
				procesaLinea(linea, respuesta);
				if (pdfFound && vkgidFound) {
					break;
				}
			}
			// No ha encontrado nada , por lo que genero el mt vacio.
			if (!pdfFound || !vkgidFound) {
				respuesta.append("<Pdf_File/>");
				respuesta.append("<vkgid/>");
			}
			cierraRespuesta(respuesta);

		} catch (PoligrafoException e) {
			e.printStackTrace();
		}
		try {
			os.write(respuesta.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void procesaLinea(String linea, StringBuffer respuesta) {
		
		String id_campo = "";
		String valor ="";
		try {
			id_campo = linea.substring(3, 7);
			if (id_campo != null) {
				if (id_campo.equalsIgnoreCase(ID_CAMPO_PDF)) {
					String[] elementos = linea.split(id_campo);
					valor = elementos.length >= 2 ? elementos[1] : null;
					if (valor != null){
						pdfFound = true;
						respuesta.append("<Pdf_File>"+valor.trim()+"</Pdf_File>");
					}
					return;
				}
				if (id_campo.equalsIgnoreCase(ID_CAMPO_VKGID)) {
					String[] elementos = linea.split(id_campo);
					valor = elementos.length >= 2 ? elementos[1] : null;
					if (valor != null){
						vkgidFound = true;
						respuesta.append("<vkgid>"+valor.trim()+"</vkgid>");
					}
					return;
				}

			}
		} catch (IndexOutOfBoundsException e) {

		}

	}

	/*
	 * 
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * 
	 * <line> <Field/> <Value/> </line> </ns0:MT_INT033_Poligrafo_HDM_Req>
	 */

	private void inicializaRespuesta(StringBuffer respuesta) {
		respuesta.append("<?xml version='1.0' encoding='UTF-8'?>");
		respuesta
				.append("<ns0:MT_INT033_Poligrafo_HDM_Resultats xmlns:ns0='http://poligrafo.hdm.csi.org'>");
	}

	private void cierraRespuesta(StringBuffer respuesta) {
		respuesta
				.append("</ns0:MT_INT033_Poligrafo_HDM_Resultats>");
	}

	@Override
	public void setParameter(Map arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputStream is = null;
		OutputStream os = null;

		try {

			is = new FileInputStream("./examples/resultados.txt");
			os = new FileOutputStream("./examples/resultados.xml");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		File2MessageType transform = new File2MessageType();
		try {
			transform.execute(is, os);
		} catch (StreamTransformationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
