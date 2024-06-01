package testbase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected static ExtentTest test; // Changed to protected for subclass access
    protected static ChromeOptions options; // Changed to protected for subclass access
    protected static WebDriver driver; // Changed to protected for subclass access
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;

    @BeforeTest
    public void setup() {
        // Initialize ChromeOptions and WebDriver
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Corrected the syntax
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);

        // Open the test page
        driver.get("https://demoqa.com/text-box");
        initializeReport();
    }

    @AfterTest
    public void tearDown() {
        extent.flush();
        driver.close();
    }

    public void initializeReport() {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/src/test/resources/extentReport.html");

        sparkReporter.config().setDocumentTitle("Extent Report");
        sparkReporter.config().setReportName("Automation Extent Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
//        String methodName = new Exception().getStackTrace()[0].getMethodName();
//        String className = new Exception().getStackTrace()[0].getClassName();

        test = extent.createTest("testFormSubmission", "SeleniumTest");
    }

    public static void step(String stepDescription) {
        if (test != null) {
            test.info(stepDescription);
        }
    }
//    protected void step(String step) {
//        test.step(step);
//    }
}
