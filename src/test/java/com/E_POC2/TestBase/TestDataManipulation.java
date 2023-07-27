package com.E_POC2.TestBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.E_POC2.TestCases.PowerBIDashBoardTest1;

public class TestDataManipulation {

	static List<String> testData=new ArrayList<>();
	static List<String> tList=new ArrayList<>();
	static List<String> tHeading=new ArrayList<>();
	static List<String> tTestData=new ArrayList<>();
	static List<String> prevTestData=new ArrayList<>();
	static int dataColCount=0;
	static Object[][] data1;

	public static String monthDynamicQuery(String courseName1,String subjectName1,String studentName1)
	{
		 String query= "SELECT \r\n"
				+ "    [MONTH] AS attendance_month,\r\n"
				+ "    ROUND(CAST(COUNT(CASE WHEN [Attendance] = 'P' THEN [Attendance_id] END) * 100.00 /\r\n"
				+ "        COUNT([Attendance_id]) AS FLOAT),2) AS attendance\r\n"
				+ "FROM   [dbo].[Fact_Attendance]\r\n"
				+ "    WHERE  [Course_id]=(select Course_id from [POC2].[dbo].[dim_course]\r\n"
				+ " where Course_name='"+courseName1+"') AND [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject]\r\n"
				+ " where Subject_name='"+subjectName1+"') \r\n"
				+ " AND[Fact_Attendance].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student]\r\n"
				+ " where Student_name='"+studentName1+"') \r\n"
				+ " GROUP BY [MONTH] ORDER BY [MONTH] DESC";
		
		return query;
	}
	
	public static String LowAttendanceReasonDynamicQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="\r\n"
				+ "\r\n"
				+ "DECLARE @Absent1  nvarchar(100)\r\n"
				+ "\r\n"
				+ "     Set @Absent1=(SELECT COUNT(Attendance)   FROM [dim_student],[Fact_Attendance]\r\n"
				+ "\r\n"
				+ "       WHERE \r\n"
				+ "\r\n"
				+ "	   [dim_student].[Enrollment_No]=[Fact_Attendance].[Enrollment_No] \r\n"
				+ "\r\n"
				+ "	   AND\r\n"
				+ "\r\n"
				+ "     [Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "\r\n"
				+ "	 and\r\n"
				+ "\r\n"
				+ "	 [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "\r\n"
				+ "	 and\r\n"
				+ "\r\n"
				+ "	 [Fact_Attendance].[Enrollment_No]=(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"') \r\n"
				+ "\r\n"
				+ "\r\n"
				+ "	 AND Attendance='A')\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "	 select [Attendance_Remarks],ROUND(CAST(((count([Attendance_Remarks])*100.00)/@Absent1) AS FLOAT),2) AS Percentage\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "	 from [Fact_Attendance]\r\n"
				+ "\r\n"
				+ "	 where \r\n"
				+ "\r\n"
				+ "	 [Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "\r\n"
				+ "	 and\r\n"
				+ "\r\n"
				+ "	 [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "\r\n"
				+ "	 and\r\n"
				+ "\r\n"
				+ "	 [Enrollment_No]=(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"')\r\n"
				+ "\r\n"
				+ "	 and [Attendance_Remarks]!='Present'\r\n"
				+ "\r\n"
				+ "	 group by [Attendance_Remarks]";
	
		
		return query;
	}

	public static String gridPerformanceQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="\r\n"
				+ "DECLARE @TotalPrice  nvarchar(100)\r\n"
				+ "Set @TotalPrice =(SELECT COUNT([Attendance]) \r\n"
				+ "    FROM [POC2].[dbo].[dim_student],[POC2].[dbo].[Fact_Attendance]\r\n"
				+ "    WHERE [dim_student].[Enrollment_No]=[Fact_Attendance].[Enrollment_No] AND\r\n"
				+ "[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"') \r\n"
				+ "AND \r\n"
				+ "[Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "AND\r\n"
				+ "[Fact_Attendance].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"')\r\n"
				+ "AND \r\n"
				+ "[Attendance]='P')\r\n"
				+ "SELECT Distinct [Student_name] As Student_Name,[Subject_name] as Subject_Name,[Marks_Obtained] As Marks_Obtained,[Total_Marks] as Total_Marks,\r\n"
				+ " ROUND(CAST(((@TotalPrice)*100.0)/150 AS FLOAT),2)AS 'Attendance_percentage',[Exam_Remark] as 'Remark',[Fact_Attendance].[Enrollment_No]\r\n"
				+ "       FROM [POC2].[dbo].[dim_student],[POC2].[dbo].[dim_subject]\r\n"
				+ "    ,[POC2].[dbo].[Fact_Attendance],[POC2].[dbo].[fact_exam_new],[POC2].[dbo].[dim_course]\r\n"
				+ "    WHERE [dim_student].[Enrollment_No]=[Fact_Attendance].[Enrollment_No] AND\r\n"
				+ " [dim_student].[Enrollment_No]=[fact_exam_new].[Enrollment_No] AND\r\n"
				+ "[dim_course].[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"') \r\n"
				+ "AND \r\n"
				+ "[dim_subject].[Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"') \r\n"
				+ "AND \r\n"
				+ "[fact_exam_new].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"') \r\n"
				+ "AND \r\n"
				+ "[sem]=(select [sem] from [POC2].[dbo].[Dim_Subject] \r\n"
				+ "where [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"'))\r\n"
				+ " \r\n"
				+ "AND \r\n"
				+ "[Marks_Obtained]=(select [Marks_Obtained] from [POC2].[dbo].[fact_exam]\r\n"
				+ "where \r\n"
				+ "[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "and \r\n"
				+ "[Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "and \r\n"
				+ "[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student]\r\n"
				+ "	 where Student_name='"+studentName1+"'));";
		
		
		
		return query;
	}
	
	public static String charPerformanceQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="\r\n"
				+ "SELECT [Exam_Remark]\r\n"
				+ "    FROM [POC2].[dbo].[dim_student],[POC2].[dbo].[fact_exam_new]\r\n"
				+ "    WHERE [dim_student].[Enrollment_No]=[fact_exam_new].[Enrollment_No] \r\n"
				+ "	AND\r\n"
				+ "[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"') \r\n"
				+ "AND \r\n"
				+ "[Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"') \r\n"
				+ "AND\r\n"
				+ "[fact_exam_new].[Enrollment_No]=(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"')";
		
		return query;
	}
	public static String marksObtainedQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="SELECT TOP (1000)   [Marks_Obtained]\r\n"
				+ "		    FROM [POC2].[dbo].[fact_exam_new],[POC2].[dbo].[dim_student]\r\n"
				+ "		    WHERE [dim_student].[Enrollment_No]=[fact_exam_new].[Enrollment_No] \r\n"
				+ "		AND [Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "		AND [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"') \r\n"
				+ "		AND [fact_exam_new].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"');";
		
		return query;
	}
	
	public static String totalMarksQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="SELECT DISTINCT [Total_Marks]\r\n"
				+ " FROM [POC2].[dbo].[fact_exam_new],[POC2].[dbo].[dim_student]\r\n"
				+ " WHERE [dim_student].[Enrollment_No]=[fact_exam_new].[Enrollment_No] \r\n"
				+ " AND[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"') \r\n"
				+ " AND [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"');";
		
		return query;
	}
	
	public static String marks_PercentageQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="SELECT [Marks_Obtained]*100/([Total_Marks]) as Percentage\r\n"
				+ "    FROM [POC2].[dbo].[fact_exam_new],[POC2].[dbo].[dim_student]\r\n"
				+ "    WHERE [dim_student].[Enrollment_No]=[fact_exam_new].[Enrollment_No] \r\n"
				+ "	AND[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "	AND [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "	AND [fact_exam_new].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"');\r\n"
				+ "";
		
		return query;
	}
	
	public static String attendance_PercentageQuery(String courseName1,String subjectName1,String studentName1)
	{
		String query="DECLARE @TotalPrice1  nvarchar(100)\r\n"
				+ "Set @TotalPrice1 =(SELECT COUNT([Attendance]) \r\n"
				+ "FROM [POC2].[dbo].[dim_student],[POC2].[dbo].[Fact_Attendance]\r\n"
				+ "WHERE [dim_student].[Enrollment_No]=[Fact_Attendance].[Enrollment_No] \r\n"
				+ "AND[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "AND [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "AND[Fact_Attendance].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"')\r\n"
				+ "AND [Attendance]='P')\r\n"
				+ "SELECT ROUND(CAST(((@TotalPrice1)*100.0)/150 AS FLOAT),2) As Attendance_Percentage;";
		
		return query;
	}


	public static String groupName(String courseName1,String subjectName1,String studentName1)
	{
		String query="SELECT DISTINCT [Group_student]\r\n"
				+ "			FROM [POC2].[dbo].[fact_exam_new] ,\r\n"
				+ "			[POC2].[dbo].[dim_student] WHERE\r\n"
				+ "			[fact_exam_new].[Enrollment_No]=[dim_student].[Enrollment_No]\r\n"
				+ "			AND [Group_student]=(select [Group_student] from [POC2].[dbo].[fact_exam_new]\r\n"
				+ "			where\r\n"
				+ "			[Course_id]=(select Course_id from [POC2].[dbo].[dim_course] where Course_name='"+courseName1+"')\r\n"
				+ "			AND [Subject_id]=(select Subject_id from [POC2].[dbo].[Dim_Subject] where Subject_name='"+subjectName1+"')\r\n"
				+ "			AND [fact_exam_new].[Enrollment_No] =(select Enrollment_No from [POC2].[dbo].[dim_student] where Student_name='"+studentName1+"'));\r\n"
				+ "";
		
		return query;
	}

	
	
		

	public static List<String> getSortedTestDataOfExcel(Object[][] data,int colNum)
	{
		int count=0;
		int col=3;
		int rowcount=1;
	

		for(int i=1;i<data.length;i++)
		{
			for(int j=0;j<data[i].length;j++)
			{  
				if(j==colNum) {
					System.out.println("i : "+i+" j : "+j);
					System.out.println("Object Data : "+data[i][j]);
					testData.add((String)data[i][j]);
					break;
				}
				/*if(j==colNum) {
					System.out.println("i : "+i+" j : "+j);
					System.out.println("Object Data : "+data[i][j]);
					testData.add((String)data[i][j]);
					break;
				}*/
			}
			//System.out.println("i : "+i+" col : "+col);
			//System.out.println("Object Data : "+data[i][col]);
			//testData.add((String)data[i][col]);

		}
		/*
		dataColCount=testData.get(1).split(">").length;
		System.out.println("data.length : "+data.length);
		data1=new Object[dataColCount][data.length-1];*/
	//	getRespectiveTestDataList(testData,data1);
		return testData;

	}
	
	public static void prevDataClearanceFromList()	
	{
		PowerBIDashBoardTest1.dropDownHeading.clear();
		PowerBIDashBoardTest1.dropDownData.clear();
		PowerBIDashBoardTest1.queryHeading.clear();
		PowerBIDashBoardTest1.queryDBDataList.clear();
		
		PowerBIDashBoardTest1.vNamesList.clear();
		PowerBIDashBoardTest1.vDataList.clear();
		PowerBIDashBoardTest1.vXpathHeading.clear();
		PowerBIDashBoardTest1.vDataXpath.clear();	
		
		PowerBIDashBoardTest1.dashBoard_DataPerVisuals.clear();
		PowerBIDashBoardTest1.dataBase_DataPerVisuals.clear();
		
		
	}
		

	public static void getRespectiveTestDataList1(List<String> testData,int testCaseRowNum,List<String> headingList,List<String> valueList) throws InterruptedException
	{
		int count=0;
		valueList.clear();
		headingList.clear();
		String dropName;
		//String str1=testDataStr.replace("<", "");
		String[] str=testData.get(testCaseRowNum).split(">>");

		System.out.println("Str.lenght : "+str.length);

		for(int i=0;i<str.length;i++)
		{
			System.out.println("String data : "+str[i]);
			String[] str11=str[i].split("=<<");
			//getRespectiveTestDataList(str11,count);

			for(int j=0;j<str11.length;j++)
			{
				if(j==0) {
					dropName=str11[j].trim();

					if(!dropName.equalsIgnoreCase("Teacher Name"))
						dropName=dropName.replace(" ", "_");
					System.out.println("dropName : "+dropName);
					//dropDownHeading.add(str11[j]);
					headingList.add(dropName);
				}
				else		
					valueList.add(str11[j]);

			}


		}

		System.out.println("tHeading1 : "+headingList);
		System.out.println("TestDataList1 : "+valueList);
		System.out.println("getRespectiveTestDataList1");

		//	System.out.println("TestDataList : "+tdata);

	}
	
	public static HashMap<String, String> listToHashMap(List<String> v_nameList,List<String> v_dataList)
	{
		HashMap<String,String> map=new HashMap<>();


		for( int i=0;i<v_nameList.size();i++)
		{
			map.put(v_nameList.get(i), v_dataList.get(i));
		}
		//System.out.println("Maps : "+map);
		return map;

	}
	
	public static void testDataOperation(Object[][] data,int colNum,int testDataListIndex,List<String> vHeadingList,List<String> vDataList) throws InterruptedException
	{
		testData.clear();
		testData=getSortedTestDataOfExcel(data,colNum);
		getRespectiveTestDataList1(testData, testDataListIndex, vHeadingList, vDataList);
	}
	

}
