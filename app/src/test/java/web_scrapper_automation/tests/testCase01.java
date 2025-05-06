package web_scrapper_automation.tests;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import web_scrapper_automation.BaseTest;
import web_scrapper_automation.DriverFactory;
import web_scrapper_automation.Wrappers.Wrappers;
import web_scrapper_automation.pages.HockeyData;
import web_scrapper_automation.pages.HomePage;

public class testCase01 extends BaseTest{
    
        HomePage homePage;
        HockeyData hockeyData;

        @Test
        public void HockeyTeamDataTC() throws InterruptedException{
            WebDriver driver = DriverFactory.getDriver();
            driver.get("https://www.scrapethissite.com/pages/");
            homePage = new HomePage(driver);
            hockeyData = new HockeyData(driver);
            homePage.selectWebData(driver, "Hockey");
            hockeyData.selectTableData(driver, 5);

            String filePath = System.getProperty("user.dir") + File.separator + "hockey-team-data.json";
            Wrappers.verifyFilePresenceAndContent(filePath);
            System.out.println("Verified Hocket Team data presence");
        }

}
