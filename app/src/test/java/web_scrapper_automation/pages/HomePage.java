package web_scrapper_automation.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import web_scrapper_automation.Wrappers.Wrappers;

public class HomePage {
    
    WebDriver driver;

    @FindBy(xpath = "//h3[@class='page-title']/a")
    List<WebElement> list;

    public HomePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void selectWebData(WebDriver driver, String webTable){
        for(WebElement element : list){
            if(element.getText().contains(webTable)){
                Wrappers.click(element, driver);
                break;
            }
        }
    }
}
