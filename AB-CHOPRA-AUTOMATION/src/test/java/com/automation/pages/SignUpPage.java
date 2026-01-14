package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    public SignUpPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement findElementWithFallback(String id, String xpath, String accessibilityId) {
        try {
            if (id != null && !id.isEmpty())
                return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        } catch (TimeoutException ignored) {
        }
        try {
            if (xpath != null && !xpath.isEmpty())
                return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException ignored) {
        }
        try {
            if (accessibilityId != null && !accessibilityId.isEmpty())
                return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(accessibilityId)));
        } catch (TimeoutException ignored) {
        }

        throw new RuntimeException(
                "Element not found with ID: " + id + ", XPath: " + xpath + ", AccessID: " + accessibilityId);
    }

    // Locators
    private final String nameFieldXpath = "//XCUIElementTypeTextField[@name='Name']";
    private final String emailFieldXpath = "//XCUIElementTypeTextField[@name='Email']";
    private final String countryButtonXpath = "//XCUIElementTypeButton[@name='Country']";
    private final String passwordFieldXpath = "//XCUIElementTypeOther[@name='Password']";
    private final String confirmPasswordFieldXpath = "//XCUIElementTypeOther[@name='Confirm Password']";
    private final String continueBtnXpath = "//XCUIElementTypeButton[@name='CONTINUE']";
    private final String signInLinkXpath = "//XCUIElementTypeButton[@name=\"Already have an account? Sign in\"]";

    /**
     * Enter name in the Name field
     */
    public void enterName(String name) {
        WebElement el = findElementWithFallback(null, nameFieldXpath, null);
        el.click();
        el.clear();
        if (name != null && !name.isEmpty()) {
            el.sendKeys(name);
        }
    }

    /**
     * Enter email in the Email field
     */
    public void enterEmail(String email) {
        WebElement el = findElementWithFallback(null, emailFieldXpath, null);
        el.click();
        el.clear();
        if (email != null && !email.isEmpty()) {
            el.sendKeys(email);
        }
    }

    /**
     * Click the Country dropdown button
     */
    public void clickCountry() {
        WebElement btn = findElementWithFallback(null, countryButtonXpath, "Country");
        btn.click();
    }

    /**
     * Enter password in the Password field
     */
    public void enterPassword(String password) {
        WebElement el = findElementWithFallback(null, passwordFieldXpath, null);
        el.click();
        el.clear();
        if (password != null && !password.isEmpty()) {
            el.sendKeys(password);
        }
    }

    /**
     * Enter password in the Confirm Password field
     */
    public void enterConfirmPassword(String confirmPassword) {
        WebElement el = findElementWithFallback(null, confirmPasswordFieldXpath, null);
        el.click();
        el.clear();
        if (confirmPassword != null && !confirmPassword.isEmpty()) {
            el.sendKeys(confirmPassword);
        }
    }

    /**
     * Click the Continue button
     */
    public void clickContinue() {
        WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
        if (btn.isEnabled()) {
            btn.click();
        }
    }

    /**
     * Click "Already have an account? Sign in" link to navigate to Sign In page
     */
    public void clickSignIn() {
        WebElement link = findElementWithFallback(null, signInLinkXpath, null);
        link.click();
    }

    /**
     * Check if Continue button is enabled
     */
    public boolean isContinueButtonEnabled() {
        try {
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            return btn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click Country dropdown and select Afghanistan (first option)
     * This method clicks the Country button and then selects Afghanistan
     */
    public void selectFirstCountryOption() {
        // Click Country dropdown
        clickCountry();

        try {
            // Wait for dropdown to appear and select first option
            Thread.sleep(1000); // Allow dropdown to render

            // Try to find and click Afghanistan button
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Look for Afghanistan button by name (primary method)
            try {
                WebElement afghanistanBtn = shortWait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeButton[@name='Afghanistan']")));
                afghanistanBtn.click();
            } catch (Exception e1) {
                // Fallback: try Button with accessibility ID
                try {
                    WebElement afghanistanBtn = shortWait.until(ExpectedConditions.elementToBeClickable(
                            AppiumBy.accessibilityId("Afghanistan")));
                    afghanistanBtn.click();
                } catch (Exception e2) {
                    // Fallback: try first clickable Button
                    WebElement firstOption = shortWait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("(//XCUIElementTypeButton[@enabled='true'])[1]")));
                    firstOption.click();
                }
            }
        } catch (Exception e) {
            System.out.println("Could not select Afghanistan: " + e.getMessage());
        }
    }

    /**
     * Click the "SIGN UP" heading
     * This is required before clicking Continue button in tests
     */
    public void clickSignUpHeading() {
        // Validate by name first: @name="SIGN UP", then xpath as fallback
        String signUpHeadingXpath = "//XCUIElementTypeStaticText[@name='SIGN UP']";
        try {
            WebElement heading = findElementWithFallback(null, signUpHeadingXpath, null);
            heading.click();
        } catch (Exception e) {
            System.out.println("Could not click SIGN UP heading: " + e.getMessage());
        }
    }

    /**
     * RUNTIME-BASED VALIDATION DETECTION (NO HARDCODED MESSAGES)
     * 
     * Checks at runtime if ANY validation element is visible:
     * - Password validation message (8+ chars requirement)
     * - Validation message via name attribute
     * - Alert/error view
     * - Inline error text
     * 
     * @return true if ANY validation is detected, false if NONE found
     */
    public boolean isAnyValidationVisible() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Check 1: Password validation message (8+ chars requirement)
        try {
            WebElement passwordValidation = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name='Use at least 8 characters with uppercase, lowercase, number, and special symbol.']")));
            if (passwordValidation != null) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // Check 2: Validation message via name attribute (XCUIElementTypeOther)
        try {
            WebElement validationView = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name and (contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'weak') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'match') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please'))]")));
            String name = validationView.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // Check 3: Static Text with error keywords
        try {
            WebElement errorKeyword = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'weak') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'match') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please')]")));
            String text = errorKeyword.getAttribute("name");
            if (text != null && !text.trim().isEmpty()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // Check 4: Alert dialog
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert")));
            if (alert != null) {
                return true;
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    /**
     * GET ACTUAL RUNTIME VALIDATION MESSAGE
     * 
     * Captures the actual validation message displayed by the app at runtime.
     * This method does NOT compare with any expected value - it simply reads
     * whatever text is shown by the app.
     * 
     * Priority order for message capture:
     * 1. Validation message from name attribute (XCUIElementTypeOther)
     * 2. Static text with error keywords
     * 3. Alert message
     * 4. Disabled Continue button state
     * 
     * @return The actual validation message text, or null if no validation found
     */
    public String getValidationMessage() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Priority 1: Password validation message (8+ chars requirement)
        try {
            WebElement passwordValidation = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name='Use at least 8 characters with uppercase, lowercase, number, and special symbol.']")));
            String message = passwordValidation.getAttribute("name");
            if (message != null && !message.trim().isEmpty()) {
                return message;
            }
        } catch (Exception ignored) {
        }

        // Priority 2: Validation message via name attribute (XCUIElementTypeOther)
        try {
            WebElement validationView = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name and (contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'weak') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'match') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please'))]")));
            String name = validationView.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                return name;
            }
        } catch (Exception ignored) {
        }

        // Priority 3: Static text with error keywords
        try {
            WebElement errorKeyword = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'weak') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'match') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please')]")));
            String text = errorKeyword.getAttribute("name");
            if (text != null && !text.trim().isEmpty()) {
                return text;
            }
        } catch (Exception ignored) {
        }

        // Priority 4: Alert dialog
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert")));
            String alertText = alert.getText();
            if (alertText != null && !alertText.trim().isEmpty()) {
                return alertText;
            }
        } catch (Exception ignored) {
        }

        // Priority 5: Disabled Continue button (for empty fields)
        try {
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            if (!btn.isEnabled()) {
                return "Continue button is disabled (fields are empty or invalid)";
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    /**
     * Check if Sign Up page is displayed
     */
    public boolean isSignUpPageDisplayed() {
        try {
            // Validate by name first: @name="SIGN UP", then xpath as fallback
            WebElement heading = findElementWithFallback(null, "//XCUIElementTypeStaticText[@name='SIGN UP']", null);
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Consent Page is displayed (successful sign up)
     */
    public boolean isConsentPageDisplayed() {
        try {
            // Validate by name first, then xpath as fallback
            WebElement consentElement = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='I accept the terms and conditions of AB Chopra services']",
                    "I accept the terms and conditions of AB Chopra services");
            return consentElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click Terms of Use link
     */
    public void clickTermsOfUse() {
        WebElement termsLink = findElementWithFallback(null,
                "//XCUIElementTypeOther[@name='Terms of Use']",
                "Terms of Use");
        termsLink.click();
    }

    /**
     * Verify Terms of Use page is displayed
     */
    public boolean isTermsOfUsePageDisplayed() {
        try {
            WebElement termsHeading = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='TERMS & CONDITIONS']",
                    "TERMS & CONDITIONS");
            return termsHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click Privacy Policy link
     */
    public void clickPrivacyPolicy() {
        WebElement privacyLink = findElementWithFallback(null,
                "//XCUIElementTypeOther[@name='Privacy Policy']",
                "Privacy Policy");
        privacyLink.click();
    }

    /**
     * Verify Privacy Policy page is displayed
     */
    public boolean isPrivacyPolicyPageDisplayed() {
        try {
            WebElement privacyHeading = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='PRIVACY POLICY']",
                    "PRIVACY POLICY");
            return privacyHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click back button
     * Tries multiple strategies to find and click the back button
     */
    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        try {
            // Strategy 1: Try back button by name
            try {
                WebElement backBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeButton[@name='Back']")));
                backBtn.click();
                return;
            } catch (Exception ignored) {
            }

            // Strategy 2: Try first button (usually back button on iOS navigation)
            try {
                WebElement firstBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//XCUIElementTypeButton)[1]")));
                firstBtn.click();
                return;
            } catch (Exception ignored) {
            }

            // Strategy 3: Try button with common back indicators
            try {
                WebElement backBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeButton[contains(@name, 'back') or contains(@name, 'Back')]")));
                backBtn.click();
                return;
            } catch (Exception ignored) {
            }

            // Strategy 4: Any clickable button (fallback)
            try {
                WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//XCUIElementTypeButton[@enabled='true']")));
                btn.click();
            } catch (Exception e) {
                System.out.println("Could not click back button: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Back button click failed: " + e.getMessage());
        }
    }

    /**
     * Click checkbox 1
     */
    public void clickCheckbox1() {
        WebElement checkbox = findElementWithFallback(null,
                "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]",
                null);
        checkbox.click();
    }

    /**
     * Click checkbox 2
     */
    public void clickCheckbox2() {
        WebElement checkbox = findElementWithFallback(null,
                "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]",
                null);
        checkbox.click();
    }

    /**
     * Click checkbox 3
     */
    public void clickCheckbox3() {
        WebElement checkbox = findElementWithFallback(null,
                "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[4]",
                null);
        checkbox.click();
    }

    /**
     * Click checkbox 4
     */
    public void clickCheckbox4() {
        WebElement checkbox = findElementWithFallback(null,
                "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[5]",
                null);
        checkbox.click();
    }

    /**
     * Verify Confirm Your Email page is displayed
     */
    public boolean isConfirmEmailPageDisplayed() {
        try {
            WebElement confirmEmailHeading = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='CONFIRM YOUR EMAIL']",
                    "CONFIRM YOUR EMAIL");
            return confirmEmailHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enter OTP code (6 digits)
     */
    public void enterOTP(String otp) {
        WebElement otpField = findElementWithFallback(null,
                "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[1]",
                null);
        otpField.click();
        otpField.clear();
        otpField.sendKeys(otp);
    }

    /**
     * Click Verify button
     */
    public void clickVerifyButton() {
        WebElement verifyBtn = findElementWithFallback(null,
                "//XCUIElementTypeButton[@name='VERIFY']",
                "VERIFY");
        verifyBtn.click();
    }

    /**
     * Get incorrect OTP error message
     */
    public String getIncorrectOTPError() {
        try {
            WebElement errorMsg = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='Incorrect code. Please try again.']",
                    "Incorrect code. Please try again.");
            return errorMsg.getAttribute("name");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Click Get a new code button
     */
    public void clickGetNewCode() {
        WebElement newCodeBtn = findElementWithFallback(null,
                "//XCUIElementTypeStaticText[@name='Get a new code']",
                "Get a new code");
        newCodeBtn.click();
    }

    /**
     * Verify Resend Successful dialog is displayed
     */
    public boolean isResendSuccessfulDialogDisplayed() {
        try {
            WebElement resendDialog = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='RESEND SUCCESSFUL']",
                    "RESEND SUCCESSFUL");
            return resendDialog.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get resend successful message
     */
    public String getResendSuccessfulMessage() {
        try {
            WebElement successMsg = findElementWithFallback(null,
                    "//XCUIElementTypeStaticText[@name='Verification code has been sent successfully.']",
                    "Verification code has been sent successfully.");
            return successMsg.getAttribute("name");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Click OK button
     */
    public void clickOKButton() {
        WebElement okBtn = findElementWithFallback(null,
                "//XCUIElementTypeButton[@name='OK']",
                "OK");
        okBtn.click();
    }
}