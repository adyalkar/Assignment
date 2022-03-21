package pageObjects;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LocationPage {
	final WebDriver driver;
	public Logger logger = Logger.getLogger("Google Map");

	public LocationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="searchboxinput")
	WebElement searchBox;
	
	@FindBy(xpath="//div/h1/span[1]")
	WebElement stadiumText;
	
	@FindBy(xpath="//div[@jsaction='pane.rating.moreReviews']/span[1]/span/span")
	WebElement rating;
	
	@FindBy(xpath="//button[@jsaction='pane.rating.moreReviews']")
	WebElement reviews;
	
	@FindBy(xpath="//button//div[text()='mumbaicricket.com']")
	WebElement websiteLink;
	
	@FindBy(xpath="//button[@data-item-id='address']/div[1]/div[2]/div[1]")
	WebElement address;
	
	public void locationSearch() throws InterruptedException, IOException {
		searchBox.sendKeys("Wankhede Stadium");
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator+"LocationInfo1"+".png")); 
		Assert.assertTrue(stadiumText.getText().contains("Stadium"));
		String title = driver.getTitle();
		System.out.println("Page Title= "+title);
		System.out.println("Rating of Location = "+ rating.getText());
		System.out.println("Review of Location = "+ reviews.getText());
		System.out.println("Website Link = "+websiteLink.getText());
		System.out.println("Address ="+address.getText()); 
		Assert.assertTrue(websiteLink.isDisplayed());
		File file2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file2, new File(System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator+"LocationInfo2"+".png"));
		Assert.assertEquals(title, "Wankhede Stadium - Google Maps");
		
	}
}
