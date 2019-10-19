package com.icrm.qa.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.icrm.qa.base.TestBase;
import com.icrm.qa.util.TestUtil;

public class Workflow extends TestBase {
	Actions action;
	WebDriverWait wait;
	
	@FindBy(xpath="//li[@id='incident|NoRelationship|Form|mli.incident.SaveAndSoute.Button']//span//span")
	WebElement btnSaveAndRoute;
	
	@FindBy(xpath="//li[@id='moreCommands']//span")
	WebElement btnMore;
	
	@FindBy(xpath="//li[@id='incident|NoRelationship|Form|mli.Mscrm.Form.incident.RunWorkflow']")
	WebElement btnRunWorkflow;
	
	@FindBy(xpath="//button[contains(@id, 'butBegin')]")
	WebElement btnAdd;
	
	@FindBy(xpath="//span[contains(@id, 'navTabLogoTextId')]//img")
	WebElement imgManulife;
	
	@FindBy(xpath="//label[contains(@id,'Status_label')]")
	WebElement statusElement;
	
	@FindBy(xpath = "//button[@id='butBegin']")
	WebElement btnCancelNotif;
	
	@FindBy(xpath="//span[contains(@id,'footer_mli_queue_lookupValue')]")
	WebElement elementTeam;
	
	
	public Workflow() {
		PageFactory.initElements(driver, this);
		action = new Actions(driver);		
	}
	
	public void SaveAndRoute(String ts) {
		wait = new WebDriverWait(driver, 15);
		try {
			Thread.sleep(3000);
			//get teamold sebelum save and route
			driver.switchTo().defaultContent();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
			String TeamCRMOld = elementTeam.getText();
			String TeamCRMNew = elementTeam.getText(); 
			
			driver.switchTo().defaultContent();
			btnSaveAndRoute.click();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
			String status = statusElement.getText();
			do {
				driver.switchTo().defaultContent();
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
				status = statusElement.getText();
				
				driver.navigate().refresh();
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
				btnCancelNotif.click();				
				driver.switchTo().defaultContent();
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
				TeamCRMNew = elementTeam.getText(); 
			}while(!(status.equals("Ready to Resolve")||(status.equals("In Progress")&&(TeamCRMOld!=TeamCRMNew))));		
			//screenshoot
			try {
				TestUtil.takeScreenshotAtEndOfTestSuccess(ts);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public void Resolved(String ts) {	
		wait = new WebDriverWait(driver, 15, 1000);
		driver.switchTo().defaultContent();
		btnMore = wait.until(ExpectedConditions.elementToBeClickable(btnMore));
		btnMore.click();
		btnRunWorkflow = wait.until(ExpectedConditions.elementToBeClickable(btnRunWorkflow));
		btnRunWorkflow.click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
		//btnAdd = wait.until(ExpectedConditions.elementToBeClickable(btnAdd));
		btnAdd.click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog1_Iframe"));
		//btnAdd = wait.until(ExpectedConditions.elementToBeClickable(btnAdd));
		btnAdd.click();
		//screenshoot
		try {
			driver.switchTo().defaultContent();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
			String status = statusElement.getText();
			int loop = 0;
			do {
				if(loop<4) {
					driver.switchTo().defaultContent();
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
					status = statusElement.getText();
					driver.navigate().refresh();
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
					btnCancelNotif.click();				
					driver.switchTo().defaultContent();
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
					loop++;
				}
			}while(!(status.equals("Resolved")&&(loop<4)));
			if(loop==3 && !status.equals("Resolved")) {
				TestUtil.takeScreenshotAtEndOfTestError();
				System.out.println("Print screen masih di 'ready to resolved' yang harusnya 'resolved'");
			}
			else if(status.equals("Resolved")) {
				TestUtil.takeScreenshotAtEndOfTestSuccess(ts);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//imgManulife.click();
	}
	
//	public void RunWorkflow() {
//		try {
//			Thread.sleep(3000);
//			wait = new WebDriverWait(driver, 15, 1000);
//			driver.switchTo().defaultContent();
//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
//			String status = statusElement.getText();			
//			if(status.equals("Ready to Resolve")) {
//				Resolved();
//			}
//			else if (status.equals("In Progress") || status.equals("Open")) {
//				SaveAndRoute();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

}
