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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Scraper {
    
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private Wait<WebDriver> fasterWait;
    private List<Utbildning> utbildningar;
    private int NBR_OF_RESULTS_PER_PAGE = 15;
    


    public Scraper  (){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\oxxer\\Desktop\\programmeingsaker\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.get("https://www.studera.nu/jamfor-utbildning/?q=&p=1&_t_dtq=true");
        //this.wait = new FluentWait<>(this.driver)
        //                        .withTimeout(Duration.ofSeconds(10))
        //                        .pollingEvery(Duration.ofMillis(500));
        //this.fasterWait = new FluentWait<>(this.driver)
        //                        .withTimeout(Duration.ofSeconds(1))
        //                        .pollingEvery(Duration.ofMillis(200));
//      
        //this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.fasterWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        this.wait = new FluentWait<>(driver)
                            .withTimeout(Duration.ofSeconds(10))
                            .pollingEvery(Duration.ofMillis(500))
                            .ignoring(NoSuchElementException.class);
                            

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
        //fixFilters();
        //System.out.println("1");
        //WebElement test = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#filterMobileMenuTole")));
        //System.out.println("2");

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
            
            String linkTillUtb = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[1]/a")))).getDomAttribute("href");
            String examen = wait.until(ExpectedConditions.visibilityOf(result.findElement(By.xpath(".//div/div[2]/div/div[2]/div[4]")))).getText().replace("Examen: ", "");

            
            scrapeBetyg(result, examen, linkTillUtb);
            dropDownKnapp.click();
        } else{
            System.out.println("Sluta");
        }
        
    } 

    public void scrapeBetyg(WebElement result, String examen, String linkTillUtb){
        easySleep(1000, 1000);
        WebElement antagningsStatKnapp = tryToScrape(".//*[@id=\"admission-stats-accordian\"]/div/button", 7, result);
        antagningsStatKnapp.click();
        easySleep(200, 300);     //*[@id="admission-stats-accordian"]/div/button
        
        Boolean cont = false;
        cont = setFilterForBetyg(result, cont);
            
            
        List<WebElement> antagningsTabeller = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[contains(@id, '-anstat-collapse')]/div")));
        System.out.println (antagningsTabeller.size()+ " antagningsTabeller");
        
        List<WebElement> filteredAntagningsTabeller = antagningsTabeller.stream()
        .filter(k -> "antstat-tables".equals(k.getDomAttribute("class")))
        .collect(Collectors.toList());
        for(WebElement antagningsTabell : filteredAntagningsTabeller){
            System.out.println(antagningsTabell.getDomAttribute("class") + "hej2");
        }
        System.out.println(filteredAntagningsTabeller.size() + " filtered antagningsTabeller");

        for(WebElement antagningsTabell : filteredAntagningsTabeller){
            System.out.println(antagningsTabell.getDomAttribute("class") + "hej");
            System.out.println("cont " + cont);
            if(cont){
                scrapeAntagningsTabell(antagningsTabell, examen, linkTillUtb);
            }
        }

        

    }

    public Boolean setFilterForBetyg(WebElement result, Boolean cont){
        WebElement filterButton = tryToScrape(".//div[2]/div/div[1]/div[1]/div[2]/div[2]/div/fieldset/div/button", 7, result);
        filterButton.click();                    
        
        List<WebElement> filterAlternativ = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[contains(@id, 'multiselect')]/div/ul/li/label/span")));
        System.out.println(filterAlternativ.size());                                                   
        
        int i = 0;
        String B1 = "BI - Gymnasiebetyg utan komplettering";
        String B2 = "BII - Gymnasiebetyg med komplettering";
        String HP = "HP - Högskoleprov";
        
        try{ 
            while(i < 3){
                String filter = filterAlternativ.get(i).getText();
                if(filter.equals(B1) || filter.equals(B2) || filter.equals(HP)){
                    filterAlternativ.get(i).click();
                    cont = true;
                }
                i++;
            }
        } catch(IndexOutOfBoundsException e){

        }

        filterButton.click();

        WebElement visa = tryToScrape(".//*[contains(@id, '-anstat-collapse')]/div[2]/div/div/button[1]", 7, result);
        visa.click();

        return cont;
    }

    public void scrapeAntagningsTabell(WebElement antagningsTabell, String examen, String linkTillUtb){
        
        Utbildning utbildning = new Utbildning();
        utbildning.setExamen(examen);
        utbildning.setLink(linkTillUtb);

        
        String namn = tryToScrape(".//div/h5", 7, antagningsTabell).getText();
        System.out.println("Namn: " + namn);
    
        String uni = tryToScrape(".//div/div[1]/span[1]", 7, antagningsTabell).getText();
        System.out.println("Uni: " + uni);
    
        String antalHP = tryToScrape(".//div[1]/span[2]", 7, antagningsTabell).getText().split(" ")[0];
        //antalHP.chars()
        //        .filter(c -> Character.isDigit(c) || c == ',')
        //        .mapToObj(c -> String.valueOf((char) c))
        //        .collect(Collectors.joining());

        antalHP = antalHP.replace("Omfattning", "").replace(",", ".");
        System.out.println("Antal HP: " + antalHP);

        String takt = tryToScrape(".//div/div[1]/span[3]", 7, antagningsTabell).getText().replace("%", "");
        System.out.println("Takt: " + takt);
        takt = takt.replace("Studietakt", "");

        String utbTyp = tryToScrape(".//div/div[1]/span[4]", 7, antagningsTabell).getText();
        utbTyp = utbTyp.replace("Utbildningsnivå", "");
        System.out.println("UtbTyp: " + utbTyp);
        

        String utbDistansEllerInte = tryToScrape(".//div/div[1]/span[5]", 7, antagningsTabell).getText();
        System.out.println("UtbDistansEllerInte: " + utbDistansEllerInte);

        String plats = "";
        try{
            plats = tryToScrape(".//div/div[1]/span[6]", 1, antagningsTabell).getText();
            System.out.println("Plats: " + plats);
        } catch(TimeoutException e){
            plats = "Ortsoberoende";
        }

        utbildning.setNamn(namn);
        utbildning.setUni(uni);
        utbildning.setAntalHP(Double.valueOf(antalHP));
        utbildning.setStudietakt(Integer.valueOf(takt));
        utbildning.setUtbildningstyp(utbTyp);
        utbildning.setPlatsDistans(utbDistansEllerInte);
        utbildning.setOrt(plats);

        scrapeBetygData(antagningsTabell, utbildning);

    }

//*[@id="29-anstat-collapse"]/div[3]
//*[@id="29-anstat-collapse"]/div[3]/div
//*[@id="29-anstat-collapse"]/div[3]/div/h5
//*[@id="51-anstat-collapse"]/div[5]/div/h5
//*[@id="51-anstat-collapse"]/div[4]/div/h5

    public void scrapeBetygData(WebElement antagningtabell, Utbildning utbildning){
        //*[@id="29-anstat-collapse"]/div[3]/div/div[2]/table/tbody/tr[2]
        //*[@id="29-anstat-collapse"]/div[3]/div/div[2]/table/tbody/tr[2]
        //*[@id="29-anstat-collapse"]/div[4]/div/div[2]/table/tbody/tr[2]
        //*[@id="29-anstat-collapse"]/div[4]/div/div[2]/table/tbody/tr[4]

        List<WebElement> TRS = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//table/tbody/tr")));
        System.out.println(TRS.size() + " TRS");
        TRS.stream()
                .map(k -> k.findElement(By.xpath("./*")))
                //.filter(o -> "row".equals(o.getDomAttribute("aria-label")))
                .collect(Collectors.toList());

        System.out.println(TRS.size() + " TRS");

        for(WebElement TR : TRS){
            System.out.println(TR.getDomAttribute("aria-label") + " TR");
            System.out.println(TR.getText());
        }
    }
    

    public WebElement tryToScrape(String path, double seconds, WebElement fromElement){
        try{
            if(seconds <= 0){
                throw new TimeoutException();
            }
            WebElement element = wait.until(ExpectedConditions.visibilityOf(fromElement.findElement(By.xpath(path))));
            return element;
        } catch(NoSuchElementException e){
            easySleep(100, 100);
            return tryToScrape(path, seconds - 0.1, fromElement);
        }
    }

        
}
//html/body/main/div/div/div/div/div/searchcompare/div[3]/div/div[3]/div[1]/ul/li[1]/div[2]/div/div[1]/div[1]/div[2]/div[2]/div/fieldset/div/button
//html/body/main/div/div/div/div/div/searchcompare/div[3]/div/div[3]/div[1]/ul/li[2]/div[2]/div/div[1]/div[1]/div[2]/div[2]/div/fieldset/div/button