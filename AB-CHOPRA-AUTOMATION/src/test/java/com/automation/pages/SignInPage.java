package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignInPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    public SignInPage(AppiumDriver driver) {
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

    // iOS locators based on actual app structure from Appium Inspector
    private final String emailXpath = "//XCUIElementTypeTextField[@name='Email']";
    // Password field: The TextField inside the Password wrapper (at index 1 after Email field)
    private final String passwordXpath = "(//XCUIElementTypeTextField)[2]";
    // iOS secure password field fallback
    private final String passwordSecureXpath = "//XCUIElementTypeSecureTextField";
    private final String continueBtnXpath = "//XCUIElementTypeButton[@name='CONTINUE']";

    public void enterEmail(String email) {
        WebElement el = findElementWithFallback(null, emailXpath, null);
        try {
            // Try reflection-based setValue on element (if provided by Appium client)
            try {
                java.lang.reflect.Method m = el.getClass().getMethod("setValue", String.class);
                m.invoke(el, email);
                return;
            } catch (NoSuchMethodException nsme) {
                // ignore - proceed to other fallbacks
            }

            // Prefer Appium's mobile:setValue to avoid keyboard animation delays
            if (driver instanceof JavascriptExecutor && el instanceof RemoteWebElement) {
                Map<String, Object> args = new HashMap<>();
                args.put("elementId", ((RemoteWebElement) el).getId());
                args.put("value", Collections.singletonList(email));
                try {
                    ((JavascriptExecutor) driver).executeScript("mobile: setValue", args);
                    return;
                } catch (Exception ignored) {
                }
            }

            // Fallback to standard sendKeys
            el.clear();
            el.sendKeys(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email: " + e.getMessage(), e);
        }
    }

    public void enterPassword(String password) {
        WebElement el = null;

        // Prefer secure text field for password on iOS, fallback to text field
        try {
            el = findElementWithFallback(null, passwordSecureXpath, "Password");
        } catch (Exception ignored) {
        }

        if (el == null) {
            el = findElementWithFallback(null, passwordXpath, null);
        }

        try {
            // Try reflection-based setValue on element (if provided by Appium client)
            try {
                java.lang.reflect.Method m = el.getClass().getMethod("setValue", String.class);
                m.invoke(el, password);
                return;
            } catch (NoSuchMethodException nsme) {
                // ignore - proceed to other fallbacks
            }

            // Use mobile:setValue to bypass keyboard animation
            if (driver instanceof JavascriptExecutor && el instanceof RemoteWebElement) {
                Map<String, Object> args = new HashMap<>();
                args.put("elementId", ((RemoteWebElement) el).getId());
                args.put("value", Collections.singletonList(password));
                try {
                    ((JavascriptExecutor) driver).executeScript("mobile: setValue", args);
                    return;
                } catch (Exception ignored) {
                }
            }

            // Fallback
            el.clear();
            el.sendKeys(password);
            return;
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter password: " + e.getMessage(), e);
        }
    }

    public void clickContinue() {
        WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
        if (btn.isEnabled()) {
            btn.click();
        }
    }

    /**
     * ========================================
     * BEHAVIOR-BASED DISABLED STATE DETECTION
     * ========================================
     * 
     * Why this method is critical for iOS:
     * =====================================
     * In iOS Appium automation, the CONTINUE button appears visually disabled (greyed out)
     * when Email and/or Password fields are empty. However, Appium Inspector shows that
     * the button's attributes REMAIN UNCHANGED between clickable and non-clickable states:
     * 
     * - enabled = true (always)
     * - visible = true (always)
     * - accessible = true (always)
     * - hittable = NOT exposed by Appium for XCUIElementTypeButton
     * 
     * This is an iOS-specific limitation: Unlike Android, XCUITest does not reliably expose
     * a "disabled" or "hittable" attribute that changes state based on logical enablement.
     * 
     * SOLUTION: Behavior-Based Detection
     * ==================================
     * Instead of relying on attributes, we detect disabled state by observing ACTUAL BEHAVIOR:
     * 1. Attempt to click the CONTINUE button
     * 2. Wait 1 second for any navigation or validation to occur
     * 3. Check if the app state changed (new page OR validation message appeared)
     * 4. If NO change occurred → button is logically DISABLED (click had no effect)
     * 5. If change occurred → button is logically ENABLED
     * 
     * This aligns with real-world user experience:
     * - User taps button → something happens = button works
     * - User taps button → nothing happens = button is disabled
     * 
     * @return true if button is logically DISABLED (click has no effect), false otherwise
     */
    public boolean isContinueButtonLogicallyDisabled() {
        try {
            // Record current state markers before clicking
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            
            // Marker 1: Check if we're currently on Sign In page (email field visible)
            boolean wasOnSignInPage = false;
            try {
                WebElement emailField = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(emailXpath)));
                wasOnSignInPage = emailField.isDisplayed();
            } catch (Exception ignored) {
            }

            // Marker 2: Record if validation message is visible BEFORE clicking
            boolean validationVisibleBefore = isAnyValidationVisible();

            // Step 1: Attempt to click the button
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            btn.click();
            
            // Step 2: Wait briefly for any navigation or validation to appear
            Thread.sleep(1000); // Allow app to respond to click
            
            // Step 3: Check if ANY change occurred
            boolean isOnSignInPageAfter = false;
            try {
                WebElement emailFieldAfter = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(emailXpath)));
                isOnSignInPageAfter = emailFieldAfter.isDisplayed();
            } catch (Exception ignored) {
            }
            
            // Check if validation message now visible (wasn't before)
            boolean validationVisibleAfter = isAnyValidationVisible();
            
            // Check if LINK DEVICES page appeared (navigation occurred)
            boolean linkDevicesAppeared = isLinkDevicesDisplayed();
            
            // Step 4: Determine if button is logically disabled
            // If we're STILL on Sign In page AND no new validation appeared AND no other navigation → button was disabled
            boolean stillOnSignInPage = (wasOnSignInPage && isOnSignInPageAfter);
            boolean noValidationChange = (!validationVisibleBefore && !validationVisibleAfter);
            boolean noOtherNavigation = !linkDevicesAppeared;
            
            boolean isLogicallyDisabled = (stillOnSignInPage && noValidationChange && noOtherNavigation);
            
            return isLogicallyDisabled;
            
        } catch (Exception e) {
            // If any exception occurs during check, assume button is not disabled
            System.out.println("Error checking if button is logically disabled: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "Don't have an account? Sign up" button to navigate to Sign Up page
     */
    public void clickSignUp() {
        String signUpBtnXpath = "//XCUIElementTypeButton[@name=\"Don't have an account? Sign up\"]";
        WebElement btn = findElementWithFallback(null, signUpBtnXpath, null);
        btn.click();
    }

    public boolean isContinueButtonEnabled() {
        try {
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            return btn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the runtime validation message that appears below the password field.
     * This method checks multiple locations and patterns for dynamic error
     * messages.
     * 
     * @return The validation message text, or null if no message is found
     */
    public String getRuntimeValidationMessage() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Strategy 1: Look for validation message below password field (most specific)
        try {
            WebElement validationMsg = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name='Password']/following-sibling::XCUIElementTypeStaticText[1]")));
            String text = validationMsg.getText();
            if (text != null && !text.trim().isEmpty()) {
                return text;
            }
        } catch (TimeoutException ignored) {
        }

        // Strategy 2: Look for any StaticText with common error keywords
        // (case-insensitive)
        try {
            WebElement errorText = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'wrong') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'incorrect') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'check') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'must') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cannot')]")));
            String text = errorText.getText();
            if (text != null && !text.trim().isEmpty() &&
                    !text.equals("Forgot Password?") && !text.equals("Don't have an account? Sign up")) {
                return text;
            }
        } catch (Exception ignored) {
        }

        // Strategy 3: iOS Alert message
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert//XCUIElementTypeStaticText")));
            String text = alert.getText();
            if (text != null && !text.trim().isEmpty()) {
                return text;
            }
        } catch (Exception ignored) {
        }

        // Strategy 4: Look for any error text (validation messages)
        // This searches for StaticText elements
        try {
            WebElement errorText = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name and string-length(@name) > 10]")));
            String text = errorText.getText();
            // Filter out known non-error texts
            if (text != null && !text.trim().isEmpty() &&
                    !text.equals("Forgot Password?") &&
                    !text.equals("Don't have an account? Sign up") &&
                    !text.equals("SIGN IN") &&
                    !text.contains("CONTINUE WITH")) {
                return text;
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    /**
     * Check if the Continue button is in a disabled state (light grey background).
     * This method checks the button's enabled state and can be extended to check
     * visual properties.
     * 
     * @return true if button is disabled (light grey), false if enabled (black)
     */
    public boolean isContinueButtonDisabled() {
        try {
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            // Check if button is disabled
            boolean isDisabled = !btn.isEnabled();

            // Optional: Check for visual properties if needed
            // String btnClass = btn.getAttribute("class");
            // String btnEnabled = btn.getAttribute("enabled");

            return isDisabled;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the Continue button's background color or state.
     * This can be used to assert that the button is light grey when fields are
     * empty/invalid.
     * 
     * @return A string describing the button state (e.g., "enabled", "disabled")
     */
    public String getContinueButtonState() {
        try {
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            boolean isEnabled = btn.isEnabled();
            String enabledAttr = btn.getAttribute("enabled");
            String clickableAttr = btn.getAttribute("clickable");

            if (!isEnabled || "false".equals(enabledAttr) || "false".equals(clickableAttr)) {
                return "disabled";
            } else {
                return "enabled";
            }
        } catch (Exception e) {
            return "not_found";
        }
    }

    /**
     * RUNTIME-BASED VALIDATION DETECTION (NO HARDCODED MESSAGES)
     * 
     * Checks at runtime if ANY validation element is visible:
     * - Toast message
     * - Inline error text (TextView with error-like content)
     * - EditText with error state / red error box
     * 
     * @return true if ANY validation is detected, false if NONE found
     */
    public boolean isAnyValidationVisible() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Check 1: iOS Alert Message
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert//XCUIElementTypeStaticText")));
            if (alert != null && alert.getText() != null && !alert.getText().trim().isEmpty()) {
                return true; // Alert detected
            }
        } catch (Exception ignored) {
        }

        // Check 2: Validation message via name attribute (iOS elements)
        try {
            WebElement validationView = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name and string-length(@name) > 10]")));
            String name = validationView.getAttribute("name");
            if (name != null && !name.trim().isEmpty() &&
                    !name.equals("SIGN IN") &&
                    !name.equals("Forgot Password?") &&
                    !name.equals("Don't have an account? Sign up") &&
                    !name.equals("CONTINUE") &&
                    !name.equals("or") &&
                    !name.contains("CONTINUE WITH")) {
                return true; // Validation message detected via name
            }
        } catch (Exception ignored) {
        }

        // Check 3: Inline Error Text (StaticText below password or email field)
        try {
            WebElement errorText = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name='Password']/following-sibling::XCUIElementTypeStaticText[1]")));
            if (errorText != null && errorText.getText() != null && !errorText.getText().trim().isEmpty()) {
                return true; // Inline error detected
            }
        } catch (Exception ignored) {
        }

        // Check 4: Any StaticText with error keywords (case-insensitive)
        try {
            WebElement errorKeyword = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'wrong') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'incorrect') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'must') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cannot')]")));
            String text = errorKeyword.getText();
            // Filter out known non-error texts
            if (text != null && !text.trim().isEmpty() &&
                    !text.equals("Forgot Password?") && !text.equals("Don't have an account? Sign up")) {
                return true; // Error keyword detected
            }
        } catch (Exception ignored) {
        }

        // Check 5: Continue button disabled (fields empty/invalid)
        try {
            if (isContinueButtonDisabled()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // No validation detected
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
     * 1. Validation message from content-desc (android.view.View)
     * 2. Toast message text
     * 3. Inline error TextView text
     * 4. TextView with error keywords
     * 5. EditText error attribute
     * 6. Disabled Continue button state
     * 
     * @return The actual validation message text, or null if no validation found
     */
    public String getValidationMessage() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Priority 1: Validation message via name attribute (iOS elements)
        // This is the primary validation message location for this app
        try {
            WebElement validationView = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name and string-length(@name) > 10]")));
            String name = validationView.getAttribute("name");
            if (name != null && !name.trim().isEmpty() &&
                    !name.equals("SIGN IN") &&
                    !name.equals("Forgot Password?") &&
                    !name.equals("Don't have an account? Sign up") &&
                    !name.equals("CONTINUE") &&
                    !name.equals("or") &&
                    !name.contains("CONTINUE WITH")) {
                return name;
            }
        } catch (Exception ignored) {
        }

        // Priority 2: iOS Alert Message
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert//XCUIElementTypeStaticText")));
            String alertText = alert.getText();
            if (alertText != null && !alertText.trim().isEmpty()) {
                return alertText;
            }
        } catch (Exception ignored) {
        }

        // Priority 3: Inline Error Text (StaticText below password field)
        try {
            WebElement errorText = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name='Password']/following-sibling::XCUIElementTypeStaticText[1]")));
            String text = errorText.getText();
            if (text != null && !text.trim().isEmpty()) {
                return text;
            }
        } catch (Exception ignored) {
        }

        // Priority 4: Inline Error Text (StaticText below email field)
        try {
            WebElement errorText = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeTextField[@name='Email']/following-sibling::XCUIElementTypeStaticText[1]")));
            String text = errorText.getText();
            if (text != null && !text.trim().isEmpty()) {
                return text;
            }
        } catch (Exception ignored) {
        }

        // Priority 5: Any StaticText with error keywords
        try {
            WebElement errorKeyword = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'wrong') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'incorrect') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'must') or " +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cannot')]")));
            String text = errorKeyword.getText();
            if (text != null && !text.trim().isEmpty() &&
                    !text.equals("Forgot Password?") && !text.equals("Don't have an account? Sign up")) {
                return text;
            }
        } catch (Exception ignored) {
        }


        // Priority 6: Disabled Continue button (for empty fields)
        try {
            WebElement btn = findElementWithFallback(null, continueBtnXpath, "CONTINUE");
            if (!btn.isEnabled()) {
                return "Continue button is disabled (fields are empty or invalid)";
            }
        } catch (Exception ignored) {
        }

        // No validation message found
        return null;
    }

    /**
     * Check if the app is currently on the Sign In page.
     * This is used to determine if navigation to Sign In page is needed.
     * 
     * @return true if on Sign In page, false otherwise
     */
    public boolean isOnSignInPage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            // Check for email field which is unique to Sign In page
            WebElement emailField = shortWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(emailXpath)));
            return emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for validation message to appear after clicking Continue.
     * This replaces Thread.sleep() with an explicit wait.
     * 
     * @param timeoutSeconds Maximum time to wait for validation
     * @return true if validation appeared, false if timeout
     */
    public boolean waitForValidationToAppear(int timeoutSeconds) {
        try {
            WebDriverWait validationWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

            // Wait for any validation element to appear
            validationWait.until(driver -> isAnyValidationVisible());
            return true;
        } catch (Exception e) {
            // Timeout - no validation appeared
            return false;
        }
    }

    /**
     * Check if the "LINK DEVICES" page is displayed after successful login.
     * 
     * @return true if LINK DEVICES element is visible
     */
    public boolean isLinkDevicesDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement linkDevices = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name='LINK DEVICES']")));
            return linkDevices.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Try to skip or continue from Link Devices page to reach Home page.
     */
    public void handleLinkDevices() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Try clicking SKIP
        try {
            WebElement skipBtn = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//XCUIElementTypeButton[@name='SKIP'] | //XCUIElementTypeStaticText[@name='SKIP']")));
            skipBtn.click();
            return;
        } catch (Exception ignored) {
        }

        // Try clicking CONTINUE
        try {
            WebElement continueBtn = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//XCUIElementTypeButton[@name='CONTINUE'] | //XCUIElementTypeStaticText[@name='CONTINUE']")));
            continueBtn.click();
            return;
        } catch (Exception ignored) {
        }

        // Try clicking close/X if exists
        try {
            WebElement closeBtn = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//XCUIElementTypeImage[contains(@name, 'close')]")));
            closeBtn.click();
        } catch (Exception ignored) {
        }
    }

    /**
     * Click "SKIP FOR NOW" button
     */
    public void clickSkipForNow() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement skipBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//XCUIElementTypeButton[@name='SKIP FOR NOW']")));
            skipBtn.click();
        } catch (Exception e) {
            // Log if not found, as it is part of the expected flow
            System.out.println("SKIP FOR NOW button not found: " + e.getMessage());
        }
    }
}
