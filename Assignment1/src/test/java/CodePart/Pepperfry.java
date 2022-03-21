package CodePart;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.testng.annotations.Test;

import Utility.ExcelOperations;
import pageObjects.PepperfryPage;

public class Pepperfry extends PublicLibrary  {
	
	public Pepperfry() throws Exception {
		super();
	}
     
	public static String classname;
	
	
	
	@Test(priority=1)
	public void orderCheck() throws Exception {
		getExcelSheetRW();
		HSSFSheet sheet = ExcelOperations.getSheet("Sheet1", "/testdata/Pepperfry.xls");
		ArrayList<String> list = new ArrayList<String>();
		list=getExcelSheetRW().getValueFromExcel(sheet);
		System.out.println(list);
		Properties config = getConfigProperties();
		navigateToPage(config.getProperty("PepperfryUrl"));
		PepperfryPage pepobj = new PepperfryPage(driver);
		for(int i=1;i<list.size();i++) {
			pepobj.sortingCheck(list.get(i));
		}
	}
}
