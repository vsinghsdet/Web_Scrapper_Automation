package web_scrapper_automation.tests;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import web_scrapper_automation.BaseTest;
import web_scrapper_automation.DriverFactory;
import web_scrapper_automation.Wrappers.Wrappers;
import web_scrapper_automation.pages.HomePage;
import web_scrapper_automation.pages.OscarData;

public class testCase02 extends BaseTest{

   HomePage homePage;
   OscarData oscarData;

   @Test
   public void OscarMovieTC() throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://www.scrapethissite.com/pages/");
        homePage = new HomePage(driver);
        oscarData = new OscarData(driver);
        homePage.selectWebData(driver, "Oscar");
        oscarData.selectYear(driver, 5);

        String filePath = System.getProperty("user.dir") + File.separator + "oscar-winner-data.json";
        Wrappers.verifyFilePresenceAndContent(filePath);
        System.out.println("Verified Oscar Winner data presence");
   }
    
    
}
