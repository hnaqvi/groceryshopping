package com.hnaqvi.scrapers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
public class WebdriverWaitService {

    public WebDriverWait webDriverWait(WebDriver webDriver, int timeOutInSec){
        return new WebDriverWait(webDriver, timeOutInSec);
    }
}
