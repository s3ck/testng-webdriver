package com.dev9.webtest;

import com.dev9.webtest.annotation.MethodDriver;
import com.dev9.webtest.listeners.SeleniumWebDriver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static util.Util.HTTP_PROTOCOL;
import static util.Util.YAHOO_DOMAIN;

@Test
@Listeners({SeleniumWebDriver.class})
public class TestMethodDriverAnnotationIgnore {

    private final static Logger LOG = LoggerFactory.getLogger(TestMethodDriverAnnotation.class);

    @MethodDriver(excludeMethods = {"assertMethodDriverUninitialized1", "assertMethodDriverUninitialized2"})
    WebDriver methodDriver;

    public void assertMethodDriverUninitialized1() throws InterruptedException {
        Assert.assertTrue(methodDriver == null, "WebDriver != null");
    }

    @Test(dependsOnMethods = {"assertMethodDriverUninitialized1"})
    public void navigateMethodDriverToSearch() {
        try {
            methodDriver.get(HTTP_PROTOCOL + YAHOO_DOMAIN);
            String url = methodDriver.getCurrentUrl();
            Assert.assertTrue(url.endsWith(YAHOO_DOMAIN), "False: " + url + " endsWith " + YAHOO_DOMAIN);

        } catch (NullPointerException e) {
            LOG.error("Something broke... check connectivity: " + e );
        }

    }

    @Test(dependsOnMethods = {"navigateMethodDriverToSearch"})
    public void assertMethodDriverUninitialized2() {
        Assert.assertTrue(methodDriver == null, "WebDriver != null");
    }
}
