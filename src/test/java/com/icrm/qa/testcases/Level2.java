package com.icrm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.icrm.qa.base.TestBase;
import com.icrm.qa.pages.CaseHome;
import com.icrm.qa.pages.HomePage;
import com.icrm.qa.pages.NewCase;
import com.icrm.qa.util.TestUtil;

public class Level2 extends TestBase {
	TestUtil testUtil;
	HomePage homePage;
	CaseHome caseHome;
	NewCase newCase;
	
	public Level2() {
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
	public void DoLevel2(String nopol, String ts, String type) {	
		//Object data[][] = TestUtil.getTestData("level1");
		Object dataTeam[][] = TestUtil.getTestData("level2");
		newCase = new NewCase();
		homePage.HandleWarningEmail();
		
		//pick case
		//homePage.pickCases(ts);
			
		String team = homePage.getTaskSubject();
		homePage.getMyActiveCase();
			
		//looping per team
		for(int i=0; i<dataTeam.length;i++) {
			if(team.equals(dataTeam[i][0].toString())) {
				for(int j=1; j<5;j++){
					if(!(dataTeam[i][j].equals("NULL"))) {
						System.out.println("Data Team ke "+i+(j)+ " = " +dataTeam[i][j]);
						//newCase.Level2(dataTeam[i][j].toString());
						if(newCase.Level2bool(dataTeam[i][j].toString(),ts)) {
							driver.navigate().refresh();
							homePage.HandleWarningEmail();						
						}else {
							Assert.assertFalse(true, "Wrong team on "+dataTeam[i][j].toString());
							break;
						}
					}else {
						break;
					}
				}
			}
		}
			driver.switchTo().defaultContent();
			homePage.gotoDashboard();
			driver.navigate().refresh();
			homePage.HandleWarningEmail();
	}
	
	@AfterMethod
	public void tearDown() {
		//driver.quit();
	}
}
