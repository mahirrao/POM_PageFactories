package prac.mda.pages.actions;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import prac.mda.Base.Page;
import prac.mda.pages.locators.HomePageLocators;
import prac.mda.pages.locators.LoginPageLocators;

public class LoginPage extends Page
{
	public LoginPageLocators login;
	public LoginPage()
	{
		this.login = new LoginPageLocators();
		AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(ajaxFactory, this.login);
	}

	public void loginToSite(String userName, String password)
	{
		type(login.userName, userName);
		type(login.password, password);
		click(login.logInButton);
	}
}
