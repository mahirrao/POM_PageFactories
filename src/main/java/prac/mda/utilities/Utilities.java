package prac.mda.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import prac.mda.Base.Page;


public class Utilities extends Page
{
	public static String screenShotName;
	public static String screenShotPath;
	
	public static void captureScreenshot() throws IOException
	{
		screenShotName = "Error_" + getCurrentDataTime() + ".jpg";
		screenShotPath = baseDir + reportsDir + screenShotName;
		
		File ssFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ssFile, new File(screenShotPath));
	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel)
	{
		String sheetName="Test_Suite";
		int rows = excel.getRowCount(sheetName);
		int columns = excel.getColumnCount(sheetName);
		
		for(int rowNum=2; rowNum<=rows; rowNum++)
		{
			String testCase = excel.getCellData(sheetName, "TCID", rowNum);
			if(testCase.equalsIgnoreCase(testName))
			{
				String runMode = excel.getCellData(sheetName, "RunMode", rowNum);
				if(runMode.equalsIgnoreCase("y"))
					return true;
				else
					return false;
			}
		}

		return false;
	}
	
	@DataProvider(name="TestData")
	public Object[][] getData(Method method)
	{
//		String className = method.getDeclaringClass().getName();
//		String sheetName = className.substring(className.indexOf(".")+1, className.length());
		String sheetName = method.getName();
		System.out.println(sheetName);
		int rows = excelPath.getRowCount(sheetName);
		int columns = excelPath.getColumnCount(sheetName);
		
		Object[][] menuItems = new Object[rows-1][1];
		Hashtable<String, String> table = null;
		
		for(int rowNum=2; rowNum<=rows; rowNum++)
		{
			table = new Hashtable<String, String>();
			for(int colNum=0; colNum<columns; colNum++)
			{
				table.put(excelPath.getCellData(sheetName, colNum, 1), excelPath.getCellData(sheetName, colNum, rowNum));
				menuItems[rowNum-2][0] = table;
			}
		}

//		menuItems[0][0] = "Top Gainers";	FundamentalAnalysis
//		
//		menuItems[1][0] = "Top Losers";		
		
		return menuItems;		
	}
}
