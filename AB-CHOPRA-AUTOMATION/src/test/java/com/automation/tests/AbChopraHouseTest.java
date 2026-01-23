package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.AbChopraHousePage;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AbChopraHouseTest extends BaseTest {

    @Test(priority = 1)
    public void testAbChopraHouse_Case1() throws InterruptedException {
        test = extent.createTest("AB Chopra House Test Case 1");
        test.log(Status.INFO, "Starting AB Chopra House Test Case 1");

        AbChopraHousePage abChopraHousePage = new AbChopraHousePage(driver);

        // Step 1: Verify DAILY PRIORITY heading is displayed on home page (iOS XPath)
        test.log(Status.INFO, "Step 1: Verifying DAILY PRIORITY heading on home page (iOS)");
        boolean isHomePageDisplayed = driver.findElements(
            org.openqa.selenium.By.xpath("//XCUIElementTypeStaticText[@name='DAILY PRIORITY']")
        ).size() > 0;
        if (!isHomePageDisplayed) {
            test.log(Status.FAIL, "DAILY PRIORITY heading not found on home page");
            Assert.fail("Home page validation failed - DAILY PRIORITY heading not displayed");
        }
        test.log(Status.PASS, "✓ DAILY PRIORITY heading is displayed on home page");

        // Step 2: Click Wellbeing Dashboard (iOS, robust fallback logic)
        test.log(Status.INFO, "Step 2: Clicking Wellbeing Dashboard (iOS, robust)");
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        boolean dashboardClicked = false;
        try {
            org.openqa.selenium.WebElement wellbeingDashboard = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name, 'WELLBEING')]")
            ));
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by xpath");
            Thread.sleep(1500);
            dashboardClicked = true;
        } catch (Exception e) {
            test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by xpath: " + e.getMessage());
            // Try by accessibility id (Appium)
            try {
                org.openqa.selenium.WebElement dashboardByAccId = driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("WELLBEING DASHBOARD HOME"));
                dashboardByAccId.click();
                test.log(Status.INFO, "✓ Clicked WELLBEING DASHBOARD HOME by accessibility id");
                Thread.sleep(1500);
                dashboardClicked = true;
            } catch (Exception ex1) {
                test.log(Status.WARNING, "⚠ WELLBEING DASHBOARD HOME not found by accessibility id: " + ex1.getMessage());
                // Try by name
                try {
                    org.openqa.selenium.WebElement dashboardByName = driver.findElement(org.openqa.selenium.By.name("WELLBEING DASHBOARD HOME"));
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
            test.log(Status.INFO, "Wellbeing Dashboard not found by any locator, assuming already on dashboard");
        }

        // Step 3: Click AB CHOPRA HOUSE
        test.log(Status.INFO, "Step 3: Clicking AB CHOPRA HOUSE");
        abChopraHousePage.clickAbChopraHouse();
        test.log(Status.PASS, "✓ Step 3: AB CHOPRA HOUSE clicked");
        Thread.sleep(2000);

        // Step 4: Click DISCOVER
        test.log(Status.INFO, "Step 4: Clicking DISCOVER");
        abChopraHousePage.clickDiscover();
        test.log(Status.PASS, "✓ Step 4: DISCOVER clicked");
        Thread.sleep(3000);

        // Step 5: Verify Discover Page is Shown (robust for newline and all locator types)
        test.log(Status.INFO, "Step 5: Verifying Discover Page is shown");
        Thread.sleep(2000); // Wait for page transition
        boolean found = false;
        // Try XPath with name 'DISCOVER +' (original)
        if (driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeStaticText[@name='DISCOVER +']")).size() > 0) found = true;
        // Try XPath with name 'DISCOVER\n +' (newline)
        if (!found && driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeStaticText[@name='DISCOVER\\n +']")).size() > 0) found = true;
        // Try XPath with value 'DISCOVER\n +'
        if (!found && driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeStaticText[@value='DISCOVER\\n +']")).size() > 0) found = true;
        // Try contains for partial match
        if (!found && driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeStaticText[contains(@name,'DISCOVER')]" )).size() > 0) found = true;
        // Try accessibility id
        if (!found) {
            try {
                driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("DISCOVER +"));
                found = true;
            } catch (Exception e) {}
        }
        if (!found) {
            try {
                driver.findElement(io.appium.java_client.MobileBy.AccessibilityId("DISCOVER\n +"));
                found = true;
            } catch (Exception e) {}
        }
        Assert.assertTrue(found, "Discover page should be displayed");
        test.log(Status.PASS, "✓ Step 5: Discover page is displayed");

        // Step 6: Swipe up once
        test.log(Status.INFO, "Step 6: Swiping up once");
        abChopraHousePage.swipeUpOnce();
        test.log(Status.PASS, "✓ Step 6: Swiped up once");
        Thread.sleep(1000);

        // Step 7: Swipe down once
        test.log(Status.INFO, "Step 7: Swiping down once");
        abChopraHousePage.swipeDownOnce();
        test.log(Status.PASS, "✓ Step 7: Swiped down once");
        Thread.sleep(1000);

        // Step 8: Click DISCOVER +
        test.log(Status.INFO, "Step 8: Clicking DISCOVER +");
        abChopraHousePage.clickDiscoverPlus();
        test.log(Status.PASS, "✓ Step 8: DISCOVER + clicked");
        Thread.sleep(2000);

        // Step 9: Click FILTER
        test.log(Status.INFO, "Step 9: Clicking FILTER");
        abChopraHousePage.clickFilter();
        test.log(Status.PASS, "✓ Step 9: FILTER clicked");
        Thread.sleep(1000);

        // Step 10: Click Mind & Emotions radio button
        test.log(Status.INFO, "Step 10: Clicking Mind & Emotions radio button");
        abChopraHousePage.clickMindEmotionsRadio();
        test.log(Status.PASS, "✓ Step 10: Mind & Emotions radio button clicked");
        Thread.sleep(1000);

        // Step 11: Set timing
        test.log(Status.INFO, "Step 11: Setting timing");
        abChopraHousePage.setTiming();
        test.log(Status.PASS, "✓ Step 11: Timing set");
        Thread.sleep(1000);

        // Step 12: Click APPLY
        test.log(Status.INFO, "Step 12: Clicking APPLY");
        abChopraHousePage.clickApply();
        test.log(Status.PASS, "✓ Step 12: APPLY clicked");
        Thread.sleep(2000);

        // Step 13: Click See All 1, verify Listen page, click back
        test.log(Status.INFO, "Step 13: Clicking See All 1");
        abChopraHousePage.clickSeeAll1();
        test.log(Status.PASS, "✓ Step 13a: See All 1 clicked");
        Thread.sleep(1000);

        test.log(Status.INFO, "Step 13: Verifying Listen page is displayed");
        Assert.assertTrue(abChopraHousePage.isListenPageDisplayed(), "Listen page should be displayed");
        test.log(Status.PASS, "✓ Step 13b: Listen page is displayed");

        test.log(Status.INFO, "Step 13: Validating back button is visible before click");
        boolean isBackButtonVisible13 = false;
        try {
            int btnCount = driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeButton")).size();
            test.log(Status.INFO, "Back button count (//XCUIElementTypeButton): " + btnCount);
            isBackButtonVisible13 = btnCount > 0;
        } catch (Exception e) {
            isBackButtonVisible13 = false;
        }
        test.log(Status.INFO, "Step 13: iOS Back button visible before click: " + isBackButtonVisible13);
        Assert.assertTrue(isBackButtonVisible13, "Back button should be visible before clicking back");
        test.log(Status.INFO, "Step 13: Clicking iOS Back button");
        try {
            driver.findElement(org.openqa.selenium.By.xpath("//XCUIElementTypeButton")).click();
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click iOS Back button: " + e.getMessage());
            Assert.fail("Failed to click iOS Back button");
        }
        test.log(Status.PASS, "✓ Step 13c: iOS Back button clicked");
        Thread.sleep(1000);

        // Step 14: Click See All 3, verify Watch page, click back
        test.log(Status.INFO, "Step 14: Clicking See All 3");
        abChopraHousePage.clickSeeAll3();
        test.log(Status.PASS, "✓ Step 14a: See All 3 clicked");
        Thread.sleep(1000);

        test.log(Status.INFO, "Step 14: Verifying Watch page is displayed");
        Assert.assertTrue(abChopraHousePage.isWatchPageDisplayed(), "Watch page should be displayed");
        test.log(Status.PASS, "✓ Step 14b: Watch page is displayed");

        test.log(Status.INFO, "Step 14: Validating back button is visible before click");
        boolean isBackButtonVisible14 = false;
        try {
            int btnCount = driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeButton")).size();
            test.log(Status.INFO, "Back button count (//XCUIElementTypeButton): " + btnCount);
            isBackButtonVisible14 = btnCount > 0;
        } catch (Exception e) {
            isBackButtonVisible14 = false;
        }
        test.log(Status.INFO, "Step 14: iOS Back button visible before click: " + isBackButtonVisible14);
        Assert.assertTrue(isBackButtonVisible14, "Back button should be visible before clicking back");
        test.log(Status.INFO, "Step 14: Clicking iOS Back button");
        try {
            driver.findElement(org.openqa.selenium.By.xpath("//XCUIElementTypeButton")).click();
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click iOS Back button: " + e.getMessage());
            Assert.fail("Failed to click iOS Back button");
        }
        test.log(Status.PASS, "✓ Step 14c: iOS Back button clicked");
        Thread.sleep(1000);

        // Step 15: Swipe up on container
        test.log(Status.INFO, "Step 15: Swiping up on container");
        abChopraHousePage.swipeUpOnContainer();
        test.log(Status.PASS, "✓ Step 15: Swiped up on container");
        Thread.sleep(1000);

        // Step 16: Click See All 2, verify Read page, click back
        test.log(Status.INFO, "Step 16: Clicking See All 2");
        abChopraHousePage.clickSeeAll2();
        test.log(Status.PASS, "✓ Step 16a: See All 2 clicked");
        Thread.sleep(5000);

        test.log(Status.INFO, "Step 16: Verifying Read page is displayed");
        Assert.assertTrue(abChopraHousePage.isReadPageDisplayed(), "Read page should be displayed");
        test.log(Status.PASS, "✓ Step 16b: Read page is displayed");
        Thread.sleep(5000);

        test.log(Status.INFO, "Step 16: Validating back button is visible before click");
        boolean isBackButtonVisible16 = false;
        try {
            int btnCount = driver.findElements(org.openqa.selenium.By.xpath("//XCUIElementTypeButton")).size();
            test.log(Status.INFO, "Back button count (//XCUIElementTypeButton): " + btnCount);
            isBackButtonVisible16 = btnCount > 0;
        } catch (Exception e) {
            isBackButtonVisible16 = false;
        }
        test.log(Status.INFO, "Step 16: iOS Back button visible before click: " + isBackButtonVisible16);
        Assert.assertTrue(isBackButtonVisible16, "Back button should be visible before clicking back");
        test.log(Status.INFO, "Step 16: Clicking iOS Back button");
        try {
            driver.findElement(org.openqa.selenium.By.xpath("//XCUIElementTypeButton")).click();
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click iOS Back button: " + e.getMessage());
            Assert.fail("Failed to click iOS Back button");
        }
        test.log(Status.PASS, "✓ Step 16c: iOS Back button clicked");
        Thread.sleep(5000);

        // Step 17: Click video item
        test.log(Status.INFO, "Step 17: Clicking video item");
        abChopraHousePage.clickVideoItem();
        test.log(Status.PASS, "✓ Step 17: Video item clicked");
        Thread.sleep(1000);

        // Step 18: Click search box and search 'one'
        test.log(Status.INFO, "Step 18: Clicking search box and searching for 'one'");
        abChopraHousePage.searchForOne();
        test.log(Status.PASS, "✓ Step 18: Searched for 'one'");
        Thread.sleep(1000);

        // Step 20: Click New File icon
        test.log(Status.INFO, "Step 20: Clicking New File icon");
        abChopraHousePage.clickNewFileIcon();
        test.log(Status.PASS, "✓ Step 20: New File icon clicked");
        Thread.sleep(1000);

        // Step 21: Create file by typing 'New'
        test.log(Status.INFO, "Step 21: Creating file by typing 'New'");
        abChopraHousePage.createFile("New");
        test.log(Status.PASS, "✓ Step 21: File name 'New' entered");
        Thread.sleep(1000);

        // Step 23: Click close icon
        test.log(Status.INFO, "Step 23: Clicking close icon");
        abChopraHousePage.clickCloseIcon();
        test.log(Status.PASS, "✓ Step 23: Close icon clicked");
        Thread.sleep(1000);

        // Step 24: Verify Saved dialog is displayed
        test.log(Status.INFO, "Step 24: Verifying Saved dialog is displayed");
        Assert.assertTrue(abChopraHousePage.isSavedDialogDisplayed(), "SAVED dialog should be displayed");
        test.log(Status.PASS, "✓ Step 24: SAVED dialog is displayed");

        // Step 25: Get success message
        test.log(Status.INFO, "Step 25: Getting success message");
        String savedMessage = abChopraHousePage.getSavedSuccessMessage();
        test.log(Status.PASS, "✓ Step 25: Success message captured");
        test.log(Status.INFO, "📋 Saved Success Message: " + savedMessage);
        Assert.assertEquals(savedMessage, "Your article has been successfully saved.", "Success message should confirm successful save");
        test.log(Status.PASS, "✓ Step 25: Verified correct success message displayed");

        // Step 26: Click OK button
        test.log(Status.INFO, "Step 26: Clicking OK button");
        abChopraHousePage.clickOkButton();
        test.log(Status.PASS, "✓ Step 26: OK button clicked");
        Thread.sleep(1000);

        // Step 27: Click ARCHIVE
        test.log(Status.INFO, "Step 27: Clicking ARCHIVE");
        abChopraHousePage.clickArchive();
        test.log(Status.PASS, "✓ Step 27: ARCHIVE clicked");
        Thread.sleep(1000);

        // Step 28: Verify Archive page is displayed
        test.log(Status.INFO, "Step 28: Verifying Archive page is displayed");
        Assert.assertTrue(abChopraHousePage.isArchivePageDisplayed(), "Archive page should be displayed");
        test.log(Status.PASS, "✓ Step 28: Archive page is displayed");

        // Step 29: Verify new file is in archive
        test.log(Status.INFO, "Step 29: Verifying new file is in archive");
        Assert.assertTrue(abChopraHousePage.isNewFileInArchive(), "New file should be in archive");
        test.log(Status.PASS, "✓ Step 29: New file is in archive");

        // Step 30: Click new file in archive
        test.log(Status.INFO, "Step 30: Clicking new file in archive");
        abChopraHousePage.clickNewFileInArchive();
        test.log(Status.PASS, "✓ Step 30: New file clicked");
        Thread.sleep(1000);

        // Step 31: Verify file is open
        test.log(Status.INFO, "Step 31: Verifying file is open");
        Assert.assertTrue(abChopraHousePage.isFileOpen(), "File should be open");
        test.log(Status.PASS, "✓ Step 31: File is open");

        // Step 31.1: Click menu icon
        test.log(Status.INFO, "Step 31.1: Clicking menu icon");
        abChopraHousePage.clickMenuIcon();
        test.log(Status.PASS, "✓ Step 31.1: Menu icon clicked");
        Thread.sleep(1000);

        // Step 31.2: Click EDIT
        test.log(Status.INFO, "Step 31.2: Clicking EDIT");
        abChopraHousePage.clickEdit();
        test.log(Status.PASS, "✓ Step 31.2: EDIT clicked");
        Thread.sleep(1000);

        // Step 31.3: Enter file name and type 'EDIT NEW'
        test.log(Status.INFO, "Step 31.3: Entering file name 'EDIT NEW'");
        abChopraHousePage.enterEditFileName("EDIT NEW");
        test.log(Status.PASS, "✓ Step 31.3: File name 'EDIT NEW' entered");
        Thread.sleep(1000);

        // Step 31.4: Click SAVE button
        test.log(Status.INFO, "Step 31.4: Clicking SAVE button");
        abChopraHousePage.clickSaveButton();
        test.log(Status.PASS, "✓ Step 31.4: SAVE button clicked");
        Thread.sleep(1000);

        // Step 31.5: Verify SUCCESS dialog is shown
        test.log(Status.INFO, "Step 31.5: Verifying SUCCESS dialog is shown");
        Assert.assertTrue(abChopraHousePage.isEditSuccessDialogDisplayed(), "SUCCESS dialog should be displayed");
        test.log(Status.PASS, "✓ Step 31.5: SUCCESS dialog is displayed");

        // Step 31.6: Get and show the success message
        test.log(Status.INFO, "Step 31.6: Getting and showing the success message");
        String editSuccessMsg = abChopraHousePage.getEditSuccessMessage();
        test.log(Status.PASS, "✓ Step 31.6: Success message captured");
        test.log(Status.INFO, "📋 Edit Success Message: " + editSuccessMsg);
        Assert.assertEquals(editSuccessMsg, "Your archive name has been successfully updated.", "Edit success message should confirm successful update");
        test.log(Status.PASS, "✓ Step 31.6: Verified correct edit success message displayed");

        // Step 31.7: Click OK button
        test.log(Status.INFO, "Step 31.7: Clicking OK button");
        abChopraHousePage.clickOkButtonEdit();
        test.log(Status.PASS, "✓ Step 31.7: OK button clicked");
        Thread.sleep(1000);

        // Step 31.8: Click menu icon again
        test.log(Status.INFO, "Step 31.8: Clicking menu icon again");
        abChopraHousePage.clickMenuIconAgain();
        test.log(Status.PASS, "✓ Step 31.8: Menu icon clicked again");
        Thread.sleep(1000);

        // Step 31.9: Click ORGANISE
        test.log(Status.INFO, "Step 31.9: Clicking ORGANISE");
        abChopraHousePage.clickOrganise();
        test.log(Status.PASS, "✓ Step 31.9: ORGANISE clicked");
        Thread.sleep(1000);

        // Step 32: Click remove icon
        test.log(Status.INFO, "Step 32: Clicking remove icon");
        abChopraHousePage.clickRemoveIcon();
        test.log(Status.PASS, "✓ Step 32: Remove icon clicked");
        Thread.sleep(1000);

        // Step 33: Click YES button
        test.log(Status.INFO, "Step 33: Clicking YES button");
        abChopraHousePage.clickYesButton();
        test.log(Status.PASS, "✓ Step 33: YES button clicked");
        Thread.sleep(1000);

        // Step 34: Verify Success dialog is displayed
        test.log(Status.INFO, "Step 34: Verifying Success dialog is displayed");
        Assert.assertTrue(abChopraHousePage.isSuccessDialogDisplayed(), "SUCCESS dialog should be displayed");
        test.log(Status.PASS, "✓ Step 34: SUCCESS dialog is displayed");

        // Step 35: Get delete success message
        test.log(Status.INFO, "Step 35: Getting delete success message");
        String deleteMessage = abChopraHousePage.getDeleteSuccessMessage();
        test.log(Status.PASS, "✓ Step 35: Delete success message captured");
        test.log(Status.INFO, "📋 Delete Success Message: " + deleteMessage);
        Assert.assertEquals(deleteMessage, "Your article has been successfully deleted.", "Delete message should confirm successful deletion");
        test.log(Status.PASS, "✓ Step 35: Verified correct delete success message displayed");

        // Step 36: Click OK button
        test.log(Status.INFO, "Step 36: Clicking OK button");
        abChopraHousePage.clickOkButton();
        test.log(Status.PASS, "✓ Step 36: OK button clicked");

        test.log(Status.PASS, "AB Chopra House Test Case 1 completed successfully");
    }
}