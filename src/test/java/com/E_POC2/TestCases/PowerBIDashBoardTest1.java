package com.E_POC2.TestCases;


import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.E_POC2.DataBaseConfiguration.DataBaseConnection;
import com.E_POC2.PageObject.PowerBIDashBoard;
import com.E_POC2.TestBase.TestBase;
import com.E_POC2.TestBase.TestDataManipulation;
import com.E_POC2.Utility.Demo_ExtentReport;
import com.E_POC2.Utility.EmailOfTestExecution;
import com.E_POC2.Utility.JiraPolicy;
import com.E_POC2.Utility.ReadFromExcel;
import com.E_POC2.Utility.setResultToExcel;

public class PowerBIDashBoardTest1 extends TestBase{

	DataBaseConnection dbCon=new DataBaseConnection();
	//static PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
	

    public 	static List<String> dropDownHeading=new ArrayList<>();
    public static List<String> testData=new ArrayList<>();
    public static List<String> dropDownData=new ArrayList<>();
    public static List<String> prevdropDownData=new ArrayList<>();

    public static List<String> vNamesList=new ArrayList<>();
    public static List<String> vDataList=new ArrayList<>();
    public static List<String> vXpathHeading=new ArrayList<>();
    public static List<String> vDataXpath=new ArrayList<>();

/*	
	static List<String> lowAttendanceNamesList=new ArrayList<>();
	static List<String> lowAttendanceperList=new ArrayList<>();
	static List<String> lowAttendanceXpathHeading=new ArrayList<>();
	static List<String> lowAttendanceDataXpath=new ArrayList<>();

	static List<String> gridHeadingList=new ArrayList<>();
	static List<String> gridContentList=new ArrayList<>();
	static List<String> gridXpathHeading=new ArrayList<>();
	static List<String> gridDataXpath=new ArrayList<>();

	static List<String> charHeadingList=new ArrayList<>();
	static List<String> charContentList=new ArrayList<>();
	static List<String> charXpathHeading=new ArrayList<>();
	static List<String> charDataXpath=new ArrayList<>();
	
	static List<String> MarksHeadingList=new ArrayList<>();
	static List<String> MarksContentList=new ArrayList<>();
	static List<String> MarksXpathHeading=new ArrayList<>();
	static List<String> MarksDataXpath=new ArrayList<>();
*/

	public static List<String> queryDBDataList=new ArrayList<>();
	public static List<String> queryHeading=new ArrayList<>();
	public static List<String> queriesList=new ArrayList<>();
	public static HashMap<String,String> dashBoard_DataPerVisuals=new HashMap<String,String>();
	public static HashMap<String,String> dataBase_DataPerVisuals=new HashMap<String,String>();


	static int dataColCount=0;
	static Object[][] data1;
	static Object[][] data;

	@JiraPolicy(logTicketReady = true)
	@Test
	public void loginAndDropDownSelection() throws InterruptedException, ClassNotFoundException, SQLException, IOException {

		Demo_ExtentReport e = new Demo_ExtentReport();

		//  dbCon.dBConnection();

		data=ReadFromExcel.readExcelData(config.getTestCasesSheet());

		//	testData=TestDataManipulation.getSortedTestDataOfExcel(data,3);

		/*dataColCount=testData.get(1).split(">").length;
		System.out.println("data.length : "+data.length);
		data1=new Object[dataColCount][data.length-1];*/

		/*
		driver.get(baseUrl);

		PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);

		Thread.sleep(3000);
		pBIDashBoard.getEmailId().sendKeys(config.getEmail());
		pBIDashBoard.getEmailIdFrameSubmit().click();

		Thread.sleep(6000);
		pBIDashBoard.getPassword().sendKeys(config.getPassword());
		pBIDashBoard.getPasswordAndStaySignInButton().click();
		Thread.sleep(3000);
		pBIDashBoard.getPasswordAndStaySignInButton().click();

		Thread.sleep(6000);
		Thread.sleep(6000);
*/
		
	}
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection")
	public void monthPerformance() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		//getRespectiveTestDataList1(testData);
		System.out.println("into month test case..............");
	    PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
		//TestDataManipulation.testDataOperation( data, 3, 0, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
	
		//TestDataManipulation.testDataOperation( data, 4, 0, vXpathHeading, vDataXpath);
				
		 String monthName="svg[name$='Line and stacked column chart'] text[class='setFocusRing']";
		 String monthPer="svg[name$='Line and stacked column chart'] text[class='label']";
		
		System.out.println("Month Performance........");
		lineAndStackedOrStackedBarChart( vNamesList, vDataList, monthName, monthPer);
		

		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);

		System.out.println("MnList : "+vNamesList);
		System.out.println("PerList : "+vDataList);
		
	//	TestDataManipulation.testDataOperation( data, 5, 0, queryHeading, queriesList);
		
		String query=TestDataManipulation.monthDynamicQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		
		dbCon.dBConnection(query,"attendance_month","attendance");		
		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);
	   dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);

	   //after removal three columns like testdata,ui_locatore,sqlquries from excel(1,4,5,6) 
	   //executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 1, 4,5,6 );
	   executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 1, 4, 5, 6 );
	   assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
	  

		System.out.println("Assert Successfull.....");

	}
	

	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 1)
	public void lowAttendancePerformance() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		
	    PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
		
		//TestDataManipulation.testDataOperation( data, 3, 1, dropDownHeading, dropDownData);	
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);

		//TestDataManipulation.testDataOperation( data, 4, 1, vXpathHeading, vDataXpath);
		System.out.println("Low Attendance Performance........");
		
	
		 String lowAtt_Reason="svg[name='Stacked bar chart'] text[class='setFocusRing']";
		 String lowAtt_ReasonPer="svg[name='Stacked bar chart'] text[class='label']";

		lineAndStackedOrStackedBarChart( vNamesList, vDataList, lowAtt_Reason, lowAtt_ReasonPer);

		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);

		System.out.println("lowAttendanceNamesList : "+vNamesList);
		System.out.println("lowAttendanceperList : "+vDataList);

		//TestDataManipulation.testDataOperation( data, 5, 1, queryHeading, queriesList);
		
		String query=TestDataManipulation.LowAttendanceReasonDynamicQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Attendance_Remarks","Percentage");
		
		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);
	   dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);

	   executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 2, 4, 5, 6 );
	   assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
		System.out.println("Assert Successful...");

	}

	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 2)
	public void GirdPerformance() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		 PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
	
		//TestDataManipulation.testDataOperation( data, 3, 2, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
			
	//	TestDataManipulation.testDataOperation( data, 4, 2, vXpathHeading, vDataXpath);
		
		System.out.println("Grid Performance........");
		//String 
		

		String gridHeading="div[role=columnheader]";
		String gridData="div[role=gridcell]";
		TestBase.readGridData(vNamesList, vDataList, gridHeading,gridData);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);

		
		//TestDataManipulation.testDataOperation( data, 5, 2, queryHeading, queriesList);		
		String query=TestDataManipulation.gridPerformanceQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Student_Name","Subject_Name","Marks_Obtained","Total_Marks",
				"Attendance_percentage","Remark","Enrollment_No");
		
		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 3, 4, 5, 6 );
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
		
		

	}
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 3)
	public void characteristicPerformance() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		
		 PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
	
		//TestDataManipulation.testDataOperation( data, 3, 3, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
		//TestDataManipulation.testDataOperation( data, 4, 3, vXpathHeading, vDataXpath);
		System.out.println("Char Performance........");
		
		Thread.sleep(3000);
		
		String charFrame="iframe[class=visual-sandbox]";
		String charData="div[id=sandbox-host] g[class=word] text:nth-of-type(1)";
		
		List<String> charListCmp=readDashBoardCharacteristicData( vNamesList, vDataList,  charFrame, charData);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);
 
		//TestDataManipulation.testDataOperation( data, 5, 3, queryHeading, queriesList);
		String query=TestDataManipulation.charPerformanceQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Exam_Remark");

		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);

		readDataBaseCharacteristicData(charListCmp);
		
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 4, 4, 5, 6 );	
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);

	}
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 4)
	public void Marks_Obtained() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		 PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
		//TestDataManipulation.testDataOperation( data, 3, 4, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
		//TestDataManipulation.testDataOperation( data, 4, 4, vXpathHeading, vDataXpath);
		System.out.println("Marks Obtained Performance........");
		
		Thread.sleep(3000);
	
		String marksObtained="//*[local-name()='svg' and contains(@aria-label,'Sum of Marks_Obtained')]//*[local-name()='tspan']";
		//System.out.println("VdataxpathMarks : "+vDataXpath.get(0)); 
		upperVisualData( vNamesList, vDataList, "Marks_Obtained", marksObtained);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);
 
		//TestDataManipulation.testDataOperation( data, 5, 4, queryHeading, queriesList);
		String query=TestDataManipulation.marksObtainedQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Marks_Obtained");

		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);

		//readDataBaseCharacteristicData(charListCmp);
		
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 5, 4, 5, 6 );	
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);

		System.out.println("Assert Successfull...");
		
		
	}
	
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 5)
	public void total_Marks() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		 PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
	//	TestDataManipulation.testDataOperation( data, 3, 5, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
		//TestDataManipulation.testDataOperation( data, 4, 5, vXpathHeading, vDataXpath);
		System.out.println("total Marks Performance........");
		
		Thread.sleep(3000);
		//System.out.println("VdataxpathMarks : "+vDataXpath.get(0)); 
		
		String totalMarks="//*[local-name()='svg' and contains(@aria-label,'Sum of Total_Marks')]//*[local-name()='tspan']";
		upperVisualData( vNamesList, vDataList, "Total_Marks", totalMarks);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);
 
		//TestDataManipulation.testDataOperation( data, 5, 5, queryHeading, queriesList);
		String query=TestDataManipulation.totalMarksQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Total_Marks");

		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);

		//readDataBaseCharacteristicData(charListCmp);
		
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 6, 4, 5, 6 );	
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
		System.out.println("Assert Successful...");
		
	}
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 6)
	public void marks_Percentage() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		 PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
		//TestDataManipulation.testDataOperation( data, 3, 6, dropDownHeading, dropDownData);
	//	TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
		//TestDataManipulation.testDataOperation( data, 4, 6, vXpathHeading, vDataXpath);
		System.out.println("Marks Percentage Performance........");
		
		Thread.sleep(3000);
		//System.out.println("VdataxpathMarks : "+vDataXpath.get(0));
		//Marks_Percentage=<<>>
		String marks_per="//*[local-name()='svg' and contains(@aria-label,'Percentage')]//*[local-name()='tspan']";
		upperVisualData( vNamesList, vDataList, "Percentage", marks_per);
		
		System.out.println("vNamsList : "+vNamesList);
		System.out.println("vDataList : "+vDataList);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);
 
	//	TestDataManipulation.testDataOperation( data, 5, 6, queryHeading, queriesList);
		String query=TestDataManipulation.marks_PercentageQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Percentage");

		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);

		//readDataBaseCharacteristicData(charListCmp);
		
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 7, 4, 5, 6 );	
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
		System.out.println("Assert Successful...");
		
	}
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 7)
	public void attendance_Percentage() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		 PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
		//TestDataManipulation.testDataOperation( data, 3, 7, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
		//TestDataManipulation.testDataOperation( data, 4, 7, vXpathHeading, vDataXpath);
		System.out.println("Ateendance Percentage Performance........");
		
		Thread.sleep(3000);
		//System.out.println("VdataxpathMarks : "+vDataXpath.get(0));
		
		String att_Per="//*[local-name()='svg' and contains(@aria-label,'attendence_percentage')]//*[local-name()='tspan']";
		upperVisualData( vNamesList, vDataList, "Attendance_Percentage", att_Per);
		
		System.out.println("vNamsList : "+vNamesList);
		System.out.println("vDataList : "+vDataList);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);
 
		//TestDataManipulation.testDataOperation( data, 5, 7, queryHeading, queriesList);
		String query=TestDataManipulation.attendance_PercentageQuery(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Attendance_Percentage");

		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);

		//readDataBaseCharacteristicData(charListCmp);
		
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 8, 4, 5, 6 );	
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
		System.out.println("Assert Successful...");

		
	}
	
	
	
	@JiraPolicy(logTicketReady = true)
	@Test(dependsOnMethods ="loginAndDropDownSelection",priority = 8)
	public void Group_Marking() throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		PowerBIDashBoard pBIDashBoard=new PowerBIDashBoard(driver);
		TestDataManipulation.prevDataClearanceFromList();
		
	//	TestDataManipulation.testDataOperation( data, 3, 8, dropDownHeading, dropDownData);
		//TestBase.dropDownIteration(dropDownHeading,dropDownData,prevdropDownData);
		
		//TestDataManipulation.testDataOperation( data, 4, 8, vXpathHeading, vDataXpath);
		System.out.println("Group Marking Performance........");
		
		Thread.sleep(3000);
		//System.out.println("VdataxpathMarks : "+vDataXpath.get(0)); 
		//Attendance_Percentage=<<>>
		String groupMarking="(//*[local-name()='svg' and contains(@aria-label,'Group')]//*[local-name()='tspan'])[1]";
		upperVisualData( vNamesList, vDataList, "Group_student", groupMarking);
		
		System.out.println("vNamsList : "+vNamesList);
		System.out.println("vDataList : "+vDataList);
		
		dashBoard_DataPerVisuals=TestDataManipulation.listToHashMap(vNamesList, vDataList);
		
		System.out.println("dashBoard_DataPerVisuals : "+dashBoard_DataPerVisuals);
 
		//TestDataManipulation.testDataOperation( data, 5, 8, queryHeading, queriesList);
		String query=TestDataManipulation.groupName(pBIDashBoard.getCourseName().getText(),pBIDashBoard.getSubjectName().getText(), pBIDashBoard.getStudentName().getText());
		dbCon.dBConnection(query,"Group_student");

		System.out.println("Sorted List : "+DataBaseConnection.visualNamesList);

		//readDataBaseCharacteristicData(charListCmp);
		
		dataBase_DataPerVisuals=TestDataManipulation.listToHashMap(DataBaseConnection.visualNamesList, DataBaseConnection.visualDataList);
		System.out.println("dataBase_DataPerVisuals : "+dataBase_DataPerVisuals);

		executionResult_setToExcel(dashBoard_DataPerVisuals, dataBase_DataPerVisuals, 9, 4, 5, 6 );	
		assertEquals(dashBoard_DataPerVisuals,dataBase_DataPerVisuals);
		System.out.println("Assert Successful..");

		
	}


/*
	public HashMap<String, String> listToHashMap(List<String> v_nameList,List<String> v_dataList)
	{
		HashMap<String,String> map=new HashMap<>();


		for( int i=0;i<v_nameList.size();i++)
		{
			map.put(v_nameList.get(i), v_dataList.get(i));
		}
		//System.out.println("Maps : "+map);
		return map;

	}*/
	/*
	public static void dropDownSelection1(WebDriver driver,List<String> dropDownHeading,List<String> dropDownData) throws InterruptedException
	{
		Thread.sleep(6000);
		List<WebElement> dropDownList;
		String dropDownName="";
		String expVal="";
		int count=0;
		while(count!=dropDownHeading.size()) 
		{
			dropDownName=dropDownHeading.get(count);
			expVal=dropDownData.get(count);
			System.out.println("expVal : "+expVal);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@class='slicer-dropdown-menu' and @aria-label='"+dropDownName+"']/i")).click();
			Thread.sleep(3000);
			if(dropDownName.equalsIgnoreCase("Year")) 
			{
				dropDownName=dropDownName.replace("Year", "Date Year");
				dropDownList=driver.findElements(By.xpath("//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span"));
			}
			else
				dropDownList=driver.findElements(By.xpath("//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span"));
			//String selVal="G P Mumbai";
			List<String> textList=new ArrayList<>();
			for(WebElement ele : dropDownList)
			{
				textList.add(ele.getText());
			}
			System.out.println(dropDownName+" List "+textList);

			for(int i=1;i<=dropDownList.size();i++)
			{

				WebElement name=driver.findElement(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span)["+i+"]"));
				WebElement nameSel=driver.findElement(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span)["+i+"]/preceding-sibling::div"));

				System.out.println("Name : "+name.getText());
				System.out.println("NameSel : "+nameSel.getAttribute("class"));

				if(name.getText().equals(expVal))
				{
					if(!nameSel.getAttribute("class").equals("slicerCheckbox selected"))
					{
						name.click();
						System.out.println("Selected "+expVal);
						break;
					}
					else {
						System.out.println("Already Selected "+expVal);
						break;
					}
				}


			}

			count++;
		}

	}
*/
/*
	public static void dropDownSelection(WebDriver driver,String dropDownName,String expVal) throws InterruptedException
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='slicer-dropdown-menu' and @aria-label='"+dropDownName+"']/i")).click();
		Thread.sleep(3000);

		List<WebElement> dropDownList=driver.findElements(By.xpath("//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span"));
		//String selVal="G P Mumbai";
		List<String> textList=new ArrayList<>();
		for(WebElement ele : dropDownList)
		{
			textList.add(ele.getText());
		}
		System.out.println(dropDownName+" List "+textList);

		for(int i=1;i<=dropDownList.size();i++)
		{

			WebElement name=driver.findElement(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span)["+i+"]"));
			WebElement nameSel=driver.findElement(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownName+"' ]//div[@class='slicerItemContainer']/span)["+i+"]/preceding-sibling::div"));

			System.out.println("Name : "+name.getText());
			System.out.println("NameSel : "+nameSel.getAttribute("class"));

			if(name.getText().equals(expVal))
			{
				if(!nameSel.getAttribute("class").equals("slicerCheckbox selected"))
				{
					name.click();
					System.out.println("Selected "+expVal);
					break;
				}
				else {
					System.out.println("Already Selected "+expVal);
					break;
				}
			}


		}


	}
*/
	/*
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

	}*/
/*
	public static void getRespectiveTestDataList(List<String> testData,Object[][] tdata) throws InterruptedException
	{
		int count=0;

		for(String testDataStr : testData) {

			//String str1=testDataStr.replace("<", "");
			String[] str=testDataStr.split(">");

			System.out.println("Str.lenght : "+str.length);

			for(int i=0;i<str.length;i++)
			{
				System.out.println("String data : "+str[i]);
				String[] str11=str[i].split("=<");
				//getRespectiveTestDataList(str11,count);

				for(int j=0;j<str11.length;j++)
				{
					if(j==0)
					{   
						if(count==0)
							dropDownHeading.add(str11[j]);
					}
					else
					{

						dropDownData.add(str11[j]);


					}

				}


			}
			if(prevdropDownData.equals(dropDownData))
			{

				//calling drop down with theading and ttestdata
				//if(count==0)
				//	dropDownSelection(driver,(String)dropDownHeading.get(count), dropDownData.get(count));

				System.out.println("TestDataList if : "+dropDownData);


			}else {

				//calling other functions.
				//		dropDownSelection(driver,(String)dropDownHeading.get(count), dropDownData.get(count));
				System.out.println("TestDataList else : "+dropDownData);



			}
			prevdropDownData.addAll(dropDownData);
			dropDownData.clear();	
			count++;

		}
		System.out.println("tHeading : "+dropDownHeading);
		System.out.println("TestDataList : "+dropDownData);
		System.out.println("prevTestData : "+prevdropDownData);
		//	System.out.println("TestDataList : "+tdata);

	}*/



}
