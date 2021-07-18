package ApplicationScreens;

import Utils.ConfigReader;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import Utils.DriverFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ContactScreen {

    WebDriver driver = DriverFactory.getDriver();
    Properties prop = new Properties();
    ConfigReader reader = new ConfigReader();
    BaseScreen baseScreen = new BaseScreen();

    public void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        System.out.println("Title of the page is : "+driver.getTitle());
        Assert.assertEquals(actualTitle,expectedTitle);
    }

    public void enterValueinTextfield( String l) {
        baseScreen.clickOnlement("LinkTest", "Contact");

    }

    public void fillFormData( DataTable dt) {
        Map<String, String> testDataMap = baseScreen.getTestDataMap(dt);
        baseScreen.enterValueinTextFied( "id", "forename", testDataMap.get("Forename"));

        baseScreen.enterValueinTextFied( "id", "surname", testDataMap.get("Surname"));
        baseScreen.enterValueinTextFied("id", "email", testDataMap.get("Email"));

        int size = driver.findElements(By.id("email-err")).size();
        System.out.println("size==" + size);
        if (size > 0) {
            String errorMessage = baseScreen.getTextFromField("id", "email-err");
            Assert.assertEquals(errorMessage,"Please enter a valid email","invalid email id provided.Please provide valid email");
        }
        baseScreen.enterValueinTextFied("id", "telephone", testDataMap.get("Telephone"));
        baseScreen.enterValueinTextFied("id", "message", testDataMap.get("Message"));
    }

    public void clickOnSubmit() {
        baseScreen.clickOnlement( "linkText", "Submit");
    }

    public void validateErrorMessage(DataTable dt) {

        List<Map<String, String>> listValues = dt.asMaps(String.class, String.class);
        Map<String, String> mapValues = new HashMap<>();
        int rowsCount = listValues.size();
        String fornameError = null;
        String emailError= null;
        String messageError=null;
        if (rowsCount > 0) {
            for (int i = 0; i < rowsCount; i++) {
                mapValues = listValues.get(i);

                System.out.println("passed value from datatable" + mapValues.get("Field"));
                if (mapValues.get("Field").equalsIgnoreCase("Forename")) {
                    fornameError = baseScreen.getTextFromField("id", "forename-err");
                    Assert.assertEquals(fornameError.trim(),mapValues.get("ErrorMessage").trim(),"Error message is not correct forename");
                } else if (mapValues.get("Field").equalsIgnoreCase("Email")) {
                    emailError = baseScreen.getTextFromField("id", "email-err");
                    Assert.assertEquals(emailError.trim(),mapValues.get("ErrorMessage").trim(),"Error message is not correct email");
                } else if (mapValues.get("Field").equalsIgnoreCase("Message")) {
                    messageError = baseScreen.getTextFromField("id", "message-err");
                    Assert.assertEquals(messageError.trim(),mapValues.get("ErrorMessage").trim(),"Error message is not correct Message ");

                }
            }
        }
    }

    public void validatefeedbackSubmitresponse() {


        String text = baseScreen.getTextFromField("xpath", "//*[@class='alert alert-success']");
        if (text.contains("we appreciate your feedback")) {
            System.out.println("validate feedback Submit response");
        }
    }

    public void validateErrorMessageforInvalidEmailId() {
        int size = driver.findElements(By.id("email-err")).size();
            String errorMessage = baseScreen.getTextFromField("id", "email-err");
            if (errorMessage.equalsIgnoreCase("Please enter a valid email"))
                System.out.println("invalid email id provided.Please provide valid email");


    }
}
