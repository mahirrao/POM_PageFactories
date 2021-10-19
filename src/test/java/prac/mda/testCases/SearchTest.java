package prac.mda.testCases;

import org.testng.annotations.Test;

import prac.mda.Base.Page;
import prac.mda.pages.actions.HomePage;

public class SearchTest extends BaseTest
{
	@Test
	public void searchTest()
	{
		HomePage home = new HomePage();
		home.searchJobs("Automation Testing", "Pune");
	}
}
