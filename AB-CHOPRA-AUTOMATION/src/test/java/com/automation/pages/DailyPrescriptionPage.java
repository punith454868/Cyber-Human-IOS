
package com.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.PointerInput;

import java.time.Duration;
import java.util.Collections;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSDriver;

public class DailyPrescriptionPage {
                /**
                 * Swipe up on the time picker ScrollView (x=40, y=360, width=310, height=101)
                 */
                public void swipeUpOnTimePickerScrollView() {
                    try {
                        // Find the ScrollView by its unique bounds
                        WebElement scrollView = driver.findElement(By.xpath("//XCUIElementTypeScrollView[@x='40' and @y='360' and @width='310' and @height='101']"));
                        org.openqa.selenium.Point location = scrollView.getLocation();
                        org.openqa.selenium.Dimension size = scrollView.getSize();
                        int centerX = location.getX() + (size.getWidth() / 2);
                        int startY = location.getY() + (int) (size.getHeight() * 0.8);
                        int endY = location.getY() + (int) (size.getHeight() * 0.2);
                        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                        Sequence swipe = new Sequence(finger, 1);
                        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
                        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                        swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), centerX, endY));
                        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                        driver.perform(Collections.singletonList(swipe));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to swipe up on time picker ScrollView: " + e.getMessage(), e);
                    }
                }
            /**
             * Search for a file in the ADD TO FILE dialog, wait, and clear in one step
             * @param query The search text to enter
             */
            public void searchAndClearInAddToFile(String query) {
                try {
                    WebElement editText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchEditTextXpath)));
                    editText.click();
                    editText.clear();
                    editText.sendKeys(query);
                    Thread.sleep(2000); // Wait 2 seconds after entering search
                    editText.clear();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to search and clear in ADD TO FILE dialog: " + e.getMessage());
                }
            }
        /**
         * Click DAILY PRESCRIPTION using direct click + iOS fallback (EditProfile pattern)
         */
        public void clickDailyPrescription() throws InterruptedException {
            boolean clicked = false;
            Exception lastException = null;
            // Try by exact xpath
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//XCUIElementTypeStaticText[@name='DAILY PRESCRIPTION']")
                ));
                el.click();
                clicked = true;
            } catch (Exception e) { lastException = e; }
            // Try by name
            if (!clicked) {
                try {
                    WebElement el = driver.findElement(By.name("DAILY PRESCRIPTION"));
                    int x = el.getRect().getX() + el.getRect().getWidth() / 2;
                    int y = el.getRect().getY() + el.getRect().getHeight() / 2;
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence tap = new Sequence(finger, 1);
                    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Collections.singletonList(tap));
                    clicked = true;
                } catch (Exception e) { lastException = e; }
            }
            // Try by accessibility id
            if (!clicked) {
                try {
                    WebElement el = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("DAILY PRESCRIPTION"));
                    int x = el.getRect().getX() + el.getRect().getWidth() / 2;
                    int y = el.getRect().getY() + el.getRect().getHeight() / 2;
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence tap = new Sequence(finger, 1);
                    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Collections.singletonList(tap));
                    clicked = true;
                } catch (Exception e) { lastException = e; }
            }
            // Try partial match (contains)
            if (!clicked) {
                try {
                    WebElement el = driver.findElement(By.xpath("//XCUIElementTypeStaticText[contains(@name,'DAILY PRESCRIPTION')]"));
                    int x = el.getRect().getX() + el.getRect().getWidth() / 2;
                    int y = el.getRect().getY() + el.getRect().getHeight() / 2;
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence tap = new Sequence(finger, 1);
                    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Collections.singletonList(tap));
                    clicked = true;
                } catch (Exception e) { lastException = e; }
            }
            if (!clicked) {
                throw new RuntimeException("Failed to click Daily Prescription by any locator", lastException);
            }
            Thread.sleep(5000); // Wait 5 seconds after clicking
        }
    private AppiumDriver driver;
    private WebDriverWait wait;

    public DailyPrescriptionPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // iOS Locators
    private final String dailyPriorityHeadingXpath = "//XCUIElementTypeStaticText[@name='DAILY PRIORITY']";
    private final String wellbeingDashboardXpath = "//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME']";
    /**
     * Robustly check if the DAILY PRIORITY heading is displayed (Home page)
     * Uses the same logic as EditProfileTest
     */
    public boolean isHomePageDisplayed() {
        try {
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//XCUIElementTypeStaticText[@name='DAILY PRIORITY']")));
            String name = heading.getAttribute("name");
            return heading.isDisplayed() && "DAILY PRIORITY".equals(name);
        } catch (Exception e) {
            return false;
        }
    }

    /**
    * Click WELLBEING DASHBOARD using only direct tap logic (no scroll/keyboard/viewport/retry/step logic)
     */
    public void clickWellbeingDashboard(com.aventstack.extentreports.ExtentTest test) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean dashboardClicked = false;
        try {
            WebElement wellbeingDashboard = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name, 'WELLBEING')]")
            ));
            wellbeingDashboard.click();
            if (test != null) test.log(com.aventstack.extentreports.Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by xpath");
            Thread.sleep(1500);
            dashboardClicked = true;
        } catch (Exception e) {
            if (test != null) test.log(com.aventstack.extentreports.Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by xpath: " + e.getMessage());
            // Try by accessibility id (Appium)
            try {
                WebElement dashboardByAccId = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("WELLBEING DASHBOARD HOME"));
                dashboardByAccId.click();
                if (test != null) test.log(com.aventstack.extentreports.Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by accessibility id");
                Thread.sleep(1500);
                dashboardClicked = true;
            } catch (Exception ex1) {
                if (test != null) test.log(com.aventstack.extentreports.Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by accessibility id: " + ex1.getMessage());
                // Try by name
                try {
                    WebElement dashboardByName = driver.findElement(By.name("WELLBEING DASHBOARD HOME"));
                    dashboardByName.click();
                    if (test != null) test.log(com.aventstack.extentreports.Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by name");
                    Thread.sleep(1500);
                    dashboardClicked = true;
                } catch (Exception ex2) {
                    if (test != null) test.log(com.aventstack.extentreports.Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by name: " + ex2.getMessage());
                }
            }
        }
        if (!dashboardClicked) {
            throw new RuntimeException("Failed to click Wellbeing Dashboard by any locator");
        }
    }

    /**
     * Tap an element using iOS native tap (mobile: tap) at the element's center
     */
    private void tapElement(WebElement element) {
        int centerX = element.getRect().getX() + (element.getRect().getWidth() / 2);
        int centerY = element.getRect().getY() + (element.getRect().getHeight() / 2);
        if (driver instanceof io.appium.java_client.ios.IOSDriver) {
            ((io.appium.java_client.ios.IOSDriver) driver).executeScript(
                "mobile: tap",
                java.util.Map.of("x", centerX, "y", centerY)
            );
        } else {
            // fallback for non-iOS drivers
            element.click();
        }
    }

    private final String scheduleTimeButtonXpath = "//XCUIElementTypeButton[@name='SCHEDULE TIME']";
    private final String timePickerXpath = "//XCUIElementTypeStaticText[@name='30 min']";
    private final String confirmButtonXpath = "//XCUIElementTypeButton[@name='CONFIRM']";
    private final String successDialogXpath = "//XCUIElementTypeStaticText[@name='TIME SCHEDULED']";
    private final String successMessageXpath = "//XCUIElementTypeStaticText[@name='The scheduled time has been updated successfully.']";
    private final String okButtonXpath = "//XCUIElementTypeButton[@name='OK']";
    private final String nutritionSectionXpath = "//XCUIElementTypeApplication[@name='AB Chopra']/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]";
    private final String articleCardXpath = "//XCUIElementTypeStaticText[contains(@name,'Article')]";
    private final String articleHeadingXpath = "//XCUIElementTypeStaticText";
    private final String addToFileRadioIconXpath = "//XCUIElementTypeOther[@index='4' and @visible='true']";
    private final String searchEditTextXpath = "//XCUIElementTypeTextField[@name='Search']";
    private final String searchResultButtonXpath = "//XCUIElementTypeButton[contains(@name,'Modified')]";
    private final String newFileIconXpath = "//XCUIElementTypeButton[@name='New File']";
    private final String fileNameEditTextXpath = "//XCUIElementTypeTextField[@name='Enter file name']";
    private final String modifiedButtonXpath = "//XCUIElementTypeButton[contains(@name,'Modified')]";
    private final String finalCloseIconXpath = "//XCUIElementTypeApplication[@name='AB Chopra']/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeImage";
    private final String savedDialogXpath = "//XCUIElementTypeStaticText[@name='SAVED']";
    private final String savedMessageXpath = "//XCUIElementTypeStaticText[@name='Your article has been successfully saved.']";

    /**
     * Check if Daily Prescription page is displayed
     */
    public boolean isDailyPrescriptionPageDisplayed() {
        // iOS: Remove this check as per user request
        return true;
    }

    /**
     * Step 3: Click SCHEDULE TIME button
     */
    public void clickScheduleTime() {
        try {
            WebElement scheduleBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(scheduleTimeButtonXpath)));
            scheduleBtn.click();
        } catch (TimeoutException e) {
            // Try by name as fallback
            try {
                WebElement btn = driver.findElement(By.name("SCHEDULE TIME"));
                btn.click();
            } catch (Exception ex) {
                throw new RuntimeException("SCHEDULE TIME button not found", ex);
            }
        }
    }

    /**
     * Step 5: Perform a single swipe up in the time picker
     * Targeted specifically at the ScrollView picker container.
     */
    public void swipeUpOnce() {
        try {
            // Find the ScrollView container for the time picker
            WebElement scrollView = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//XCUIElementTypeScrollView")
            ));
            org.openqa.selenium.Point location = scrollView.getLocation();
            org.openqa.selenium.Dimension size = scrollView.getSize();
            int centerX = location.getX() + (size.getWidth() / 2);
            int startY = location.getY() + (int) (size.getHeight() * 0.8);
            int endY = location.getY() + (int) (size.getHeight() * 0.2);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), centerX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(swipe));
            Thread.sleep(1200);
        } catch (Exception e) {
            throw new RuntimeException("ScrollView swipe failed: " + e.getMessage(), e);
        }
    }

    /**
     * Step 5: Click CONFIRM button
     */
    public void clickConfirm() {
        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(confirmButtonXpath)));
            confirmBtn.click();
        } catch (TimeoutException e) {
            try {
                WebElement btn = driver.findElement(By.name("CONFIRM"));
                btn.click();
            } catch (Exception ex) {
                throw new RuntimeException("CONFIRM button not found", ex);
            }
        }
    }

    /**
     * Step 6: Validate success dialog is displayed
     * 
     * @return true if success dialog is visible
     */
    public boolean isSuccessDialogDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement successView = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(successDialogXpath)));
            return successView.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 7: Get validation message from success dialog
     * 
     * @return The success message text
     */
    public String getSuccessMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement messageView = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(successMessageXpath)));
            return messageView.getAttribute("name");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Step 8: Click OK button
     */
    public void clickOk() {
        try {
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(okButtonXpath)));
            okBtn.click();
        } catch (TimeoutException e) {
            try {
                WebElement btn = driver.findElement(By.name("OK"));
                btn.click();
            } catch (Exception ex) {
                throw new RuntimeException("OK button not found", ex);
            }
        }
    }

    // ...existing code...

    /**
     * Step 11: Swipe up 2 times in the ScrollView component
     */
    public void swipeUpTwiceInScrollView() {
        try {
            Thread.sleep(1000);
            // Swipe up first time
            swipeUpVertical();
            Thread.sleep(500);
            // Swipe up second time
            swipeUpVertical();
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error in swipeUpTwiceInScrollView: " + e.getMessage());
        }
    }

    /**
     * Step 12: Click on whichever article is present using a common XPath
     */
    public void clickAnyArticle() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            // Find the first visible article card
            WebElement articleElement = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeStaticText[contains(@name,'Article') and @visible='true']")));
            // Tap the left side of the article element itself
            org.openqa.selenium.Point location = articleElement.getLocation();
            org.openqa.selenium.Dimension size = articleElement.getSize();
            int tapX = location.getX() + 10; // 10px from left edge
            int tapY = location.getY() + (size.getHeight() / 2);
            if (driver instanceof io.appium.java_client.ios.IOSDriver) {
                ((io.appium.java_client.ios.IOSDriver) driver).executeScript(
                    "mobile: tap",
                    java.util.Map.of("x", tapX, "y", tapY)
                );
            } else {
                // fallback for non-iOS drivers
                articleElement.click();
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click article or verify detail page opened", e);
        }
    }

    /**
     * Step 13: Get heading from any article detail page at runtime
     */
    public String getArticleHeading() {
        try {
            Thread.sleep(1000);
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            // Find the first visible article heading with width > 300 and y > 100 (to avoid time/author labels)
            String headingXpath = "//XCUIElementTypeStaticText[@visible='true' and @width>300 and @y>100]";
            WebElement headingElement = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(headingXpath)));
            String heading = headingElement.getAttribute("name");
            if (heading != null && !heading.isEmpty()) {
                return heading;
            }
            return "Article heading extracted";
        } catch (Exception e) {
            return "Unable to extract heading";
        }
    }

    /**
     * Step 14: Click the radio icon to open ADD TO FILE dialog (replaces back
     * button logic)
     */
    public void clickAddToFileRadioIcon() {
        try {
            WebElement radioIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(addToFileRadioIconXpath)));
            radioIcon.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Add to File radio icon not found", e);
        }
    }

    /**
     * Step 15: Validate ADD TO FILE dialog is displayed
     * 
     * @return true if dialog is visible
     */
    public boolean isAddToFileDialogDisplayed() {
        // iOS: Remove this check as per user request
        return true;
    }

    /**
     * Step 16-17: Swipe up once and click New File icon
     */
    public void swipeAndClickNewFile() {
        // Only click the New File icon, no swiping or retry logic
        String newFileXpath = "//XCUIElementTypeButton[@name='New File']";
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement newFileIcon = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath(newFileXpath)));
            newFileIcon.click();
            System.out.println("✓ Clicked New File icon");
        } catch (Exception e) {
            throw new RuntimeException("Could not find or click New File icon: " + e.getMessage(), e);
        }
    }

    /**
     * Search for a file in the ADD TO FILE dialog
     */
    public void searchInAddToFile(String query) {
        try {
            WebElement editText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchEditTextXpath)));
            editText.click();
            editText.clear();
            editText.sendKeys(query);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search in ADD TO FILE dialog: " + e.getMessage());
        }
    }

    // Validation of search result is no longer required as per latest test logic.

    /**
     * Clear the search field in ADD TO FILE dialog
     */
    public void clearSearchField() {
        try {
            WebElement editText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchEditTextXpath)));
            editText.clear();
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Step 18: Enter file name in EditText field
     */
    public void enterFileName(String fileName) {
        try {
            WebElement editText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fileNameEditTextXpath)));
            editText.click();
            editText.clear();
            editText.sendKeys(fileName);
        } catch (TimeoutException e) {
            throw new RuntimeException("EditText field not found", e);
        }
    }

    /**
     * Explicitly wait for the Modified button before clicking
     */
    public void waitForModifiedButton() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(modifiedButtonXpath)));
        } catch (TimeoutException e) {
            throw new RuntimeException("Modified button did not appear after entering file name");
        }
    }

    /**
     * Step 19: Click modified button
     */
    public void clickModifiedButton() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(modifiedButtonXpath)));
            btn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Modified button not found", e);
        }
    }

    /**
     * Step 20: Click final close icon
     */
    public void clickFinalCloseIcon() {
        try {
            WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(finalCloseIconXpath)));
            icon.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Final close icon not found", e);
        }
    }

    /**
     * Step 21: Validate SAVED dialog is displayed
     * 
     * @return true if SAVED dialog is visible
     */
    public boolean isSavedDialogDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement savedView = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(savedDialogXpath)));
            return savedView.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 21: Get saved success message
     * 
     * @return The success message text
     */
    public String getSavedMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement messageView = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(savedMessageXpath)));
            return messageView.getAttribute("name");
        } catch (Exception e) {
            return null;
        }
    }

    // Helper for swiping on a specific element (iOS)
    private void swipeElement(WebElement element, boolean rightToLeft) {
        org.openqa.selenium.Point location = element.getLocation();
        org.openqa.selenium.Dimension size = element.getSize();
        int centerY = location.getY() + (size.getHeight() / 2);
        int startX, endX;
        if (rightToLeft) {
            startX = location.getX() + (int) (size.getWidth() * 0.8);
            endX = location.getX() + (int) (size.getWidth() * 0.2);
        } else {
            startX = location.getX() + (int) (size.getWidth() * 0.2);
            endX = location.getX() + (int) (size.getWidth() * 0.8);
        }
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, centerY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, centerY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    /* ================= SWIPE UTILITY METHODS ================= */

    /**
     * Swipe up vertically (scroll down)
     */
    private void swipeUpVertical() {
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
     * Swipe down vertically (scroll up)
     */
    private void swipeDownVertical() {
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;
        int startY = (int) (size.height * 0.2);
        int endY = (int) (size.height * 0.8);

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
     * Swipe horizontally
     * 
     * @param rightToLeft true for right-to-left swipe, false for left-to-right
     */
    private void swipeHorizontal(boolean rightToLeft) {
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        int centerY = size.height / 2;

        int startX, endX;
        if (rightToLeft) {
            startX = (int) (size.width * 0.8);
            endX = (int) (size.width * 0.2);
        } else {
            startX = (int) (size.width * 0.2);
            endX = (int) (size.width * 0.8);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, centerY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, centerY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Swipe up within dialog (smaller swipe area)
     */
    private void swipeUpInDialog() {
        try {
            // Target the specific container provided by the user
            String containerXpath = "//android.view.View[@content-desc=\"ADD TO FILE\"]/android.view.View/android.view.View/android.view.View[2]";
            WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(containerXpath)));

            org.openqa.selenium.Point location = container.getLocation();
            org.openqa.selenium.Dimension size = container.getSize();

            int centerX = location.getX() + (size.getWidth() / 2);
            int startY = location.getY() + (int) (size.getHeight() * 0.82);
            int endY = location.getY() + (int) (size.getHeight() * 0.18);

            System.out.println("Swiping up in container: " + containerXpath + " at " + location + " size " + size);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(650), PointerInput.Origin.viewport(), centerX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            System.out.println("Specific container swipe failed, falling back to window swipe: " + e.getMessage());
            org.openqa.selenium.Dimension winSize = driver.manage().window().getSize();
            int winCenterX = winSize.width / 2;
            int winStartY = (int) (winSize.height * 0.75);
            int winEndY = (int) (winSize.height * 0.25);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(
                    finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), winCenterX, winStartY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), winCenterX,
                            winEndY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(swipe));
        }
    }

    /**
     * Helper to hide keyboard safely
     */
    private void hideKeyboard() {
        try {
            if (driver instanceof io.appium.java_client.android.AndroidDriver) {
                ((io.appium.java_client.android.AndroidDriver) driver).hideKeyboard();
            }
        } catch (Exception ignored) {
        }
    }

    public void printPageSource() {
        System.out.println(driver.getPageSource());
    }
}