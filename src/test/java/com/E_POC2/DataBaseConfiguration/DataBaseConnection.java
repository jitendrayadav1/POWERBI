package com.E_POC2.DataBaseConfiguration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.E_POC2.TestBase.TestBase;
import com.E_POC2.Utility.ReadSQLQueryFromExcel;

public class DataBaseConnection extends TestBase {

	public ReadSQLQueryFromExcel readQueryFromExcel=new ReadSQLQueryFromExcel();
	public  static List<String> visualNamesList=new ArrayList<>();
	public  static List<String> visualDataList=new ArrayList<>();
	public  static List<String> course_nameList=new ArrayList<>();
	

	public void dBConnection(String query1,String col1,String col2) throws SQLException, ClassNotFoundException, IOException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con=DriverManager.getConnection(config.getConnection_string());
	
		String query=query1;

		//String query=readQueryFromExcel.readSQLQuery();

		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);

		visualNamesList.clear();
		visualDataList.clear();

		String str="";
		
		while(rs.next())
		{
			visualNamesList.add(rs.getString(col1));
			str=rs.getString(col2);
		
		  //System.out.println("DBStr : "+str);
			//System.out.println("DBStr_Substring : "+(rs.getString(col2).substring(rs.getString(col2).length()-2, rs.getString(col2).length()-1)));
			
			if((rs.getString(col2).substring(rs.getString(col2).length()-2, rs.getString(col2).length())).equals(".0"))
				str=rs.getString(col2).replace(".0", ".00");
				
			visualDataList.add(str);
		
			
		}
          
		System.out.println("visualNamesList_inDb : "+visualNamesList);
		System.out.println("visualDataList_inDb : "+visualDataList);
		System.out.println("ResultSet : "+rs.toString());
		
	}
	
	public void dBConnection(String query1,String col1,String col2,String col3,
			String col4,String col5,String col6,String col7) throws SQLException, ClassNotFoundException, IOException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con=DriverManager.getConnection(config.getConnection_string());
	
		String query=query1;

		//String query=readQueryFromExcel.readSQLQuery();

		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);

		visualNamesList.clear();
		visualDataList.clear();

		String str="";
		
		while(rs.next())
		{
			visualNamesList.add(col1);		
			visualDataList.add(rs.getString(col1));
			
			visualNamesList.add(col2);		
			visualDataList.add(rs.getString(col2));
			
			visualNamesList.add(col3);		
			visualDataList.add(rs.getString(col3));
			
			visualNamesList.add(col4);		
			visualDataList.add(rs.getString(col4));
			
			System.out.println("Col 5 :"+col5);
			
			if((rs.getString(col5).substring(rs.getString(col5).length()-2, rs.getString(col5).length())).equals(".0"))
			{
				str=rs.getString(col5).replace(".0", ".00");
				visualDataList.add(str);
			}
			else
			visualDataList.add(rs.getString(col5));
			
			visualNamesList.add(col5);		
			
		
			visualNamesList.add(col6);		
			visualDataList.add(rs.getString(col6).replaceAll("\"", ""));
			
			visualNamesList.add(col7);		
			visualDataList.add(rs.getString(col7));
			
		}
          
		System.out.println("visualNamesList_inDb : "+visualNamesList);
		System.out.println("visualDataList_inDb : "+visualDataList);
		System.out.println("ResultSet : "+rs.toString());
		
	}
	
	public void dBConnection(String query1,String col1) throws SQLException, ClassNotFoundException, IOException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con=DriverManager.getConnection(config.getConnection_string());
	
		String query=query1;

		//String query=readQueryFromExcel.readSQLQuery();

		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);

		visualNamesList.clear();
		visualDataList.clear();

		String str="";
		
		while(rs.next())
		{
			visualNamesList.add(col1);		
			str=rs.getString(col1);
				if(!col1.equalsIgnoreCase("Group_student")) {
				if((rs.getString(col1).substring(rs.getString(col1).length()-2, rs.getString(col1).length())).equals(".0"))
					str=rs.getString(col1).replace(".0", ".00");
				}
				visualDataList.add(str);
			
			
			
					
		}
          
		System.out.println("visualNamesList_inDb : "+visualNamesList);
		System.out.println("visualDataList_inDb : "+visualDataList);
		System.out.println("ResultSet : "+rs.toString());
		
	}
}


