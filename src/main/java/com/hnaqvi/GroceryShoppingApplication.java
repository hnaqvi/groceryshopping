package com.hnaqvi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hnaqvi.scrapers.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;


@SpringBootApplication
public class GroceryShoppingApplication implements CommandLineRunner {

    @Autowired
    Scraper scraper;

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(GroceryShoppingApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }


    @Override
    public void run(String... args) throws Exception {

        Gson g = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        String result = g.toJson(scraper.scrape(args[0]));

        Stream.of(
                "===================================Start Of Result====================================",
                result,
                "====================================End Of Result====================================")
                .forEach(s -> System.out.println(s));
    }
}