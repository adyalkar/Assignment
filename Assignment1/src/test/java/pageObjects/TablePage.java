package pageObjects;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class TablePage {
	final WebDriver driver;
	public Logger logger = Logger.getLogger("Table Page");

	public TablePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="example_length")
	WebElement drpdown;
	
	@FindBy(xpath="//th[text()='Age']")
	WebElement age;
	
	@FindBy(xpath="//table[@id='example']")
	WebElement table;
	
	public void tableProcess() {
		Select drp = new Select(drpdown);
		drp.selectByVisibleText("25");
		age.click();
		for(int i=0;i<25;i++) {
			List<WebElement> rows = table.findElements(By.xpath("./tbody/tr"));
			if((rows.get(i).findElement(By.xpath("./td[2]")).getText().contains("Software Engineer"))){
				if(Integer.parseInt(rows.get(i).findElement(By.xpath("./td[4]")).getText())<30) {
					System.out.println("----------------------------------------------------------");
					System.out.println("Name ="+rows.get(i).findElement(By.xpath("./td[1]")).getText());
					System.out.println("Position="+rows.get(i).findElement(By.xpath("./td[2]")).getText());
					System.out.println("Office="+rows.get(i).findElement(By.xpath("./td[3]")).getText());
					System.out.println("Age="+rows.get(i).findElement(By.xpath("./td[4]")).getText());
					System.out.println("Start="+rows.get(i).findElement(By.xpath("./td[5]")).getText());
					System.out.println(rows.get(i).findElement(By.xpath("./td[6]")).getText());
					System.out.println("----------------------------------------------------------");
				}
				
			}
		}
		
	}
}
