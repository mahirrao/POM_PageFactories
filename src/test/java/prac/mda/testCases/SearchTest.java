package prac.mda.testCases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import prac.mda.Base.Page;
import prac.mda.pages.actions.HomePage;
import prac.mda.utilities.Utilities;

public class SearchTest extends BaseTest
{
	@Test(dataProviderClass = Utilities.class, dataProvider = "TestData")
	public void searchTest(Hashtable<String, String> data)
	{

		if (data.get("RunMode").equalsIgnoreCase("y")) 
		{
			Page.initConfiguration();
			HomePage home = new HomePage();
			home.searchJobs(data.get("SearchKey"), data.get("Location"));
			Page.log.info(
					"Searching the keyword: " + data.get("SearchKey") + ", at the location: " + data.get("Location"));
			Page.testReport.get().log(Status.INFO,
					"Searching the keyword: " + data.get("SearchKey") + ", at the location: " + data.get("Location"));
		} else {
			Page.log.info("Test Skipped: " + this.getClass().getEnclosingMethod().getName());
			Page.testReport.get().log(Status.INFO, "Test Skipped: " + this.getClass().getEnclosingMethod().getName());
			throw new SkipException("Skipped the test: " + this.getClass().getEnclosingMethod().getName());
		}
	}

	@AfterMethod
	public void tearDown()
	{
		if (Page.driver != null)
			Page.quitBrowser();
	}
}
