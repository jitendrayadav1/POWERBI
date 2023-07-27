package com.E_POC2.Utility;


import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties pro;

	public ReadConfig()
	{
		try {

		//	FileInputStream fis=new FileInputStream(".//Configurations//config.properties");
			FileInputStream fis=new FileInputStream(".//Configurations//config.properties");
			pro=new Properties();
			pro.load(fis);

		}catch(Exception e)
		{
          System.out.println("Exception is "+e.getMessage());
		}
	}

	public String getApplicationUrl()
	{
		return pro.getProperty("baseUrl");
	}
	
	public String getEmail()
	{
		return pro.getProperty("email");
	}
	
	public String getPassword()
	{
		return pro.getProperty("password");
	}
	
	public String getExcelInputQuerry()
	{
		return pro.getProperty("excelInputQuerry");
	}
	public String getExcelInputData()
	{
		return pro.getProperty("excelInputData");
	}
	public String getTestCasesSheet()
	{
		return pro.getProperty("TestCasesSheet");
	}
	public String getEmailidsSheet()
	{
		return pro.getProperty("EmailidsSheet");
	}
	public String getSQLQueriesSheet()
	{
		return pro.getProperty("SQLQueriesSheet");
	}
	public String getObjectRepositorySheet()
	{
		return pro.getProperty("ObjectRepositorySheet");
	}
	
	public String getExcelTestData()
	{
		return pro.getProperty("excelTestData");
	}
	
	public String getConnection_string()
	{
		return pro.getProperty("connection_string");
	}
	public String getJira_Mail()
	{
		return pro.getProperty("jira_Mail");
	}
	public String getJira_API_key()
	{
		return pro.getProperty("jira_API_key");
	}
	public String getExcel_Report_File()
	{
		return pro.getProperty("Excel_Report_File");
	}
	public String getSheet_Name()
	{
		return pro.getProperty("Sheet_Name");
	}
	public String getSheet_Name4()
	{
		return pro.getProperty("Sheet4");
	}
	public String getJira_url()
	{
		return pro.getProperty("jira_url");
	}
	
	

}
