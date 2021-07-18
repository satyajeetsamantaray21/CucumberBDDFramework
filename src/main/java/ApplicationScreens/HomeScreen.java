package ApplicationScreens;

import Utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import Utils.DriverFactory;

import java.util.Properties;

public class HomeScreen {

    WebDriver driver = DriverFactory.getDriver();
    Properties prop = new Properties();
    ConfigReader reader = new ConfigReader();
    BaseScreen baseScreen = new BaseScreen();

    public void clickOnContact() {
        baseScreen.clickOnlement( "linkText", "Contact");

    }

    public void clickOnShop() {
        baseScreen.clickOnlement("linkText", "Shop");

    }


}
