package com.innoppl.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyValues {
	final static Logger logger = Logger.getLogger(PropertyValues.class);	
	public static String getPropValue(String param) {
		 
		String result = "";
		Properties prop = new Properties();
		String propFileName = "config.properties";
		 
		//InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);			
		try {
			InputStream inputStream = PropertyValues.class.getClassLoader().getResourceAsStream("config.properties");
			if (inputStream != null) {
			prop.load(inputStream);
			} 	 
			 
			result = prop.getProperty(param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("property file '" + propFileName + "' not found in the classpath");
		}
		return result;
		}
}
