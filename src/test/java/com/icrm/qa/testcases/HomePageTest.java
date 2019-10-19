package com.icrm.qa.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.icrm.qa.base.TestBase;
import com.icrm.qa.pages.CaseHome;
import com.icrm.qa.pages.HomePage;
import com.icrm.qa.pages.NewCase;
import com.icrm.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	TestUtil testUtil;
	HomePage homePage;
	CaseHome caseHome;
	NewCase newCase;

	public HomePageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		//testUtil = new TestUtil();
		//homePage = new HomePage();
	}

//	@DataProvider
//	public Object[][] getData() {
//		Object data[][] = TestUtil.getTestData("level1");
//		return data;
//	}

	//@Test(dataProvider = "getData")
	// String nopol, String ts, String type
	@Test(enabled = false)
	public void Level1() { 
		// create case
		Object data[][] = TestUtil.getTestData("level1");
		Object dataTeam[][] = TestUtil.getTestData("level2");
		homePage.HandleWarningEmail();
		for (int i = 0; i < data.length; i++) {
			System.out.println("==================================");
			System.out.println("------DO LEVEL 1 DATA "+i+"-------");
			System.out.println("==================================");
			caseHome = homePage.ToCasesPages();
			newCase = caseHome.ToNewCase();
			driver.switchTo().defaultContent();
			newCase.Level1(data[i][0].toString(), data[i][1].toString(), data[i][2].toString(), i);
			newCase.goToDashboard();
		}
	}

	@Test(enabled = false)
	public void Level2() {	
		Object data[][] = TestUtil.getTestData("level1");
		Object dataTeam[][] = TestUtil.getTestData("level2");
		newCase = new NewCase();
		homePage.HandleWarningEmail();
		
		//pick case
//		for(int k=0;k<data.length;k++) {
//			System.out.println("==================================");
//			System.out.println("--------DO PICK CASE ke "+k+"-------");
//			System.out.println("==================================");			
//			homePage.pickCases(data[k][1].toString());
//		}
//		
		for(int k=0;k<data.length;k++) {
			//String team = homePage.getTaskSubject(k);
			String team = homePage.getTaskSubject();
			//homePage.getMyActiveCase(k);
			homePage.getMyActiveCase();
			
			//looping per team
			for(int i=0; i<dataTeam.length;i++) {
				if(team.equals(dataTeam[i][0].toString())) {
					for(int j=1; j<5;j++){
						if(!(dataTeam[i][j].equals("NULL"))) {
							System.out.println("Data Team ke "+i+(j)+ " = " +dataTeam[i][j]);
							newCase.Level2(dataTeam[i][j].toString());
							driver.navigate().refresh();
							homePage.HandleWarningEmail();
						}else {
							break;
						}
					}
				}
			}
			driver.switchTo().defaultContent();
			homePage.gotoDashboard();
			driver.navigate().refresh();
			//homePage.HandleWarningEmail();
		}
	}
	
	@AfterMethod
	public void tearDown() {
		//driver.quit();
	}
}
