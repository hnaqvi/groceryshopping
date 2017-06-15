package com.hnaqvi.scrapers;


import com.google.common.base.Preconditions;
import com.hnaqvi.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class Scraper {

    @Autowired
    PLPScraperService plPScraperService;

    @Autowired
    PDPScraperService pdpScraperService;

    public Results scrape(String url) throws Exception {

        Preconditions.checkArgument(url != null);

        Results results = new Results();

        plPScraperService.scrape(url).forEach(result -> results.getResults().add(result));

        results.getResults().stream().forEach(result -> {
            try {
                pdpScraperService.scrape(result.getUrl(), result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        BigDecimal total = results.getResults().
                stream().
                map(result -> result.getUnit_price())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).setScale(2);

        results.setTotal(total);

        return results;
    }



}
