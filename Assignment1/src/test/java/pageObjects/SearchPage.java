package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

	final WebDriver driver;
	public Logger logger = Logger.getLogger("Search Page");

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//*[@class='bus-items']/div/li")
	List<WebElement> busInfo;
	
	@FindBy(xpath="//div[@class='close']")
	WebElement close;


	public int busStorage() {
		//close.click();
		System.out.println("Number of Buses"+busInfo.size());
		return busInfo.size();
	}
	
	public void busInfo(int value) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		
		File src=new File("./testdata/test.xlsx");
		FileOutputStream fout=new FileOutputStream(src);
		XSSFWorkbook wb=new XSSFWorkbook();
		XSSFSheet sh1 = wb.createSheet("Sheet1");
		XSSFSheet sh2 = wb.createSheet("Sheet2");
		Float rating;
		int sh1row=1;
		int sh2row=1;
		for(int i=0;i<value;i++) {
			try {
				rating=Float.parseFloat(busInfo.get(i).findElement(By.xpath("./div/div/div/div[5]//span")).getText());
			}catch(Exception e) {
				continue;
			}
			if(rating>4) {
				Row row = sh1.createRow(sh1row);
				row.createCell(0).setCellValue(busInfo.get(i).findElement(By.xpath("./div/div/div/div/div")).getText());
				row.createCell(1).setCellValue(busInfo.get(i).findElement(By.xpath("./div/div/div/div[5]//span")).getText());
				sh1row++;
			}else {
				Row row = sh2.createRow(sh2row);
				row.createCell(0).setCellValue(busInfo.get(i).findElement(By.xpath("./div/div/div/div/div")).getText());
				row.createCell(1).setCellValue(busInfo.get(i).findElement(By.xpath("./div/div/div/div[5]//span")).getText());
				sh2row++;
			}
			map.put(busInfo.get(i).findElement(By.xpath("./div/div/div/div/div")).getText(),busInfo.get(i).findElement(By.xpath("./div/div/div/div[5]//span")).getText());
		}
		
		wb.write(fout);
		fout.flush();
		fout.close();
		System.out.println(map);
	}

}
