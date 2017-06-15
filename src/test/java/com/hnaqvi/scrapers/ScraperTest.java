package com.hnaqvi.scrapers;


import com.hnaqvi.model.Result;
import com.hnaqvi.model.Results;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScraperTest {

    private static final String RESULT_MOCK_1_URL = "resultMock1Url";
    private static final String RESULT_MOCK_2_URL = "resultMock2Url";

    @InjectMocks
    Scraper testObject = new Scraper();

    @Mock
    PLPScraperService plpScraperServiceMock;

    @Mock
    PDPScraperService pdpScraperServiceMock;

    @Mock
    Result resultMock1, resultMock2;

    @Before
    public void setup() throws Exception{
        when(resultMock1.getUrl()).thenReturn(RESULT_MOCK_1_URL);
        when(resultMock2.getUrl()).thenReturn(RESULT_MOCK_2_URL);
        when(resultMock1.getUnit_price()).thenReturn(new BigDecimal("22.35"));
        when(resultMock2.getUnit_price()).thenReturn(new BigDecimal("40.3"));
        when(pdpScraperServiceMock.scrape(RESULT_MOCK_1_URL, resultMock1)).thenReturn(resultMock1);
        when(pdpScraperServiceMock.scrape(RESULT_MOCK_2_URL, resultMock2)).thenReturn(resultMock2);
    }


    @Test
    public void testNoResultFromPLPShouldNotInvokePDPScraperService() throws Exception {

        Results results  = testObject.scrape("some_url");

        when(plpScraperServiceMock.scrape(any(String.class))).thenReturn(Collections.emptyList());

        verify(pdpScraperServiceMock, never()).scrape(any(String.class), any(Result.class));

        assertEquals(0, results.getResults().size());

    }

    @Test
    public void testResultFromPLPShouldInvokePDPScraperService() throws Exception {

        when(plpScraperServiceMock.scrape("some_url")).thenReturn(Arrays.asList(resultMock1, resultMock2));

        Results results  = testObject.scrape("some_url");

        verify(pdpScraperServiceMock).scrape(RESULT_MOCK_1_URL, resultMock1);
        verify(pdpScraperServiceMock).scrape(RESULT_MOCK_2_URL, resultMock2);

        assertEquals(2, results.getResults().size());

    }

    @Test
    public void testTotal() throws Exception {

        when(plpScraperServiceMock.scrape("some_url")).thenReturn(Arrays.asList(resultMock1, resultMock2));

        Results results  = testObject.scrape("some_url");

        verify(pdpScraperServiceMock).scrape(RESULT_MOCK_1_URL, resultMock1);
        verify(pdpScraperServiceMock).scrape(RESULT_MOCK_2_URL, resultMock2);

        assertEquals(0, new BigDecimal("62.65").compareTo(results.getTotal()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoArgumentThrowsException() throws Exception{
        testObject.scrape(null);
    }

}
