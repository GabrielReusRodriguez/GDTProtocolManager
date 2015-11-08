package gdtManager.utils;

import gdtManager.exceptions.GDTException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

/**
 * Various XML utilities.
 * 
 * @author simonjsmith, ksim
 * @version 1.1 - ksim - March 6th, 2007 - Added functions regarding streaming
 * @version 1.2 - ksim - March 10th, 2007 - Added functions regarding DOM
 *          manipulation
 */
public abstract class Utils {
	public static Document newDocumentFromInputStream(InputStream in) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			ret = builder.parse(new InputSource(in));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String newStringFromInputStream(InputStream is)
			throws GDTException {

		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {

				Reader reader = new BufferedReader(new InputStreamReader(is));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
				is.close();
				return writer.toString();

			} catch (IOException e) {
				throw new GDTException(e.getMessage());
			}
		} else {
			return "";
		}

	}

	public static InputStream InputStreamFromString(String inputString) {

		InputStream inputStream = null;
		inputStream = new ByteArrayInputStream(inputString.getBytes());
		// InputStream is = new
		// ByteArrayInputStream(Charset.forName("UTF-16").encode(myString()).array());
		return inputStream;
	}

	public static InputStream InputStreamFromString(String inputString,String encode) {

		InputStream inputStream = null;
		inputStream = new ByteArrayInputStream(Charset.forName(encode).encode(inputString).array());
		return inputStream;
	}
}
