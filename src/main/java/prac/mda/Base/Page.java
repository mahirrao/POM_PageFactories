package prac.mda.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import prac.mda.pages.actions.TopNavigation;
import prac.mda.utilities.ExcelReader;
import prac.mda.utilities.ExtentManager;
import prac.mda.utilities.Utilities;

public class Page
{
	// Static Variables
	
	private static final String execDir = "\\src\\test\\resources\\prac\\mda\\webDriverExecutables\\";
	private static final String excelFileName = "testData.xlsx";
	public static final String baseDir = System.getProperty("user.dir");
	public static final String propertiesDir = "\\src\\test\\resources\\prac\\mda\\properties\\";
	public static final String reportsDir = "\\target\\surefire-reports\\html\\";
	public static final String excelDir = "\\src\\test\\resources\\prac\\mda\\excel\\";

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions builder;

	public static String browser;

	public static Logger log = Logger.getLogger("mayureshLogger");

	public static ExcelReader excelPath = new ExcelReader(baseDir + excelDir + excelFileName);
	public static String datePattern = "dd-MMM-yyyy_HH-mm-ss-SSS_z";
	private static String reportName = "ExtentReport.html";// "Extent_" + getCurrentDataTime() + ".html";
	public static ExtentReports extentReport = ExtentManager.getInstance(baseDir + reportsDir + reportName);
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	public static ExtentTest test;

	public static TopNavigation topNavigation;
//	public static CRMHeaders header;

	public static boolean oldURL = false;

//	public Page()
//	{
//		if (driver == null) {
//			test = extentReport.createTest("Page Object Model Test");
//			testReport.set(test);
////			
//
//
//
//
////			header = new CRMHeaders(driver);
//		}
//	}

	public static void initConfiguration()
	{
		// Jenkins env setup

		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
			System.out.println(browser);
		} else
			browser = Constants.browser;

//		config.setProperty("browser", browser);
		Constants.browser = browser;
		
		// Start the browser based upon the properties file

		switch (browser) {
		case "Firefox":
			launchFirefoxBrowser();
			break;

		case "Chrome":
			launchChromeBrowser();
			break;

		case "IE":
			launchIEbrowser();
			break;

		default:
			launchChromeBrowser();
			break;
		}
		
		driver.get(Constants.testURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Constants.implicitWait, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Constants.explicitWait);
//		wait.until(ExpectedConditions.alertIsPresent(null));
//		wait.until(ExpectedConditions.and(null));
//		wait.until(ExpectedConditions.attributeContains(null));
//		wait.until(ExpectedConditions.attributeToBe(null));
//		wait.until(ExpectedConditions.attributeToBeNotEmpty(null));
//		wait.until(ExpectedConditions.elementSelectionStateToBe(null));
//		wait.until(ExpectedConditions.elementToBeClickable(null));
//		wait.until(ExpectedConditions.elementToBeSelected(null));
//		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(null));
//		wait.until(ExpectedConditions.invisibilityOf(null));
//		wait.until(ExpectedConditions.javaScriptThrowsNoExceptions(null));
//		wait.until(ExpectedConditions.jsReturnsValue(null));
//		wait.until(ExpectedConditions.numberOfElementsToBe(null));
//		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(null));
//		wait.until(ExpectedConditions.numberOfWindowsToBe(null));
//		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(null));
//		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(null));
//		wait.until(ExpectedConditions.stalenessOf(null));
//		wait.until(ExpectedConditions.textMatches(null));
//		wait.until(ExpectedConditions.titleContains(null));
//		wait.until(ExpectedConditions.urlContains(null));
//		wait.until(ExpectedConditions.visibilityOfAllElements(null));
//		wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(null));
		
		
		topNavigation = new TopNavigation();
		
	}

	private static void launchFirefoxBrowser()
	{
//		System.setProperty("webdriver.gecko.driver", baseDir + execDir + "geckodriver.exe");
		
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		log.info("Firefox browser launched");
	}

	private static void launchIEbrowser()
	{
		System.setProperty("webdriver.ie.driver", baseDir + execDir + "IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		log.info("IE browser launched");
	}

	private static void launchChromeBrowser()
	{
//		System.setProperty("webdriver.chrome.driver", baseDir + execDir + "chromedriver.exe");
		
		WebDriverManager.chromedriver().setup();
		/*
		 * Removes the unwanted pop-ups at the start of the test
		 */
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-infobars");
		driver = new ChromeDriver(options);
		log.info("Chrome browser launched");
	}

	public static void quitBrowser()
	{
		if (driver != null) {
			driver.quit();
		}
		log.info("Test Execution completed!!!");
	}

	// Methods Library

	public static boolean isElementPresent(String locator)
	{
//		By elementLocator = getElement(locator);
		try {
//			driver.findElement(elementLocator);
			return true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static String getCurrentDataTime()
	{
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
//		LocalDateTime now = LocalDateTime.now(); 
//		String currentDateTime = dtf.format(now);

		Date date = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		String currentDateTime = dateFormatter.format(date);
		return currentDateTime;
	}

	public void verifyEquals(String pageTitle, String expectedTitle) throws IOException
	{
		try {
			Assert.assertEquals(pageTitle, expectedTitle);
		} catch (Throwable t) {
			Utilities.captureScreenshot();

//			ReportNG REports Settings
			System.setProperty("org.uncommons.reportng.escape-output", "false"); // Required for ReportNG reports
			Reporter.log("<br>" + "Verification Failed: " + t.getMessage()); // Required for ReportNG reports
			/*
			 * Creates a link to the screenshot, in the reports The attribute : target =
			 * "_blank" - Opens the screenshot in the new tab
			 */
//			Reporter.log("<a target = \"_blank\" href="+TestUtil.screenShotName+">Screenshot</a>");		// Required for ReportNG reports

//			Add the image as thumbnail for the link
			Reporter.log("<a target = \"_blank\" href=" + Utilities.screenShotName + "><img src="
					+ Utilities.screenShotName + " height=200 width=200></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
//			Extent Reports Settings
			testReport.get().log(Status.FAIL, "Verification failed with exception: " + "");
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(Utilities.screenShotName).build());

		}
	}

	/*
	 * Click method for individual element
	 */
	public static void click(WebElement element)
	{
		element.click();
		log.info("Clicking on an Element : " + element);
		testReport.get().log(Status.INFO, "Clicking on : " + element);
	}

	/*
	 * Click method for element in an array list of elements
	 */
	public static void click(List<WebElement> elements, int index)
	{
		elements.get(index).click();
		log.info("Clicking on " + index + " Element from the list: " + elements);
		testReport.get().log(Status.INFO, "Clicking on " + index + " Element from the list: " + elements);
	}

	public static void type(WebElement element, String value)
	{
		element.sendKeys(value);
		log.info("Typing in an Element : " + element + " entered value as : " + value);
		testReport.get().log(Status.INFO, "Typing in : " + element + " entered value as " + value);
	}

	public static void hoverAndClick(WebElement hoverElement, By menuItemLocator)
	{
		builder = new Actions(driver);
		builder.moveToElement(hoverElement).build().perform();

		wait.until(ExpectedConditions.presenceOfElementLocated(menuItemLocator));
		driver.findElement(menuItemLocator).click();
	}

}
