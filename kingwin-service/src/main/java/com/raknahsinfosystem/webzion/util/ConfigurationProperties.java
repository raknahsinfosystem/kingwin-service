package com.raknahsinfosystem.webzion.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigurationProperties {
	final static Logger logger = Logger.getLogger(ConfigurationProperties.class);

	
	public String getConfigValue(String key) throws IOException{
		InputStream inputStream = null;		
		String propertyValue = null;
		try {
			//Find the environment Local or Comcast
			Properties prop = new Properties();
			String propFileName = "application-dev.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			logger.debug(propFileName+"is the name of the Property File");
			
			if (prop.getProperty(key) != null) {
				logger.debug("Property Key is: " +key);
				propertyValue = prop.getProperty(key);
			}
			else if (prop.getProperty(key).equalsIgnoreCase(null))
			{
				logger.debug("----------N U L L property key received----------");
			}

//			logger.debug("Property Value is: " +propertyValue);
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
		} finally {
			inputStream.close();
		}
		return propertyValue;
	}
	
	
	public String getEnvironmentName(){
		
		String envName = null; 
    	try {
    		envName = "dev";
			System.out.println("Environment Name is: " +envName );
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("Either  manifest file not found or NULL environment Name returned or Incorrect location of manifest file");
		e.printStackTrace();
	}
    	return envName;
	}
	
	
	public String getApplicationPropertyFileName(String envName){
		
		String applicationPropertyFileName = null; 
		applicationPropertyFileName = "application-dev.properties";
		System.out.println("Environment File Name is: " +applicationPropertyFileName );
    	return applicationPropertyFileName;
	}

	public String getAgentConfigFileName(String envName){
		
		String agentConfigFileName = null; 
		agentConfigFileName = "agent-config-dev.txt";
		System.out.println("Agent Config File Name is: " +agentConfigFileName);
    	return agentConfigFileName;
	}

	public String getCEAppProperties(String key) throws IOException {
		InputStream inputStream = null;		
		String uri = "";		
		String env = getEnvironmentName();
		String appPropFileName = getApplicationPropertyFileName(env);
		
		try {				
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(appPropFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + appPropFileName + "' not found in the classpath");
			}
			uri = prop.getProperty(key);	
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
		} finally {
			inputStream.close();
		}
		return uri;
	}
	
	public String getConfigMgmtProperties() throws IOException{
		String configValues=null;
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configuration.json");
		try {
			if (inputStream != null) {
				    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			        StringBuilder out = new StringBuilder();
			        String line;
			        while ((line = reader.readLine()) != null) {
			            out.append(line);
			        }
			        configValues=out.toString();
			        reader.close();
			}else {
				throw new FileNotFoundException("property file configuration.json not found in the classpath");
			}
		} catch (FileNotFoundException e) {
			logger.error("Exception Occured: " + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			inputStream.close();
		}
		return configValues;
	}

	public String saveConfigMgmtProperties(String obj) {
		String status="failure";
		URL url = getClass().getClassLoader().getResource("configuration.json");
		File configfile =null ;
		FileWriter configWriter =null;
		try {
			configfile = new File(url.toURI().getPath());
			configWriter = new FileWriter(configfile, false);
			configWriter.write(obj);
			configWriter.close();
			status="Success";
		} catch (IOException e) {
			status="failure";
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			status="failure";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}


}