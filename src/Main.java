import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDocuments\\BrowserDriver\\chromedriver.exe");

        WebDriver dr = new ChromeDriver();

        dr.get("https://www.cars.com/");

        dr.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        dr.manage().window().maximize();


        Select sel1 = new Select(dr.findElement(By.cssSelector("#make-model-search-stocktype")));

        Assert.assertEquals(sel1.getFirstSelectedOption().getText(), "New & used cars");

        Assert.assertEquals(new Select(dr.findElement(By.cssSelector("#makes"))).getFirstSelectedOption().getText(), "All makes");

        Assert.assertEquals(new Select(dr.findElement(By.cssSelector("#models"))).getFirstSelectedOption().getText(), "All models");

        Assert.assertEquals(new Select(dr.findElement(By.cssSelector("#make-model-max-price"))).getFirstSelectedOption().getText(), "No max price");

        Assert.assertEquals(new Select(dr.findElement(By.cssSelector("#make-model-maximum-distance"))).getFirstSelectedOption().getText(),
                "20 miles");


        sel1.selectByValue("used");
        new Select(dr.findElement(By.cssSelector("#makes"))).selectByValue("tesla");

        List<WebElement> TeslaModels = dr.findElements(By.cssSelector("#models"));
        for (WebElement teslaModel : TeslaModels) {
            Assert.assertTrue((teslaModel.getText().contains("Model 3")) ||
                    (teslaModel.getText().contains("Model S")) ||
                    (teslaModel.getText().contains("Model X")) ||
                    (teslaModel.getText().contains("Model Y")) ||
                    (teslaModel.getText().contains("Roadster")));
        }

        new Select(dr.findElement(By.cssSelector("#models"))).selectByValue("tesla-model_3");
        new Select(dr.findElement(By.cssSelector("#make-model-max-price"))).selectByValue("100000");
        new Select(dr.findElement(By.cssSelector("#make-model-maximum-distance"))).selectByValue("50");
        dr.findElement(By.cssSelector("#make-model-zip")).sendKeys("22182" + Keys.TAB + Keys.ENTER);

        Thread.sleep(500);
        List<WebElement> titles = dr.findElements(By.xpath("//h2[@class = 'title']"));


//        Assert.assertEquals(titles.size()+ad.size(),20);

        Thread.sleep(500);
        for (WebElement el : titles) {
            Assert.assertTrue(el.getText().contains("Tesla Model 3"));
        }

        new Select(dr.findElement(By.id("sort-dropdown"))).selectByVisibleText("Lowest price");

        List<WebElement> prices = dr.findElements(By.xpath("//span[@class = 'primary-price']"));

        List<String> priceSort = new ArrayList<>();

        for (WebElement price : prices) {
            priceSort.add(price.getText().substring(1, 7).replace(",", ""));
        }

        for (int i = 0; i < priceSort.size() - 1; i++) {
            System.out.println(Integer.parseInt(priceSort.get(i)));
            Assert.assertTrue((Integer.parseInt(priceSort.get(i + 1)) - Integer.parseInt(priceSort.get(i))) > 0);

        }


        new Select(dr.findElement(By.id("sort-dropdown"))).selectByValue("mileage_desc");


    }
}



