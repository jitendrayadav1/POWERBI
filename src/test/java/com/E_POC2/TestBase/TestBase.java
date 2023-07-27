package com.E_POC2.TestBase;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.E_POC2.DataBaseConfiguration.DataBaseConnection;
import com.E_POC2.TestCases.PowerBIDashBoardTest1;
import com.E_POC2.Utility.JiraPolicy;
import com.E_POC2.Utility.JiraServiceProvider;
import com.E_POC2.Utility.ReadConfig;
import com.E_POC2.Utility.setResultToExcel;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.rcarz.jiraclient.Issue;

public class TestBase {

	public static ReadConfig config=new ReadConfig();
	public String baseUrl=config.getApplicationUrl();
	public static WebDriver driver;
	public WebDriverWait wait;
	public Actions act;
	public static SoftAssert sftAssert;


	@Parameters("browser") 
	@BeforeClass
	public void setUp(@Optional("chrome") String browsr)
	{


		switch(browsr.toLowerCase())
		{
		case "chrome": 
			/*
			WebDriverManager.chromedriver().setup();
			ChromeOptions cop=new ChromeOptions();
			cop.addArguments("--remote-allow-origins=*");
			driver=new ChromeDriver(cop);*/
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			WebDriverManager.chromedriver().setup();
			ChromeOptions opt = new ChromeOptions();
			opt.addArguments("--disable-dev-shm-usage");
			opt.addArguments("--remote-allow-origins=*");
			opt.addArguments("--headless");
			opt.addArguments("--disable-dev-shm-usage");
			opt.addArguments("--ignore-ssl-errors=yes");
			opt.addArguments("--ignore-certificate-errors");
			
					//opt.add_argument("--ignore-ssl-errors=yes");
					//opt.add_argument("--ignore-certificate-errors");
			opt.addArguments("user-agent=GetMyBoat/Test");
			opt.setExperimentalOption("debuggerAddress", "localhost:9222");
			driver = new ChromeDriver(opt);

			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			break;

		default :
			System.err.println("Invalid Browser Name : ");
			System.err.println("Go to valid Browser Name : ");
		}


		//driver.manage().window().maximize();
		wait = new WebDriverWait(driver,Duration.ofMillis(3000));
		act=new Actions(driver);
		sftAssert=new SoftAssert();

	}

	
	public static ReadConfig getConfigObject()
	{
		return config;

	}
	public static String takesScreenshot(String testName) throws IOException
	{        


		LocalDateTime myLocalDateTimeObj=LocalDateTime.now();
		DateTimeFormatter dateTimeFormatterObj=DateTimeFormatter.ofPattern("dd_MM_yyy_HH_mm_ss");
		String cuDateAndTime= myLocalDateTimeObj.format(dateTimeFormatterObj);

		String filepath = System.getProperty("user.dir")+"\\Screenshots\\"+testName+"_"+cuDateAndTime+".png";		
		File fType=(File)((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);		
		FileUtils.copyFile(fType, new File(filepath));
		return filepath;

	}



	public static Issue generateJiraTicket( ITestResult result,HashMap<String, String> dashBoard_DataPerVisuals,
			HashMap<String, String> dataBase_DataPerVisuals)
	{
		JiraPolicy jiraPolicy = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraPolicy.class);
		System.out.println("JiraPolicy : "+jiraPolicy);
		boolean isTicketReady = jiraPolicy.logTicketReady();
		Issue NewIssue=null;
		

		if(isTicketReady)
		{
			//raise jira ticket:
			System.out.println("is ticket ready for jira :"+isTicketReady);
			JiraServiceProvider jiraSp= new JiraServiceProvider(TestBase.getConfigObject().getJira_url(),TestBase.getConfigObject().getJira_Mail() ,TestBase.getConfigObject().getJira_API_key() , "DEMO");
			String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName() + " test case got failed because dashboard data did not match with database data.";

			String issueDescription1 = result.getThrowable().getMessage()+"\n";
			String issueDescription="Dashboard Data: "+dashBoard_DataPerVisuals+" did not match with "
			+" Database data : "+dataBase_DataPerVisuals;
			//issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
			issueDescription.concat(" beacuase "+result.getThrowable().getMessage());

			NewIssue= jiraSp.CreateJiraTicket("Bug",issueSummary, issueDescription, "Jitendra Yadav");
		}
		return NewIssue;
	}



	

	public static void executionResult_setToExcel(HashMap<String, String> dashBoard_DataPerVisuals,HashMap<String, String> dataBase_DataPerVisuals,int rowNum,int actaulOp,int testCaseStatus,int defectId ) throws IOException
	{
		if(dashBoard_DataPerVisuals.equals(dataBase_DataPerVisuals))
		{
			setResultToExcel.setOutputToExcel(rowNum, actaulOp, "Dashboard Data: " +dashBoard_DataPerVisuals+" matched with "
					+" Database data : "+dataBase_DataPerVisuals);
			setResultToExcel.setOutputToExcel(rowNum, testCaseStatus, "Pass");
			setResultToExcel.setOutputToExcel(rowNum, defectId, "No Defect");
		}
		else
		{
			setResultToExcel.setOutputToExcel(rowNum, actaulOp, "Dashboard Data: " +dashBoard_DataPerVisuals+" did not match with "
					+" Database data : "+dataBase_DataPerVisuals);
			setResultToExcel.setOutputToExcel(rowNum, testCaseStatus, "Fail");
			//setResultToExcel.setOutputToExcel(rowNum, defectId, "Defect");
			
		}

		//dashBoard_DataPerVisuals.clear();
		//dataBase_DataPerVisuals.clear();
	}

	
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

	public static void lineAndStackedOrStackedBarChart(List<String> vNamesList,List<String> vDataList, String vDataXpath1,String vDataXpath2)
	{
		System.out.println("LineStacked or stacked bar Performance........");
		//	List<WebElement> monthName=driver.findElements(By.cssSelector("svg[name$='Line and stacked column chart'] text[class='setFocusRing']"));
		List<WebElement> nameList=driver.findElements(By.cssSelector(vDataXpath1));

		//System.out.println("List Of Months "+monthName);
		for(WebElement ele : nameList)
		{
			System.out.println("VisualName_Text : "+ele.getText());
			vNamesList.add(ele.getText());

		}


		List<WebElement> vPerList=driver.findElements(By.cssSelector(vDataXpath2));
		for(WebElement ele : vPerList)
		{
			System.out.println("Per_Text : "+ele.getText());
			String per=ele.getText().substring(0, ele.getText().indexOf("%"));
			vDataList.add(per);

		}



	}

	public static void readGridData(List<String> vNamesList,List<String> vDataList, String vDataXpath1,String vDataXpath2) throws InterruptedException
	{
		List<WebElement> gridList=driver.findElements(By.cssSelector(vDataXpath1));		
		Thread.sleep(3000);		

		for(int j=2;j<=gridList.size();j++)
		{
			//	WebElement gridEleHeading=driver.findElement(By.cssSelector("div[role=columnheader]:nth-of-type("+j+")"));
			WebElement gridEleHeading=driver.findElement(By.cssSelector(""+vDataXpath1+":nth-of-type("+j+")"));

			String str=gridEleHeading.getText().trim().replace(" ", "_");
			vNamesList.add(str);

			if(j==4) {

				//WebElement gridEleContent=driver.findElement(By.cssSelector("div[role=gridcell]:nth-of-type("+j+") :nth-child(1)"));
				WebElement gridEleContent=driver.findElement(By.cssSelector(""+vDataXpath2+":nth-of-type("+j+") :nth-child(1)"));
				vDataList.add(gridEleContent.getText());

				System.out.println("GridContentList4 : "+gridEleContent.getText());
			}
			else 
			{	
				WebElement gridEleContent=driver.findElement(By.cssSelector(""+vDataXpath2+":nth-of-type("+j+")"));

				str=gridEleContent.getText();

				if(str.contains("%")) 
					str=str.replace("%", "");

				vDataList.add(str);
				System.out.println("GridContentList : "+gridEleContent.getText());
			}
		}

	}

	public static List<String> readDashBoardCharacteristicData(List<String> vNamesList,List<String> vDataList, String vDataXpath1,String vDataXpath2)
	{
		driver.switchTo().frame(driver.findElement(By.cssSelector(vDataXpath1)));
		List<WebElement> charList=driver.findElements(By.cssSelector(vDataXpath2));

		List<String> charListCmp=new ArrayList<>();
		String str="\"";
		int count=0;
		for(WebElement ele : charList)
		{
			charListCmp.add(ele.getText());
			str=str+ele.getText();
			if(count!=(charList.size()-1))
				str=str+",";
			if(count==charList.size()-1)
				str=str+"\"";
			count++;
		}
		System.out.println("str : "+str);
		vDataList.add(str);
		System.out.println("Char_List : "+vDataList);
		driver.switchTo().defaultContent();

		vNamesList.add("Exam_Remark");
		return charListCmp;
	}

	public static void readDataBaseCharacteristicData(List<String> charListCmp)
	{

		String st=DataBaseConnection.visualDataList.get(0).replaceAll("\"", "");
		String[] st_arr=st.split(",");
		String[] st_arr1=new String[st_arr.length];
		String str1 ="\"";
		for(int i=0;i<st_arr.length;i++)
		{
			int count1=0;
			for(String charCont : charListCmp) 
			{
				if(st_arr[i].equals(charCont))	
				{		
					st_arr1[count1]=st_arr[i];
					break;
				}

				count1++;
			}
		}
		int count2=0;
		for(int i=0;i<st_arr1.length;i++)
		{		
			str1=str1+st_arr1[i];
			if(count2!=(st_arr1.length-1))
				str1=str1+",";
			if(count2==st_arr1.length-1)
				str1=str1+"\"";
			count2++;
		}

		System.out.println("str1 : "+str1);
		DataBaseConnection.visualDataList.clear();
		DataBaseConnection.visualDataList.add(str1);
	}

	public static void upperVisualData(List<String> vNamesList,List<String> vDataList, String vName,String vDataXpath1)
	{
	     	
		     WebElement vData=driver.findElement(By.xpath(vDataXpath1));
		     String vD ="";
			System.out.println("Per_Text : "+vData.getText());
			
			if(vData.getText().contains("%")) {
			 vD=vData.getText().substring(0, vData.getText().indexOf("%"));
			 
			 if((vD.substring(vD.length()-2, vD.length())).equals(".0"))
					vD=vD.replace(".0", "");
			 
			 vDataList.add(vD);
			}
			else
			vDataList.add(vData.getText());
			
			System.out.println("AfterPer_Text : "+vData.getText());
			System.out.println("AfterPer_Text : "+vD);
			vNamesList.add(vName);

		}
		
	
	
	public static void dropDownIteration(List<String> dropDownHeading,List<String> dropDownData,List<String> prevdropDownData) throws InterruptedException
	{
		if(!(prevdropDownData.equals(dropDownData)))
		{
			//selectAll( dropDownHeading.toArray());
			dropDownSelection1(driver,dropDownHeading,dropDownData);
			prevdropDownData.clear();
			prevdropDownData.addAll(dropDownData);
		}

	}

	////////////////////
	public static void selectAll(Object[] dropDownTitle) throws InterruptedException
	{
		//List<String> allStudents=new ArrayList<>();
		//HashMap<String,String> allStudents1=new HashMap<>();

		//Actions act=new Actions(driver);
		List<WebElement> dropDownList;
		/*
		int j=1,records=39;
		int rem=records%8;
		int limit_count=records-rem;
		int que=records/8;
		if(rem!=0)
			que++;*/

		for(int i=0;i<dropDownTitle.length;i++)
		{
			/*if(dropDownTitle[i].equals("Student_name"))
			{
				WebElement scheckbox=driver.findElement(By.xpath("//div[@class='slicer-dropdown-menu' and @aria-label='"+dropDownTitle[i]+"']/i"));
				scheckbox.click();
				Thread.sleep(3000);
				while(records!=0) {

					List<WebElement> snameList=driver.findElements(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownTitle[i]+"' ]//div[@class='slicerItemContainer']/span)"));
					int count=snameList.size();
					for(WebElement ele : snameList)
					{
						System.out.println("Sname : "+(j++)+" "+ele.getText());
					//	allStudents.add(ele.getText());
						if(!allStudents1.containsKey(ele.getText()))
							allStudents1.put(ele.getText(), "1");
					}

						act.keyDown(Keys.ARROW_DOWN).perform();
						Thread.sleep(2000);


					records--;
				}


				//
				System.out.println("AllStudent List : "+allStudents1);
			}
			else {*/
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='slicer-dropdown-menu' and @aria-label='"+dropDownTitle[i]+"']/i")).click();
			Thread.sleep(3000);
			dropDownList=driver.findElements(By.xpath("//div[@class='slicerBody'and @aria-label='"+dropDownTitle[i]+"' ]//div[@class='slicerItemContainer']/span"));
			System.out.println("DList : "+dropDownList.size());
			Thread.sleep(3000);
			int j1=1;
			for(WebElement ele:dropDownList)
			{
				WebElement name=driver.findElement(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownTitle[i]+"' ]//div[@class='slicerItemContainer']/span)["+(j1)+"]"));
				WebElement nameSel=driver.findElement(By.xpath("(//div[@class='slicerBody'and @aria-label='"+dropDownTitle[i]+"' ]//div[@class='slicerItemContainer']/span)["+(j1++)+"]/preceding-sibling::div"));

				System.out.println("DContentName : "+name.getText());
				System.out.println("DContentArialval : "+nameSel.getAttribute("class"));

				if(nameSel.getAttribute("class").equals("slicerCheckbox selected"))
				{
					name.click();
					System.out.println("AllSelected "+name.getText());
					break;
				}
			}
			//}
		}
	}




	/*
	@AfterClass
	public void tearDown()
	{
		//driver.quit();
	}*/


}
