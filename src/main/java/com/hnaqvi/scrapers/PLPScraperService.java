package com.hnaqvi.scrapers;


import com.hnaqvi.model.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class PLPScraperService {

    @Autowired
    PhantomJSDriver driver;

    @Autowired
    WebdriverWaitService webdriverWaitService;

    protected static String xpathProductList = "//*[@id=\"productLister\"]/ul";
    protected static String xpathProductListElement = "//*[@id=\"productLister\"]/ul/li";
    protected static String xpathProductListProductInfo = "div[@class=\"product \"]/div/div/div/h3/a";
    protected static String xpathProductListProductInfoUnitPrice = "div[@class=\"product \"]/div/div[2]/div/div/div/div/p[@class=\"pricePerUnit\"]";


    public List<Result> scrape(String url) throws Exception {

        driver.get(url);

        webdriverWaitService.webDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathProductList)));

        List<WebElement> elements = driver.findElements(By.xpath(xpathProductListElement));

        return elements.stream()
                .map(geElementToResultFunction())
                .collect(Collectors.toList());
    }

    private String getTitle(WebElement e) {
        return e.findElement(By.xpath(xpathProductListProductInfo)).getText();
    }

    private Function<WebElement, Result> geElementToResultFunction() {
        return p -> {
            Result r = new Result();
            r.setTitle(getTitle(p));
            r.setUnit_price(getUnitPrice(p));
            r.setUrl(getUrl(p));
            return r;
        };
    }

    private String stripCurrencyAndSlashPostfix(String string) {
        return string.split("/")[0].substring(1);
    }

    private BigDecimal getUnitPrice(WebElement e) {
        return new BigDecimal(stripCurrencyAndSlashPostfix(e.findElement(By.xpath(xpathProductListProductInfoUnitPrice)).getText())).setScale(2);
    }


    private String getUrl(WebElement e) {
        return e.findElement(By.xpath(xpathProductListProductInfo)).getAttribute("href");
    }


}
