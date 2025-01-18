package com.example;

import java.util.List;
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
import java.util.ArrayList;

public class Scraper {
    
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private Wait<WebDriver> fasterWait;
    private List<Utbildning> utbildningar;
    private int NBR_OF_RESULTS_PER_PAGE = 15;
    


    public Scraper  (){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\oxxer\\Desktop\\programmeingsaker\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.get("https://www.studera.nu/jamfor-utbildning/?q=&p=2&_t_dtq=true");
        this.wait = new FluentWait<>(driver)
                                .withTimeout(Duration.ofSeconds(10))
                                .pollingEvery(Duration.ofMillis(500));
        this.fasterWait = new FluentWait<>(driver)
                                .withTimeout(Duration.ofSeconds(1))
                                .pollingEvery(Duration.ofMillis(200));

        this.utbildningar = new ArrayList<>();
                                
    }

    public void easySleep(int millisUpper, int millisLower){
        
        int millis = (int) (Math.random() * (millisUpper - millisLower) + millisLower);
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void scrape(){
        System.out.println("Scraping...");
        
        acceptCookies();
        fixFilters();
        
        Integer nbrOfResults = getNumberOfResults(); 
        Integer nbrOfPages = (int) Math.ceil(nbrOfResults / NBR_OF_RESULTS_PER_PAGE);

        for(int i = 1; i <= nbrOfPages; i++){
            System.out.println("Page: " + i);
            easySleep(200, 200);
            getResults();
            driver.get("https://www.studera.nu/jamfor-utbildning/?q=&p="+ i + "&_t_dtq=true");
            //WebElement nextPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#SCPaging > ul > li:nth-child(7) > a")));
            //nextPage.click();
        }                                                                               
        
    }
    
    public void fixFilters(){

        WebElement filterButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#filterMobileMenuToggle")));
        easySleep(100, 200);
        filterButton.click();
        WebElement filterUtbNiva = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#filterbutton8")));
        easySleep(100, 200);
        filterUtbNiva.click();
        WebElement forUtbildning = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#filterMenuToggle > li:nth-child(9) > ul > li:nth-child(1) > label")));
        forUtbildning.click();
        easySleep(400, 500);

        WebElement grundUtb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#filterMenuToggle > li:nth-child(9) > ul > li:nth-child(2) > label")));
        grundUtb.click();
        easySleep(500, 600);
        filterButton.click();
        easySleep(200, 300);
        

    }

    public void acceptCookies(){
        try{
            WebElement acceptCookies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#klaro > div > div > div > div > div > button.cm-btn.cm-btn-danger.cn-decline")));
            acceptCookies.click();
        }catch(Exception e){
            System.out.println("No cookies to accept");
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

        List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[id^='i.']")));
        //System.out.println(results.size());
        for(WebElement result : results){
            scrapeResult(result);
        }
        
    }

    public void scrapeResult(WebElement result){
        
        easySleep(200, 300);
        

        WebElement dropDownKnapp= wait.until(ExpectedConditions.visibilityOf(result.findElement(By.cssSelector("div.info-container > div > div.info-buttons > button.btn.btn-default.btn-accordion-toggle"))));
        dropDownKnapp.click();

        String utbNivaOchTyp = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[1]/div[4]/span[2]")))).getText();
        

        System.out.println(utbNivaOchTyp);

        String[] utbNivaOchTypArr = utbNivaOchTyp.replace(" ", "").split(",");
        String utbNiva = utbNivaOchTypArr[1];   //
        boolean sluta = utbNiva.equals("Avancerad");
        if(!sluta){
            System.out.println("Fortsatt");
            //ha kvar ellr inte
            String linkTillUtb = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[1]/a")))).getDomAttribute("href");
            //ha kvar
            String examin = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[2]/div[4]")))).getText().replace("Examen: ", "");

            setFilterForBetyg();
            scrapeBetyg(result, examin, linkTillUtb);
        } else{
            System.out.println("Sluta");
        }
        
    } 

    public void scrapeBetyg(WebElement result, String examin, String linkTillUtb){
        WebElement antagningsStatKnapp = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//*[@id=\"admission-stats-accordian\"]/div/button"))));
        antagningsStatKnapp.click();
        easySleep(200, 300);

        List<WebElement> antagningsTabeller = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".//div[4]")));
        for(WebElement antagningsTabell : antagningsTabeller){
            scrapeAntagningsTabell(antagningsTabell);
        }

        String utbNamn = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.cssSelector("div.info-container > div > div.info-left > h3")))).getText();
        String uni = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div[1]/div/div[1]/div/span[1]/text()")))).getText();
        //får med %
        String studietakt = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div[1]/div/div[1]/div/span[3]/text()")))).getText();
        String antalHP = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div[1]/div/div[1]/div/span[2]/text()")))).getText();
        String utbTyp = utbNivaOchTypArr[0];
        String studieform = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[1]/div[5]/span[2]")))).getText();
        String ort = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[2]/div[1]")))).getText().replace("Ort: ", ""); 

    }

    public void setFilterForBetyg(){
        WebElement filterButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"256-multiselect\"]/button")));
        
    }
}
