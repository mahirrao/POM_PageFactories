package prac.mda.pages.locators;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopNavigationLocators
{
	@FindBy(id = "login_Layer")
	public WebElement logInMenu;
	
	@FindBy(css = "div.headGNB.cloneCont.wrap>ul.midSec.menu>li")
	public List<WebElement> headerTabs;	
}
