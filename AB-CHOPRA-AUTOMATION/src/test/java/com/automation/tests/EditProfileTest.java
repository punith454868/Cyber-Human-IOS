package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.MobileBy;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EditProfileTest extends BaseTest {

    /**
     * NEGATIVE TEST DATA FOR EDIT PROFILE
     * Test scenarios for Edit Profile page validation
     */
    @DataProvider(name = "negativeEditProfileData")
    public Object[][] getNegativeEditProfileData() {
        return new Object[][] {
                // Scenario, Name, Email, Phone
                { "Email Without @", "John Doe", "testexample.com", "123456789" },
                { "Email Without Domain", "John Doe", "test@", "123456789" },
                // { "Invalid Phone - Letters", "John Doe", "test@example.com", "abcdefgh" },
                // { "Short Phone Number", "John Doe", "test@example.com", "12" },
                // { "Invalid Email Format", "John Doe", "invalidemail", "123456789" },
                // { "Empty Name Field", "", "test@example.com", "123456789" },
                // { "Empty Email Field", "John Doe", "", "123456789" },
                // { "Empty Phone Number", "John Doe", "test@example.com", "" },

        };
    }

    /**
     * TEST DATA FOR CHANGE PASSWORD
     * Scenarios: Wrong Old Pass, Weak Pass, Same Pass, Valid Change
     */
    @DataProvider(name = "changePasswordData")
    public Object[][] getChangePasswordData() {
        // "loginPassword" is assumed to be "Ramesh@2025" based on
        // navigateToEditProfileFirstTime
        String currentLoginPass = "Testing@2026";

        return new Object[][] {
                // Scenario, Current Pass, New Pass, Confirm Pass, Expected Error XPath (or
                // "SUCCESS")
                { "Wrong Old Password Validation", "Testing@2025", "Testing@2026", "Testing@2026",
                        "//android.view.View[@content-desc='Wrong password. Please enter correct password']" },
                { "Weak Password Validation", currentLoginPass, "test2026", "test2026",
                        "//android.view.View[@content-desc='Use at least 8 characters with uppercase, lowercase, number, and special symbol.']" },
                { "Same Old and New Password Validation", currentLoginPass, currentLoginPass, currentLoginPass,
                        "//android.view.View[@content-desc='Current and new password cannot be the same.']" },
                { "Valid Change Password", currentLoginPass, "Human@2026", "Human@2026", "SUCCESS" }
        };
    }

    // No global navigation flag needed; use test index for navigation control

    /**
     * COMPLETE NAVIGATION FLOW TO EDIT PROFILE PAGE (FIRST TEST ONLY)
     * This method handles the entire navigation from Sign In to Edit Profile
     */
    private void navigateToEditProfileFirstTime() throws InterruptedException {
        // Step 1: Sign In with valid credentials
        SignInPage signInPage = new SignInPage(driver);
        signInPage.enterEmail("ramesh@navadhiti.com");
        signInPage.enterPassword("Testing@2026");
        signInPage.clickContinue();
        test.log(Status.INFO, "Signed in with valid credentials");
        Thread.sleep(2000);

        // Step 1.1: Tap "NO" button if present (by xpath or name)
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        boolean tappedNo = false;
        try {
            WebElement noButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//XCUIElementTypeButton[@name='NO']")));
            noButton.click();
            test.log(Status.INFO, "Tapped 'NO' button by xpath");
            tappedNo = true;
            Thread.sleep(9000);
        } catch (Exception e) {
            test.log(Status.INFO, "'NO' button not found by xpath, trying by name");
            try {
                WebElement noButtonByName = driver.findElement(By.name("NO"));
                noButtonByName.click();
                test.log(Status.INFO, "Tapped 'NO' button by name");
                tappedNo = true;
                Thread.sleep(1000);
            } catch (Exception ex) {
                test.log(Status.INFO, "'NO' button not found by name either, continuing");
            }
        }

        // Step 2: Wait for LINK DEVICES page and click SKIP FOR NOW
        boolean isLinkDevicesPage = signInPage.isLinkDevicesDisplayed();
        if (isLinkDevicesPage) {
            signInPage.clickSkipForNow();
            test.log(Status.INFO, "✓ Clicked SKIP FOR NOW on Link Devices page");
            Thread.sleep(1500);
        } else {
            test.log(Status.WARNING, "⚠ Link Devices page not found, continuing anyway");
        }

        // Step 3: Click WELLBEING DASHBOARD (using working XPath from SignInTest)
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        boolean dashboardClicked = false;
        try {
            WebElement wellbeingDashboard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name, 'WELLBEING')]")));
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by xpath");
            Thread.sleep(1500);
            dashboardClicked = true;
        } catch (Exception e) {
            test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by xpath: " + e.getMessage());
            // Try by accessibility id (Appium)
            try {
                WebElement dashboardByAccId = driver.findElement(MobileBy.AccessibilityId("WELLBEING DASHBOARD HOME"));
                dashboardByAccId.click();
                test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by accessibility id");
                Thread.sleep(1500);
                dashboardClicked = true;
            } catch (Exception ex1) {
                test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by accessibility id: " + ex1.getMessage());
                // Try by name
                try {
                    WebElement dashboardByName = driver.findElement(By.name("WELLBEING DASHBOARD HOME"));
                    dashboardByName.click();
                    test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by name");
                    Thread.sleep(1500);
                    dashboardClicked = true;
                } catch (Exception ex2) {
                    test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by name: " + ex2.getMessage());
                }
            }
        }
        if (!dashboardClicked) {
            throw new RuntimeException("Failed to click Wellbeing Dashboard by any locator");
        }

        // Step 4: Click PROFILE (using working XPath from SignInTest)
        try {
            WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
            profile.click();
            test.log(Status.INFO, "✓ Clicked PROFILE");
            Thread.sleep(2000); // Wait 2 seconds as requested
        } catch (Exception e) {
            test.log(Status.WARNING, "⚠ PROFILE button not found: " + e.getMessage());
            throw new RuntimeException("Failed to click Profile", e);
        }

        // Step 5: Click ACCOUNT to navigate to Edit Profile
        boolean accountClicked = false;
            try {
                WebElement accountAccId = driver.findElement(MobileBy.AccessibilityId("ACCOUNT"));
                accountAccId.click();
                test.log(Status.INFO, "✓ Clicked ACCOUNT by accessibility id");
                Thread.sleep(1500);
                accountClicked = true;
            } catch (Exception e) {
                throw new RuntimeException("Failed to click ACCOUNT by accessibility id", e);
            }

        // Verify Edit Profile page is displayed
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        if (editProfilePage.isEditProfilePageDisplayed()) {
            test.log(Status.INFO, "✓ Edit Profile page displayed successfully");
        }

        // No longer needed: isLoggedIn removed, handled by firstTimeFlow
    }

    /**
     * NAVIGATION FROM HOMEPAGE TO EDIT PROFILE (SUBSEQUENT TESTS)
     * This method starts from Homepage and navigates to Edit Profile
     * Used for all tests after the first one
     */
    private void navigateToEditProfileFromHome() throws InterruptedException {
        // Start directly from Home page (user already logged in)
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));

        // Step 1: Click WELLBEING DASHBOARD with iOS force tap fallback
        WebElement wellbeingDashboard = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | " +
                         "//XCUIElementTypeImage[contains(@name,'WELLBEING')]")
            )
        );
        try {
            wellbeingDashboard.click();   // first attempt
        } catch (Exception e) {
            // force tap fallback (iOS fix)
            int x = wellbeingDashboard.getRect().getX() + wellbeingDashboard.getRect().getWidth() / 2;
            int y = wellbeingDashboard.getRect().getY() + wellbeingDashboard.getRect().getHeight() / 2;

            org.openqa.selenium.interactions.PointerInput finger = new org.openqa.selenium.interactions.PointerInput(org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
            org.openqa.selenium.interactions.Sequence tap = new org.openqa.selenium.interactions.Sequence(finger, 1);

            tap.addAction(finger.createPointerMove(java.time.Duration.ZERO, org.openqa.selenium.interactions.PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(java.util.Collections.singletonList(tap));
        }

        // Step 2: Click PROFILE
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
        profile.click();
        test.log(Status.INFO, "✓ Clicked PROFILE");
        Thread.sleep(2000); // Wait 2 seconds as requested

        // Step 3: Click ACCOUNT to navigate to Edit Profile
        WebElement account = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='ACCOUNT']")));
        account.click();
        test.log(Status.INFO, "✓ Clicked ACCOUNT");
        Thread.sleep(1500);

        // Verify Edit Profile page is displayed
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        if (editProfilePage.isEditProfilePageDisplayed()) {
            test.log(Status.INFO, "✓ Edit Profile page displayed successfully");
        }
    }



    /**
     * Unified navigation method: fullSignIn = true for first test, false for others
     */
    private void navigateToEditProfile(boolean fullSignIn) throws InterruptedException {
        if (fullSignIn) {
            navigateToEditProfileFirstTime();
        } else {
            navigateToEditProfileFromHome();
        }
    }

    /**
     * NAVIGATE TO CHANGE PASSWORD PAGE
     * Ensures we are on the Change Password page, navigating from Edit Profile if
     * needed.
     */
    private void navigateToChangePassword() throws InterruptedException {
        // First check if we are already on Change Password Page
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        if (changePasswordPage.isChangePasswordPageDisplayed()) {
            test.log(Status.INFO, "Already on Change Password Page");
            return;
        }

        // Step 4: Navigate to Homepage and click Wellbeing Dashboard
        HomePage homePage = new HomePage(driver);
        if (homePage.isHomePageDisplayed()) {
            homePage.clickWellbeingDashboard();
            test.log(Status.INFO, "Clicked Wellbeing Dashboard menu");
            Thread.sleep(2000);

            // Step 4.1: Click PROFILE
            homePage.clickProfile();
            test.log(Status.INFO, "Clicked PROFILE button");
            Thread.sleep(2000);
        }

        // Step 5: Check if Profile page displayed and wait for it to load
        ProfilePage profilePage = new ProfilePage(driver);
        if (profilePage.isProfilePageDisplayed()) {
            test.log(Status.INFO, "Profile page displayed successfully");
            Thread.sleep(1000); // Additional wait for page elements to be fully loaded
        } else {
            test.log(Status.WARNING, "Profile page not detected, attempting to continue anyway");
        }

        // Step 6: Click ACCOUNT to navigate to Edit Profile
        profilePage.clickAccount();
        test.log(Status.INFO, "Clicked ACCOUNT button");
        Thread.sleep(2000);

        // Verify Edit Profile page is displayed
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        if (editProfilePage.isEditProfilePageDisplayed()) {
            test.log(Status.INFO, "Edit Profile page displayed successfully");
        }

        // Click Change Password button
        editProfilePage.clickChangePassword();
        test.log(Status.INFO, "Clicked Change Password button");
        Thread.sleep(2000);

        if (changePasswordPage.isChangePasswordPageDisplayed()) {
            test.log(Status.INFO, "Change Password page displayed");
        }
    }

    /**
     * TEST CHANGE PASSWORD SCENARIOS
     * Covers Negative (Wrong, Weak, Same) and Positive (Success) scenarios
     */
    @Test(dataProvider = "changePasswordData", priority = 1)
    public void testChangePassword(String scenario, String currentPass, String newPass, String confirmPass,
            String expectedResult) throws InterruptedException {
        test = extent.createTest("Change Password: " + scenario);
        test.log(Status.INFO, "Scenario: " + scenario);

        try {
            // 1. Navigate to Change Password Page
            navigateToChangePassword();
            ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);

            // 2. Enter Password Fields (Step 4 Common Action)
            changePasswordPage.enterCurrentPassword(currentPass);
            test.log(Status.INFO, "Entered Current Password");

            changePasswordPage.enterNewPassword(newPass);
            test.log(Status.INFO, "Entered New Password");

            changePasswordPage.enterConfirmPassword(confirmPass);
            test.log(Status.INFO, "Entered Confirm Password");

            // 3. Click Change Password Button
            changePasswordPage.clickChangePasswordButton();
            test.log(Status.INFO, "Clicked Change Password Button");
            Thread.sleep(2000);

            // 4. Verify Result
            if (expectedResult.equals("SUCCESS")) {
                // Positive Scenario
                if (changePasswordPage.isSuccessDialogDisplayed()) {
                    test.log(Status.PASS, "Success Dialog Displayed");

                    String successMsg = changePasswordPage.getSuccessMessage();
                    if (successMsg != null) {
                        test.log(Status.INFO, "Success Message: " + successMsg);
                    } else {
                        test.log(Status.WARNING, "Could not capture Success Message text");
                    }

                    changePasswordPage.clickOkButton();
                    test.log(Status.INFO, "Clicked OK Button");
                    Thread.sleep(1000);

                } else {
                    test.log(Status.FAIL, "Success Dialog NOT Displayed");
                    Assert.fail("Expected Success Dialog was not displayed.");
                }
            } else {
                // Negative Scenarios
                String actualError = changePasswordPage.getValidationErrorMessage(expectedResult);
                if (actualError != null) {
                    test.log(Status.PASS, "✓ Error validated: " + actualError);
                } else {
                    test.log(Status.FAIL, "✗ Expected error not found or mismatch");
                    test.log(Status.INFO, "Expected Xpath: " + expectedResult);
                    Assert.fail("Expected error validation not found: " + expectedResult);
                }
            }
        } finally {
            // CRITICAL: Navigate back to home page for next test iteration
            // This ensures each test starts from a clean state
            try {
                test.log(Status.INFO, "Navigating back to Home Page for next iteration");

                // Press back multiple times to reach home page
                for (int i = 0; i < 5; i++) {
                    driver.navigate().back();
                    Thread.sleep(500);

                    // Check if we reached home page
                    HomePage homePage = new HomePage(driver);
                    if (homePage.isHomePageDisplayed()) {
                        test.log(Status.INFO, "Successfully navigated back to Home Page");
                        break;
                    }
                }
            } catch (Exception e) {
                test.log(Status.WARNING, "Could not navigate back to Home Page: " + e.getMessage());
            }
        }
    }

    /**
     * RUNTIME-BASED NEGATIVE TEST FOR EDIT PROFILE
     * 
     * Test Flow:
     * 1. Navigate from Sign In through all pages to Edit Profile
     * 2. Enter invalid/empty data in form fields
     * 3. Click SAVE CHANGES button
     * 4. Check if ANY validation appears at runtime
     * 5. PASS if validation detected, FAIL if not
     * 6. Log the actual runtime validation message in Extent Report
     */
    @Test(dataProvider = "negativeEditProfileData")
    public void testNegativeEditProfile(String testScenario, String name, String email, String phone)
            throws InterruptedException {

        test = extent.createTest("Negative Test: " + testScenario);
        test.log(Status.INFO, "Name: '" + name + "' | Email: '" + email + "' | Phone: '" + phone + "'");


        // At runtime, check if SignIn page is present. If not, continue with navigation steps directly.
        boolean signInPagePresent = false;
        try {
            SignInPage signInPage = new SignInPage(driver);
            signInPagePresent = signInPage.isOnSignInPage();
        } catch (Exception e) {
            // If any error, assume not present
            signInPagePresent = false;
        }

        if (signInPagePresent) {
            // Normal flow: use navigation logic as before
            if (testNegativeEditProfileIndex == 0) {
                navigateToEditProfile(true); // full SignIn flow
            } else {
                navigateToEditProfile(false); // from Home
            }
            testNegativeEditProfileIndex++;
        } else {
            // SignIn page is not present, continue with navigation steps directly (no wait)
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            try {
                WebElement wellbeingDashboard = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name, 'WELLBEING')]") ));
                wellbeingDashboard.click();
                test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME");
                Thread.sleep(1500);
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found: " + e.getMessage());
                throw new RuntimeException("Failed to click Wellbeing Dashboard", e);
            }

            try {
                WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
                profile.click();
                test.log(Status.INFO, "✓ Clicked PROFILE");
                Thread.sleep(2000);
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠ PROFILE button not found: " + e.getMessage());
                throw new RuntimeException("Failed to click Profile", e);
            }

            boolean accountClicked = false;
            try {
                WebElement account = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeStaticText[@name='ACCOUNT']")));
                account.click();
                test.log(Status.INFO, "✓ Clicked ACCOUNT by StaticText xpath");
                Thread.sleep(1500);
                accountClicked = true;
            } catch (Exception e1) {
                test.log(Status.WARNING, "⚠ ACCOUNT not found by StaticText xpath: " + e1.getMessage());
                // Try by accessibility id
                try {
                    WebElement accountAccId = driver.findElement(MobileBy.AccessibilityId("ACCOUNT"));
                    accountAccId.click();
                    test.log(Status.INFO, "✓ Clicked ACCOUNT by accessibility id");
                    Thread.sleep(1500);
                    accountClicked = true;
                } catch (Exception e2) {
                    test.log(Status.WARNING, "⚠ ACCOUNT not found by accessibility id: " + e2.getMessage());
                    // Try by name
                    try {
                        WebElement accountByName = driver.findElement(By.name("ACCOUNT"));
                        accountByName.click();
                        test.log(Status.INFO, "✓ Clicked ACCOUNT by name");
                        Thread.sleep(1500);
                        accountClicked = true;
                    } catch (Exception e3) {
                        test.log(Status.WARNING, "⚠ ACCOUNT not found by name: " + e3.getMessage());
                        // Try by class chain
                        try {
                            WebElement accountClassChain = driver.findElement(MobileBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'ACCOUNT'`]"));
                            accountClassChain.click();
                            test.log(Status.INFO, "✓ Clicked ACCOUNT by iOS class chain");
                            Thread.sleep(1500);
                            accountClicked = true;
                        } catch (Exception e4) {
                            test.log(Status.WARNING, "⚠ ACCOUNT not found by iOS class chain: " + e4.getMessage());
                            // Try by iOSNsPredicateString
                            try {
                                WebElement accountPredicate = driver.findElement(MobileBy.iOSNsPredicateString("name == 'ACCOUNT'"));
                                accountPredicate.click();
                                test.log(Status.INFO, "✓ Clicked ACCOUNT by iOSNsPredicateString");
                                Thread.sleep(1500);
                                accountClicked = true;
                            } catch (Exception e5) {
                                test.log(Status.WARNING, "⚠ ACCOUNT not found by iOSNsPredicateString: " + e5.getMessage());
                                // Try by alternative XPath (Button)
                                try {
                                    WebElement accountButtonXpath = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='ACCOUNT']"));
                                    accountButtonXpath.click();
                                    test.log(Status.INFO, "✓ Clicked ACCOUNT by Button xpath");
                                    Thread.sleep(1500);
                                    accountClicked = true;
                                } catch (Exception e6) {
                                    test.log(Status.WARNING, "⚠ ACCOUNT not found by Button xpath: " + e6.getMessage());
                                }
                            }
                        }
                    }
                }
            }
            if (!accountClicked) {
                throw new RuntimeException("Failed to click ACCOUNT by any locator");
            }

            EditProfilePage editProfilePage = new EditProfilePage(driver);
            if (editProfilePage.isEditProfilePageDisplayed()) {
                test.log(Status.INFO, "✓ Edit Profile page displayed successfully");
            }
        }

        try {
            EditProfilePage editProfilePage = new EditProfilePage(driver);

            // Clear and enter test data
            editProfilePage.enterName(name);
            test.log(Status.INFO, "Entered name: '" + name + "'");


            editProfilePage.enterEmail(email);
            test.log(Status.INFO, "Entered email: '" + email + "'");

            // Add a short wait to ensure UI is ready for Date of Birth field
            Thread.sleep(1000);

            // HANDLE DATE OF BIRTH (robust locator)
            boolean dobClicked = false;
            WebDriverWait dobWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            try {
                WebElement dobField = dobWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//XCUIElementTypeOther[@name='Date of birth']")));
                dobField.click();
                test.log(Status.INFO, "Clicked Date of Birth by xpath");
                dobClicked = true;
            } catch (Exception e1) {
                test.log(Status.WARNING, "Date of Birth not found by xpath: " + e1.getMessage());
                // Try by accessibility id
                try {
                    WebElement dobAccId = driver.findElement(MobileBy.AccessibilityId("Date of birth"));
                    dobAccId.click();
                    test.log(Status.INFO, "Clicked Date of Birth by accessibility id");
                    dobClicked = true;
                } catch (Exception e2) {
                    test.log(Status.WARNING, "Date of Birth not found by accessibility id: " + e2.getMessage());
                    // Try by class chain
                    try {
                        WebElement dobClassChain = driver.findElement(MobileBy.iOSClassChain("**/XCUIElementTypeOther[`name == 'Date of birth'`]"));
                        dobClassChain.click();
                        test.log(Status.INFO, "Clicked Date of Birth by iOS class chain");
                        dobClicked = true;
                    } catch (Exception e3) {
                        test.log(Status.WARNING, "Date of Birth not found by iOS class chain: " + e3.getMessage());
                        // Try by iOSNsPredicateString
                        try {
                            WebElement dobPredicate = driver.findElement(MobileBy.iOSNsPredicateString("name == 'Date of birth'"));
                            dobPredicate.click();
                            test.log(Status.INFO, "Clicked Date of Birth by iOSNsPredicateString");
                            dobClicked = true;
                        } catch (Exception e4) {
                            test.log(Status.WARNING, "Date of Birth not found by iOSNsPredicateString: " + e4.getMessage());
                        }
                    }
                }
            }
            if (!dobClicked) {
                throw new RuntimeException("Date of Birth field not found or clickable on Edit Profile page after retry");
            }
            editProfilePage.performDateSelection();
            test.log(Status.INFO, "Performed Date Selection (Swipe & Confirm)");

            // HANDLE COUNTRY (Standardized to Belarus for negative tests)
            editProfilePage.clickCountryCode();
            test.log(Status.INFO, "Clicked Country Code dropdown");
            Thread.sleep(500);
            editProfilePage.selectCountry("Belarus");
            test.log(Status.INFO, "Selected country: Belarus");

            editProfilePage.enterPhoneNumber(phone);
            test.log(Status.INFO, "Entered phone: '" + phone + "'");

            // HANDLE GENDER (Standardized to Male for negative tests)
            editProfilePage.clickGender();
            test.log(Status.INFO, "Clicked Gender dropdown");
            Thread.sleep(500);
            editProfilePage.selectGender("Male");
            test.log(Status.INFO, "Selected gender: Male");


            // Hide keyboard immediately after entering phone number
            editProfilePage.hideKeyboard();

            // Click SAVE CHANGES
            editProfilePage.clickSaveChanges();
            test.log(Status.INFO, "Clicked SAVE CHANGES button");

            // Wait for validation to appear
            Thread.sleep(2000);

            // RUNTIME VALIDATION CHECK (NO HARDCODED MESSAGES)
            boolean validationDetected = editProfilePage.isAnyValidationVisible();

            if (validationDetected) {
                // PASS: Validation appeared (negative case handled correctly)

                // Capture and log the actual runtime validation message
                String validationMessage = editProfilePage.getValidationMessage();
                if (validationMessage != null && !validationMessage.trim().isEmpty()) {
                    test.log(Status.INFO, "📋 Validation message displayed: \"" + validationMessage + "\"");
                }

                test.log(Status.PASS, "✓ Validation detected at runtime - Negative case handled correctly");
                test.log(Status.PASS, "Test PASSED: Application showed validation for invalid input");
            } else {
                // FAIL: No validation appeared (security/UX issue)
                test.log(Status.FAIL, "✗ NO validation detected at runtime");
                test.log(Status.FAIL, "Test FAILED: Application did not show any validation for invalid input");
                Assert.fail("Expected validation to appear for negative test case, but NONE was detected");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Test encountered an error: " + e.getMessage());
            throw e; // Re-throw to ensure TestNG records it as failure
        }
    }
    // Static counter for negative test index
    private static int testNegativeEditProfileIndex = 0;

    /**
     * TEST WITH DATE OF BIRTH, GENDER, AND COUNTRY SELECTION
     * 
     * This test demonstrates handling of:
     * - Date of Birth picker (runtime)
     * - Gender dropdown
     * - Country selection (India)
     */
    @Test
    public void testEditProfileWithDateGenderCountry() throws InterruptedException {
        test = extent.createTest("Test: Edit Profile with Date, Gender, Country");
        test.log(Status.INFO, "Testing date picker, gender dropdown, and country selection");

        // Navigate to Edit Profile page (from Home, not full sign-in)
        navigateToEditProfile(false);

        EditProfilePage editProfilePage = new EditProfilePage(driver);

        // Enter valid data
        editProfilePage.enterName("Test User");
        test.log(Status.INFO, "Entered name");

        editProfilePage.enterEmail("test@example.com");
        test.log(Status.INFO, "Entered email");

        editProfilePage.clickDateOfBirth();
        test.log(Status.INFO, "Clicked Date of Birth field");

        editProfilePage.performDateSelection();
        test.log(Status.INFO, "Performed Date Selection (Swipe & Confirm)");
        Thread.sleep(1000);

        // Click and select Gender
        editProfilePage.clickGender();
        test.log(Status.INFO, "Clicked Gender dropdown");
        Thread.sleep(1000);

        editProfilePage.selectGender("Male");
        test.log(Status.INFO, "Selected gender: Male");
        Thread.sleep(1000);

        // Click and select Country (India)
        editProfilePage.clickCountryCode();
        test.log(Status.INFO, "Clicked Country Code dropdown");
        Thread.sleep(1000);

        editProfilePage.selectCountry("Belarus");
        test.log(Status.INFO, "Selected country: Belarus");
        Thread.sleep(1000);

        editProfilePage.enterPhoneNumber("9876543210");
        test.log(Status.INFO, "Entered phone number");

        // Hide keyboard before clicking SAVE CHANGES
        editProfilePage.hideKeyboard();
        // Click SAVE CHANGES
        editProfilePage.clickSaveChanges();
        test.log(Status.INFO, "Clicked SAVE CHANGES button");

        Thread.sleep(2000);

        // Check for validation (should not appear for valid data)
        boolean validationDetected = editProfilePage.isAnyValidationVisible();

        if (!validationDetected) {
            test.log(Status.PASS, "✓ No validation errors - Profile update successful");
            test.log(Status.PASS, "Test PASSED: Valid data accepted");
        } else {
            String validationMessage = editProfilePage.getValidationMessage();
            if (validationMessage != null) {
                test.log(Status.INFO, "Validation message: \"" + validationMessage + "\"");
            }
            test.log(Status.INFO, "Validation appeared - may need to check data format");
        }
    }
}
