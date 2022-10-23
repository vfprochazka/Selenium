package selenium.pageobject.driver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.pageobject.properties.TestProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Driver {
    private static final Logger logger = LoggerFactory.getLogger(Driver.class);
    private RemoteWebDriver driver;
    private final TestProperties testProperties;

    public Driver(TestProperties testProperties) {
        this.testProperties = testProperties;
        this.initDriver();
    }

    public Driver() {
        this(new TestProperties());
    }

    private void initDriver() {
        TestProperties.Browser browser = this.testProperties.getBrowser();
        TestProperties.Execution execution = this.testProperties.getRun();
        switch (execution) {
            case LOCAL:
                switch (browser) {
                    case CHROME:
                        this.driver = new ChromeDriver((ChromeOptions) this.testProperties.getCapabilities());
                        break;
                    case IE:
                        this.driver = new InternetExplorerDriver((InternetExplorerOptions) this.testProperties.getCapabilities());
                        break;
                    case FIREFOX:
                        this.driver = new FirefoxDriver((FirefoxOptions)this.testProperties.getCapabilities());
                        break;
                    default:
                        throw new RuntimeException("Browser of type '" + browser + "' is not supported.");
                }
                break;
            case REMOTE:
                this.driver = new RemoteWebDriver(this.testProperties.getGridUrl(), this.testProperties.getCapabilities());
                break;
            default:
                throw new RuntimeException("Run of type '" + execution + "' is not supported.");
        }
        this.driver.manage().window().maximize();
        this.driver.get(this.testProperties.getApplicationUrl());

        logger.info("Driver started");
    }

    public void synchronization() {
        logger.info("Driver.synchronization");
        this.waitForPageToLoad();
        this.waitForJQueriesDone();
    }

    public WebElement waitForElement(WebElement element) {
        logger.info("Driver.waitForElement");
        WebDriverWait wait = new WebDriverWait(this.driver, 20);
        ExpectedCondition<Boolean> expectedCondition = (d) -> {
            try {
                return element.isDisplayed();
            } catch (WebDriverException e) {
                return false;
            }
        };
        wait.until(expectedCondition);
        return element;
    }

    public WebElement waitForElement(String xPath) {
        logger.info(
                getClass().getName() + "." +
                        Thread.currentThread().getStackTrace()[1].getMethodName());

        WebDriverWait wait = new WebDriverWait(this.driver, 20);
        WebElement foundElement = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(By.xpath(xPath)));
        if (!(foundElement == null)) {
            return foundElement;
        } else {
            return null;
        }
    }


    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(this.driver, 20);
        ExpectedCondition<Boolean> expectedCondition = (d) -> this.driver.executeScript("return document.readyState;").equals("complete");
        wait.until(expectedCondition);
    }

    public void waitForJQueriesDone() {
        WebDriverWait wait = new WebDriverWait(this.driver, 20);
        ExpectedCondition<Boolean> expectedCondition = (d) -> {
            if ((Boolean) this.driver.executeScript("return window.jQuery === undefined;")) {
                return true;
            }
            try {
                return (Boolean) this.driver.executeScript("return jQuery.active === 0;");
            } catch (JavascriptException e) {
                return false;
            }
        };
        wait.until(expectedCondition);
    }

    public void takeScreenshot() {
        this.takeScreenshot(this.driver.findElement(By.xpath("//body")));
    }

    public void takeScreenshot(WebElement element) {
        if (driver == null || element == null) {
            return;
        }
        File screenshot = element.getScreenshotAs(OutputType.FILE);
        String fileName = "reports/screenshots/" + screenshot.getName();
        try {
            Files.copy(screenshot.toPath(), Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Screenshot saved to: " + fileName);
    }

    public RemoteWebDriver getDriver() {
        return this.driver;
    }

    public void reset() {
        logger.info("Driver.reset");
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
