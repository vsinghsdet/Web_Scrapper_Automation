/*
 * This source file was generated by the Gradle 'init' task
 */
package web_scrapper_automation;

import java.io.IOException;
import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


import web_scrapper_automation.Wrappers.Wrappers;

public class BaseTest {

    WebDriver driver;
    
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(Method m){
        ExtentReportManager.startTest(m.getName());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        DriverFactory.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(ITestResult iTestResult) throws IOException{
        if(iTestResult.getStatus() == ITestResult.SUCCESS){
            ExtentReportManager.logPass("Test Case Paas");
        }
        else if(iTestResult.getStatus() == ITestResult.FAILURE){
            ExtentReportManager.getExtentTest().log(Status.FAIL, 
            "Test Case Failed", 
            MediaEntityBuilder.createScreenCaptureFromPath(Wrappers.capture(driver)).build());
        }
        else{
            ExtentReportManager.logSkip("Test Case Skipped");
        }

        ExtentReportManager.endTest();
        DriverFactory.removeDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void endTestSuite(){
        ReportSingleton.getExtentReport().flush();
    }



}
