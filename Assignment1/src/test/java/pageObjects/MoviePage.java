package pageObjects;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class MoviePage {
	
	final WebDriver driver;
	public Logger logger = Logger.getLogger("Movie Page");
	
	public MoviePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//input[@type='search']")
	WebElement search;
	
	@FindBy(xpath="//div[@class='results']/h1")
	WebElement searchResult;
	
	@FindBy(xpath="//li[@class='movie']/div[2]/div[1]")
	List<WebElement> release;
	
	@FindBy(xpath="//span[@class='header-movie-genres']/a")
	WebElement genres;
	
	@FindBy(xpath="//span[contains(text(),'MPAA Rating')]/span")
	WebElement rating;
	
	@FindBy(xpath="//a[contains(text(),'Cast & Crew')]")
	WebElement castCrew;
	
	@FindBy(xpath="//div[@class='director_container']//div[@class='info']//a")
	WebElement directorName;
	
	@FindBy(xpath="//a[text()='Al Pacino']/../../div[@class='cast_role']")
	WebElement castRole;
	
	public void movieResult() throws InterruptedException {
		search.sendKeys("The Godfather");
		search.sendKeys(Keys.ENTER);
		System.out.println(searchResult.getText());
		for(int i=0;i<release.size();i++) {
			if(release.get(i).getText().contains("1972")) {
				Thread.sleep(3000);
				release.get(i).findElement(By.xpath("./a")).click();
				break;
			}else {
				continue;
			}
		}
		String Genres= genres.getText() ;
		String Rating = rating.getText();
		System.out.println("Genres = "+Genres);		
		System.out.println("Rating = "+Rating);
		castCrew.click();
		String DirectorName =directorName.getText() ;
		String AlPacinoCastRole=castRole.getText();
		System.out.println("Director Name = "+DirectorName);
		System.out.println("Al Pacino CastRole = "+AlPacinoCastRole);
		Assert.assertEquals(Genres, "Crime");
		Assert.assertEquals(Rating, "A");
		Assert.assertEquals(DirectorName,"Francis Ford Coppola");
		Assert.assertEquals(AlPacinoCastRole, "Michael Corleone");	
	}
}
