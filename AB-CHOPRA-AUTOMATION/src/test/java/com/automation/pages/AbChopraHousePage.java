
package com.automation.pages;

import io.appium.java_client.AppiumDriver;
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

public class AbChopraHousePage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public AbChopraHousePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Step 9: Click Filter button (iOS)
     */
    public void clickFilter() {
        try {
            WebElement filterBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filterButtonXpath)));
            filterBtn.click();
            // Wait for filter dialog to appear
            Thread.sleep(2000);
        } catch (TimeoutException e) {
            throw new RuntimeException("FILTER button not found", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while waiting for filter dialog", e);
        }
    }

    // ==================== LOCATORS ====================

    // Step 3: AB Chopra House (iOS)
    private final String abChopraHouseXpath = "//XCUIElementTypeStaticText[@name=\"AB CHOPRA HOUSE\"]";

    // Step 4: Discover button (iOS)
    private final String discoverButtonXpath = "//XCUIElementTypeStaticText[@name=\"DISCOVER\"]";

    // Step 5: Discover page heading (iOS)
    private final String discoverPageHeadingXpath = "//XCUIElementTypeStaticText[@name=\"DISCOVER +\"]";

    // Step 6 & 7: Scroll view for swipe (iOS)
    private final String scrollViewXpath = "//XCUIElementTypeScrollView";

    // Step 8: Discover + button (iOS)
    private final String discoverPlusButtonXpath = "//XCUIElementTypeStaticText[@name=\"DISCOVER +\"]";

    // Step 9: Filter button (iOS)
    private final String filterButtonXpath = "//XCUIElementTypeButton[@name=\"FILTER\"]";

    // Step 10: Mind & Emotions radio button (iOS)
    private final String mindEmotionsRadioXpath = "//XCUIElementTypeStaticText[@name=\"Mind & Emotions\"]";
    // Step 11: Timing SeekBar (iOS)
    private final String timingSeekBarXpath = "//XCUIElementTypeOther[@value=\"the end value is 60.0\"]";

    // Step 12: Apply button (iOS)
    private final String applyButtonXpath = "//XCUIElementTypeButton[@name=\"APPLY\"]";

    // Step 13: See All 1, Listen page, Back button (iOS)
    private final String seeAll1Xpath = "(//XCUIElementTypeButton[@name=\"See All\"])[1]";
    private final String listenPageXpath = "//XCUIElementTypeStaticText[@name=\"Listen\"]";
    // Use a more specific iOS class chain for the top-left back button
    private final String backButtonClassChain = "**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeButton";

    // Step 14: See All 3, Watch page (iOS)
    // (seeAll3Xpath and watchPageXpath already declared below, removing duplicate)
    // Step 16: See All 2, Read page (iOS)
    private final String seeAll2Xpath = "(//XCUIElementTypeButton[@name=\"See All\"])[3]";
    private final String readPageXpath = "//XCUIElementTypeStaticText[@name=\"Read\"]";

    // Step 15: Swipe container (iOS)
    private final String swipeContainerXpath = "//XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeScrollView";

    // Step 16: See All 3, Watch page
    private final String seeAll3Xpath = "(//android.widget.ImageView[@content-desc=\"See All\"])[2]";
    private final String watchPageXpath = "//XCUIElementTypeStaticText[@name=\"Watch\"]";

    // Step 17: Article item (XPath now inline in clickVideoItem() method using
    // contains())

    // Step 18: Search box (iOS)
    private final String searchBoxXpath = "//XCUIElementTypeTextField[@name=\"Search\"]";

    // Step 19: Search result (XPath now inline in isSearchResultOneDisplayed()
    // method using contains())

    // Step 20: New File icon (iOS)
    private final String newFileIconXpath = "//XCUIElementTypeButton[@name=\"New File\"]";

    // Step 21: File name input (iOS)
    private final String fileNameInputXpath = "//XCUIElementTypeTextField[@name=\"Enter file name\"]";

    // Step 23: Close icon (iOS)
    private final String closeIconXpath = "//XCUIElementTypeImage";

    // Step 24: Saved dialog and success message (iOS)
    private final String savedDialogXpath = "//XCUIElementTypeStaticText[@name=\"SAVED\"]";
    private final String savedSuccessMessageXpath = "//XCUIElementTypeStaticText[@name=\"Your article has been successfully saved.\"]";

    // Step 26: OK button (iOS)
    private final String okButtonXpath = "//XCUIElementTypeButton[@name=\"OK\"]";

    // Step 27: Archive button (iOS)
    private final String archiveButtonXpath = "//XCUIElementTypeStaticText[@name=\"ARCHIVE\"]";

    // Step 28: Archive page heading (iOS)
    private final String archivePageHeadingXpath = "//XCUIElementTypeStaticText[@name=\"ARCHIVE\"]";

    // Step 26 & 27: New file in archive (uses contains() to handle multiline
    // content-desc like "New\nModified Dec 30")
    // Locator is now defined inline in methods to use contains() for dynamic date
    // handling

    // Step 31: File open verification (iOS)
    private final String fileOpenXpath = "//XCUIElementTypeImage[@name=\"New Modified Jan 22\"]";

    // Step 33: Yes button (iOS)
    private final String yesButtonXpath = "//XCUIElementTypeButton[@name=\"YES\"]";

    // Step 34: Success dialog and delete message (iOS)
    private final String successDialogXpath = "//XCUIElementTypeStaticText[@name=\"SUCCESS\"]";
    private final String deleteSuccessMessageXpath = "//XCUIElementTypeStaticText[@name=\"Your article has been successfully deleted.\"]";
    // Step 31.1: Menu icon (iOS)
    private final String menuIconXpath = "//XCUIElementTypeApplication[@name=\"AB Chopra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeImage[2]";
    // Step 31.2: Edit
    private final String editButtonXpath = "//XCUIElementTypeStaticText[@name=\"EDIT\"]";
    // Step 31.3: Enter File Name (edit)
    private final String editFileNameInputXpath = "//XCUIElementTypeTextField[@name=\"Enter File Name\"]";
    // Step 31.4: Save button
    private final String saveButtonXpath = "//XCUIElementTypeButton[@name=\"SAVE\"]";
    // Step 31.5: Success dialog (edit)
    private final String editSuccessDialogXpath = "//XCUIElementTypeStaticText[@name=\"SUCCESS\"]";
    // Step 31.6: Success message (edit)
    private final String editSuccessMessageXpath = "//XCUIElementTypeStaticText[@name=\"Your archive name has been successfully updated.\"]";
    // Step 31.7: OK button (edit)
    private final String okButtonEditXpath = "//XCUIElementTypeButton[@name=\"OK\"]";
    // Step 31.9: Organise
    private final String organiseButtonXpath = "//XCUIElementTypeStaticText[@name=\"ORGANISE\"]";
    /**
     * Step 31.1: Click menu icon
     */
    public void clickMenuIcon() {
        try {
            WebElement menuIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuIconXpath)));
            menuIcon.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Menu icon not found", e);
        }
    }

    /**
     * Step 31.2: Click EDIT
     */
    public void clickEdit() {
        try {
            WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(editButtonXpath)));
            editBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("EDIT button not found", e);
        }
    }

    /**
     * Step 31.3: Enter file name and type 'EDIT NEW'
     */
    public void enterEditFileName(String fileName) {
        try {
            WebElement fileNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(editFileNameInputXpath)));
            fileNameInput.clear();
            fileNameInput.sendKeys(fileName);
        } catch (TimeoutException e) {
            throw new RuntimeException("Edit file name input not found", e);
        }
    }

    /**
     * Step 31.4: Click SAVE button
     */
    public void clickSaveButton() {
        try {
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(saveButtonXpath)));
            saveBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("SAVE button not found", e);
        }
    }

    /**
     * Step 31.5: Verify SUCCESS dialog is shown (edit)
     */
    public boolean isEditSuccessDialogDisplayed() {
        try {
            WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editSuccessDialogXpath)));
            return dialog.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 31.6: Get and show the success message (edit)
     */
    public String getEditSuccessMessage() {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editSuccessMessageXpath)));
            return msg.getText();
        } catch (TimeoutException e) {
            throw new RuntimeException("Edit success message not found", e);
        }
    }

    /**
     * Step 31.7: Click OK button (edit)
     */
    public void clickOkButtonEdit() {
        try {
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(okButtonEditXpath)));
            okBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("OK button (edit) not found", e);
        }
    }

    /**
     * Step 31.8: Click menu icon again
     */
    public void clickMenuIconAgain() {
        clickMenuIcon();
    }

    /**
     * Step 31.9: Click ORGANISE
     */
    public void clickOrganise() {
        try {
            WebElement organiseBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(organiseButtonXpath)));
            organiseBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("ORGANISE button not found", e);
        }
    }

    // ==================== METHODS ====================

    /**
     * Step 3: Click AB Chopra House
     */
    public void clickAbChopraHouse() {
        try {
            WebElement abChopraHouse = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(abChopraHouseXpath)));
            abChopraHouse.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("AB CHOPRA HOUSE button not found", e);
        }
    }

    /**
     * Step 4: Click Discover button
     */
    public void clickDiscover() {
        try {
            WebElement discoverBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(discoverButtonXpath)));
            discoverBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("DISCOVER button not found", e);
        }
    }

    /**
     * Step 6: Swipe up once
     */
    public void swipeUpOnce() {
        try {
            // Wait 2 seconds before swiping
            Thread.sleep(2000);
            WebElement scrollView = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(scrollViewXpath)));
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            throw new RuntimeException("Failed to swipe up", e);
        }
    }

    /**
     * Step 7: Swipe down once
     */
    public void swipeDownOnce() {
        try {
            WebElement scrollView = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(scrollViewXpath)));
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.2);
            int endY = (int) (size.height * 0.8);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            throw new RuntimeException("Failed to swipe down", e);
        }
    }

    /**
     * Step 8: Click Discover + button
     */
    public void clickDiscoverPlus() {
        try {
            // Wait 3 seconds for page to settle after swipes
            Thread.sleep(3000);

            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement discoverPlusBtn = null;

            // Try iOS XPaths and locator strategies
            // 1. name='DISCOVER +'
            try {
                discoverPlusBtn = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeStaticText[@name='DISCOVER +']")));
            } catch (Exception e1) {}
            // 2. name='DISCOVER\n +'
            if (discoverPlusBtn == null) {
                try {
                    discoverPlusBtn = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeStaticText[@name='DISCOVER\\n +']")));
                } catch (Exception e2) {}
            }
            // 3. value='DISCOVER\n +'
            if (discoverPlusBtn == null) {
                try {
                    discoverPlusBtn = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeStaticText[@value='DISCOVER\\n +']")));
                } catch (Exception e3) {}
            }
            // 4. contains(@name, 'DISCOVER')
            if (discoverPlusBtn == null) {
                try {
                    discoverPlusBtn = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeStaticText[contains(@name,'DISCOVER')]")));
                } catch (Exception e4) {}
            }
            // 5. accessibility id
            if (discoverPlusBtn == null) {
                try {
                    discoverPlusBtn = (WebElement) driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("DISCOVER +"));
                } catch (Exception e5) {}
            }
            if (discoverPlusBtn == null) {
                try {
                    discoverPlusBtn = (WebElement) driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("DISCOVER\n +"));
                } catch (Exception e6) {}
            }
            if (discoverPlusBtn != null) {
                discoverPlusBtn.click();
            } else {
                throw new RuntimeException("DISCOVER + button not found by any iOS locator");
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("DISCOVER + button not found", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while waiting", e);
        }
    }

    /**
     * Step 10: Click Mind & Emotions radio button
     */
    public void clickMindEmotionsRadio() {
        try {
            // Use longer wait time for filter dialog elements to load
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement radioBtn = longWait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(mindEmotionsRadioXpath)));
            radioBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Mind & Emotions radio button not found", e);
        }
    }

    /**
     * Step 11: Set timing on SeekBar
     */
    public void setTiming() {
        try {
            WebElement seekBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(timingSeekBarXpath)));
            seekBar.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Timing SeekBar not found", e);
        }
    }

    /**
     * Step 12: Click Apply button
     */
    public void clickApply() {
        try {
            WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(applyButtonXpath)));
            applyBtn.click();
            // Wait for loading to complete
            Thread.sleep(3000);
        } catch (TimeoutException e) {
            throw new RuntimeException("APPLY button not found", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while waiting for loading", e);
        }
    }

    /**
     * Step 13 (new): Search for "love" in search box
     */
    public void searchForLove() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchBoxXpath)));
            searchBox.click();
            searchBox.clear();
            searchBox.sendKeys("love");
        } catch (TimeoutException e) {
            throw new RuntimeException("Search box not found", e);
        }
    }

    /**
     * Step 13 (new): Hide keyboard
     */
    public void hideKeyboard() {
        try {
            ((io.appium.java_client.android.AndroidDriver) driver).hideKeyboard();
        } catch (Exception e) {
            // Keyboard might already be hidden, ignore
            System.out.println("Keyboard hide failed or already hidden: " + e.getMessage());
        }
    }

    /**
     * Step 13 (new): Verify Love & Unity article is displayed
     */
    public boolean isLoveArticleDisplayed() {
        try {
            WebElement article = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//android.view.View[@content-desc='Love & Unity\nAudio \n 8 min']")));
            return article.isDisplayed();
        } catch (Exception e) {
            System.out.println("Love & Unity article not found. Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Step 13 (new): Clear search box
     */
    public void clearSearch() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchBoxXpath)));
            searchBox.click();
            searchBox.clear();
        } catch (TimeoutException e) {
            throw new RuntimeException("Search box not found for clearing", e);
        }
    }

    /**
     * Step 13: Click See All 1
     */
    public void clickSeeAll1() {
        try {
            WebElement seeAll1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(seeAll1Xpath)));
            seeAll1.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("See All 1 button not found", e);
        }
    }

    /**
     * Step 13: Verify Listen page is displayed
     */
    public boolean isListenPageDisplayed() {
        try {
            // Use longer wait time for Listen page to load
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement listenPage = longWait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(listenPageXpath)));
            return listenPage.isDisplayed();
        } catch (Exception e) {
            System.out.println("Listen page not found. Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Step 13, 14, 16, 23: Click back button
     */
    public void clickBackButton() {
        // Use the same logic as in the test for steps 13, 14, 16
        boolean isBackButtonVisible = false;
        try {
            int btnCount = driver.findElements(By.xpath("//XCUIElementTypeButton")).size();
            isBackButtonVisible = btnCount > 0;
        } catch (Exception e) {
            isBackButtonVisible = false;
        }
        if (!isBackButtonVisible) {
            throw new RuntimeException("Back button should be visible before clicking back");
        }
        try {
            driver.findElement(By.xpath("//XCUIElementTypeButton")).click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click iOS Back button: " + e.getMessage(), e);
        }
    }

    /**
     * Step 14: Click See All 2
     */
    public void clickSeeAll2() {
        try {
            WebElement seeAll2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(seeAll2Xpath)));
            seeAll2.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("See All 2 button not found", e);
        }
    }

    /**
     * Step 14: Verify Read page is displayed
     */
    public boolean isReadPageDisplayed() {
        try {
            // Use longer wait time for Read page to load
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement readPage = longWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(readPageXpath)));
            return readPage.isDisplayed();
        } catch (Exception e) {
            System.out.println("Read page not found. Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Step 15: Swipe up on container
     */
    public void swipeUpOnContainer() {
        try {
            WebElement container = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(swipeContainerXpath)));
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            throw new RuntimeException("Failed to swipe up on container", e);
        }
    }

    /**
     * Step 16: Click See All 3
     */
    public void clickSeeAll3() {
        try {
            // iOS: Use the 2nd 'See All' button by XPath as per user request
            WebElement seeAll3Btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//XCUIElementTypeButton[@name='See All'])[2]")));
            seeAll3Btn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("See All 3 button not found", e);
        }
    }

    /**
     * Step 16: Verify Watch page is displayed
     */
    public boolean isWatchPageDisplayed() {
        try {
            // Use longer wait time for Watch page to load
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement watchPage = longWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(watchPageXpath)));
            return watchPage.isDisplayed();
        } catch (Exception e1) {
            // Fallback: try by name if XPath fails
            try {
                WebElement watchPageByName = driver.findElement(By.name("Watch"));
                return watchPageByName.isDisplayed();
            } catch (Exception e2) {
                System.out.println("Watch page not found by xpath or name. Error: " + e2.getMessage());
                return false;
            }
        }
    }

    /**
     * Step 17: Click article radio button
     * Clicks the radio button inside the article card, not the card itself
     * Radio button is a clickable android.view.View without content-desc
     */
    public void clickVideoItem() {
        try {
            // iOS: Use provided XPath for the radio button
            By radioXpath = By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]");
            WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(radioXpath));
            try {
                radioButton.click();
            } catch (Exception clickEx) {
                // Fallback: tap by coordinates if click fails
                int x = 24 + 25 / 2; // center x from inspector
                int y = 600 + 25 / 2; // center y from inspector
                org.openqa.selenium.interactions.PointerInput finger = new org.openqa.selenium.interactions.PointerInput(org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
                org.openqa.selenium.interactions.Sequence tap = new org.openqa.selenium.interactions.Sequence(finger, 1);
                tap.addAction(finger.createPointerMove(java.time.Duration.ZERO, org.openqa.selenium.interactions.PointerInput.Origin.viewport(), x, y));
                tap.addAction(finger.createPointerDown(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
                tap.addAction(finger.createPointerUp(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(java.util.Collections.singletonList(tap));
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Article radio button not found", e);
        }
    }

    /**
     * Step 18: Click search box and search for "one"
     */
    public void searchForOne() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchBoxXpath)));
            searchBox.click();
            searchBox.clear();
            searchBox.sendKeys("one");
        } catch (TimeoutException e) {
            throw new RuntimeException("Search box not found", e);
        }
    }

    /**
     * Step 19: Verify "One" is shown in search results
     * Uses contains() to handle async loading and partial text matches
     */
    public boolean isSearchResultOneDisplayed() {
        try {
            // Use contains() to handle partial text match and async rendering
            By searchResult = By.xpath(
                    "//android.view.View[contains(@content-desc,'One')]");

            // Wait for element to be visible (handles async rendering)
            WebElement result = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(searchResult));

            return result.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 18 (second): Click New File icon
     */
    public void clickNewFileIcon() {
        try {
            WebElement newFileIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(newFileIconXpath)));
            newFileIcon.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("New File icon not found", e);
        }
    }

    /**
     * Step 19 (second): Create file by typing "New"
     */
    public void createFile(String fileName) {
        try {
            WebElement fileNameInput = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(fileNameInputXpath)));
            fileNameInput.click();
            fileNameInput.clear();
            fileNameInput.sendKeys(fileName);
        } catch (TimeoutException e) {
            throw new RuntimeException("File name input not found", e);
        }
    }

    /**
     * Step 19.5: Trigger save before closing by forcing focus loss
     * This method MUST be called after entering data and BEFORE clicking Close icon
     * 
     * The app only saves data when input focus is lost.
     * Direct Close click skips the save logic.
     * 
     * Solution: Force focus loss by:
     * 1. Hide keyboard to remove input focus
     * 2. Tap outside the input field (bottom of screen) using W3C touch actions
     * 3. Wait for app to trigger its save callback
     * 
     * This simulates real user behavior and ensures data is saved.
     */
    public void triggerSaveBeforeClose() {
        try {
            // Step 1: Hide keyboard to remove input focus
            try {
                ((io.appium.java_client.android.AndroidDriver) driver).hideKeyboard();
                Thread.sleep(500); // Allow keyboard to fully hide
            } catch (Exception e) {
                // Keyboard might already be hidden, continue
                System.out.println("Keyboard hide failed or already hidden: " + e.getMessage());
            }

            // Step 2: Tap outside the input field at bottom of screen
            // This forces focus loss and triggers the app's auto-save callback
            Dimension screenSize = driver.manage().window().getSize();

            // Calculate tap coordinates: center X, 90% down Y (bottom area)
            int tapX = screenSize.width / 2;
            int tapY = (int) (screenSize.height * 0.9);

            // Perform coordinate-based tap using W3C Actions
            // PointerInput.Kind.TOUCH simulates a real finger tap
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);

            // Move pointer to bottom area
            tap.addAction(finger.createPointerMove(
                    Duration.ZERO,
                    PointerInput.Origin.viewport(),
                    tapX,
                    tapY));

            // Pointer down (finger press)
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

            // Pointer up (finger release)
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Execute the tap action
            driver.perform(Collections.singletonList(tap));

            // Step 3: Wait for app to trigger save callback
            Thread.sleep(1000);

        } catch (Exception e) {
            throw new RuntimeException("Failed to trigger save before close", e);
        }
    }

    /**
     * Step 20: Click close icon
     * NOTE: Must call triggerSaveBeforeClose() BEFORE this method
     * to ensure data is saved (by forcing focus loss)
     */
    public void clickCloseIcon() {
        try {
            WebElement closeIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(closeIconXpath)));
            closeIcon.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Close icon not found", e);
        }
    }

    /**
     * Step 21: Verify Saved dialog is displayed
     */
    public boolean isSavedDialogDisplayed() {
        try {
            WebElement savedDialog = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(savedDialogXpath)));
            return savedDialog.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 21: Get success message
     */
    public String getSavedSuccessMessage() {
        try {
            WebElement successMessage = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(savedSuccessMessageXpath)));
            return successMessage.getAttribute("content-desc");
        } catch (TimeoutException e) {
            throw new RuntimeException("Saved success message not found", e);
        }
    }

    /**
     * Step 22, 32: Click OK button
     */
    public void clickOkButton() {
        try {
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(okButtonXpath)));
            okBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("OK button not found", e);
        }
    }

    /**
     * Step 24: Click Archive button
     */
    public void clickArchive() {
        try {
            WebElement archiveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(archiveButtonXpath)));
            archiveBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("ARCHIVE button not found", e);
        }
    }

    /**
     * Step 25: Verify Archive page is displayed
     */
    public boolean isArchivePageDisplayed() {
        try {
            WebElement archivePage = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(archivePageHeadingXpath)));
            return archivePage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 26: Verify new file is in archive
     * Uses contains() to handle multiline content-desc (e.g., "New\nModified Dec
     * 30")
     * Element is android.widget.ImageView, not View
     */
    public boolean isNewFileInArchive() {
        try {
            // Use contains() to handle multiline content-desc with dynamic date
            By archivedNewFile = By.xpath(
                    "//android.widget.ImageView[contains(@content-desc,'New')]");

            // Wait until the archive list is visible
            WebElement file = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(archivedNewFile));

            // Validate using isDisplayed()
            return file.isDisplayed();
        } catch (Exception e) {
            System.out.println("New file not found in archive. Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Step 27: Click new file in archive using coordinate-based tap
     * Taps the LEFT SIDE (image area) of the container at 25% width from left
     * Normal click() taps center/right which doesn't trigger navigation
     * Uses W3C PointerInput actions for precise coordinate tapping
     */
    public void clickNewFileInArchive() {
        try {
            // Locate the article/container element
            By articleLocator = By.xpath(
                    "//android.widget.ImageView[contains(@content-desc,'New')]");

            WebElement articleElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(articleLocator));

            // Get container position & size
            org.openqa.selenium.Point location = articleElement.getLocation();
            Dimension size = articleElement.getSize();

            // Calculate LEFT-SIDE tap (image area) - 25% width from left + vertical center
            int tapX = location.getX() + (int) (size.getWidth() * 0.25);
            int tapY = location.getY() + (size.getHeight() / 2);

            // Perform coordinate tap using W3C Actions
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(
                    Duration.ZERO,
                    PointerInput.Origin.viewport(),
                    tapX,
                    tapY));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(tap));

            // Wait for navigation to complete
            Thread.sleep(2000);

        } catch (Exception e) {
            throw new RuntimeException("Failed to tap left side of archive item", e);
        }
    }

    /**
     * Step 28: Verify file is open
     */
    public boolean isFileOpen() {
        try {
            WebElement fileOpen = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fileOpenXpath)));
            return fileOpen.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 29: Click remove icon
     * Uses relative XPath to locate the clickable ImageView within the article view
     * Waits for element to be visible and clickable before clicking
     */
    public void clickRemoveIcon() {
        try {
            // Step 32: Click Remove Icon (center of element) by position and size
            int expectedX = 40;
            int expectedY = 393;
            int expectedWidth = 21;
            int expectedHeight = 21;
            java.util.List<WebElement> images = driver.findElements(By.className("XCUIElementTypeImage"));
            WebElement target = null;
            for (WebElement img : images) {
                org.openqa.selenium.Rectangle rect = img.getRect();
                if (rect.getX() == expectedX && rect.getY() == expectedY && rect.getWidth() == expectedWidth && rect.getHeight() == expectedHeight) {
                    target = img;
                    break;
                }
            }
            if (target == null) {
                throw new RuntimeException("Remove icon not found by position/size");
            }
            int centerX = target.getRect().getX() + target.getRect().getWidth() / 2;
            int centerY = target.getRect().getY() + target.getRect().getHeight() / 2;
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(tap));
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Remove icon at center", e);
        }
    }

    /**
     * Step 30: Click Yes button
     */
    public void clickYesButton() {
        try {
            WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(yesButtonXpath)));
            yesBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("YES button not found", e);
        }
    }

    /**
     * Step 31: Verify Success dialog is displayed
     */
    public boolean isSuccessDialogDisplayed() {
        try {
            WebElement successDialog = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(successDialogXpath)));
            return successDialog.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Step 31: Get delete success message
     */
    public String getDeleteSuccessMessage() {
        try {
            // Try XPath first
            try {
                WebElement msg = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='Your article has been successfully deleted.']")));
                if (msg != null && msg.isDisplayed()) {
                    return msg.getText();
                }
            } catch (Exception ignore) {}
            // Fallback: search all static texts for the name
            java.util.List<WebElement> elements = driver.findElements(By.className("XCUIElementTypeStaticText"));
            for (WebElement el : elements) {
                String name = el.getAttribute("name");
                if ("Your article has been successfully deleted.".equals(name)) {
                    return el.getText();
                }
            }
            return null;
        } catch (TimeoutException e) {
            throw new RuntimeException("Delete success message not found", e);
        }
    }
}


