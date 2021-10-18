package prac.mda.testCases;

import org.testng.annotations.Test;

import prac.mda.Base.Page;
import prac.mda.errorCollectors.ErrorCollector;
import prac.mda.pages.actions.LoginPage;

public class LoginTest
{
	@Test
	public void loginTest()
	{
		Page.initConfiguration();
		ErrorCollector.verifyEquals(Page.topNavigation.topNavigation.headerTabs.size(), 8);
		LoginPage login = Page.topNavigation.goToLoginMenu();
		login.loginToSite("mayuresh.ahirrao@gmail.com", "Mayur31885");
		System.out.println("Logged In");
		Page.quitBrowser();
	}
}
