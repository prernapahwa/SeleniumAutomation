import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeleniumTest {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
    private ExtentSparkReporter sparkReporter;

    @BeforeTest
    public void setup() {
        sparkReporter = new ExtentSparkReporter("extentReport.html");
        sparkReporter.config().setDocumentTitle("Extent Report");
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        test = extent.createTest("Amazon Search Test", "Test for searching LG soundbar on Amazon");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);

        // Open the test page
        driver.get("https://www.amazon.in");
        test.info("Opened Amazon homepage");
    }

    @Test
    public void testAmazonSearch() {
        test.info("Searching for 'lg soundbar'");

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("lg soundbar");
        searchBox.submit();
        test.info("Submitted search for 'lg soundbar'");

        try {
            Thread.sleep(2000); // Simple wait; replace with WebDriverWait if needed
        } catch (InterruptedException e) {
            test.fail("Interrupted Exception during wait");
        }

        // Extract product names and prices
        List<WebElement> products = driver.findElements(By.cssSelector(".s-main-slot .s-result-item"));
        Map<String, Integer> productPrices = new HashMap<>();

        for (WebElement product : products) {
            try {
                String name = product.findElement(By.cssSelector("h2")).getText();
                String priceString = product.findElement(By.cssSelector("a-price-whole")).getText().replace(",", "");
                int price = Integer.parseInt(priceString);
                productPrices.put(name, price);
                test.info("Found product: " + name + " with price: " + price);
            } catch (Exception e) {
                try {
                    String name = product.findElement(By.cssSelector("h2")).getText();
                    productPrices.put(name, 0); // Price not found, set to 0
                    test.info("Found product: " + name + " but price not available");
                } catch (Exception ex) {
                    test.fail("Error while extracting product details");
                }
            }
        }
        List<Map.Entry<String, Integer>> sortedProducts = productPrices.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> entry : sortedProducts) {
            String result = entry.getValue() + " " + entry.getKey();
            System.out.println(result);
            test.info("Product: " + result);
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            test.info("Browser closed");
        }
        extent.flush();
    }

    public static void main(String[] args) {
        SeleniumTest test = new SeleniumTest();
        test.setup();
        test.testAmazonSearch();
        test.tearDown();
    }
}
