package prac.mda.testCases;

import org.testng.annotations.Test;

import prac.mda.Base.Page;
import prac.mda.pages.actions.HomePage;

public class SearchTest
{
	@Test
	public void searchTest()
	{
		Page.initConfiguration();
		HomePage home = new HomePage();
		home.searchJobs("Automation Testing", "Pune");
		Page.quitBrowser();
	}
}
