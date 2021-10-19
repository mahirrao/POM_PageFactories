package prac.mda.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
//		driver.manage().timeouts().implicitlyWait(Constants.implicitWait, TimeUnit.SECONDS);
//		wait = new WebDriverWait(driver, Constants.explicitWait);
		
		topNavigation = new TopNavigation();
		
	}

	private static void launchFirefoxBrowser()
	{
		System.setProperty("webdriver.gecko.driver", baseDir + execDir + "geckodriver.exe");
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
		System.setProperty("webdriver.chrome.driver", baseDir + execDir + "chromedriver.exe");
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
	public void click(By locator)
	{
		driver.findElement(locator).click();
	}

	/*
	 * Click method for element in an array list of elements
	 */
	public void click(By locator, int index)
	{
		(driver.findElements(locator)).get(index).click();
	}

	public static void click(String locator)
	{
//		By elementLocator = getElement(locator);

//		driver.findElement(elementLocator).click();
		log.debug("Clicking on an Element : " + locator);
		testReport.get().log(Status.INFO, "Clicking on : " + locator);
//		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

//	public static By getElement(String locator)
//	{
//		if (locator.endsWith("_CSS")) {
//			return By.cssSelector(OR.getProperty(locator));
//		} else if (locator.endsWith("_XPATH")) {
//			return By.xpath(OR.getProperty(locator));
//		} else if (locator.endsWith("_ID")) {
//			return By.id(OR.getProperty(locator));
//		}
//		return null;
//	}

	public static void type(String locator, String value)
	{
//		By elementLocator = getElement(locator);

//		driver.findElement(elementLocator).sendKeys(value);
		log.debug("Typing in an Element : " + locator + " entered value as : " + value);
		testReport.get().log(Status.INFO, "Typing in : " + locator + " entered value as " + value);

//		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}

	public void type(By locator, String value)
	{
		driver.findElement(locator).sendKeys(value);
	}

	public static void hoverAndClick(WebElement hoverElement, By menuItemLocator)
	{
		builder = new Actions(driver);
		builder.moveToElement(hoverElement).build().perform();

		wait.until(ExpectedConditions.presenceOfElementLocated(menuItemLocator));
		driver.findElement(menuItemLocator).click();
	}

}
