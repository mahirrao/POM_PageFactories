package prac.mda.pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageLocators
{
	@FindBy(css = "input#qsb-keyword-sugg")
	public WebElement searchText;

	@FindBy(css = "input#qsb-location-sugg")
	public WebElement locationText;

	@FindBy(css = "div.search-btn button")
	public WebElement searchButton;
	
	
}
