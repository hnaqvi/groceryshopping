package com.hnaqvi.scrapers;

import com.hnaqvi.model.Result;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PDPScraperService {

    @Autowired
    PhantomJSDriver driver;

    @Autowired
    WebdriverWaitService webdriverWaitService;

    private static String descriptionSelector = "//*[@id=\"information\"]/productcontent/htmlcontent/div[1]/p[1]";

    Logger LOG = LoggerFactory.getLogger(PDPScraperService.class);

    public Result scrape(String url, Result result) throws Exception {

        LOG.info("Scraping: "+url);

        try {
            driver.get(url);
            webdriverWaitService.webDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(descriptionSelector)));
            WebElement element = driver.findElement(By.xpath(descriptionSelector));
            result.setDescription(element.getText());
            result.setSize(getSize(driver.getPageSource()));
            return result;
        } catch (Exception e){
            LOG.error(e.getMessage());
            return result;
        } finally {
            return result;
        }
    }

    private static String getSize(String s) throws Exception{
        return FileUtils.byteCountToDisplaySize(s.getBytes("UTF-8").length);
    }
}
