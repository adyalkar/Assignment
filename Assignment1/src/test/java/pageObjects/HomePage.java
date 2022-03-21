package pageObjects;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {


	final WebDriver driver;
	public Logger logger = Logger.getLogger("Home Page");

	List<String> monthList = Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sept","Oct","Nov","Dec");
	String expDate = null;
	int expMonth;
	int expYear;

	String calDate = null;
	int calMonth;
	int calYear;

	boolean dateNotFound;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Locating the username text box
	@FindBy(id = "src")
	WebElement sourceLocation;

	@FindBy(xpath=".//*[@id='search']/div/div[1]/div/ul/li[1]")
	WebElement sourceConfirmation;

	// Locating the password text box
	@FindBy(id = "dest")
	WebElement destinationLocation;

	@FindBy(xpath=".//*[@id='search']/div/div[2]/div/ul/li[1]")
	WebElement destiConfirmation;

	// Locating the Login button box
	@FindBy(id = "search_btn")
	WebElement searchButton;

	@FindBy(xpath=".//input[@id='onward_cal']")
	WebElement dateSelection;

	public void search(String From, String To) throws InterruptedException {
		sourceLocation.clear();
		sourceLocation.sendKeys(From);
		sourceConfirmation.click();
		destinationLocation.clear();
		destinationLocation.sendKeys(To);
		destiConfirmation.click();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(date);
		System.out.println(format);
		String[] dated = format.split("-");
		expDate=dated[0];
		//expDate = "20";
		expMonth=Integer.parseInt(dated[1]);
		expYear=Integer.parseInt(dated[2]);
		//expMonth = 3;
		//expYear=2022;
		dateNotFound = true;
		
		while(dateNotFound){
			WebElement monthYearEle = driver.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//table//td[@class='monthTitle']"));
			String monthYear= monthYearEle.getAttribute("innerHTML");

			String[] s = monthYear.split(" ");
			String calMonth = s[0];
			int calYear = Integer.parseInt(s[1]);


			////If current selected month and year are same as expected month and year then go Inside this condition.
			if(monthList.indexOf(calMonth)+1 ==expMonth && expYear==calYear){
				selectDate(expDate);
				dateNotFound = false;
			}

			//If current selected month and year are less than expected month and year then go Inside this condition
			else if(monthList.indexOf(calMonth)+1 <expMonth && expYear==calYear||expYear>calYear){
				//Click on next button of date picker.
				dateSelection.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//button[.='>']")).click();
			}
			//If current selected month and year are greater than expected month and year then go Inside this condition.
			else if(monthList.indexOf(calMonth)+1 >expMonth && expYear==calYear||expYear<calYear){
				dateSelection.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//button[.='<']")).click();
			}
		}
		
		searchButton.click();
	}

	public void selectDate(String date){
		WebElement datePicker = driver.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']"));
		List<WebElement> dates = datePicker.findElements(By.tagName("td"));
		for(WebElement temp:dates){
			if(temp.getText().equals(date)){
				temp.click();
				break;
			}
		}
	}

}
