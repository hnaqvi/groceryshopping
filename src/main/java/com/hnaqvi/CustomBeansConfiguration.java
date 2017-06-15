package com.hnaqvi;



import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeansConfiguration {

    private static String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";
    private static String DEFAULT_PHANTOMJS_PATH =  "/usr/local/bin/phantomjs";
    private Logger LOG = LoggerFactory.getLogger(CustomBeansConfiguration.class);

    @Bean
    public PhantomJSDriver PhantomJSDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        String phantomBinaryPath = DEFAULT_PHANTOMJS_PATH;

        if(System.getenv().containsKey(PHANTOMJS_BINARY_PATH)){
            phantomBinaryPath = System.getenv(PHANTOMJS_BINARY_PATH);
        } else {
            LOG.info("No environment variable 'phantomjs.binary.path' found on the system. using default binary path for phantomjs");
        }

        LOG.info("Using phantom binary:" + phantomBinaryPath);
        capabilities.setCapability(PHANTOMJS_BINARY_PATH , phantomBinaryPath);

       return new PhantomJSDriver(capabilities);
    }

}
