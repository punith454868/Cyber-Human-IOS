package com.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ForgotPasswordPage {
            /**
             * Robustly clear a text field on iOS by sending backspaces for each character.
             * Works for XCUIElementTypeTextField and custom fields.
             */
            /**
             * Clear a text field on iOS by sending TAB to focus, then 5 backspaces.
             */
            public void clearTextFieldIOS(WebElement element) {
                element.sendKeys(org.openqa.selenium.Keys.TAB);
                for (int i = 0; i < 5; i++) {
                    element.sendKeys("\u232B"); // BACKSPACE unicode
                }
            }
        // ==================== HELPER METHODS ====================

        public WebElement findElementWithFallback(String id, String xpath, String accessibilityId) {
            try {
                if (id != null && !id.isEmpty())
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            } catch (TimeoutException ignored) {}
            try {
                if (xpath != null && !xpath.isEmpty())
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            } catch (TimeoutException ignored) {}
            try {
                if (accessibilityId != null && !accessibilityId.isEmpty())
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(accessibilityId)));
            } catch (TimeoutException ignored) {}
            // iOS fallback: try by name if XPath fails
            try {
                if (xpath != null && xpath.contains("@name=")) {
                    String name = xpath.replaceAll(".*@name=\\\"(.*?)\\\".*", "$1");
                    if (!name.isEmpty()) {
                        return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(name)));
                    }
                }
            } catch (TimeoutException ignored) {}
            throw new RuntimeException(
                    "Element not found with ID: " + id + ", XPath: " + xpath + ", AccessID: " + accessibilityId);
        }

        public WebElement waitForElement(String xpath, int timeoutSeconds) {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return customWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        }

        public void clickClearAndSendKeys(String xpath, String text) throws InterruptedException {
            WebElement element = findElementWithFallback(null, xpath, null);
            element.click();
            Thread.sleep(500);
            element.clear();
            Thread.sleep(500);
            element.sendKeys(text);
        }

        // ==================== PAGE METHODS ====================

        public void clickForgotPassword() {
            WebElement btn = findElementWithFallback(null, forgotPasswordBtnXpath, "Forgot Password?");
            btn.click();
        }

        public void enterInvalidEmail(String email) throws InterruptedException {
            clickClearAndSendKeys(emailInputXpath, email);
        }

        public void clickSendMessage() {
            WebElement btn = findElementWithFallback(null, sendMessageBtnXpath, "SEND MESSAGE");
            btn.click();
        }

        public String getInvalidEmailErrorMessage() {
            String iosErrorName = "We couldn’t find an account with that email. Try a different one or sign up to get started.";
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement errorMsg = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(invalidEmailErrorXpath)));
                String name = errorMsg.getAttribute("name");
                if (name != null && !name.isEmpty()) {
                    return name;
                } else {
                    return errorMsg.getText();
                }
            } catch (Exception e) {
                try {
                    WebElement errorMsg = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(iosErrorName)));
                    String name = errorMsg.getAttribute("name");
                    if (name != null && !name.isEmpty()) {
                        return name;
                    } else {
                        return errorMsg.getText();
                    }
                } catch (Exception ex) {
                    return "No error message displayed: " + ex.getMessage();
                }
            }
        }

        public void enterValidEmail(String email) throws InterruptedException {
            clickClearAndSendKeys(emailInputXpath, email);
            Thread.sleep(5000);
        }

        public void enterOtpCode(String otpCode) throws InterruptedException {
            String otpFieldXpath = "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[1]";
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement otpField = longWait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(otpFieldXpath)));
            otpField.click();
            Thread.sleep(500);
            otpField.clear();
            Thread.sleep(500);
            otpField.sendKeys(otpCode);
            Thread.sleep(500);
        }

        public boolean isVerificationCodePageDisplayed() {
            try {
                WebElement page = findElementWithFallback(null, verificationCodePageXpath, "ENTER VERIFICATION CODE");
                return page.isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public void clickVerifyButton() {
            WebElement btn = findElementWithFallback(null, verifyBtnXpath, "VERIFY");
            btn.click();
        }

        public boolean isFailedToVerifyOtpDialogDisplayed() {
            try {
                WebElement dialog = findElementWithFallback(null, failedToVerifyOtpXpath, "FAILED TO VERIFY OTP");
                return dialog.isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public void clearOtpCode() throws InterruptedException {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            try {
                String field6Xpath = "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[6]";
                WebElement field6 = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(field6Xpath)));
                field6.click();
                Thread.sleep(200);
                field6.clear();
                Thread.sleep(200);
            } catch (Exception e) {}
            for (int i = 5; i >= 1; i--) {
                try {
                    String fieldXpath = "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[" + i + "]";
                    WebElement field = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fieldXpath)));
                    field.click();
                    Thread.sleep(200);
                    field.clear();
                    Thread.sleep(200);
                } catch (Exception e) {}
            }
        }
    private AppiumDriver driver;
    private WebDriverWait wait;

    public ForgotPasswordPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ==================== LOCATORS ====================

    // Step 1: Forgot Password button (iOS)
    private final String forgotPasswordBtnXpath = "//XCUIElementTypeButton[@name=\"Forgot Password?\"]";

    // Step 2 & 5: Email input box (iOS, generic)
    private final String emailInputXpath = "//XCUIElementTypeTextField";

    // Step 3 & 6: Send Message button (iOS)
    private final String sendMessageBtnXpath = "//XCUIElementTypeButton[@name=\"SEND MESSAGE\"]";

    // Step 4: Invalid email error message (iOS)
    // name == "We couldn’t find an account with that email. Try a different one or sign up to get started."
    private final String invalidEmailErrorXpath = "//XCUIElementTypeStaticText[@name=\"We couldn’t find an account with that email. Try a different one or sign up to get started.\"]";

    // Step 7: Enter Verification Code page (iOS)
    private final String verificationCodePageXpath = "//XCUIElementTypeStaticText[@name=\"ENTER VERIFICATION CODE\"]";

    // Step 8: Verify button (iOS)
    private final String verifyBtnXpath = "//XCUIElementTypeButton[@name=\"VERIFY\"]";

    // Step 9: Failed to verify OTP dialog (iOS)
    private final String failedToVerifyOtpXpath = "//XCUIElementTypeStaticText[@name=\"FAILED TO VERIFY OTP\"]";

    // Step 10: OTP error message (iOS)
    private final String otpErrorMessageXpath = "//XCUIElementTypeStaticText[@name=\"Please try again later.\"]";

    // Step 11: OK button (iOS)
    private final String okButtonXpath = "//XCUIElementTypeButton[@name=\"OK\"]";

    // Step 12: Get a new code link (iOS)
    private final String getNewCodeXpath = "//XCUIElementTypeStaticText[@name=\"Get a new code\"]";

    // Step 13: Resend Successful dialog (iOS)
    private final String resendSuccessfulDialogXpath = "//XCUIElementTypeStaticText[@name=\"RESEND SUCCESSFUL\"]";
    private final String resendSuccessMessageXpath = "//XCUIElementTypeStaticText[@name=\"Verification code has been send successfully.\"]";

    // Step 16: Reset Password page (iOS)
    private final String resetPasswordPageXpath = "//XCUIElementTypeStaticText[@name=\"RESET PASSWORD\"]";

    // ...existing code...

    // ==================== HELPER METHODS ====================

    /**
     * Step 10: Get OTP error message
     */
    public String getOtpErrorMessage() {
        String iosOtpErrorName = "Please try again later.";
        try {
            WebElement errorElement = waitForElement(otpErrorMessageXpath, 5);
            // Try to get the 'name' attribute (iOS)
            String name = errorElement.getAttribute("name");
            if (name != null && !name.isEmpty()) {
                return name;
            } else {
                // Fallback: get text
                return errorElement.getText();
            }
        } catch (Exception e) {
            // Fallback: try to find by name directly
            try {
                WebElement errorElement = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(iosOtpErrorName)));
                String name = errorElement.getAttribute("name");
                if (name != null && !name.isEmpty()) {
                    return name;
                } else {
                    return errorElement.getText();
                }
            } catch (Exception ex) {
                return "No OTP error message displayed: " + ex.getMessage();
            }
        }
    }

    /**
     * Step 11: Click OK button
     */
    public void clickOkButton() {
        WebElement btn = findElementWithFallback(null, okButtonXpath, "OK");
        btn.click();
    }

    /**
     * Step 12: Wait until "Get a new code" is found and click it
     */
    public void waitAndClickGetNewCode(int timeoutSeconds) {
        WebElement link = waitForElement(getNewCodeXpath, timeoutSeconds);
        link.click();
    }

    /**
     * Step 13: Verify Resend Successful dialog is displayed
     */
    public boolean isResendSuccessfulDialogDisplayed() {
        try {
            WebElement dialog = findElementWithFallback(null, resendSuccessfulDialogXpath, "RESEND SUCCESSFUL");
            return dialog.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 13: Get resend success message
     */
    public String getResendSuccessMessage() {
        String iosResendSuccessName = "Verification code has been send successfully.";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement messageElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(resendSuccessMessageXpath)));
            String name = messageElement.getAttribute("name");
            if (name != null && !name.isEmpty()) {
                return name;
            } else {
                return messageElement.getText();
            }
        } catch (Exception e) {
            // Fallback: try to find by name directly
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement messageElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(iosResendSuccessName)));
                String name = messageElement.getAttribute("name");
                if (name != null && !name.isEmpty()) {
                    return name;
                } else {
                    return messageElement.getText();
                }
            } catch (Exception ex) {
                return "No resend success message displayed: " + ex.getMessage();
            }
        }
    }

    /**
     * Step 16: Verify Reset Password page is displayed
     */
    public boolean isResetPasswordPageDisplayed() {
        try {
            WebElement page = findElementWithFallback(null, resetPasswordPageXpath, "RESET PASSWORD");
            return page.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for specified seconds
     */
    public void waitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }
}