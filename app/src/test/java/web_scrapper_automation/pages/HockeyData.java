package web_scrapper_automation.pages;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import web_scrapper_automation.Wrappers.Team;
import web_scrapper_automation.Wrappers.Wrappers;

public class HockeyData {

    WebDriver driver;
    
    static ArrayList<Team> teamData = new ArrayList<>();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @FindBy(xpath = "//tr[@class='team']")
    List<WebElement> teamList;

    public HockeyData(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void selectTableData(WebDriver driver, int pages) throws InterruptedException{
        
        for(int i = 1 ; i <= pages ; i++){
            WebElement nextPage = driver.findElement(By.xpath("//a[@aria-label='Next']"));
            System.out.println("Page: "+ i);
            for(WebElement element : teamList){
                WebElement winPct = element.findElement(By.xpath("./td[contains(@class,'pct')]"));
                double winPctNum = Double.parseDouble(winPct.getText());
                if(winPctNum<0.40){
                    WebElement nameElement = element.findElement(By.xpath("./td[@class='name']"));
                    String name = nameElement.getText();
                    WebElement yearElement = element.findElement(By.xpath("./td[@class='year']"));            
                    int year = Integer.parseInt(yearElement.getText());
                    long epoch = System.currentTimeMillis()/1000;
                    teamData.add(new Team(epoch, name, winPctNum, year));
                }
            }   
            Wrappers.click(nextPage, driver);
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            teamList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr[@class='team']"))); 
           
        }

        String json = gson.toJson(teamData);

        // Write JSON to a file
        try (FileWriter writer = new FileWriter("hockey-team-data.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
