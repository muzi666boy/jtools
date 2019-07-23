// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.selenium;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * The SeleniumTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SeleniumTest {

    @Test
    public void testBaidu() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=foo;bar");
//        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        // 设置代理
//        options.addArguments("--proxy-server=127.0.0.1:8080");
        ChromeDriver driver = new ChromeDriver(options);
        try {
            driver.get("http://www.baidu.com");
            Object userAgent = driver.executeScript("return window.navigator.userAgent");
            assertThat(userAgent).isEqualTo("foo;bar");
            new WebDriverWait(driver, 10).until(titleIs("百度一下，你就知道"));
            File tempFile = driver.getScreenshotAs(OutputType.FILE);
            System.out.println(tempFile.getPath());

            if (tempFile != null) {
                tempFile.delete();
                tempFile = null;
            }
            System.out.println(driver.getPageSource());

        } finally {
            driver.quit();
        }
    }

    @Test
    public void testPdd() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        // 设置代理
        options.addArguments("--proxy-server=127.0.0.1:8080");
        ChromeDriver driver = new ChromeDriver(options);
        try {
            driver.get("https://yangkeduo.com/catgoods.html?opt_id=14&opt_type=1");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");

            Thread.sleep(2000);
            List<WebElement> elements = driver.findElementsByClassName("nN9FTMO2");

            elements.forEach((ele) -> {
//                System.out.println(ele.getText());
                Map<String, String> product = Maps.newHashMap();
                product.put("price", ele.findElement(By.className("W2aG482G")).getText());
                product.put("solds", ele.findElement(By.className("_2zosSFdU")).getText());
                product.put("img", ele.findElement(By.cssSelector(".bqyzKuVp img")).getAttribute("src"));
                List<WebElement> eles = ele.findElements(By.className("_1r184qiT"));
                if (eles.size() > 0) {
                    product.put("shopName", eles.get(0).getText());
                }
                product.put("name", ele.findElement(By.className("troiqcp4")).getText());
                eles = ele.findElements(By.className("XOW1V9LU"));
                if (eles.size() > 0) {
                    eles.forEach((ele2)-> {
                        if (product.containsKey("services")) {
                            product.put("services", product.get("services") + ";" + ele2.getText());
                        } else {
                            product.put("services", ele2.getText());
                        }
                    });
                }
                System.out.println(product);
            });
        } finally {
            driver.quit();
        }
    }

}
