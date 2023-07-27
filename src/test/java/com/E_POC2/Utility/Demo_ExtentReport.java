package com.E_POC2.Utility;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.E_POC2.DataBaseConfiguration.DataBaseConnection;
import com.E_POC2.TestBase.TestBase;
import com.E_POC2.TestCases.PowerBIDashBoardTest1;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import net.rcarz.jiraclient.Issue;



//public class Demo_ExtentReport  extends TestListenerAdapter {
	
public class Demo_ExtentReport extends TestListenerAdapter{
	static Date d = new Date();
	static int passCount=0;
	static int failedCount=0;
	static int totalTestCases=0;
	String filepath="";
	
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	public static ExtentReports extent = ExtentManager
			.createInstance(".\\reports\\" + fileName);
	//ExtentTest testlog=ExtentManager.createTest();

	public static ExtentTest test;
	

	public void onTestStart(ITestResult result) {
	
		test = extent
				.createTest("@TestCase : " + result.getMethod().getMethodName());
		Capabilities capabilities=( (RemoteWebDriver) TestBase.driver).getCapabilities();
        extent.setSystemInfo("Browser Name", capabilities.getBrowserName());
        extent.setSystemInfo("Browser Varsion",capabilities.getBrowserVersion());
		//test.debug(MarkupHelper.createLabel(ExtentManager.createTest(),ExtentColor.TRANSPARENT));
		//extentTest.debug(MarkupHelper.createLabel(getBrowser() + " " + getVersion(), ExtentColor.TRANSPARENT));
		
	}

	public void onTestSuccess(ITestResult result) {
		
		passCount++;
		totalTestCases++;
		
		
		Capabilities capabilities=( (RemoteWebDriver) TestBase.driver).getCapabilities();
		
		try {
			//filepath=TestBase.takesScreenshot(fileName);
			switch(result.getMethod().getMethodName())
			{
			case "monthPerformance":
				setResultToExcel.setOutputToExcel(1, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "lowAttendancePerformance":
				setResultToExcel.setOutputToExcel(2, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "GirdPerformance":
				setResultToExcel.setOutputToExcel(3, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "characteristicPerformance":
				setResultToExcel.setOutputToExcel(4, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "Marks_Obtained":
				setResultToExcel.setOutputToExcel(5, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "total_Marks":
				setResultToExcel.setOutputToExcel(6, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "marks_Percentage":
				setResultToExcel.setOutputToExcel(7, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "attendance_Percentage":
				setResultToExcel.setOutputToExcel(8, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			case "Group_Marking":
				setResultToExcel.setOutputToExcel(9, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				break;
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED ON <h2><font color=white> "+capabilities.getBrowserName().toUpperCase() + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.pass(m);
	}
	public void onTestFailure(ITestResult result) {
		
		failedCount++;
		totalTestCases++;
		Issue NewIssue=TestBase.generateJiraTicket(result,PowerBIDashBoardTest1.dashBoard_DataPerVisuals,PowerBIDashBoardTest1.dataBase_DataPerVisuals);
		System.out.println("Issues : "+NewIssue.toString());
		System.out.println("Issues : "+NewIssue.getKey());
		
		Capabilities capabilities=( (RemoteWebDriver) TestBase.driver).getCapabilities();
		try {
			//TestBase.takesScreenshot(fileName);
			switch(result.getMethod().getMethodName())
			{
			case "monthPerformance":
				setResultToExcel.setOutputToExcel(1, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(1, 6, NewIssue.getKey());
				break;
				
			case "lowAttendancePerformance":
				setResultToExcel.setOutputToExcel(2, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(2, 6, NewIssue.getKey());
				break;
				
			case "GirdPerformance":
				setResultToExcel.setOutputToExcel(3, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(3, 6, NewIssue.getKey());
				break;
				
			case "characteristicPerformance":
				setResultToExcel.setOutputToExcel(4, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(4, 6, NewIssue.getKey());
				break;
				
			case "Marks_Obtained":
				setResultToExcel.setOutputToExcel(5, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(5, 6, NewIssue.getKey());
				break;
				
			case "total_Marks":
				setResultToExcel.setOutputToExcel(6, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(6, 6, NewIssue.getKey());
				break;
				
			case "marks_Percentage":
				setResultToExcel.setOutputToExcel(7, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(7, 6, NewIssue.getKey());
				break;
				
			case "attendance_Percentage":
				setResultToExcel.setOutputToExcel(8, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(8, 6, NewIssue.getKey());
				break;
				
			case "Group_Marking":
				setResultToExcel.setOutputToExcel(9, 7, TestBase.takesScreenshot(result.getMethod().getMethodName()));
				setResultToExcel.setOutputToExcel(9, 6, NewIssue.getKey());
				break;
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.fail(result.getThrowable().getLocalizedMessage());
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " FAILED on  <h2><font color=white>"+capabilities.getBrowserName().toUpperCase() +"</b>";	
		//		String excepionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		//		test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
		//				+ "</font>" + "</b >" + "</summary>" +excepionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		//	
		//	

		try {
			test.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b><br>"+TestBase.takesScreenshot(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		test.log(Status.FAIL, m);
		
		//TestBase.generateJiraTicket(result,PowerBIDashBoardTest1.dashBoard_DataPerVisuals,PowerBIDashBoardTest1.dataBase_DataPerVisuals);
		
		/*/////////////
		//JiraPolicy jiraPolicy = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraPolicy.class);
		JiraPolicy jiraPolicy = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraPolicy.class);
		System.out.println("JiraPolicy : "+jiraPolicy);
	       boolean isTicketReady = jiraPolicy.logTicketReady();
	       
	       if(isTicketReady)
	       {
	           //raise jira ticket:
	           System.out.println("is ticket ready for jira :"+isTicketReady);
	           JiraServiceProvider jiraSp= new JiraServiceProvider(TestBase.getConfigObject().getJira_url(),TestBase.getConfigObject().getJira_Mail() ,TestBase.getConfigObject().getJira_API_key() , "CP");
	           String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName() + "got failed due to some assertion failed or exceptions.";
	           
	           String issueDescription = result.getThrowable().getMessage()+"\n";
	           issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
	           
	           jiraSp.CreateJiraTicket("Bug",issueSummary, issueDescription, "Aniket Shirke");
	       }
	   
		
		
		
		/////////////*/
		
		
		


	}

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		test.skip(m);

	}

	

	public void onFinish(ITestContext context) {
		
		System.out.println("Total Test Cases : "+totalTestCases);
		System.out.println("FailedCount : "+failedCount);
		System.out.println("PassedCount : "+passCount);
		EmailOfTestExecution.mail(totalTestCases, passCount, failedCount);

		if (extent != null) {

			extent.flush();
		}

	}

}


