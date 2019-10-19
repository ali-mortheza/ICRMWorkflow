package com.icrm.qa.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.icrm.qa.util.TestUtil;


public class HeaderLink extends TestUtil {
	TestUtil testUtil;

	@FindBy(xpath = "//span[@id='TabCS']//a[@class='navTabButtonLink']")
	WebElement ddService;
	
	@FindBy(xpath = "//a[@id='nav_accts']//span[@class='nav-rowLabel']")
	WebElement LinkCompanies;
	
	@FindBy(xpath = "//a[@id='nav_cases']//span[@class='nav-rowLabel']")
	WebElement LinkCases;
	
	@FindBy(xpath = "//a[@id='nav_contacts']//span[@class='nav-rowLabel']")
	WebElement LinkContacts;
	
	@FindBy(xpath="//span[@id='navTabLogoTextId']//img")
	WebElement btnDashboard;
	
	public HeaderLink() {
		testUtil = new TestUtil();
	}
	
	public void GoToCases() {
		testUtil.switchToDefaultFrame();
		ddService.click();
		LinkCases.click();
	}
	
	public void GoToDashboard() {
		btnDashboard.click();
	}
}
