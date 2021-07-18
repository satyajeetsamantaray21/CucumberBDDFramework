package ApplicationScreens;

import Utils.ConfigReader;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import Utils.DriverFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ShopScreen {

    WebDriver driver = DriverFactory.getDriver();
    Properties prop = new Properties();
    ConfigReader reader = new ConfigReader();
    BaseScreen baseScreen = new BaseScreen();
    public static final String funnyCowXpath = "//*[@class='products ng-scope']/ul/li[6]/div/p/a";
    public static final String FluffyBunnyXpath = "//*[@class='products ng-scope']/ul/li[4]/div/p/a";
    public static final String cartXpath = "//*[@id='nav-cart']/a";
    public static final String cartTableXpath = "//table[@class='table table-striped cart-items']";


    public void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        System.out.println("Actual titile==" + driver.getTitle());
        if (expectedTitle.equalsIgnoreCase(actualTitle)) {
            System.out.println("Titles match");

        }
    }


    public void clickOnitem(String itemName) {
        if (itemName.equalsIgnoreCase("Funny Cow"))
            baseScreen.clickOnlement("xpath", funnyCowXpath);
        else if (itemName.equalsIgnoreCase("Fluffy Bunny"))
            baseScreen.clickOnlement("xpath", FluffyBunnyXpath);

    }


    public void clickOnCart() {
        baseScreen.clickOnlement("xpath", cartXpath);

    }

    public void verfiyItemInCartDetail(DataTable datatable) {

        List<Map<String, String>> list = datatable.asMaps(String.class, String.class);
        Map<String, String> map = new HashMap<>();
        int listsize = list.size();
        int quantityValidatedCounter = 0;


        System.out.println("List Size: " + listsize);
        if (listsize > 0) {
            for (int count = 0; count < listsize; count++) {
                map = list.get(count);

                String ExpectedItemName = map.get("ItemName");
                System.out.println("Expected Item Name is " + ExpectedItemName);
                String ExpectedQuantityName = map.get("Count");
                System.out.println("Expected Quantity Name is " + ExpectedQuantityName);
                WebElement BooksTable = driver.findElement(By.xpath(cartTableXpath));
                List<WebElement> rowVals = BooksTable.findElements(By.tagName("tr"));
                System.out.println("Total row count is" + rowVals.size());
                List<WebElement> colHeader = rowVals.get(0).findElements(By.tagName("th"));
                System.out.println("Total Column count is" + colHeader.size());
                for (int i = 1; i < rowVals.size() - 2; i++) {
                    List<WebElement> colVals = rowVals.get(i).findElements(By.tagName("td"));
                    colVals.get(0).getText();
                    System.out.println(" Column Text : " + colVals.get(0).getText());
                    if (colVals.get(0).getText().equalsIgnoreCase(ExpectedItemName)) {
                        for (int j = 0; j < colVals.size(); j++) {
                            // Print the coulumn values to console
                            System.out.println(colVals.get(j).getText());
                            if (colVals.get(j).getText().equalsIgnoreCase(ExpectedItemName)) {

                                rowVals.get(i).findElements(By.xpath(".//input"));

                                if (rowVals.get(i).findElements(By.xpath(".//input")).size() > 0) {
                                    String actualQuantity = rowVals.get(i).findElement(By.xpath(".//input"))
                                            .getAttribute("value");
                                    System.out.println("quantity-----" + actualQuantity);
                                    if (ExpectedQuantityName.equalsIgnoreCase(actualQuantity)) {
                                        System.out.println("quantity-matched ----");

                                        quantityValidatedCounter++;
                                        j = colVals.size() + 1;
                                        i = rowVals.size() + 2;
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }
        Assert.assertEquals(quantityValidatedCounter, listsize);
    }


}