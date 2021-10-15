package prac.mda.pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageLocators
{
	@FindBy(xpath = "//button[@type = 'button']/div[text()= 'Sign in']")
	public WebElement signInMenu;
}
