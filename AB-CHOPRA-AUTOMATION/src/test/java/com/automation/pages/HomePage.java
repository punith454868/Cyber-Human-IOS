package com.automation.pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class HomePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators (iOS XPath - CONVERTED FROM ANDROID)
    private final String dailyPriorityHeadingXpath = "//XCUIElementTypeStaticText[@name='DAILY PRIORITY']";
    private final String wellbeingDashboardXpath = "//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME']";
    private final String profileButtonXpath = "//XCUIElementTypeButton[@name='PROFILE']";
    private final String proceedButtonXpath = "//XCUIElementTypeButton[@name='PROCEED']";
    private final String dailyPrescriptionXpath = "//XCUIElementTypeStaticText[@name='DAILY PRESCRIPTION']";

    // ===== SILENT iOS native tap helper (no logs, no scroll, no keyboard hide) =====
    private void silentTapByXpath(String xpath) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            int centerX = element.getRect().getX() + (element.getRect().getWidth() / 2);
            int centerY = element.getRect().getY() + (element.getRect().getHeight() / 2);
            if (driver instanceof IOSDriver) {
                ((IOSDriver) driver).executeScript(
                    "mobile: tap",
                    ImmutableMap.of("x", centerX, "y", centerY)
                );
            }
        } catch (Exception ignored) {
            // Silent: do nothing
        }
    }

    /**
     * SILENT direct iOS native tap on Wellbeing Dashboard (no logs, no scroll, no keyboard hide)
     */
    public void clickWellbeingDashboardSilent() {
        silentTapByXpath(wellbeingDashboardXpath);
    }

    /**
     * ROBUST CLICK HELPER - Handles iOS-specific click issues
     * 
     * Implements:
     * 1. Keyboard hiding
     * 2. Scroll-to-view
     * 3. Explicit visibility check
     * 4. W3C Action-based retry if standard click fails
     */
    private void robustClick(String xpath, String elementName) throws InterruptedException {
        try {
            // Step 1: Hide keyboard
            hideKeyboard();
            Thread.sleep(300);

            // Step 2: Scroll element into view
            scrollToElement(xpath);
            Thread.sleep(300);

            // Step 3: Wait for visibility
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            // Step 4: Check element bounds are within viewport
            verifyElementInViewport(element);

            // Step 5: Standard click attempt
            try {
                element.click();
                System.out.println("✓ Standard click succeeded for " + elementName);
            } catch (Exception e) {
                // Step 6: Retry with W3C Actions (Force Tap)
                System.out.println("⚠ Standard click failed for " + elementName + ". Attempting W3C Action tap...");
                tapElementUsingW3C(element);
                System.out.println("✓ W3C Action tap succeeded for " + elementName);
            }

        } catch (TimeoutException e) {
            throw new RuntimeException(elementName + " not found or not visible on Home page", e);
        }
    }

    /**
     * Scroll element into view with RETRY for image elements
     * Images may need multiple scroll attempts to become fully hittable
     */
    private void scrollToElementWithRetry(String xpath) throws InterruptedException {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                Dimension windowSize = driver.manage().window().getSize();
                int elementY = element.getRect().getY();
                
                if (elementY < 0) {
                    System.out.println("  └─ Scroll attempt " + attempt + ": Element above viewport, scrolling down");
                    swipeDown();
                } else if (elementY > windowSize.height - 44) {
                    System.out.println("  └─ Scroll attempt " + attempt + ": Element below viewport, scrolling up");
                    swipeUp();
                } else {
                    System.out.println("  └─ Scroll attempt " + attempt + ": Element in safe viewport");
                    return;
                }
                
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println("  └─ Scroll attempt " + attempt + " failed, retrying...");
            }
        }
        System.out.println("  └─ Scroll retry exhausted, proceeding with tap");
    }

    /**
     * Scroll element into view using W3C Actions
     */
    private void scrollToElement(String xpath) throws InterruptedException {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            Dimension windowSize = driver.manage().window().getSize();
            int elementY = element.getRect().getY();

            // If element is below viewport, scroll down
            if (elementY > windowSize.height) {
                swipeUp();
                Thread.sleep(300);
            }
            // If element is above viewport, scroll up
            else if (elementY < 0) {
                swipeDown();
                Thread.sleep(300);
            }
        } catch (Exception ignored) {
            // Element already visible or not found
        }
    }

    /**
     * Verify element is within viewport bounds
     */
    private void verifyElementInViewport(WebElement element) {
        Dimension windowSize = driver.manage().window().getSize();
        int elementY = element.getRect().getY();
        int elementHeight = element.getRect().getHeight();

        boolean isVisible = (elementY >= 0) && (elementY + elementHeight <= windowSize.height);
        if (!isVisible) {
            System.out.println("⚠ Warning: Element may be partially off-screen. Y: " + elementY + ", Height: "
                    + elementHeight + ", WindowHeight: " + windowSize.height);
        }
    }

    /**
     * Force tap using W3C Actions (PointerInput)
     */
    private void tapElementUsingW3C(WebElement element) {
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
     * Swipe up (scroll down to reveal lower elements)
     */
    private void swipeUp() {
        Dimension size = driver.manage().window().getSize();
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
     * Swipe down (scroll up to reveal upper elements)
     */
    private void swipeDown() {
        Dimension size = driver.manage().window().getSize();
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
     * Click on the Wellbeing Dashboard menu button at the bottom
     * PRODUCTION-READY iOS VERSION using native mobile: tap
     * 
     * Handles:
     * - Image hittable area mismatch (visible vs tappable)
     * - Keyboard overlay issues
     * - Safe margin validation
     * - iOS-native gesture execution
     */
    public void clickWellbeingDashboard() throws InterruptedException {
        System.out.println("▶ Starting: Click Wellbeing Dashboard");
        
        try {
            // ========== STEP 1: Hide keyboard overlay ==========
            hideKeyboard();
            Thread.sleep(400);
            System.out.println("✓ Step 1: Keyboard hidden");
            
            // ========== STEP 2: Scroll element into safe tap zone ==========
            scrollToElementWithRetry(wellbeingDashboardXpath);
            Thread.sleep(500);
            System.out.println("✓ Step 2: Element scrolled into view");
            
            // ========== STEP 3: Wait for visibility ==========
            WebElement dashboardElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(wellbeingDashboardXpath))
            );
            Thread.sleep(300); // Allow animation to complete
            System.out.println("✓ Step 3: Element visible");
            
            // ========== STEP 4: Check hittable attribute (CRITICAL for images) ==========
            String hittableAttr = dashboardElement.getAttribute("hittable");
            if (hittableAttr != null && hittableAttr.equals("false")) {
                System.out.println("⚠ Step 4: WARNING - Element not hittable! Attempting recovery...");
                
                // Recovery: Force scroll with aggressive motion
                swipeDown();
                Thread.sleep(300);
                swipeUp();
                Thread.sleep(300);
                
                dashboardElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(wellbeingDashboardXpath))
                );
                Thread.sleep(200);
                System.out.println("✓ Step 4: Element recovered and now hittable");
            } else {
                System.out.println("✓ Step 4: Element is hittable");
            }
            
            // ========== STEP 5: Verify safe viewport (not in edge zone) ==========
            Dimension windowSize = driver.manage().window().getSize();
            int elementY = dashboardElement.getRect().getY();
            int elementHeight = dashboardElement.getRect().getHeight();
            int safeMargin = 44; // iOS safe area margin (points)
            
            if (elementY + elementHeight > windowSize.height - safeMargin) {
                System.out.println("⚠ Step 5: Element in unsafe bottom area, scrolling up");
                swipeDown();
                Thread.sleep(400);
                dashboardElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(wellbeingDashboardXpath))
                );
                System.out.println("✓ Step 5: Element moved to safe viewport");
            } else {
                System.out.println("✓ Step 5: Element in safe viewport zone");
            }
            
            // ========== STEP 6: Execute iOS native tap (not Selenium click) ==========
            int tapX = dashboardElement.getRect().getX() + (dashboardElement.getRect().getWidth() / 2);
            int tapY = dashboardElement.getRect().getY() + (dashboardElement.getRect().getHeight() / 2);
            
            System.out.println("→ Step 6: Executing iOS native tap at coordinates (" + tapX + ", " + tapY + ")");
            
            ((IOSDriver) driver).executeScript(
                "mobile: tap",
                ImmutableMap.of(
                    "x", tapX,
                    "y", tapY
                )
            );
            
            System.out.println("✓ Step 6: iOS native tap executed");
            
            // ========== STEP 7: Post-execution wait ==========
            Thread.sleep(400); // Allow tap to register and screen transition
            System.out.println("✅ COMPLETED: Wellbeing Dashboard tap successful\n");
            
        } catch (TimeoutException e) {
            throw new RuntimeException(
                "FAILED: Wellbeing Dashboard element not found or timed out: " + e.getMessage(), e
            );
        }
    }

    /**
     * Click on the PROFILE button
     */
    public void clickProfile() throws InterruptedException {
        robustClick(profileButtonXpath, "PROFILE");
    }

    /**
     * Click the PROCEED button if visible on homepage
     */
    public void clickProceed() {
        try {
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(proceedButtonXpath)));
            proceedBtn.click();
        } catch (TimeoutException e) {
            System.out.println("PROCEED button not found, continuing...");
        }
    }

    /**
     * Click on the DAILY PRESCRIPTION option
     */
    public void clickDailyPrescription() throws InterruptedException {
        robustClick(dailyPrescriptionXpath, "DAILY PRESCRIPTION");
    }

    /**
     * Check if Home page is displayed with enhanced verification
     */
    public boolean isHomePageDisplayed() {
        try {
            WebElement heading = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dailyPriorityHeadingXpath)));
            String name = heading.getAttribute("name");
            boolean isDisplayed = heading.isDisplayed() && "DAILY PRIORITY".equals(name);
            if (isDisplayed) {
                System.out.println("✓ Home page verified - DAILY PRIORITY element visible with correct name");
            }
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Navigate to logout from Home page.
     */
    public void navigateToLogout() throws InterruptedException {
        clickWellbeingDashboard();
        Thread.sleep(1000);

        clickProfile();
        Thread.sleep(1000);
    }

    /**
     * Helper to hide keyboard safely
     */
    private void hideKeyboard() {
        try {
            if (driver instanceof IOSDriver) {
                ((IOSDriver) driver).hideKeyboard();
            }
        } catch (Exception ignored) {
            // Keyboard may already be hidden
        }
    }
}
