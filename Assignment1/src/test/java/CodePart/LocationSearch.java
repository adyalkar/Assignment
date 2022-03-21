package CodePart;

import java.util.Properties;

import org.testng.annotations.Test;
import pageObjects.LocationPage;

public class LocationSearch extends PublicLibrary {

	public LocationSearch() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static String classname;
	PublicLibrary library;
	
	@Test(priority=1)
	public void LocationDetails() throws Exception {
		LocationPage locPage = new LocationPage(driver);
		Properties config = getConfigProperties();
		navigateToPage(config.getProperty("MapsUrl"));
		locPage.locationSearch();
	}

}
