package com.icrm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.icrm.qa.base.TestBase;

public class CaseHome extends TestBase {
	Actions action;
	WebDriverWait wait;
	
	@FindBy(xpath="//li[@id='incident|NoRelationship|HomePageGrid|Mscrm.HomepageGrid.incident.NewRecord']//span//span")
	WebElement btnNewCase;
		
	public CaseHome() {
		PageFactory.initElements(driver, this);
		action = new Actions(driver);
	}
	
	public NewCase ToNewCase() {
		wait = new WebDriverWait(driver, 15);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//li[@id='incident|NoRelationship|HomePageGrid|Mscrm.HomepageGrid.incident.NewRecord']//span//span")));
			//btnNewCase.click();
			jse.executeScript("arguments[0].click();", btnNewCase);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new NewCase();
	}
}
