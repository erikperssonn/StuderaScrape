package com.example;

import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.stream.Collectors;
import org.openqa.selenium.support.ui.Wait;

public class Scraper {
    
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private int NBR_OF_RESULTS_PER_PAGE = 15;
    private 


    public Scraper  (){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\oxxer\\Desktop\\programmeingsaker\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.get("https://www.studera.nu/jamfor-utbildning/?q=&p=2&_t_dtq=true");
        this.wait = new FluentWait<>(driver)
                                .withTimeout(Duration.ofSeconds(10))
                                .pollingEvery(Duration.ofMillis(500));
                                
    }

    public void scrape(){
        System.out.println("Scraping...");
        
        Integer nbrOfResults = getNumberOfResults(); 
        Integer nbrOfPages = (int) Math.ceil(nbrOfResults / NBR_OF_RESULTS_PER_PAGE);

        for(int i = 1; i <= nbrOfPages; i++){
            System.out.println("Page: " + i);
            driver.get("https://www.studera.nu/jamfor-utbildning/?q=&p=" + i + "&_t_dtq=true");
            getResults();
        }

    }

    public int getNumberOfResults(){
        String results = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#resultmetrics"))).getText();   
        String digits = results.chars()
                .filter(Character::isDigit)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
        Integer result = Integer.parseInt(digits);
        System.out.println(result); 
        return result;
    }

    public void getResults(){

    }
}
