package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.SignInPage;
import com.automation.pages.HomePage;
import com.automation.pages.ProfilePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class SignInTest extends BaseTest {

    /**
     * NAVIGATE TO SIGN IN PAGE BEFORE EACH TEST
     * This ensures every test starts from Sign In page.
     */
    @BeforeMethod
    public void navigateToSignInPageBeforeTest() throws Exception {
        navigateToSignInPage();
    }

    /**
     * NAVIGATE TO SIGN IN PAGE
     * This ensures tests start from Sign In page.
     */
    private void navigateToSignInPage() throws Exception {
        // Driver already initialized by BaseTest.setup()

        SignInPage signInPage = new SignInPage(driver);

        // Check if already on Sign In page
        if (signInPage.isOnSignInPage()) {
            System.out.println("✓ Already on Sign In page");
            return;
        }

        // Check if on Home page (logged in)
        HomePage homePage = new HomePage(driver);
        if (homePage.isHomePageDisplayed()) {
            System.out.println("User is logged in, navigating to Sign In page...");

            // Navigate: Wellbeing Dashboard → Profile → Logout → Yes
            homePage.navigateToLogout();

            ProfilePage profilePage = new ProfilePage(driver);
            profilePage.clickLogout();
            Thread.sleep(1000);

            // Click YES in logout confirmation
            profilePage.clickYes();
            Thread.sleep(2000);

            System.out.println("✓ Navigated to Sign In page");
            return;
        }

        // If on other page, press back until Sign In page appears
        int maxAttempts = 10;
        int attempts = 0;
        while (!signInPage.isOnSignInPage() && attempts < maxAttempts) {
            driver.navigate().back();
            Thread.sleep(500);
            attempts++;
        }

        if (signInPage.isOnSignInPage()) {
            System.out.println("✓ Navigated to Sign In page via back button");
        } else {
            System.out.println("⚠ Warning: Could not navigate to Sign In page");
        }
    }

    /**
     * NEGATIVE TEST DATA (NO HARDCODED EXPECTED MESSAGES)
     * Only test scenario name, email, and password
     */
    @DataProvider(name = "negativeLoginData")
    public Object[][] getNegativeLoginData() {
        return new Object[][] {
                { "Invalid Password", "testuser@example.com", "WrongPass" },
                { "Invalid Email Format", "testuser", "Password@123" },
                { "Non-existent Account", "notfound@example.com", "Password@123" },
                { "Empty Email", "", "Password@123" },
                { "Very Short Password", "testuser@example.com", "123" },
                { "Both Empty", "", "" },
                { "Special Characters Email", "test@#$%@example.com", "Password@123" },
        };
    }

    /**
     * RUNTIME-BASED NEGATIVE TEST (BEHAVIOR-BASED VALIDATION)
     * 
     * ===================================================================
     * iOS-Specific Testing Strategy: Behavior-Based Button State Detection
     * ===================================================================
     * 
     * Challenge:
     * In iOS, the CONTINUE button appears visually disabled (greyed) when fields are empty,
     * but Appium Inspector shows identical attributes (enabled=true, visible=true) regardless
     * of visual state. This is an iOS XCUITest limitation - the "disabled" or "hittable"
     * attribute is not exposed reliably.
     * 
     * Solution: BEHAVIOR-BASED DETECTION
     * Instead of checking unreliable attributes, we observe what actually happens:
     * 1. Attempt to click the button
     * 2. Wait ~1 second for app response
     * 3. If NO change in page state AND NO validation appears → button is logically disabled
     * 4. If validation appears OR navigation occurs → button is logically enabled
     * 
     * Test Flow:
     * 1. Enter invalid credentials (empty email, empty password, etc.)
     * 2. Attempt to click CONTINUE button
     * 3. Check if button is logically disabled (no response)
     *    ✓ PASS: Button disabled = app prevents invalid submission
     * 4. If button responds, check for validation message
     *    ✓ PASS: Validation appears = app catches invalid input
     *    ✗ FAIL: No validation = security issue
     */
    @Test(dataProvider = "negativeLoginData")
    public void testNegativeSignIn(String testScenario, String email, String password)
            throws InterruptedException {

        test = extent.createTest("Negative Test: " + testScenario);
        test.log(Status.INFO, "📝 Test Scenario: " + testScenario);
        test.log(Status.INFO, "Email: '" + email + "' | Password: '" + password + "'");

        SignInPage signInPage = new SignInPage(driver);

        // Step 1: Enter credentials (even if empty or invalid)
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
        test.log(Status.INFO, "✓ Entered credentials");

        // Step 2: Attempt to click CONTINUE button and detect logical disabled state
        test.log(Status.INFO, "🔍 Checking if CONTINUE button is logically disabled...");
        boolean isButtonLogicallyDisabled = signInPage.isContinueButtonLogicallyDisabled();

        if (isButtonLogicallyDisabled) {
            // ✓ PASS: Button is logically disabled (click had no effect)
            test.log(Status.PASS, 
                "✓ CONTINUE button is LOGICALLY DISABLED (click produced no response)");
            test.log(Status.PASS, 
                "✓ Invalid input prevented at UI level - button click was ignored");
            test.log(Status.PASS, 
                "✓ Test PASSED: App prevents invalid form submission via disabled button");
            return; // Early exit - validation confirmed via behavior
        }

        // Step 3: Button is enabled, but check what it did
        test.log(Status.INFO, "ℹ CONTINUE button responded to click (logically enabled)");
        
        // Wait for validation or navigation to appear
        Thread.sleep(1000);
        signInPage.waitForValidationToAppear(2);

        // Step 4: Check if validation message appeared
        boolean validationDetected = signInPage.isAnyValidationVisible();

        if (validationDetected) {
            // ✓ PASS: Validation appeared after clicking enabled button
            String validationMessage = signInPage.getValidationMessage();
            if (validationMessage != null && !validationMessage.trim().isEmpty()) {
                test.log(Status.INFO, 
                    "📋 Validation message: \"" + validationMessage + "\"");
            }

            test.log(Status.PASS, 
                "✓ Runtime validation detected - invalid input caught at server level");
            test.log(Status.PASS, 
                "✓ Test PASSED: App shows validation error for invalid input");
        } else {
            // ✗ FAIL: Button was enabled and clickable but produced no validation
            test.log(Status.FAIL, 
                "✗ Button enabled but NO validation appeared - SECURITY ISSUE");
            test.log(Status.FAIL, 
                "✗ Test FAILED: Invalid credentials accepted without validation");
            Assert.fail(
                "SECURITY ISSUE: CONTINUE button accepted invalid credentials without validation. " +
                "Expected either: (1) Disabled button, or (2) Validation message. Got neither.");
        }
    }

    /**
     * POSITIVE LOGIN TEST (Optional - for comparison)
     * This test expects successful login without validation errors
     */
    @Test
    public void testPositiveSignIn() throws InterruptedException {
        test = extent.createTest("Positive Test: Valid Login");
        test.log(Status.INFO, "Testing valid credentials");

        SignInPage signInPage = new SignInPage(driver);

        // Use valid credentials
        String validEmail = "ramesh@navadhiti.com";
        String validPassword = "Testing@2026";

        signInPage.enterEmail(validEmail);
        signInPage.enterPassword(validPassword);
        test.log(Status.INFO, "Entered valid credentials");

        signInPage.clickContinue();
        test.log(Status.INFO, "Clicked Continue button");

        // Wait for navigation to LINK DEVICES page
        // User requested approx 5 sec wait/check for "LINK DEVICES"
        boolean isLinkDevicesPage = signInPage.isLinkDevicesDisplayed();

        if (isLinkDevicesPage) {
            // PASS: Successfully navigated to Link Devices page
            test.log(Status.PASS, "✓ 'LINK DEVICES' page displayed - Login successful");
            test.log(Status.PASS, "Test PASSED: Valid credentials accepted and navigated to next screen");

            // Click "SKIP FOR NOW"
            signInPage.clickSkipForNow();
            test.log(Status.INFO, "✓ Clicked 'SKIP FOR NOW' - Test completed successfully");

            // Step 1: Verify DAILY PRIORITY page shows
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                WebElement dailyPriority = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//XCUIElementTypeStaticText[@name='DAILY PRIORITY']")));
                test.log(Status.PASS, "✓ DAILY PRIORITY page verified - Home page loaded");
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ DAILY PRIORITY not found");
            }

            // Step 2: Click WELLBEING DASHBOARD
            try {
                WebElement wellbeingDashboard = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name, 'WELLBEING')]")));
                wellbeingDashboard.click();
                test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME");
                Thread.sleep(1500);
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found: " + e.getMessage());
            }

            // Step 3: Click PROFILE
            try {
                WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
                profile.click();
                test.log(Status.INFO, "✓ Clicked PROFILE");
                Thread.sleep(1500);
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ PROFILE button not found: " + e.getMessage());
            }
            Thread.sleep(5000);

            // Step 4: Click LOG OUT
            try {
                WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeStaticText[@name='LOG OUT']")));
                logout.click();
                test.log(Status.INFO, "✓ Clicked LOG OUT");
                Thread.sleep(1500);
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ LOG OUT button not found: " + e.getMessage());
            }

            // Step 5: Click YES
            try {
                WebElement yes = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeStaticText[@name='YES']")));
                yes.click();
                test.log(Status.INFO, "✓ Clicked YES");
                Thread.sleep(1000);
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ YES button not found: " + e.getMessage());
            }

            // Step 6: Wait 3 seconds
            Thread.sleep(3000);
            test.log(Status.INFO, "⏳ Waited 3 seconds for logout");

            // Step 7: Verify SIGN IN page shows after logout
            try {
                WebElement signInPage_verify = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//XCUIElementTypeStaticText[@name='SIGN IN']")));
                test.log(Status.PASS, "✓ SIGN IN page verified - Logout successful");
                test.log(Status.PASS, "✓✓✓ FULL TEST PASSED: Login → Skip → Logout → Sign In Page");
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ SIGN IN page not found after logout: " + e.getMessage());
                test.log(Status.FAIL, "Failed to verify Sign In page after logout");
            }

        } else {
            // FAIL: Did not navigate to Link Devices page
            test.log(Status.FAIL, "✗ Failed to navigate to 'LINK DEVICES' page");

            // Check if validation error is present to give more context
            if (signInPage.isAnyValidationVisible()) {
                String msg = signInPage.getValidationMessage();
                test.log(Status.INFO, "Validation error detected: " + msg);
            }

            Assert.fail("Expected to navigate to 'LINK DEVICES' page after valid login");
        }
    }
}
