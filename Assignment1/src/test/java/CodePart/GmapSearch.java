package CodePart;

import java.util.Properties;

import org.testng.annotations.Test;

import pageObjects.GoogleMap;

public class GmapSearch extends PublicLibrary {

	public GmapSearch() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String classname;
	PublicLibrary library;
	
	@Test(priority=1)
	public void MapDetails() throws Exception {
		GoogleMap mapPage = new GoogleMap(driver);
		Properties config = getConfigProperties();
		navigateToPage(config.getProperty("GmapUrl"));
		mapPage.addressSearch();
	}
}
