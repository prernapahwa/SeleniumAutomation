import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
    private ExtentSparkReporter sparkReporter;

    @BeforeClass
    public void setup() {
        // Initialize ExtentReports
        sparkReporter = new ExtentSparkReporter(new File("/Users/prerna/Desktop/SeleniumAutomation/extentReport.html"));
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Initialize SafariDriver
        driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(dependsOnMethods = {"magentoLoginTest"})
    public void magentoCreateAccountTest() {
        test = extent.createTest("magentoCreateAccountTest", "Test for creating account in Magento");
        try {
            driver.get("https://magento.softwaretestingboard.com/");
            test.info("Opened Magento homepage.");

            WebElement createAccount = driver.findElement(By.linkText("Create an Account"));
            createAccount.click();
            test.info("Clicked on Create an Account button");

            test.info("fill firstname");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname"))).sendKeys("Prerna");

            test.info("fill lastName");
            driver.findElement(By.id("lastname")).sendKeys("Pahwa");

            test.info("fill email");
            driver.findElement(By.name("email")).sendKeys("preeernapahwa64@gmail.com");

            test.info("fill email");
            driver.findElement(By.id("password")).sendKeys("1234Prerna");
            driver.findElement(By.id("password-confirmation")).sendKeys("1234Prerna");

            test.info("submit form");
            driver.findElement(By.xpath("//button[@title='Create an Account']")).click();

            WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("logged-in")));
            if (userName.getText().contains("Prerna")) {
                test.pass("Account created successfully and user is logged in");
            } else {
                test.fail("Account creation failed");
            }
        } catch (Exception e) {
            test.fail("Magento Test Failed due to: " + e.getMessage());
        }
    }

    @Test
    public void magentoLoginTest() {
        test = extent.createTest("magentoLoginTest", "Test for logging into Magento");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            driver.get("https://magento.softwaretestingboard.com/");
            test.info("Opened Magento homepage.");

            driver.findElement(By.linkText("Sign In")).click();
            test.info("Clicked on Sign In button");

            test.info("fill email");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            driver.findElement(By.id("email")).click();
            driver.findElement(By.id("email")).sendKeys("prrrernapahwa64@gmail.com");

            test.info("fill password");
            driver.findElement(By.id("pass")).click();
            driver.findElement(By.id("pass")).sendKeys("1234Prerna");

            test.info("click signin");
            driver.findElement(By.xpath("//button[@class='action login primary']")).click();

            WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("logged-in")));
            if (userName.getText().contains("Prerna")) {
                test.pass("User successfully logged in");
            } else {
                test.fail("Login failed");
            }
        } catch (Exception e) {
            test.fail("Magento Test Failed due to: " + e.getMessage());
        }
    }

    @AfterMethod
    public void clearCache() {
        driver.manage().deleteAllCookies();
        test.info("Browser cache and cookies cleared after test");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
