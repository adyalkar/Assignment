package CodePart;

import java.util.Properties;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.SearchPage;

public class RedBusTC extends PublicLibrary {

	public RedBusTC() throws Exception {
		super();
	}
     
	public static String classname;
	PublicLibrary library;
	
	@Test(priority=1)
	public void BusRatingDetails() throws Exception {
		Properties config = getConfigProperties();
		navigateToPage(config.getProperty("redbusUrl"));
		HomePage homeobj = new HomePage(driver);
		homeobj.search("Mumbai","Pune");
		Thread.sleep(10000);
		int previous_result = 1;
		int present_result = 0;
		SearchPage searchobj = new SearchPage(driver);
		while(previous_result!=present_result) {
			previous_result= present_result;
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		    present_result= searchobj.busStorage();
		}
		searchobj.busInfo(present_result);
	}
}
