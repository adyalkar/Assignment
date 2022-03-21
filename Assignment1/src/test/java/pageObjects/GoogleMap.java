package pageObjects;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoogleMap {

	final WebDriver driver;
	public Logger logger = Logger.getLogger("Google Map");

	public GoogleMap(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="searchboxinput")
	WebElement searchBox;

	@FindBy(xpath="//h2/span")
	List<WebElement> addressInfo;
	
	@FindBy(xpath="//div[text()='Directions']")
	WebElement directions;
	
	@FindBy(xpath="//img[@aria-label='Driving']")
	WebElement driving;
	
	@FindBy(xpath="//input[@placeholder='Choose starting point, or click on the map...']")
	WebElement startingPoint;
	
	@FindBy(xpath="//div[@id='section-directions-trip-0']/div/div/div/div/span")
	WebElement firstTime;
	
	@FindBy(xpath="//div[@id='section-directions-trip-0']/div/div/div/div[2]/div")
	WebElement distance;
	


	public void addressSearch() throws InterruptedException, IOException {
		searchBox.sendKeys("Manish Nagar Nagpur");
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		for(int i=0;i<addressInfo.size();i++) {
			System.out.println(addressInfo.get(i).getText());
		}
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator+"AddressInfo"+".png"));
		directions.click();
		startingPoint.sendKeys("Airoli,Mumbai");
		startingPoint.sendKeys(Keys.ENTER);
		driving.click();
		
		System.out.println("Total Time of Journey"+firstTime.getText());
		System.out.println("Total distance of Journey"+distance.getText());
		
	}

}
