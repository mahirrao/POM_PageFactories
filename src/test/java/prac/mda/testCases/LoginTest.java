package prac.mda.testCases;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import prac.mda.Base.Page;
import prac.mda.errorCollectors.ErrorCollector;
import prac.mda.pages.actions.LoginPage;
import prac.mda.utilities.Utilities;

public class LoginTest extends BaseTest
{
	@Test(dataProviderClass = Utilities.class, dataProvider = "TestData")
	public void loginTest(Hashtable<String, String> data)
	{
		if (data.get("RunMode").equalsIgnoreCase("y"))
		{
			Page.initConfiguration();
			ErrorCollector.verifyEquals(Page.topNavigation.topNavigation.headerTabs.size(), 8);
			LoginPage login = Page.topNavigation.goToLoginMenu();
			login.loginToSite(data.get("UserName"), data.get("Password"));
			Page.log.info("Logged in to the page");
			Page.testReport.get().log(Status.INFO, "Logged in to the page");
		}
	}
	
	@AfterMethod
	public void tearDown()
	{
		if (Page.driver != null)
			Page.quitBrowser();
	}
}
