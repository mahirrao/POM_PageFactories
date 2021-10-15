package prac.mda.pages.actions;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import prac.mda.Base.Constants;
import prac.mda.Base.Page;
import prac.mda.pages.locators.HomePageLocators;

public class HomePage extends Page
{
	public HomePageLocators home;
	public HomePage()
	{
		this.home = new HomePageLocators();
		AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(ajaxFactory, this.home);
	}
	
	public void searchJobs(String searchKey, String location)
	{
		home.searchText.sendKeys(searchKey);
		home.locationText.sendKeys(location);
		home.searchButton.click();
	}
	
//	Go to Flight and Hotel packages
	
	public void goToPackages()
	{
		
	}
	
	public void bookAFlight(String from, String to, String departingDate, String returnDate,
			String adultsNumber, String childrenNumber)
	{
		
	}
	
	public void bookAFlight(String from, String to, String departingDate,
			String adultsNumber, String childrenNumber)
	{
		
	}
}
