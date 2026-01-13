package com.automation.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import com.automation.utils.ConfigReader;

public class BaseTest {

    public static AppiumDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    /* ================= REPORT SETUP ================= */

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("extent-report.html");
        spark.config().setReportName("Appium Login Automation Report");
        spark.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", "iOS");
        extent.setSystemInfo("Tester", "Antigravity");
    }

    /* ================= DRIVER SETUP ================= */

    @BeforeMethod
    public void setup(java.lang.reflect.Method method) throws MalformedURLException {

        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(ConfigReader.getProperty("deviceName"));
        options.setPlatformName(ConfigReader.getProperty("platformName"));
        options.setPlatformVersion(ConfigReader.getProperty("platformVersion"));
        options.setAutomationName(ConfigReader.getProperty("automationName"));
        options.setUdid(ConfigReader.getProperty("udid"));
        options.setBundleId(ConfigReader.getProperty("bundleId"));
        
        // iOS-specific capabilities
        options.setCapability("appium:webDriverAgentUrl", ConfigReader.getProperty("webDriverAgentUrl"));
        
        boolean noReset = Boolean.parseBoolean(ConfigReader.getProperty("noReset"));
        options.setNoReset(noReset);
        
        boolean usePrebuiltWDA = Boolean.parseBoolean(ConfigReader.getProperty("usePrebuiltWDA"));
        options.setCapability("appium:usePrebuiltWDA", usePrebuiltWDA);
        
        boolean useNewWDA = Boolean.parseBoolean(ConfigReader.getProperty("useNewWDA"));
        options.setCapability("appium:useNewWDA", useNewWDA);

        int newCommandTimeout = Integer.parseInt(ConfigReader.getProperty("newCommandTimeout"));
        options.setNewCommandTimeout(Duration.ofSeconds(newCommandTimeout));

        String appiumUrl = ConfigReader.getProperty("appiumUrl");
        driver = new IOSDriver(new URL(appiumUrl), options);

        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicitWait"));
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWait));

        // CONDITIONAL RESET: Skip resetAppToHomePage for tests with custom navigation
        // EditProfileTest: manages Sign In -> Home -> Edit Profile flow
        // SignInTest: needs to start from Sign In page (logged out state)
        // SignUpTest: needs to start from Sign Up page
        String testClassName = method.getDeclaringClass().getSimpleName();
        if (!"EditProfileTest".equals(testClassName) &&
                !"SignInTest".equals(testClassName) &&
                !"SignInTest".equals(testClassName)) {
            // RESET APP STATE: Navigate to Home page before each test
            // This ensures test independence without requiring re-login
            resetAppToHomePage();
        } else {
            System.out.println("⏭ Skipping resetAppToHomePage for " + testClassName + " - uses custom navigation");
        }
    }

    /**
     * Reset app state by restarting the app
     * This ensures each test starts from a fresh state at the Main Activity
     */
    private void resetAppToHomePage() {
        try {
            String bundleId = ConfigReader.getProperty("bundleId");
            // Terminate and Activate ensures a fresh start of the main activity
            ((IOSDriver) driver).terminateApp(bundleId);
            Thread.sleep(500);
            ((IOSDriver) driver).activateApp(bundleId);

            // Wait for app to load
            Thread.sleep(3000);

            System.out.println("✓ App state reset: App restarted successfully");
        } catch (Exception e) {
            System.out.println("⚠ Warning: App state reset failed: " + e.getMessage());
        }
    }

    /* ================= CLEAN TEARDOWN ================= */

    @AfterMethod
    public void tearDown(ITestResult result) {

        // Screenshot + FAIL logging ONLY on real failure
        if (test != null && result.getStatus() == ITestResult.FAILURE) {

            test.log(Status.FAIL, "Test Failed");
            test.log(Status.FAIL, result.getThrowable());

            try {
                String screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BASE64);
                test.addScreenCaptureFromBase64String(screenshot);
            } catch (Exception ignored) {
            }
        }

        // App + Driver cleanup
        if (driver != null) {
            try {
                ((IOSDriver) driver)
                        .terminateApp(ConfigReader.getProperty("bundleId"));
            } catch (Exception ignored) {
            }
            driver.quit();
        }

        // User requested 2-second delay after every test
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* ================= REPORT FLUSH ================= */

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
