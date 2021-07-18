package ApplicationScreens;

import Utils.ConfigReader;
import Utils.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;



public class BaseScreen {

    Properties property = new Properties();
    WebDriver driver = DriverFactory.getDriver();
    ConfigReader configreader = new ConfigReader();
    ExtentReports report = new ExtentReports();


    public void launchUrl() {

        property = configreader.init_prop();
        String url = property.getProperty("URL");
        System.out.println("fetched url from properties file: " + url);
        driver.get(url);


    }

    public WebElement waitForElementToPresent(String locatorTypeAnValue) {
        WebElement webElement = null;

        FluentWait<WebDriver> fluentWait;
       /* String[] splited = locatorTypeAnValue.split("_");
        String locatorType = splited[0];
        String locatorValue = splited[1];*/

        String locatorType = "";
        String locatorValue = "";
        fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(3000))
                .ignoring(NoSuchElementException.class);
        if (locatorType.equalsIgnoreCase("id"))
            webElement = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
        else if (locatorType.equalsIgnoreCase("xpath"))
            webElement = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
        else if (locatorType.equalsIgnoreCase("LinkText"))
            webElement = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
        if (webElement != null) {
            System.out.println("Element found");

            return webElement;
        } else {
            System.out.println("Element not found.Please provide correct value ");

        }
        return webElement;
    }


    public void clickOnlement(String locatorType, String locatorvalue) {
        WebElement performClick = null;
        performClick = elementBy(locatorType, locatorvalue);

        performClick.click();
    }

    public void enterValueinTextFied(String locatorType, String locatorvalue, String valuetobeenter) {
        WebElement enterValue = null;
        enterValue = elementBy(locatorType, locatorvalue);

        enterValue.sendKeys(valuetobeenter);
    }

    public WebElement elementBy(String locatorType, String locatorvalue) {
        WebElement el = null;

        try {
            if (locatorType.equalsIgnoreCase("linkText")) {
                el = driver.findElement(By.linkText(locatorvalue));
            } else if (locatorType.equalsIgnoreCase("id")) {

                el = driver.findElement(By.id(locatorvalue));
            } else if (locatorType.equalsIgnoreCase("xpath")) {

                el = driver.findElement(By.xpath(locatorvalue));
            }
        } catch (NoSuchElementException e) {
            Assert.fail("No such element found" + e.getMessage());


        }
        return el;
    }


    public Map<String, String> getTestDataMap(DataTable dataTable) {

        Map<String, String> testdataMap = new HashMap<>();
        try {
            List<Map<String, String>> listValues = dataTable.asMaps(String.class, String.class);
            if (listValues.size() > 0) {
                testdataMap = listValues.get(0);
            } else {
                Assert.fail("Data table is not provided in test step of scenario");
            }

        } catch (Exception e) {
            Assert.fail("Data table is not provided correctly, please check the exception: " + e.getMessage());
        }
        return testdataMap;
    }

    public String getTextFromField(String locatorType, String locatorvalue) {
        String value = null;
        try {
            WebElement el = null;
            el = elementBy(locatorType, locatorvalue);

            value = el.getText();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found" + e.getMessage());
        }
        return value;

    }

    public void takeScreenShot(String fileWithPath) throws IOException {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            byte[] SrcFile = scrShot.getScreenshotAs(OutputType.BYTES);

        } catch (Exception e) {
            Assert.fail("Unable to take screenshot");
        }

    }

}
