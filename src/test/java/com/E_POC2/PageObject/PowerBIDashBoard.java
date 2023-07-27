package com.E_POC2.PageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.E_POC2.TestBase.TestBase;

public class PowerBIDashBoard extends TestBase {
	
	WebDriver driver;
	
	
	
	@FindBy(xpath = "//input[@id='email']")
	WebElement emailId;
	
	@FindBy(xpath = "//button[@id='submitBtn']")
	WebElement emailIdFrameSubmit;
	
	@FindBy(id = "i0118")
	WebElement password;
	
	@FindBy(xpath = "//input[@id='idSIButton9']")
	WebElement passwordAndStaySignInButton;

	@FindBy(xpath="//div[@aria-label=\"Course_name\"]/div")
	WebElement courseName;

	@FindBy(xpath="//div[@aria-label=\"semester\"]/div")
	WebElement sem;

	@FindBy(xpath="//div[@aria-label=\"Subject_name\"]/div")
	WebElement subjectName;

	@FindBy(xpath = "//div[@aria-label=\"Teacher Name\"]/div")
	WebElement teacherName;

	@FindBy(xpath="//div[@aria-label=\"Student_name\"]/div")
	WebElement studentName;

	@FindBy(xpath="//div[@aria-label=\"Year\"]/div")
	WebElement year;

	@FindBy(css="svg[name$='Line and stacked column chart'] text[class='setFocusRing']")
	List<WebElement> monthName;
	
	@FindBy(css="svg[name$='Line and stacked column chart'] text[class='label']")
	List<WebElement> monthPer;
	
	
	
	public List<WebElement> getMonthName() {
		return monthName;
	}

	public List<WebElement> getMonthPer() {
		return monthPer;
	}

	public WebElement getCourseName() {
		return courseName;
	}

	public WebElement getSem() {
		return sem;
	}

	public WebElement getSubjectName() {
		return subjectName;
	}


	public WebElement getTeacherName() {
		return teacherName;
	}

	

	public WebElement getStudentName() {
		return studentName;
	}


	public WebElement getYear() {
		return year;
	}

	
	public PowerBIDashBoard(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

	public WebElement getEmailId() {
		return emailId;
	}

	public WebElement getEmailIdFrameSubmit() {
		return emailIdFrameSubmit;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getPasswordAndStaySignInButton() {
		return passwordAndStaySignInButton;
	}
		

}
