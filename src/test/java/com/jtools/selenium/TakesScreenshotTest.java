// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.selenium;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

/**
 * Test screenshot feature.
 *
 * 1. check output for all possible types
 *
 * 2. check screenshot image
 *
 * Logic of screenshot check test is simple:
 * - open page with fixed amount of fixed sized and coloured areas
 * - take screenshot
 * - calculate expected colors as in tested HTML page
 * - scan screenshot for actual colors * compare
 *
 */

// TODO(user): verify expected behaviour after frame switching

// TODO(user): test screenshots at guaranteed maximized browsers
// TODO(user): test screenshots at guaranteed non maximized browsers
// TODO(user): test screenshots at guaranteed minimized browsers
// TODO(user): test screenshots at guaranteed fullscreened/kiosked browsers (WINDOWS platform specific)

public class TakesScreenshotTest {

    private TakesScreenshot screenshooter;
    private ChromeDriver driver;
    protected Wait<WebDriver> wait;
    protected Wait<WebDriver> shortWait;
    private File tempFile = null;
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=foo;bar");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        screenshooter = driver;
        wait = new WebDriverWait(driver, 10);
        shortWait = new WebDriverWait(driver, 5);
    }

    @After
    public void tearDown() {
        if (tempFile != null) {
            tempFile.delete();
            tempFile = null;
        }
    }

    @Test
    public void testGetScreenshotAsFile() {
        driver.get("http://www.baidu.com");
        tempFile = screenshooter.getScreenshotAs(OutputType.FILE);
        assertThat(tempFile.exists()).isTrue();
        assertThat(tempFile.length()).isGreaterThan(0);
    }

    @Test
    public void testGetScreenshotAsBase64() {
        driver.get("http://www.baidu.com");
        String screenshot = screenshooter.getScreenshotAs(OutputType.BASE64);
        assertThat(screenshot.length()).isGreaterThan(0);
    }

    @Test
    public void testGetScreenshotAsBinary() {
        driver.get("http://www.baidu.com");
        byte[] screenshot = screenshooter.getScreenshotAs(OutputType.BYTES);
        assertThat(screenshot.length).isGreaterThan(0);
    }

    @Test
    public void testShouldCaptureScreenshotOfCurrentViewport() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step */ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6);

        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotOfAnElement() throws Exception {
        driver.get("http://www.baidu.com");
        WebElement element = driver.findElement(By.id("cell11"));

        byte[] imageData = element.getScreenshotAs(OutputType.BYTES);
        assertThat(imageData).isNotNull();
        assertThat(imageData.length).isGreaterThan(0);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
        assertThat(image).isNotNull();

        Raster raster = image.getRaster();
        String hex = String.format("#%02x%02x%02x",
                (raster.getSample(1, 1, 0)),
                (raster.getSample(1, 1, 1)),
                (raster.getSample(1, 1, 2)));
        assertThat(hex).isEqualTo("#0f12f7");
    }

    @Test
    public void testShouldCaptureScreenshotOfPageWithLongX() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 50,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6);

        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotOfPageWithLongY() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 50);

        Set<String> expectedColors = generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6);

        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotOfPageWithTooLongX() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 100,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6);

        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotOfPageWithTooLongY() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 100);

        Set<String> expectedColors = generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6);

        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotOfPageWithTooLongXandY() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 100,
                /* stepY in pixels */ 100);

        Set<String> expectedColors = generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6);

        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotAtFramePage() {
        driver.get("http://www.baidu.com");
        wait.until(frameToBeAvailableAndSwitchToIt(By.id("frame1")));
        wait.until(visibilityOfAllElementsLocatedBy(By.id("content")));

        driver.switchTo().defaultContent();
        wait.until(frameToBeAvailableAndSwitchToIt(By.id("frame2")));
        wait.until(visibilityOfAllElementsLocatedBy(By.id("content")));

        driver.switchTo().defaultContent();
        wait.until(titleIs("screen test"));
        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = new HashSet<>();
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0xDFDFDF,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));

        // expectation is that screenshot at page with frames will be taken for full page
        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotAtIFramePage() {
        driver.get("http://www.baidu.com");

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = new HashSet<>();
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0xDFDFDF,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));

        // expectation is that screenshot at page with Iframes will be taken for full page
        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotAtFramePageAfterSwitching() {
        driver.get("http://www.baidu.com");

        driver.switchTo().frame(driver.findElement(By.id("frame2")));

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = new HashSet<>();
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0xDFDFDF,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));

        // expectation is that screenshot at page with frames after switching to a frame
        // will be taken for full page
        compareColors(expectedColors, actualColors);
    }

    @Test
    public void testShouldCaptureScreenshotAtIFramePageAfterSwitching() {
        driver.get("http://www.baidu.com");

        driver.switchTo().frame(driver.findElement(By.id("iframe2")));

        BufferedImage screenshot = getImage();

        Set<String> actualColors = scanActualColors(screenshot,
                /* stepX in pixels */ 5,
                /* stepY in pixels */ 5);

        Set<String> expectedColors = new HashSet<>();
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0x0F0F0F,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));
        expectedColors.addAll(generateExpectedColors( /* initial color */ 0xDFDFDF,
                /* color step*/ 1000,
                /* grid X size */ 6,
                /* grid Y size */ 6));

        // expectation is that screenshot at page with Iframes after switching to a Iframe
        // will be taken for full page
        compareColors(expectedColors, actualColors);
    }

    /**
     * get actual image screenshot
     *
     * @return Image object
     */
    private BufferedImage getImage() {
        BufferedImage image = null;
        try {
            byte[] imageData = screenshooter.getScreenshotAs(OutputType.BYTES);
            assertThat(imageData).isNotNull();
            assertThat(imageData.length).isGreaterThan(0);
            image = ImageIO.read(new ByteArrayInputStream(imageData));
            assertThat(image).isNotNull();
        } catch (IOException e) {
            fail("Image screenshot file is invalid: " + e.getMessage());
        }

        //saveImageToTmpFile(image);
        return image;
    }

    /**
     * generate expected colors as in checked page.
     *
     * @param initialColor - initial color of first (right top) cell of grid
     * @param stepColor    - step b/w grid colors as number
     * @param nX           - grid size at X dimension
     * @param nY           - grid size at Y dimension
     * @return set of colors in string hex presentation
     */
    private Set<String> generateExpectedColors(final int initialColor, final int stepColor,
                                               final int nX, final int nY) {
        Set<String> colors = new TreeSet<>();
        int cnt = 1;
        for (int i = 1; i < nX; i++) {
            for (int j = 1; j < nY; j++) {
                int color = initialColor + (cnt * stepColor);
                String hex =
                        String.format("#%02x%02x%02x", ((color & 0xFF0000) >> 16), ((color & 0x00FF00) >> 8),
                                ((color & 0x0000FF)));
                colors.add(hex);
                cnt++;
            }
        }

        return colors;
    }

    /**
     * Get colors from image from each point at grid defined by stepX/stepY.
     *
     * @param image - image
     * @param stepX - interval in pixels b/w point in X dimension
     * @param stepY - interval in pixels b/w point in Y dimension
     * @return set of colors in string hex presentation
     */
    private Set<String> scanActualColors(BufferedImage image, final int stepX, final int stepY) {
        Set<String> colors = new TreeSet<>();

        try {
            int height = image.getHeight();
            int width = image.getWidth();
            assertThat(width > 0).isTrue();
            assertThat(height > 0).isTrue();

            Raster raster = image.getRaster();
            for (int i = 0; i < width; i = i + stepX) {
                for (int j = 0; j < height; j = j + stepY) {
                    String hex = String.format("#%02x%02x%02x",
                            (raster.getSample(i, j, 0)),
                            (raster.getSample(i, j, 1)),
                            (raster.getSample(i, j, 2)));
                    colors.add(hex);
                }
            }
        } catch (Exception e) {
            fail("Unable to get actual colors from screenshot: " + e.getMessage());
        }

        assertThat(colors).isNotEmpty();

        return colors;
    }

    /**
     * Compares sets of colors are same.
     *
     * @param expectedColors - set of expected colors
     * @param actualColors   - set of actual colors
     */
    private void compareColors(Set<String> expectedColors, Set<String> actualColors) {
        assertThat(onlyBlack(actualColors)).as("Only black").isFalse();
        assertThat(onlyWhite(actualColors)).as("Only white").isFalse();

        // Ignore black and white for further comparison
        Set<String> cleanActualColors = Sets.newHashSet(actualColors);
        cleanActualColors.remove("#000000");
        cleanActualColors.remove("#ffffff");

        if (! expectedColors.containsAll(cleanActualColors)) {
            fail("There are unexpected colors on the screenshot: " +
                    Sets.difference(cleanActualColors, expectedColors));
        }

        if (! cleanActualColors.containsAll(expectedColors)) {
            fail("There are expected colors not present on the screenshot: " +
                    Sets.difference(expectedColors, cleanActualColors));
        }
    }

    private boolean onlyBlack(Set<String> colors) {
        return colors.size() == 1 && "#000000".equals(colors.toArray()[0]);
    }

    private boolean onlyWhite(Set<String> colors) {
        return colors.size() == 1 && "#ffffff".equals(colors.toArray()[0]);
    }

    /**
     * Simple helper to save screenshot to tmp file. For debug purposes.
     *
     * @param im image
     */
    @SuppressWarnings("unused")
    private void saveImageToTmpFile(BufferedImage im) {

        File outputfile = new File( testName.getMethodName() + "_image.png");
        try {
            ImageIO.write(im, "png", outputfile);
        } catch (IOException e) {
            fail("Unable to write image to file: " + e.getMessage());
        }
    }

}