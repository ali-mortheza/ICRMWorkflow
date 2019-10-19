package com.icrm.qa.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.icrm.qa.base.TestBase;
import com.icrm.qa.util.TestUtil;


public class NewCase extends TestBase {
	Actions action;
	WebDriverWait wait;

	@FindBy(xpath = "//input[@id='customerid_ledit']")
	WebElement txtCustomer;

	@FindBy(xpath = "//input[@id='mli_policy_ledit']")
	WebElement txtPolicy;
	
	@FindBy(xpath = "//input[@id='mli_tasksubjectid_ledit']")
	WebElement txtTaskSubject;

	@FindBy(xpath = "//img[@id='customerid_i']")
	WebElement btnSearchCustomer;
	
	@FindBy(xpath = "//img[@id='mli_policy_i']")
	WebElement btnSearchPolicy;
	
	@FindBy(xpath = "//img[@id='mli_tasksubjectid_i']")
	WebElement btnSearchTaskSubject;
	
	@FindBy(xpath = "//ul[@id='customerid_IMenu']//li[1]")
	WebElement customer;
	
	@FindBy(xpath = "//ul[@id='mli_policy_IMenu']//li[1]")
	WebElement policy;
	
	@FindBy(xpath = "//ul[@id='mli_tasksubjectid_IMenu']//li[1]")
	WebElement taskSubject;
	
	@FindBy(xpath = "//label[@id='Verification Result_label']")
	WebElement lblVerificationResult;
	
	@FindBy(xpath = "//label[@id='Touchpoint_label']")
	WebElement lblTouchPointResult;
	
	
	@FindBy(xpath = "//label[@id='Case Category_label']")
	WebElement lblCaseCategory;
	
	@FindBy(xpath = "//li[@id='incident|NoRelationship|Form|Mscrm.Form.incident.SaveAndClose']//span//span")
	WebElement btnSaveAndClose;
	
	@FindBy(xpath = "//li[@id='incident|NoRelationship|Form|Mscrm.Form.incident.Save']//span//span")
	WebElement btnSave;
	
	@FindBy(xpath = "//li[@id='incident|NoRelationship|Form|mli.incident.SaveAndSoute.Button']//span//span")
	WebElement btnSaveAndRoute;
	
	@FindBy(xpath="//span[@id='navTabLogoTextId']//img")
	WebElement btnDashboard;
	
	@FindBy(xpath="//li[@id='moreCommands']//span")
	WebElement btnMore;
	
	@FindBy(xpath="//span[contains(@id,'footer_mli_queue_lookupValue')]")
	WebElement elementTeam;
	
	@FindBy(xpath="//label[contains(@id,'Status_label')]")
	WebElement statusElement;
	
	@FindBy(xpath = "//button[@id='butBegin']")
	WebElement btnCancelNotif;
			

	public NewCase() {
		PageFactory.initElements(driver, this);
		action = new Actions(driver);
	}
	
	public void goToDashboard() {
		driver.switchTo().defaultContent();
		btnDashboard.click();
	}
	
	public void Level1(String nopol, String ts, String type, int i) {
		System.out.println("ts = "+ts);
		wait = new WebDriverWait(driver, 15);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			Thread.sleep(3000);
			//driver.switchTo().defaultContent();
			if(i==0) {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));
			}else {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
			}
			//set policy
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("mli_policy_ledit")));
			jse.executeScript("arguments[0].click();", txtPolicy);
			//WebElement nopolField = driver.findElement(By.id("mli_policy_ledit"));
			//jse.executeScript("arguments[0].setAttribute('value', '" +nopol.toString()+"')", nopolField);
			jse.executeScript("document.getElementById('mli_policy_ledit').value= arguments[0];",nopol); //excel
			jse.executeScript("arguments[0].click();", btnSearchPolicy);
			wait.until(ExpectedConditions.elementToBeClickable(policy));
			policy.sendKeys(Keys.ENTER);
			
			//set customers
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("customerid_ledit")));
			jse.executeScript("arguments[0].click();", txtCustomer);
			jse.executeScript("arguments[0].click();", btnSearchCustomer);
			wait.until(ExpectedConditions.elementToBeClickable(customer));
			action.moveToElement(customer).build().perform();
			customer.sendKeys(Keys.ENTER);
			
			//set Touchpoint
			//if(ts.equals("Welcome Call")) {
				wait.until(ExpectedConditions.elementToBeClickable(lblTouchPointResult));
				jse.executeScript("arguments[0].click();", lblTouchPointResult);
				Select drpTch = new Select(driver.findElement(By.id("mli_touchpoint_i")));
				drpTch.selectByIndex(0);
			//}
			
			//VerificationResult
			jse.executeScript("arguments[0].click();", lblVerificationResult);
			Select drpVR = new Select(driver.findElement(By.id("mli_verificationresult_i")));
			drpVR.selectByIndex(0);
			
			//case category
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("lblCaseCategory")));
			jse.executeScript("arguments[0].click();", lblCaseCategory);
			Select drpCategory = new Select(driver.findElement(By.id("mli_casecategory_i")));
			if(type.equals("Inquiry")) {
				drpCategory.selectByIndex(0);
			}else if(type.equals("Request")) {
				drpCategory.selectByIndex(1);
			}else if(type.equals("Complaint")) {
				drpCategory.selectByIndex(2);
			}
			
			
			//Task Subject
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("txtTaskSubject")));
			jse.executeScript("arguments[0].click();", txtTaskSubject);	
			//WebElement tsField = driver.findElement(By.id("mli_tasksubjectid_ledit"));
			//jse.executeScript("arguments[0].setAttribute('value', '" +ts.toString()+"')", tsField);
			jse.executeScript("document.getElementById('mli_tasksubjectid_ledit').value=arguments[0];",ts); //excel
			jse.executeScript("arguments[0].click();", btnSearchTaskSubject);
			wait.until(ExpectedConditions.elementToBeClickable(taskSubject));
			action.moveToElement(taskSubject).build().perform();
			taskSubject.sendKeys(Keys.ENTER);
			
			//Save
			driver.switchTo().defaultContent();
			btnSave.click();
			
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='incident|NoRelationship|Form|mli.incident.SaveAndSoute.Button']//span//span")));
			//screenshoot
			try {			
				TestUtil.takeScreenshotAtEndOfTestSuccess(ts);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Save and Route
			btnSaveAndRoute.click();
			//popup
			driver.switchTo().alert().accept();
			driver.navigate().refresh();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
			btnCancelNotif.click();	
			driver.switchTo().defaultContent();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
			String status = statusElement.getText();
			if(status.equals("Open")) {
				int loop = 0;
				do {
					if(loop<4) {
						driver.navigate().refresh();
						wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
						btnCancelNotif.click();
						Thread.sleep(2000);
						driver.switchTo().defaultContent();
						wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
						status = statusElement.getText();
						Thread.sleep(1000);
						loop++;
					}
				}while(status.equals("Open")&&(loop<4));
			}		
			//screenshoot
			try {
				Thread.sleep(3000);
				driver.switchTo().defaultContent();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='moreCommands']//span")));
				TestUtil.takeScreenshotAtEndOfTestSuccess(ts);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	
	public void Level2(String TeamExcel) {
		wait = new WebDriverWait(driver, 15);
		//pick case
		Workflow workflow = new Workflow();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@id,'footer_mli_queue_lookupValue')]")));
		String TeamCRM = elementTeam.getText();
		String status = statusElement.getText();
		System.out.println("Team Excel = "+TeamExcel);
		System.out.println("Team CRM = "+TeamCRM);
		System.out.println("status = "+status);
		
		//cek team on case
		if((TeamCRM.equals(TeamExcel)) && (status.equals("In Progress"))) {
			//SaveAndRoute
			workflow.SaveAndRoute(TeamExcel);
		}
		else if((TeamCRM.equals(TeamExcel)) && (status.equals("Ready to Resolve"))) {
			//resolved
			workflow.Resolved(TeamExcel);
		}else {
			System.out.println("WRONG TESTING - Data Team Tidak sesuai");
		}
	}
	
	public boolean Level2bool(String TeamExcel, String ts) {
		wait = new WebDriverWait(driver, 15);
		//pick case
		Workflow workflow = new Workflow();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@id,'footer_mli_queue_lookupValue')]")));
		String TeamCRM = elementTeam.getText();
		String status = statusElement.getText();
		System.out.println("Team Excel = "+TeamExcel);
		System.out.println("Team CRM = "+TeamCRM);
		System.out.println("status = "+status);
		
		//cek team on case
		if((TeamCRM.equals(TeamExcel)) && (status.equals("In Progress"))) {
			//SaveAndRoute
			workflow.SaveAndRoute(ts);
			return true;
		}
		else if((TeamCRM.equals(TeamExcel)) && (status.equals("Ready to Resolve"))) {
			//resolved
			workflow.Resolved(ts);
			return true;
		}else {
			System.out.println("WRONG TESTING - Data Team Tidak sesuai");	
			return false;
		}
	}
}
