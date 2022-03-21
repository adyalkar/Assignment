package pageObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PepperfryPage {

	final WebDriver driver;
	public Logger logger = Logger.getLogger("Pepperfry Page");
	WebDriverWait wait;

	public PepperfryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="search")
	WebElement searchText;

	@FindBy(id="searchbutton")
	WebElement searchButton;

	@FindBy(xpath="//li[@class='radio']/label[@for='price-asc']")
	WebElement priceAscOrder;

	@FindBy(xpath="//span[@class='clipCard__price-offer']")
	List<WebElement> offerPrice;

	@FindBy(xpath="//div[@id='reg_login_box']/div/a[@class='popup-close']")
	WebElement popup;

	public void sortingCheck(String value) throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor)driver;
		searchText.clear();
		searchText.sendKeys(value);
		searchText.sendKeys(Keys.ENTER);
		try {
			popup.click();
			searchText.sendKeys(Keys.ENTER);
		}catch(Exception e) {
			System.out.println("2nd Attempt");
		}
		js.executeScript("arguments[0].click();", priceAscOrder);
		int iSize = offerPrice.size();	
		System.out.println("iSize = " + iSize);
		Thread.sleep(5000);
		ArrayList<Integer> actualSortedPriceList = new ArrayList<Integer>(iSize); 
		for(int i=0;i<iSize;i++) {
			String strPrice =  offerPrice.get(i).getText().replaceAll("[^0-9]","");
			int iPrice = Integer.parseInt(strPrice);
			actualSortedPriceList.add(iPrice);
		}

		//After Sort by 'Low to High'
		System.out.println("Price list before comparing with the collection sort method");
		for(int obj:actualSortedPriceList)  {  
			System.out.print(obj+":"); 
		}
		//print a new line
		System.out.println("");

		ArrayList<Integer> finalsortedlist = new ArrayList<Integer>(iSize);
		finalsortedlist = actualSortedPriceList;

		//After Sorting (In Ascending Order)
		Collections.sort(finalsortedlist);
		System.out.println("Price list After using Colection sort method");
		for(int obj1:finalsortedlist)  {
			System.out.print(obj1+":"); 
		}
		//print a new line
		System.out.println("");
		try {
			Assert.assertEquals(actualSortedPriceList, finalsortedlist);
		}catch(AssertionError e) {
			System.out.println("Sort By feature 'Low to High' is defective");
		}
	}


	public static void handlePopupsException(WebDriver driver, WebDriverWait wait ) {

		try {
			// //*[@id="page"]
			By popup1 = By.xpath("//*[@id='page']");
			wait.until(ExpectedConditions.visibilityOfElementLocated(popup1));
			WebElement wePopup1 = driver.findElement(popup1);
			wePopup1.click();
			wePopup1.sendKeys(Keys.ESCAPE);
			System.out.println("##Successfully handled popup1 - method2");

		}catch(Exception e1) {

			System.out.println("##No popup1 displayed ##");
			System.out.println("Exception " + e1.getMessage());
			System.out.println("##No popup1 displayed##");			

		}

		// //*[@id="webklipper-publisher-widget-container-notification-close-div"]
		//webklipper-publisher-widget-container-notification-frame
		try {
			// Second popup is in a frame
			By frameId = By.id("webklipper-publisher-widget-container-notification-frame");
			WebElement frameElement = driver.findElement(frameId);
			driver.switchTo().frame(frameElement);

			By popup2 = By.xpath("//*[@id=\"webklipper-publisher-widget-container-notification-close-div\"]");
			WebElement wePopup2 = driver.findElement(popup2);
			wait.until(ExpectedConditions.visibilityOfElementLocated(popup2));
			wePopup2.click();

			System.out.println("##Successfully handled popup2");

		}catch(Exception e2) {

			System.out.println("****No popup2 displayed****");
			System.out.println("Exception " + e2.getMessage());
			System.out.println("***No popup2 displayed***");
		}	
		
	}
}

