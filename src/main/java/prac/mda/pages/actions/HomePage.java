package prac.mda.pages.actions;

import org.openqa.selenium.support.PageFactory;

import prac.mda.Base.Page;
import prac.mda.pages.locators.HomePageLocators;

public class HomePage extends Page
{
	public HomePageLocators home;
	public HomePage()
	{
		this.home = new HomePageLocators();
		PageFactory.initElements(driver, this.home);
	}
	
	public void loginToSite()
	{
		home.signInMenu.click();
	}
	
	public void goToFlights()
	{
		
	}
	
	public void goToHotels()
	{
		
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
