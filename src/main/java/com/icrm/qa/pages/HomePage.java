package com.icrm.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.icrm.qa.base.TestBase;

public class HomePage extends TestBase {
	Actions action;
	WebDriverWait wait;
	Workflow workflow;
	String [][] CaseAvailable = null;
	//List<String> CaseAvailable = new ArrayList<String>();
	
	@FindBy(xpath = "//button[@id='butBegin']")
	WebElement btnCancelNotif;
	
	@FindBy(xpath = "//span[@id='TabCS']//a[@class='navTabButtonLink']")
	WebElement ddService;
	
	@FindBy(xpath = "//a[@id='nav_accts']//span[@class='nav-rowLabel']")
	WebElement LinkCompanies;
	
	@FindBy(xpath = "//a[@id='nav_cases']//span[@class='nav-rowLabel']")
	WebElement LinkCases;
	
	@FindBy(xpath = "//a[@id='nav_contacts']//span[@class='nav-rowLabel']")
	WebElement LinkContacts;
	
	@FindBy(xpath = "//div[@id='Component9036443_divDataArea']//table[@id='gridBodyTable']//tbody/tr")
	WebElement tblQueueCaseRow1;
	
	@FindBy(xpath="//li[@id='queueitem|NoRelationship|SubGridStandard|Mscrm.SubGrid.queueitem.Pick']//span//span")
	WebElement btnPick;
	
	@FindBy(xpath="//div[@id='Componentbe1a3b0_divDataArea']//table[@id='gridBodyTable']//tbody/tr[1]")
	WebElement tblMyActiveCaseRow1;
	
	@FindBy(xpath="//span[@id='navTabLogoTextId']//img")
	WebElement btnDashboard;
	
	@FindBy(xpath="//label[@id='Also remove the item(s) from the Queue_label']")
	WebElement lblPickModal;
	
	@FindBy(xpath="//button[@id='ok_id']")
	WebElement btnPickModal;
	
	
	
	public HomePage() {
		PageFactory.initElements(driver, this);
		action = new Actions(driver);
	}
	
	public void gotoDashboard() {
		btnDashboard.click();
	}
	
	public void HandleWarningEmail() {
		wait = new WebDriverWait(driver, 15, 1000);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
		//driver.switchTo().frame("InlineDialog_Iframe");
		btnCancelNotif.click();
	}
	
	public CaseHome ToCasesPages() {
		//HandleWarningEmail();
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.switchTo().defaultContent();
		wait = new WebDriverWait(driver, 15, 1000);
		wait.until(ExpectedConditions.elementToBeClickable(ddService));
		ddService.click();
		LinkCases.click();
		return new CaseHome();
	}
	
	public void pickCases(String TestCase) {
		try {
			wait = new WebDriverWait(driver, 15, 1000);
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("contentIFrame0");
			//scroll page
			//JavascriptExecutor jse = (JavascriptExecutor) driver;
			//jse.executeScript("arguments[0].scrollIntoView();",tblQueueCaseRow1);
			//jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			wait.until(ExpectedConditions.elementToBeClickable(tblQueueCaseRow1));
			List<WebElement> rows = driver.findElements(By.xpath("//div[@id='Component9036443_divDataArea']//table[@id='gridBodyTable']//tbody/tr"));
			List<WebElement> cols = driver.findElements(By.xpath("//div[@id='Component9036443_divDataArea']//table[@id='gridBodyTable']//tbody/tr//td"));
			
			//for add to CaseAvalaible
			if(rows.size() != 0) {
				CaseAvailable = new String[rows.size()][cols.size()/rows.size()];
				int r = 0;
				int c = 0;
				for(int j=0;j<cols.size();j++) {
					if(j%(cols.size()/rows.size()) == 0 && j!=0) {
						r++;
						c = 0; 
					}
					CaseAvailable[r][c] = cols.get(j).getText();
					c++;
				}
				
				//cek data with searching available testcase on table
				for(int i=0; i<rows.size();i++) {
					if(CaseAvailable[i][6].equals(TestCase) && CaseAvailable[i][3].equals("No")) {
						WebElement rowSelected = driver.findElement(By.xpath("//div[@id='Component9036443_divDataArea']//table[@id='gridBodyTable']//tbody/tr["+(i+1)+"]//td[1]"));
						//tblQueueCaseRow1.click();
						rowSelected.click();
						//System.out.println("rows = "+rows.size());
						//System.out.println("cols = "+cols.size());
						//System.out.println("cols 0  = "+cols.get(2).getText());
						driver.switchTo().defaultContent();
						Thread.sleep(3000);
						btnPick.click();
						wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
						lblPickModal.click();
						btnPickModal.click();
						driver.navigate().refresh();
						Thread.sleep(3000);
						HandleWarningEmail();
						break;
					}else {
						System.out.println(+i+". TestCase Tidak sesuai");
					}
				}
			}else {
				System.out.println("Data at PICK Table is empty");
			}
			
//			//int i = 1;
//			//for(WebElement test:cols) {
//			//	System.out.println("test ke " +i+ " = "+test.getText());
//			//	i++;
//			//}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public String getTaskSubject() {
		driver.switchTo().defaultContent();		
//		if(k==0) {
//			driver.switchTo().frame("contentIFrame0");
//		}else {			
//			driver.switchTo().frame("contentIFrame1");
//		}	
		
		driver.switchTo().frame("contentIFrame0");
		//wait.until(ExpectedConditions.elementToBeClickable(tblMyActiveCaseRow1));		
		WebElement MyActiveCase = driver.findElement(By.xpath("//div[@id='Componentbe1a3b0_divDataArea']//table[@id='gridBodyTable']//tbody/tr[1]//td[4]"));
		return MyActiveCase.getText().toString();
	}
	
	public void getMyActiveCase() {		
		driver.switchTo().defaultContent();
//		if(k==0) {
//			driver.switchTo().frame("contentIFrame0");
//		}
//		else {
//			driver.switchTo().frame("contentIFrame1");
//		}
		driver.switchTo().frame("contentIFrame0");
		action.doubleClick(tblMyActiveCaseRow1).perform();			
	}
	
//	public void test() {
//		HandleWarningEmail();
//		for(int i=0; i<3; i++) { //3 akan diganti berdasarkan jumlah dari excel
//			driver.switchTo().defaultContent();
//			driver.switchTo().frame("contentIFrame0");
//			List<WebElement> rows = driver.findElements(By.xpath("//div[@id='Component4309321_divDataArea']//table[@id='gridBodyTable']//tbody//tr"));
//			int count = rows.size();
//			System.out.println("count size = "+count);		
//			if(count == 0) { //table myActive case kosong
//				//pickCases();
//			}else {
//				getMyActiveCase();
//				workflow = new Workflow();
//				workflow.RunWorkflow();
//			}
//		}
//	}
}
