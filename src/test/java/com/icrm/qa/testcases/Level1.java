package com.icrm.qa.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.icrm.qa.base.TestBase;
import com.icrm.qa.pages.CaseHome;
import com.icrm.qa.pages.HomePage;
import com.icrm.qa.pages.NewCase;
import com.icrm.qa.util.TestUtil;
import com.relevantcodes.extentreports.ExtentTest;

public class Level1 extends TestBase {
	public static ExtentTest parentTest;
	
	TestUtil testUtil;
	HomePage homePage;
	CaseHome caseHome;
	NewCase newCase;
	int i = 0;

	public Level1() {
		super();
	}

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		initialization();
		testUtil = new TestUtil();
		homePage = new HomePage();
	}
	
	@DataProvider
	public Object[][] getData() {
		Object data[][] = TestUtil.getTestData("level1");
		return data;
	}
	
	@Test(dataProvider = "getData")
	public void DoLevel1(String nopol, String ts, String type) { 
		// create case
		if(i==0) {
			homePage.HandleWarningEmail();
		}
		//for (int i = 0; i < data.length; i++) {
			caseHome = homePage.ToCasesPages();
			newCase = caseHome.ToNewCase();
			driver.switchTo().defaultContent();
			newCase.Level1(nopol, ts, type, i);
			newCase.goToDashboard();
			i++;
		//}
	}
	
	@AfterMethod
	public void tearDown() {
		//driver.quit();
	}
}
