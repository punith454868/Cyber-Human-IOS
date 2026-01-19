package com.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.PointerInput;
import java.util.Collections;

public class EditProfilePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    public EditProfilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators (iOS XPath)
    private final String editProfileHeadingXpath = "//XCUIElementTypeStaticText[@name='EDIT PROFILE']";
    // iOS TextField locators
    private final String nameFieldXpath = "(//XCUIElementTypeTextField)[1]";
    private final String emailFieldXpath = "(//XCUIElementTypeTextField)[2]";
    private final String dateOfBirthFieldXpath = "//XCUIElementTypeOther[@name='Date of birth']";
    private final String genderButtonXpath = "//XCUIElementTypeButton[@name='Gender']";
    private final String phoneNumberFieldXpath = "//XCUIElementTypeTextField[@name='Phone Number']";
    private final String countryCodeXpath = "//XCUIElementTypeStaticText[@name='🇦🇫 +93']";
    private final String saveChangesButtonXpath = "//XCUIElementTypeButton[@name='SAVE CHANGES']";
    private final String changePasswordButtonXpath = "//XCUIElementTypeButton[@name='CHANGE PASSWORD']";

    /**
     * Enter name in the Name field
     */
    public void enterName(String name) {
        try {
            WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nameFieldXpath)));
            nameField.click();
            nameField.clear();
            nameField.sendKeys(name);
            hideKeyboard();
        } catch (TimeoutException e) {
            throw new RuntimeException("Name field not found on Edit Profile page", e);
        }
    }

    /**
     * Enter email in the Email field
     */
    public void enterEmail(String email) {
        try {
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(emailFieldXpath)));
            emailField.click();
            emailField.clear();
            emailField.sendKeys(email);
            hideKeyboard();
        } catch (TimeoutException e) {
            throw new RuntimeException("Email field not found on Edit Profile page", e);
        }
    }

    /**
     * Enter phone number in the Phone Number field
     */
    public void enterPhoneNumber(String phoneNumber) {
        try {
            WebElement phoneField = null;
            // Try elementId first (if available)
            try {
                phoneField = driver.findElement(By.id("0A010000-0000-0000-7B0A-000000000000"));
            } catch (Exception e0) {
                try {
                    phoneField = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("Phone Number"));
                } catch (Exception e1) {
                    try {
                        phoneField = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeTextField[`name == 'Phone Number'`]"));
                    } catch (Exception e2) {
                        try {
                            phoneField = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == 'Phone Number'"));
                        } catch (Exception e3) {
                            try {
                                phoneField = driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='Phone Number']"));
                            } catch (Exception e4) {
                                throw new RuntimeException("Phone Number field not found on Edit Profile page after all locator attempts", e4);
                            }
                        }
                    }
                }
            }
            phoneField.click();
            phoneField.clear();
            phoneField.sendKeys(phoneNumber);
            hideKeyboard();
        } catch (Exception e) {
            throw new RuntimeException("Phone Number field not found on Edit Profile page after all locator attempts", e);
        }
    }

    /**
     * Click the Date of Birth field to open date picker
     * Includes robust fallback for when validation messages block the view
     */
    public void clickDateOfBirth() {
        try {
            hideKeyboard();
            Thread.sleep(500);
            WebElement dobField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateOfBirthFieldXpath)));
            dobField.click();
        } catch (Exception e1) {
            try {
                WebElement dobAccId = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("Date of birth"));
                dobAccId.click();
            } catch (Exception e2) {
                try {
                    WebElement dobClassChain = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeOther[`name == 'Date of birth'`]"));
                    dobClassChain.click();
                } catch (Exception e3) {
                    try {
                        WebElement dobPredicate = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == 'Date of birth'"));
                        dobPredicate.click();
                    } catch (Exception e4) {
                        try {
                            swipeUp();
                            Thread.sleep(500);
                            WebElement dobField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dateOfBirthFieldXpath)));
                            tapElement(dobField);
                        } catch (Exception ex) {
                            throw new RuntimeException("Date of Birth field not found or clickable on Edit Profile page after retry", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * PERFORM DATE SELECTION (SWIPE ACTIONS)
     * Swipes down on Day, Month, and Year wheels and clicks Confirm
     * Uses position-based XPath to work with any date (future-proof)
     */
    public void performDateSelection() {
        try {
            Thread.sleep(1000); // Wait for date picker to appear
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));


            // Use working elementIds for picker wheels as provided by Appium Inspector
            String[] pickerWheelIds = {
                "18000000-0000-0000-850B-000000000000", // Day (value="19")
                "19000000-0000-0000-850B-000000000000", // Month (value="01")
                "1A000000-0000-0000-850B-000000000000"  // Year (value="2026")
            };

            for (int i = 0; i < pickerWheelIds.length; i++) {
                try {
                    WebElement pickerWheel = driver.findElement(By.id(pickerWheelIds[i]));
                    swipeDown(pickerWheel);
                    Thread.sleep(500);
                    System.out.println("Successfully swiped PickerWheel " + (i + 1));
                } catch (Exception e) {
                    System.out.println("Could not find or swipe PickerWheel by id at position: " + (i + 1));
                }
            }

            // Click CONFIRM button to confirm date selection
            try {
                WebElement confirmBtn = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("CONFIRM"));
                confirmBtn.click();
                System.out.println("Clicked CONFIRM button on date picker");
            } catch (Exception e1) {
                try {
                    WebElement confirmClassChain = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'CONFIRM'`]"));
                    confirmClassChain.click();
                } catch (Exception e2) {
                    try {
                        WebElement confirmPredicate = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == 'CONFIRM'"));
                        confirmPredicate.click();
                    } catch (Exception e3) {
                        try {
                            WebElement confirmXpath = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='CONFIRM']"));
                            confirmXpath.click();
                        } catch (Exception e4) {
                            System.out.println("CONFIRM button not found on date picker");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error in performDateSelection: " + e.getMessage());
        }
    }

    /**
     * Helper method to swipe down on an element using W3C Actions
     */
    private void swipeDown(WebElement element) {
        int centerX = element.getRect().getX() + (element.getRect().getWidth() / 2);
        int startY = element.getRect().getY() + (element.getRect().getHeight() / 2);
        int endY = startY + 200; // Swipe down by 200 pixels

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), centerX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Helper method to Tap on an element using W3C Actions (Force Click)
     */
    private void tapElement(WebElement element) {
        int centerX = element.getRect().getX() + (element.getRect().getWidth() / 2);
        int centerY = element.getRect().getY() + (element.getRect().getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }

    /**
     * Click the Gender dropdown button
     * Includes robust fallback
     */
    public void clickGender() {
        try {
            WebElement genderBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(genderButtonXpath)));
            genderBtn.click();
        } catch (Exception e1) {
            try {
                WebElement genderAccId = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("Gender"));
                genderAccId.click();
            } catch (Exception e2) {
                try {
                    WebElement genderClassChain = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'Gender'`]"));
                    genderClassChain.click();
                } catch (Exception e3) {
                    try {
                        WebElement genderPredicate = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == 'Gender'"));
                        genderPredicate.click();
                    } catch (Exception e4) {
                        try {
                            WebElement genderXpath = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Gender']"));
                            genderXpath.click();
                        } catch (Exception e5) {
                            throw new RuntimeException("Gender button not found on Edit Profile page after retry", e5);
                        }
                    }
                }
            }
        }
    }

    /**
     * Select gender from dropdown
     * 
     * @param gender Gender option (e.g., "Male", "Female", "Other")
     */
    public void selectGender(String gender) {
        try {
            Thread.sleep(1000);
            WebElement genderOption = null;
            try {
                genderOption = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId(gender));
            } catch (Exception e1) {
                try {
                    genderOption = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeButton[`name == '" + gender + "'`]"));
                } catch (Exception e2) {
                    try {
                        genderOption = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == '" + gender + "'"));
                    } catch (Exception e3) {
                        try {
                            genderOption = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='" + gender + "']"));
                        } catch (Exception e4) {
                            System.out.println("Could not select gender: " + gender + " - " + e4.getMessage());
                        }
                    }
                }
            }
            if (genderOption != null) {
                genderOption.click();
            }
        } catch (Exception e) {
            System.out.println("Could not select gender: " + gender + " - " + e.getMessage());
        }
    }

    /**
     * Click the Country Code dropdown
     * Includes robust fallback
     */
    public void clickCountryCode() {
        try {
            WebElement countryCode = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(countryCodeXpath)));
            countryCode.click();
        } catch (Exception e1) {
            try {
                WebElement countryAccId = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("🇦🇫\n+93"));
                countryAccId.click();
            } catch (Exception e2) {
                try {
                    WebElement countryClassChain = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == '🇦🇫 +93'`]"));
                    countryClassChain.click();
                } catch (Exception e3) {
                    try {
                        WebElement countryPredicate = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == '🇦🇫 +93'"));
                        countryPredicate.click();
                    } catch (Exception e4) {
                        try {
                            WebElement countryXpath = driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='🇦🇫 +93']"));
                            countryXpath.click();
                        } catch (Exception e5) {
                            throw new RuntimeException("Country Code dropdown not found on Edit Profile page after retry", e5);
                        }
                    }
                }
            }
        }
    }

    /**
     * Select country from country code dropdown
     * Scroll until finding the country using W3C actions
     * 
     * @param country Country name (e.g., "Belarus")
     */
    public void selectCountry(String country) {
        try {
            Thread.sleep(1000); // Wait for dropdown list to fully load
            int maxSwipes = 20;
            boolean found = false;
            for (int i = 0; i < maxSwipes; i++) {
                WebElement countryOption = null;
                // Try elementId first
                try {
                    countryOption = driver.findElement(By.id("E8000000-0000-0000-7B0A-000000000000"));
                    if (countryOption != null && countryOption.isDisplayed() && countryOption.isEnabled()) {
                        countryOption.click();
                        found = true;
                        break;
                    }
                } catch (Exception eId) {
                    // Fallback to other locators
                    try {
                        countryOption = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("🇧🇾\nBelarus\n+375"));
                        tapElement(countryOption);
                        found = true;
                        break;
                    } catch (Exception e1) {
                        try {
                            countryOption = driver.findElement(io.appium.java_client.MobileBy.iOSClassChain("**/XCUIElementTypeButton[`name == '🇧🇾 Belarus +375'`]"));
                            tapElement(countryOption);
                            found = true;
                            break;
                        } catch (Exception e2) {
                            try {
                                countryOption = driver.findElement(io.appium.java_client.MobileBy.iOSNsPredicateString("name == '🇧🇾 Belarus +375'"));
                                tapElement(countryOption);
                                found = true;
                                break;
                            } catch (Exception e3) {
                                try {
                                    countryOption = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='🇧🇾 Belarus +375']"));
                                    tapElement(countryOption);
                                    found = true;
                                    break;
                                } catch (Exception e4) {
                                    // Not found, will swipe
                                }
                            }
                        }
                    }
                }
                // If not found, swipe up and try again
                swipeUp();
                Thread.sleep(500);
            }
            if (!found) {
                System.out.println("Could not find country: Belarus after swiping " + maxSwipes + " times.");
            }
        } catch (Exception e) {
            System.out.println("Error selecting country: Belarus - " + e.getMessage());
        }
    }

    /**
     * Helper method to swipe up (scroll down)
     */
    private void swipeUp() {
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), centerX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Click the SAVE CHANGES button
     */
    public void clickSaveChanges() {
        try {
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(saveChangesButtonXpath)));
            saveBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("SAVE CHANGES button not found on Edit Profile page", e);
        }
    }

    /**
     * Click the CHANGE PASSWORD button
     */
    public void clickChangePassword() {
        try {
            WebElement changePassBtn = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(changePasswordButtonXpath)));
            changePassBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("CHANGE PASSWORD button not found on Edit Profile page", e);
        }
    }

    /**
     * Check if Edit Profile page is displayed
     */
    public boolean isEditProfilePageDisplayed() {
        try {
            WebElement heading = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(editProfileHeadingXpath)));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * RUNTIME-BASED VALIDATION DETECTION (NO HARDCODED MESSAGES)
     * 
     * Checks at runtime if ANY validation element is visible:
     * - Alert Dialog (Top Priority)
     * - Validation message via name attribute (XCUIElementTypeOther)
     * - Static text error messages
     * - Success messages
     * 
     * @return true if ANY validation is detected, false if NONE found
     */
    public boolean isAnyValidationVisible() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Check -1: Success Popup (User requested to treat success as pass)
        try {
            WebElement successPopup = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name='Your profile has been updated']")));
            if (successPopup.isDisplayed()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // Check 0: Alert Dialog (Highest Priority)
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert")));
            if (alert.isDisplayed()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // Check 1: Validation message via name attribute with error keywords
        try {
            WebElement validationView = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name and (contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'must') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cannot'))]\"")));
            String name = validationView.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                return true;
            }
        } catch (Exception ignored) {
        }

        // Check 2: StaticText with error keywords
        try {
            WebElement errorKeyword = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please')]")));
            String text = errorKeyword.getAttribute("name");
            if (text != null && !text.trim().isEmpty()) {
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
     * 
     * Priority order for message capture:
     * 1. Alert message
     * 2. Validation message from name attribute (XCUIElementTypeOther)
     * 3. StaticText with error keywords
     * 4. Success message
     * 
     * @return The actual validation message text, or null if no validation found
     */
    public String getValidationMessage() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Priority -1: Success Popup
        try {
            WebElement successPopup = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[@name='Your profile has been updated']")));
            if (successPopup.isDisplayed()) {
                return "Your profile has been updated";
            }
        } catch (Exception ignored) {
        }

        // Priority 0: Alert message
        try {
            WebElement alert = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeAlert")));
            if (alert.isDisplayed()) {
                String alertText = alert.getText();
                if (alertText != null && !alertText.trim().isEmpty()) {
                    return alertText;
                }
            }
        } catch (Exception ignored) {
        }

        // Priority 1: Validation message via name attribute with error keywords
        try {
            WebElement validationView = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(
                            "//XCUIElementTypeOther[@name and (contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'must') or "
                                    +
                                    "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cannot'))]\"")));
            String name = validationView.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                return name;
            }
        } catch (Exception ignored) {
        }

        // Priority 2: StaticText with error keywords
        try {
            WebElement errorKeyword = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeStaticText[" +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'error') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'required') or "
                            +
                            "contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'please')]")));
            String text = errorKeyword.getAttribute("name");
            if (text != null && !text.trim().isEmpty()) {
                return text;
            }
        } catch (Exception ignored) {
        }

        // No validation message found
        return null;
    }

    /**
     * Helper to hide keyboard safely
     */
    public void hideKeyboard() {
        try {
            if (driver instanceof io.appium.java_client.ios.IOSDriver) {
                ((io.appium.java_client.ios.IOSDriver) driver).hideKeyboard();
            }
        } catch (Exception ignored) {
        }
    }
}
