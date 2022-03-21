package CodePart;

import java.lang.Exception;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import Utility.ExcelOperations;
import Utility.FunctionalConstants;
import Utility.PropertiesReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.TakesScreenshot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PublicLibrary {

	public static WebDriver driver;
	public static PublicLibrary Library;
	private ExcelOperations excelSheetRW;
	public Logger logger;
	private Properties configProperties;
	PropertiesReader propFileReader;

	/**
	 * @throws Exception 
	 * @constructor-name: PublicLibrary
	 * @Description It performs the initialization of excel file,
	 * 				and log file library objects before the actual testcase execution.
	 * 
	 */
	public PublicLibrary() throws Exception { 
		excelSheetRW = new ExcelOperations();
		logger=Logger.getLogger("Testing");
		PropertyConfigurator.configure("Resources//Log4j.properties");
		propFileReader = PropertiesReader.getInstance();
		configProperties = propFileReader.getPropFile(FunctionalConstants.CONFIG_PROPERTIES);
	}

	public static PublicLibrary getLibrary() {
		return Library;
	}

	public WebDriver returnDriver() {
		return driver;
	}

	public Logger getLogger() {
		return logger;
	}

	public ExcelOperations getExcelSheetRW() {
		return excelSheetRW;
	}

	public void setExcelRW(ExcelOperations excelSheetRW) {
		this.excelSheetRW = excelSheetRW;
	}

	public Properties getConfigProperties() {
		return configProperties;
	}


	public void setDriver(WebDriver driver2) {
		driver = driver2;

	}

	/**
	 * @method-name: sleep
	 * @param: milliseconds
	 * @description: Adds a pause before/after steps in the testcase execution.
	 */
	public void sleep(int milliseconds) throws Exception {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		}
		catch (Exception e) {
			throw new Exception("Exception in sleep() method "+e.getStackTrace()[0].getLineNumber()+" ", e);
		}
	}

	/**
	 * @method-name: navigateToPage
	 * @param: URL
	 * @description: It opens the browser with the parameterized url.
	 */
	public void navigateToPage(String URL) throws Exception {
		try {
			driver.manage().timeouts().implicitlyWait(15000, TimeUnit.MILLISECONDS);
			driver.get(URL);
			driver.manage().window().maximize();
			sleep(5000);
		}	 
		catch (Exception e) {
			throw new Exception("Exception While Navigate to the webpage ", e);
		}
	}

	public void captureScreenshot(String filename) throws Exception {
		File file = ((TakesScreenshot)returnDriver()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + File.separator + "Test-Extent-Report" + File.separator+filename+".png"));
	}

	public Map<String, String> setTestDataMap(String strTestCaseName,String strSheetname) throws Exception {
		//LogMessage.setup();
		int rowNum;
		Map<String, String> map = new HashMap<String, String>();

		try {				
			rowNum=ExcelOperations.getRowContains(strTestCaseName, 1, strSheetname);
			map=ExcelOperations.setTestDataMap(strSheetname, rowNum);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return map;
	}
	
	@BeforeSuite
    public void clearScreenshots() throws IOException {
    	String dir = System.getProperty("user.dir")+"\\Test-Extent-Report";
    	BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"\\Resources\\test.log"));
        bw.write("");
        bw.flush();
        bw.close();
		File file = new File(dir);
		for(File f: file.listFiles()) {
			if(f.getName().endsWith(".png")) {
				f.delete();
			}
		}
    }

	@BeforeMethod
	@Parameters("browserName")
	public void openBrowserNew(String browserType) {
		switch(browserType.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("Invalid browser details");
			break;
		}
	}
	
	@AfterMethod
	public void closeBroswer(ITestResult result) throws Exception {
		if(ITestResult.FAILURE==result.getStatus()) {
			captureScreenshot(result.getName());
		}
		driver.close();
	}
	
	public static boolean elementClick(WebDriver driver, By byObject) {

		// Wait for the element to be clickable

		WebDriverWait wait = new WebDriverWait(driver,10);

		try {
			wait.until(ExpectedConditions.elementToBeClickable(byObject));
			// continue even if the page is not loaded
			driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
			driver.findElement(byObject).click();
			System.out.println("Clicked on " + byObject);
			return true;

		}
		catch (Exception e) {
			System.out.println("Not able to click on " + byObject);
			System.out.println(e.getMessage());
			return false;
		}
	} // end of elementClick
}
