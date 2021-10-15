package prac.mda.pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageLocators
{
	@FindBy(xpath = "//input[contains(@placeholder, 'active Email ID')]")
	public WebElement userName;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'password')]")
	public WebElement password;

	@FindBy(css = "button.btn-primary.loginButton")
	public WebElement logInButton;
}
