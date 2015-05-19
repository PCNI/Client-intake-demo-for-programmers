package com.innoppl.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;


public class ClientHelper {
	final static Logger logger = Logger.getLogger(ClientHelper.class);	
	
	String username = PropertyValues.getPropValue("username");
	String password = PropertyValues.getPropValue("password");
	String serverName = PropertyValues.getPropValue("serverAddress");
	String port = PropertyValues.getPropValue("serverPort");
	
	public String BuildServerUrl(String username, String password){
		
		String url = "";

			url = "http://"+serverName+":"+port+"/OpenHMIS-master/services/clients/lastName/Smith/"+username+"/"+password;			
		return url;
		
	}
	
public String BuildServerUrlforSearch(String search){
		
		String url = "";
		url = "http://"+serverName+":"+port+"/OpenHMIS-master/services/clients/lastName/"+search+"/"+username+"/"+password;			
		return url;
	}
	

public String BuildServerUrlforSearchResults(String clientKey){
	
	String url = "";
	url = "http://"+serverName+":"+port+"/OpenHMIS-master/services/clients/clientdetail"+clientKey+"/"+username+"/"+password;	
     //http://localhost:8080/OpenHMIS-master/services/clients/clientdetail/76392/judy.whiddon/welcome
	return url;
}

public NodeList getNodeList (String xml, String tagName) throws SAXException, IOException, ParserConfigurationException{
	
	Document doc = DocumentBuilderFactory.newInstance()
			.newDocumentBuilder()
			.parse(new InputSource(new StringReader(xml)));
				
	doc.getDocumentElement().normalize();

	NodeList nList = doc.getElementsByTagName(tagName);
	return nList;
}

	public String getHTML(String urlToRead) {
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader rd = null;
		String line = null;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (FileNotFoundException file_not_found_ex) {
			logger.error("Webservice Server down ..Please try again later.(Reading URL is Problem)."
					+ file_not_found_ex.getMessage());
		}
		catch (IOException io_ex) {
			logger.error("File IO Exception occured while  reading  file( failed or interrupted) : "
							+ io_ex.getMessage());
		} catch (Exception all_ex) {
			logger.error("Error due to  : " + all_ex.getMessage());
		}
		return result;
	}

}
