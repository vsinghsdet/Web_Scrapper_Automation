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

import web_scrapper_automation.Wrappers.Oscar;
import web_scrapper_automation.Wrappers.Wrappers;

public class OscarData {
    
    WebDriver driver;

    static ArrayList<Oscar> oscar = new ArrayList<>();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @FindBy(xpath = "//a[@class='year-link']")
    List<WebElement> years;

    public OscarData(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void selectYear(WebDriver driver, int limit) throws InterruptedException{

        boolean isBestPicture = false;

        for(WebElement element : years){
            Wrappers.click(element, driver);
          
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            //waiting for films to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='film']")));
            
            int year = Integer.parseInt(element.getText().trim());
            List<WebElement> titles = driver.findElements(By.xpath("//tr[@class='film']"));
            int topFive = limit;
            int count = 1;
            for(WebElement filmTitle : titles){        
                String title = filmTitle.getText();
                WebElement nominationsElement = filmTitle.findElement(By.xpath("./td[contains(@class,'nominations')]"));
                int nominations = Integer.parseInt(nominationsElement.getText().trim());
                WebElement awardsElement = filmTitle.findElement(By.xpath("./td[contains(@class,'awards')]"));
                int awards = Integer.parseInt(awardsElement.getText().trim());
                if(count==1){
                    isBestPicture=true;
                }
                long epoch = System.currentTimeMillis()/1000;
                oscar.add(new Oscar(epoch, title, year, nominations, awards, isBestPicture));
                topFive--;
                if(topFive==0) break;
                count++;
                isBestPicture=false;
            }
            titles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr[@class='film']")));
        }

        String json = gson.toJson(oscar);

        // Write JSON to a file
        try (FileWriter writer = new FileWriter("oscar-winner-data.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    
}
