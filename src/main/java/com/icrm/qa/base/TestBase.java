package com.icrm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.icrm.qa.util.WebEventListener;


public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("D:\\Ali\\Selenium\\STS\\STSProject\\ICRM\\src\\main\\java\\com\\icrm\\qa\\config\\config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialization() {
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\Ali\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();						
		    options.setExperimentalOption("useAutomationExtension", false);
		    options.addArguments("chrome.switches", "--disabsle-extensions");
			//options.addArguments("user-data-dir=C:/Users/aliridh/AppData/Local/Google/Chrome/User Data/Profile 2/");
			driver = new ChromeDriver(options);
			
//			 DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
//			 ChromeOptions options = new ChromeOptions();
//			 options.setExperimentalOption("useAutomationExtension", false);
//			 //options.addArguments("user-data-dir=C:\\Users\\"+System.getenv("USERNAME")+"\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 2");
//			            //desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
//			 driver = new ChromeDriver(desiredCapabilities);
		}
		else if(browserName.equals("Firefox")) {
			//set firefox driver
		}
		
		else if(browserName.equals("ie")) {
			System.setProperty("webdriver.ie.driver","D:\\Ali\\Selenium\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();			
		}
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();		
		//driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
	}
}
