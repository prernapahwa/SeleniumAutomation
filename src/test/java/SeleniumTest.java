import com.aventstack.extentreports.ExtentReports;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import testbase.BaseTest;

public class SeleniumTest extends BaseTest {

    private static ExtentReports extent;
    @Test
    public void testFormSubmission() {
        try {
            step("Fill name");
            WebElement name = driver.findElement(By.id("userName"));
            name.click();
            name.sendKeys("Prerna");

            step("Fill email");
            WebElement email = driver.findElement(By.id("userEmail"));
            email.click();
            email.sendKeys("prernapahwa64@gmail.com");

            step("Fill current address");
            WebElement currentAddress = driver.findElement(By.id("currentAddress"));
            currentAddress.sendKeys("Rajpura, Punjab");

            step("Fill permanent address");
            WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
            permanentAddress.sendKeys("Rajpura, Punjab");

            step("Click submit button");
            WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
            submit.click();

            step("Checking output is displayed");
            boolean output = driver.findElement(By.id("output")).isDisplayed();
            Assert.assertTrue(output);

            step("Form submission test completed successfully");

        } catch (Exception e) {
            if (test != null) {
                test.fail("Form submission test failed with exception: " + e.getMessage());
            }
            throw e;
        }
    }
}
