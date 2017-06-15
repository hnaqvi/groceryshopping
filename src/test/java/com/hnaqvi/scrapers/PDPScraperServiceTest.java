package com.hnaqvi.scrapers;


import com.hnaqvi.model.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PDPScraperServiceTest {


    @InjectMocks
    PDPScraperService testObject;

    @Mock
    PhantomJSDriver phantomJSDriverMock;

    @Mock
    Result resultMock;

    @Mock
    WebElement productDescriptinElementMock;

    @Mock
    WebDriverWait webDriverWaitMock;

    @Mock
    WebdriverWaitService webdriverWaitServiceMock;

    @Before
    public void setup(){
        when(productDescriptinElementMock.getText()).thenReturn("A Product Description");
        when(phantomJSDriverMock.getPageSource()).thenReturn("<html></html>");
        when(webdriverWaitServiceMock.webDriverWait(any(WebDriver.class), any(Integer.class))).thenReturn(webDriverWaitMock);
    }

    @Test
    public void descriptionShouldBeNullBecauseOfTimeout() throws Exception {
        when(phantomJSDriverMock.findElement(any())).thenReturn(productDescriptinElementMock);
        when(webDriverWaitMock.until(any(ExpectedCondition.class))).thenThrow(new TimeoutException());
        Result result = testObject.scrape("some_url", resultMock);
        verify(phantomJSDriverMock).get("some_url");

        assertEquals(null, result.getDescription());
    }

    @Test
    public void testResultData() throws Exception {
        when(phantomJSDriverMock.findElement(any(By.class))).thenReturn(productDescriptinElementMock);

        when(webDriverWaitMock.until(any(ExpectedCondition.class))).thenReturn(Object.class);

        Result result = testObject.scrape("some_url", new Result());

        verify(phantomJSDriverMock).get("some_url");

        assertEquals("A Product Description", result.getDescription());
        assertEquals("13 bytes", result.getSize());

    }

}
