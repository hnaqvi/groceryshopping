package com.hnaqvi.scrapers;


import com.hnaqvi.model.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.hnaqvi.scrapers.PLPScraperService.xpathProductListElement;
import static com.hnaqvi.scrapers.PLPScraperService.xpathProductListProductInfo;
import static com.hnaqvi.scrapers.PLPScraperService.xpathProductListProductInfoUnitPrice;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PLPScraperServiceTest {


    @InjectMocks
    PLPScraperService testObject;

    @Mock
    PhantomJSDriver phantomJSDriverMock;

    @Mock
    WebElement productListElement1Mock, productListElement2Mock, productTitleElementMock, productTitleElement2Mock, productUnitPriceElement1Mock, productUnitPriceElement2Mock;

    @Mock
    WebDriverWait webDriverWaitMock;

    @Mock
    WebdriverWaitService webdriverWaitServiceMock;

    @Before
    public void setup(){
        when(webdriverWaitServiceMock.webDriverWait(any(WebDriver.class), any(Integer.class))).thenReturn(webDriverWaitMock);
        when(webDriverWaitMock.until(any(ExpectedCondition.class))).thenReturn(Object.class);
        when(phantomJSDriverMock.findElements(By.xpath(xpathProductListElement))).thenReturn(Arrays.asList(productListElement1Mock, productListElement2Mock));
        when(productListElement1Mock.findElement(By.xpath(xpathProductListProductInfo))).thenReturn(productTitleElementMock);
        when(productListElement2Mock.findElement(By.xpath(xpathProductListProductInfo))).thenReturn(productTitleElement2Mock);
        when(productListElement1Mock.findElement(By.xpath(xpathProductListProductInfoUnitPrice))).thenReturn(productUnitPriceElement1Mock);
        when(productListElement2Mock.findElement(By.xpath(xpathProductListProductInfoUnitPrice))).thenReturn(productUnitPriceElement2Mock);
        when(productTitleElementMock.getText()).thenReturn("Product 1");
        when(productTitleElementMock.getAttribute("href")).thenReturn("Product 1 Link");
        when(productTitleElement2Mock.getText()).thenReturn("Product 2");
        when(productTitleElement2Mock.getAttribute("href")).thenReturn("Product 2 Link");
        when(productUnitPriceElement1Mock.getText()).thenReturn("€2.00/unit");
        when(productUnitPriceElement2Mock.getText()).thenReturn("€33.10/unit");

    }

    @Test
    public void verifyResults() throws Exception {
        List <Result> results = testObject.scrape("some_url");

        assertEquals(2, results.size());
        assertEquals("Product 1", results.get(0).getTitle());
        assertEquals("Product 1 Link", results.get(0).getUrl());
        assertEquals(0, new BigDecimal("2.00").compareTo(results.get(0).getUnit_price()));
        assertEquals("Product 2", results.get(1).getTitle());
        assertEquals("Product 2 Link", results.get(1).getUrl());
        assertEquals(0, new BigDecimal("33.10").compareTo(results.get(1).getUnit_price()));
    }
}
