package CodePart;

import java.util.Properties;
import org.testng.annotations.Test;
import pageObjects.TablePage;

public class TableSearch extends PublicLibrary {

	public TableSearch() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static String classname;
	PublicLibrary library;
	
	@Test(priority=1)
	public void tableInformation() throws Exception {
		getLogger().info("Entered");
		Properties config = getConfigProperties();
		navigateToPage(config.getProperty("datatableUrl"));
		TablePage tableobj = new TablePage(driver);
		tableobj.tableProcess();
		
	}

}
