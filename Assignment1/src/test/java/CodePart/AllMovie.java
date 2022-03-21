package CodePart;

import java.util.Properties;

import org.testng.annotations.Test;

import pageObjects.MoviePage;

public class AllMovie extends PublicLibrary{
	
	public AllMovie() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String classname;
	PublicLibrary library;
	
	@Test(priority=1)
	public void MovieSearchDetails() throws Exception {
		MoviePage moviePage = new MoviePage(driver);
		Properties config = getConfigProperties();
		navigateToPage(config.getProperty("movieUrl"));
		moviePage.movieResult();
	}

}
