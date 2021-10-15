package prac.mda.pages.actions;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import prac.mda.Base.Constants;
import prac.mda.Base.Page;
import prac.mda.pages.locators.HomePageLocators;
import prac.mda.pages.locators.TopNavigationLocators;

public class TopNavigation
{
	public TopNavigationLocators topNavigation;
	public TopNavigation()
	{
		this.topNavigation = new TopNavigationLocators();
		AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(Page.driver, 10);
		PageFactory.initElements(ajaxFactory, this.topNavigation);
	}
	
	public LoginPage goToLoginMenu()
	{
		topNavigation.logInMenu.click();
		return new LoginPage();
	}
		
	public void openMyNaukri()
	{

	}

	public void openJobs()
	{

	}

	public void openRecruiters()
	{

	}

	public void openCompanies()
	{

	}

	public void openTools()
	{

	}

	public void openServices()
	{

	}

	public void openMore()
	{

	}

	public void openNotifications()
	{

	}
}
