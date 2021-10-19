package prac.mda.testCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import prac.mda.Base.Page;

public class BaseTest
{
	@BeforeTest
	public void setUp()
	{
		Page.initConfiguration();
	}
	
	@AfterTest
	public void tearDown()
	{
		Page.quitBrowser();		
	}
}
