package com.automation.pages;

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

public class ProfilePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    public ProfilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ==================== MAIN PROFILE LOCATORS (iOS XPath) ====================
    private final String profileHeadingXpath = "//XCUIElementTypeStaticText[@name='PROFILE']";
    private final String accountButtonXpath = "//XCUIElementTypeButton[@name='ACCOUNT']";
    private final String myOrdersXpath = "//XCUIElementTypeButton[@name='MY ORDERS']";
    private final String helpSupportXpath = "//XCUIElementTypeButton[@name='HELP & SUPPORT']";
    private final String legalInformationXpath = "//XCUIElementTypeButton[@name='LEGAL INFORMATION']";
    private final String logoutXpath = "//XCUIElementTypeButton[@name='LOG OUT']";

    // ==================== MY ORDERS LOCATORS ====================
    private final String trackOrderXpath = "//XCUIElementTypeButton[@name='TRACK ORDER']";
    private final String sendSampleXpath = "//XCUIElementTypeButton[@name='SEND SAMPLE']";

    // ==================== HELP & SUPPORT LOCATORS ====================
    private final String gettingStartedXpath = "//XCUIElementTypeButton[@name='GETTING STARTED']";
    private final String dnaKitXpath = "//XCUIElementTypeButton[@name='DNA KIT']";

    // ==================== LEGAL INFORMATION LOCATORS ====================
    private final String tcXpath = "//XCUIElementTypeButton[@name='T&C']";
    private final String privacyPolicyXpath = "//XCUIElementTypeButton[@name='PRIVACY POLICY']";

    // ==================== LOGOUT LOCATORS ====================
    private final String noButtonXpath = "//XCUIElementTypeButton[@name='NO']";
    private final String yesButtonXpath = "//XCUIElementTypeButton[@name='YES']";

    /**
     * ROBUST CLICK HELPER - Handles iOS-specific click issues
     * 
     * Implements:
     * 1. Keyboard hiding
     * 2. Scroll-to-view
     * 3. Explicit visibility check
     * 4. W3C Action-based retry if standard click fails
     */
    public void robustClick(String xpath, String elementName) throws InterruptedException {
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
            throw new RuntimeException(elementName + " not found or not visible on Profile page", e);
        }
    }

    /**
     * Scroll element into view using W3C Actions
     */
    private void scrollToElement(String xpath) throws InterruptedException {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            Dimension windowSize = driver.manage().window().getSize();
            int elementY = element.getRect().getY();

            if (elementY > windowSize.height) {
                swipeUp();
                Thread.sleep(300);
            } else if (elementY < 0) {
                swipeDown();
                Thread.sleep(300);
            }
        } catch (Exception ignored) {
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
     * Click the ACCOUNT button to navigate to Edit Profile page
     */
    public void clickAccount() throws InterruptedException {
        robustClick(accountButtonXpath, "ACCOUNT");
    }

    /**
     * Click the MY ORDERS option
     */
    public void clickMyOrders() throws InterruptedException {
        robustClick(myOrdersXpath, "MY ORDERS");
    }

    /**
     * Click the HELP & SUPPORT option
     */
    public void clickHelpSupport() throws InterruptedException {
        robustClick(helpSupportXpath, "HELP & SUPPORT");
    }

    /**
     * Click the LEGAL INFORMATION option
     */
    public void clickLegalInformation() throws InterruptedException {
        robustClick(legalInformationXpath, "LEGAL INFORMATION");
    }

    /**
     * Click the LOG OUT option
     */
    public void clickLogout() throws InterruptedException {
        robustClick(logoutXpath, "LOG OUT");
    }

    /**
     * Check if Profile page is displayed with enhanced verification
     */
    public boolean isProfilePageDisplayed() {
        try {
            WebElement heading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(profileHeadingXpath)));
            String name = heading.getAttribute("name");
            boolean isDisplayed = heading.isDisplayed() && "PROFILE".equals(name);
            if (isDisplayed) {
                System.out.println("✓ Profile page verified - PROFILE element visible with correct name");
            }
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click TRACK ORDER button
     */
    public void clickTrackOrder() throws InterruptedException {
        robustClick(trackOrderXpath, "TRACK ORDER");
    }

    /**
     * Click SEND SAMPLE button
     */
    public void clickSendSample() throws InterruptedException {
        robustClick(sendSampleXpath, "SEND SAMPLE");
    }

    /**
     * Click GETTING STARTED button
     */
    public void clickGettingStarted() throws InterruptedException {
        robustClick(gettingStartedXpath, "GETTING STARTED");
    }

    /**
     * Click DNA KIT button
     */
    public void clickDnaKit() throws InterruptedException {
        robustClick(dnaKitXpath, "DNA KIT");
    }

    /**
     * Click T&C button
     */
    public void clickTC() throws InterruptedException {
        robustClick(tcXpath, "T&C");
    }

    /**
     * Click PRIVACY POLICY button
     */
    public void clickPrivacyPolicy() throws InterruptedException {
        robustClick(privacyPolicyXpath, "PRIVACY POLICY");
    }

    /**
     * Click NO button in logout confirmation
     */
    public void clickNo() throws InterruptedException {
        robustClick(noButtonXpath, "NO");
    }

    /**
     * Click YES button in logout confirmation
     */
    public void clickYes() throws InterruptedException {
        robustClick(yesButtonXpath, "YES");
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
        }
    }

    /**
     * Navigate back using system back button
     */
    public void navigateBack() {
        try {
            driver.navigate().back();
        } catch (Exception e) {
            System.out.println("Navigate back failed: " + e.getMessage());
        }
    }

    /**
     * Navigate back to Profile page
     */
    public void navigateBackToProfile() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            navigateBack();
            Thread.sleep(500);
            if (isProfilePageDisplayed()) {
                System.out.println("✓ Successfully navigated back to Profile page");
                return;
            }
        }
        System.out.println("⚠ Could not navigate back to Profile page after multiple attempts");
    }

    /**
     * Click OPEN CONTENT button
     */
    public void clickOpenContent() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='OPEN CONTENT']", "OPEN CONTENT");
    }

    /**
     * Check if Privacy Policy page is displayed
     */
    public boolean isPrivacyPolicyPageDisplayed() {
        try {
            WebElement heading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='PRIVACY POLICY']")));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Open Source Content page is displayed
     */
    public boolean isOpenSourceContentPageDisplayed() {
        try {
            WebElement heading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='OPEN SOURCE CONTENT']")));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Sign In page is displayed
     */
    public boolean isSignInPageDisplayed() {
        try {
            WebElement heading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='SIGN IN']")));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== MISSING PAGE DISPLAY VERIFICATION METHODS ====================

    /**
     * Check if Track Order page is displayed
     */
    public boolean isTrackOrderPageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='TRACK ORDER']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Send Sample page is displayed
     */
    public boolean isSendSamplePageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='SEND SAMPLE']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Getting Started page is displayed
     */
    public boolean isGettingStartedPageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='GETTING STARTED']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if DNA Kit page is displayed
     */
    public boolean isDnaKitPageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='DNA KIT']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== MISSING CLICK METHODS ====================

    /**
     * Click Mail Sample button
     */
    public void clickMailSample() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='MAIL SAMPLE']", "MAIL SAMPLE");
    }

    /**
     * Click Confirm Shipment button
     */
    public void clickConfirmShipment() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='CONFIRM SHIPMENT']", "CONFIRM SHIPMENT");
    }

    /**
     * Click Received At Lab button
     */
    public void clickReceivedAtLab() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='RECEIVED AT LAB']", "RECEIVED AT LAB");
    }

    /**
     * Click Report Generated button
     */
    public void clickReportGenerated() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='REPORT GENERATED']", "REPORT GENERATED");
    }

    /**
     * Click How To Create Account button
     */
    public void clickHowToCreateAccount() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='HOW DO I CREATE AN ACCOUNT?']", "HOW DO I CREATE AN ACCOUNT?");
    }

    /**
     * Click Do I Need App button
     */
    public void clickDoINeedApp() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='DO I NEED TO DOWNLOAD AN APP?']", "DO I NEED APP");
    }

    /**
     * Click How Long To Receive Kit button
     */
    public void clickHowLongToReceiveKit() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='HOW LONG TO RECEIVE KIT?']", "HOW LONG TO RECEIVE KIT");
    }

    /**
     * Click How To Activate Kit button
     */
    public void clickHowToActivateKit() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='HOW TO ACTIVATE KIT?']", "HOW TO ACTIVATE KIT");
    }

    /**
     * Click What To Do If Kit Damaged button
     */
    public void clickWhatToDoIfKitDamaged() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='KIT DAMAGED?']", "KIT DAMAGED");
    }

    /**
     * Click What Payment Methods button
     */
    public void clickWhatPaymentMethods() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='PAYMENT METHODS?']", "PAYMENT METHODS");
    }

    /**
     * Click Can I Pause Cancel Sub button
     */
    public void clickCanIPauseOrCancelSub() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='PAUSE OR CANCEL?']", "PAUSE OR CANCEL");
    }

    /**
     * Click Will Be Notified Before Renewal button
     */
    public void clickWillBeNotifiedBeforeRenewal() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='NOTIFIED BEFORE RENEWAL?']", "NOTIFIED BEFORE RENEWAL");
    }

    /**
     * Click Cant Log Into Account button
     */
    public void clickCantLogIntoAccount() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='CANNOT LOG IN?']", "CANNOT LOG IN");
    }

    /**
     * Click Results Delayed button
     */
    public void clickResultsDelayed() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='RESULTS DELAYED?']", "RESULTS DELAYED");
    }

    /**
     * Click Subscription Billing button
     */
    public void clickSubscriptionBilling() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='SUBSCRIPTION & BILLING']", "SUBSCRIPTION & BILLING");
    }

    /**
     * Check if Subscription Billing page is displayed
     */
    public boolean isSubscriptionBillingPageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='SUBSCRIPTION & BILLING']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click What Payment Methods Accepted button
     */
    public void clickWhatPaymentMethodsAccepted() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='PAYMENT METHODS?']", "PAYMENT METHODS");
    }

    /**
     * Click Troubleshooting button
     */
    public void clickTroubleshooting() throws InterruptedException {
        robustClick("//XCUIElementTypeButton[@name='TROUBLESHOOTING']", "TROUBLESHOOTING");
    }

    /**
     * Check if Troubleshooting page is displayed
     */
    public boolean isTroubleshootingPageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='TROUBLESHOOTING']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Terms Conditions page is displayed
     */
    public boolean isTermsConditionsPageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='TERMS & CONDITIONS']")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an element is displayed using its XPath
     */
    public boolean isElementDisplayed(String xpath) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
