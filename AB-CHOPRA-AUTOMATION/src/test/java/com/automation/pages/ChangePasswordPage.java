package com.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChangePasswordPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    public ChangePasswordPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators (iOS XPath)
    private final String changePasswordHeadingXpath = "//XCUIElementTypeStaticText[@name='CHANGE PASSWORD']";
    private final String currentPasswordFieldXpath = "//XCUIElementTypeOther[@name='Enter Current Password']";
    private final String newPasswordFieldXpath = "//XCUIElementTypeOther[@name='New Password']";
    private final String confirmPasswordFieldXpath = "//XCUIElementTypeOther[@name='Confirm New Password']";
    private final String changePasswordButtonXpath = "//XCUIElementTypeButton[@name='CHANGE PASSWORD']";

    // Validation Messages (iOS XPath)
    private final String wrongPasswordErrorXpath = "//XCUIElementTypeStaticText[@name='Wrong password. Please enter correct password']";
    private final String weakPasswordErrorXpath = "//XCUIElementTypeStaticText[@name='Use at least 8 characters with uppercase, lowercase, number, and special symbol.']";
    private final String samePasswordErrorXpath = "//XCUIElementTypeStaticText[@name='Current and new password cannot be the same.']";
    private final String successDialogXpath = "//XCUIElementTypeStaticText[@name='PASSWORD CHANGE SUCCESSFUL']";
    private final String successMessageXpath = "//XCUIElementTypeStaticText[@name='Your password was successfully changed.']";
    private final String okButtonXpath = "//XCUIElementTypeButton[@name='OK']";

    /**
     * Verify if Change Password page is displayed
     */
    public boolean isChangePasswordPageDisplayed() {
        try {
            WebElement heading = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(changePasswordHeadingXpath)));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enter Current Password
     */
    public void enterCurrentPassword(String password) {
        enterText(currentPasswordFieldXpath, password);
    }

    /**
     * Enter New Password
     */
    public void enterNewPassword(String password) {
        enterText(newPasswordFieldXpath, password);
    }

    /**
     * Enter Confirm Password
     */
    public void enterConfirmPassword(String password) {
        enterText(confirmPasswordFieldXpath, password);
    }

    /**
     * Helper method to Click -> Clear -> SendKeys -> HideKeyboard
     */
    private void enterText(String xpath, String text) {
        try {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            field.click();
            field.clear();
            field.sendKeys(text);
            hideKeyboard();
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not found: " + xpath, e);
        }
    }

    /**
     * Click Change Password Button
     */
    public void clickChangePasswordButton() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(changePasswordButtonXpath)));
            btn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Change Password button not found", e);
        }
    }

    /**
     * Get Validation Error Message
     * Returns the text of the error if found, else null.
     */
    public String getValidationErrorMessage(String expectedErrorXpath) {
        try {
            WebElement error = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(expectedErrorXpath)));
            // Try both content-desc and name for iOS/Android compatibility
            String msg = error.getAttribute("content-desc");
            if (msg == null || msg.trim().isEmpty()) {
                msg = error.getAttribute("name");
            }
            if (msg == null || msg.trim().isEmpty()) {
                msg = error.getAttribute("value");
            }
            if (msg == null || msg.trim().isEmpty()) {
                msg = error.getAttribute("label");
            }
            return msg;
        } catch (Exception e) {
            // Fallback: search all visible static texts for a match by name, value, or label
            try {
                java.util.List<WebElement> staticTexts = driver.findElements(By.className("XCUIElementTypeStaticText"));
                for (WebElement el : staticTexts) {
                    String name = el.getAttribute("name");
                    String value = el.getAttribute("value");
                    String label = el.getAttribute("label");
                    boolean visible = true;
                    try { visible = el.isDisplayed(); } catch (Exception ex) { visible = true; }
                    if (visible) {
                        if ((name != null && name.trim().equalsIgnoreCase("Wrong password. Please enter correct password.")) ||
                            (value != null && value.trim().equalsIgnoreCase("Wrong password. Please enter correct password.")) ||
                            (label != null && label.trim().equalsIgnoreCase("Wrong password. Please enter correct password."))) {
                            return name != null ? name : (value != null ? value : label);
                        }
                        if ((name != null && name.trim().equalsIgnoreCase("Use at least 8 characters with uppercase, lowercase, number, and special symbol.")) ||
                            (value != null && value.trim().equalsIgnoreCase("Use at least 8 characters with uppercase, lowercase, number, and special symbol.")) ||
                            (label != null && label.trim().equalsIgnoreCase("Use at least 8 characters with uppercase, lowercase, number, and special symbol."))) {
                            return name != null ? name : (value != null ? value : label);
                        }
                        if ((name != null && name.trim().equalsIgnoreCase("Current and new password cannot be the same.")) ||
                            (value != null && value.trim().equalsIgnoreCase("Current and new password cannot be the same.")) ||
                            (label != null && label.trim().equalsIgnoreCase("Current and new password cannot be the same."))) {
                            return name != null ? name : (value != null ? value : label);
                        }
                    }
                }
                return null;
            } catch (Exception ex) {
                return null;
            }
        }
    }

    // Getters for specific error XPaths to use in tests
    public String getWrongPasswordErrorXpath() {
        return wrongPasswordErrorXpath;
    }

    public String getWeakPasswordErrorXpath() {
        return weakPasswordErrorXpath;
    }

    public String getSamePasswordErrorXpath() {
        return samePasswordErrorXpath;
    }

    /**
     * Check if Success Dialog is displayed
     */
    public boolean isSuccessDialogDisplayed() {
        try {
            WebElement dialog = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(successDialogXpath)));
            return dialog.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get Success Message
     */
    public String getSuccessMessage() {
        try {
            // Use the correct iOS XPath for the success message
            String iosXpath = "//XCUIElementTypeStaticText[@name='Your password was successfully changed.']";
            WebElement msg = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(iosXpath)));
            String text = msg.getAttribute("content-desc");
            if (text == null || text.trim().isEmpty()) {
                text = msg.getAttribute("name");
            }
            return text;
        } catch (Exception e) {
            // Fallback: try to find by name if not found by xpath
            try {
                String iosName = "Your password was successfully changed.";
                WebElement msgByName = driver.findElement(By.name(iosName));
                String text = msgByName.getAttribute("name");
                return text;
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Click OK Button
     */
    public void clickOkButton() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(okButtonXpath)));
            btn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("OK button not found", e);
        }
    }

    private void hideKeyboard() {
        try {
            if (driver instanceof io.appium.java_client.ios.IOSDriver) {
                ((io.appium.java_client.ios.IOSDriver) driver).hideKeyboard();
            }
        } catch (Exception ignored) {
        }
    }
}
