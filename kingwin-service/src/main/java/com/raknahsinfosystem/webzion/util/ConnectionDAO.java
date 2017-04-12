package com.raknahsinfosystem.webzion.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.internal.logging.ConsoleLogging;

public class ConnectionDAO {
	private static final Log logger = LogFactory.getLog(ConnectionDAO.class);

		//public static final String URL_CORE_1 = System.getenv("NEO4J_BOLT_CORE1_URL") != null ? System.getenv("NEO4J_BOLT_CORE1_URL") : "bolt://localhost:7687"; //7680
		//public static final String URL_CORE_2 = System.getenv("NEO4J_BOLT_CORE2_URL") != null ? System.getenv("NEO4J_BOLT_CORE2_URL") : "bolt://localhost:7688"; //7680
		//public static final String URL_CORE_3 = System.getenv("NEO4J_BOLT_CORE3_URL") != null ? System.getenv("NEO4J_BOLT_CORE3_URL") : "bolt://localhost:7689"; //7680
		
	public static Connection getMySqlConnection(String mode){
		Connection conn=null;
		try {
			ConfigurationProperties conf = new ConfigurationProperties();
			if(mode.equals("mysql")){
//				Class.forName(conf.getConfigValue("MySQL_JDBC_DRIVER"));
				conn = DriverManager.getConnection(conf.getConfigValue("MySQL_DB_URL"),conf.getConfigValue("MySQL_USER"),conf.getConfigValue("MySQL_PASS"));
			}
			else if(mode.equals("oracle")){
				try{
					Class.forName(conf.getConfigValue("ORACLE_JDBC_DRIVER"));
					conn = DriverManager.getConnection(conf.getConfigValue("ORACLE_DB_URL"),conf.getConfigValue("ORACLE_USER"),conf.getConfigValue("ORACLE_PASS"));
				}
				catch(ClassNotFoundException e){
					System.out.println(e);
				}
			}
			else {
//				Class.forName(conf.getConfigValue("MySQL_JDBC_DRIVER"));
				conn = DriverManager.getConnection(conf.getConfigValue("MySQL_DB_URL"),conf.getConfigValue("MySQL_USER"),conf.getConfigValue("MySQL_PASS"));
			}

		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return conn; 
	}
	public static Connection getDataSourceConnection(JSONObject datasource_credentials) throws Exception{
		Connection conn=null;
		ConfigurationProperties conf = new ConfigurationProperties();
		try {
			if((datasource_credentials.getString("ds_type")).equalsIgnoreCase("oracle")){
				Class.forName(conf.getConfigValue("ORACLE_JDBC_DRIVER"));
			}
			String DbUrl=datasource_credentials.getString("ds_url");
			String Db_userName= datasource_credentials.getString("ds_userName");
			String Db_pwd=datasource_credentials.getString("ds_password");
			conn = DriverManager.getConnection(DbUrl,Db_userName,Db_pwd);
			System.out.println(conn);
		}catch(Exception e){
			logger.error("Exception Occured", e);
		}
		return conn; 
	}
	
	/*@Bean
    public org.neo4j.ogm.config.Configuration interfaceAppConfig() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setCredentials(USERNAME, PASSWORD)
                .setURI(URL);
        return config;
    } */
	
}
