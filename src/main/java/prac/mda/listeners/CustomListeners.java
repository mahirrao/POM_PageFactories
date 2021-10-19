package prac.mda.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import prac.mda.Base.Page;
import prac.mda.utilities.ExtentManager;
import prac.mda.utilities.MailMonitoring;
import prac.mda.utilities.TestConfig;
import prac.mda.utilities.Utilities;

public class CustomListeners extends Page implements ITestListener, ISuiteListener
{
	static String messageBody;
	

	public void onTestStart(ITestResult result)
	{
		test = extentReport.createTest(result.getTestClass().getName()+"     @TestCase : "+result.getMethod().getMethodName());
        testReport.set(test);
        System.out.println("CustomListeners.onTestStart");
        
        System.out.println(result.getName() + ": " + Utilities.isTestRunnable(result.getMethod().getMethodName(), excelPath));
        
        if(!Utilities.isTestRunnable(result.getMethod().getMethodName(), excelPath))
        {
        	throw new SkipException("Testcase not runnable. Skipping the test: " + result.getName());
        }
	}

	public void onTestSuccess(ITestResult result)
	{
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
		
	}

	public void onTestFailure(ITestResult result)
	{
		String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		
//		testReport.log(Status.FAIL, result.getName() + " Failed with exteption: " +result.getThrowable());
		/*Flag to set to show the Screenshot link, instead of html code in plain text	*/	

		try {
			Utilities.captureScreenshot();
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(Utilities.screenShotName)
					.build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		testReport.log(Status.FAIL, (Markup) test.addScreenCaptureFromPath(TestUtil.screenShotPath));
		
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);
		
	}

	public void onTestSkipped(ITestResult result)
	{
//		testReport.log(Status.SKIP, result.getName() + " Skipped");
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"Test Case:- "+ methodName+ " Skipped"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
		// TODO Auto-generated method stub
//		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	public void onTestFailedWithTimeout(ITestResult result)
	{
		// TODO Auto-generated method stub
//		ITestListener.super.onTestFailedWithTimeout(result);
	}

	public void onStart(ITestContext context)
	{
		// TODO Auto-generated method stub
//		ITestListener.super.onStart(context);
	}

	public void onFinish(ITestContext context)
	{
		if (extentReport != null) {

			extentReport.flush();
		}
	}

	public void onStart(ISuite suite)
	{
		// TODO Auto-generated method stub
//		ISuiteListener.super.onStart(suite);
	}

	public void onFinish(ISuite suite)
	{
		MailMonitoring mail = new MailMonitoring();
		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/job/Data-driven%20Framework/Data-Driven_20framework_20report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to , TestConfig.subject , messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
