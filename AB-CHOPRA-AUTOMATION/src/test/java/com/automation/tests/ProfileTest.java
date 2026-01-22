package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.ProfilePage;
import com.automation.utils.ProfileNavigationHelper;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.EditProfilePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * ✅ PROFILE PAGE TEST SUITE
 * 
 * This test class contains 5 comprehensive test cases for the Profile page:
 * 1. My Orders - Track Order
 * 2. My Orders - Send Sample
 * 3. Help & Support (4 sub-sections: Getting Started, DNA Kit, Subscription &
 * Billing, Troubleshooting)
 * 4. Legal Information (3 sub-sections: T&C, Privacy Policy, Open Content)
 * 5. Logout functionality
 * 
 * All tests use the ProfileNavigationHelper module for common navigation steps.
 */
public class ProfileTest extends BaseTest {

    /**
     * ✅ TEST CASE 1: MY ORDERS - TRACK ORDER
     * 
     * Steps:
     * 1-4: Common navigation to Profile page (via ProfileNavigationHelper)
     * 5. Click MY ORDERS
     * 6. Click TRACK ORDER button
     * 7. Verify TRACK ORDER page is displayed
     */
    @Test(priority = 1)
    public void testMyOrders_TrackOrder() throws InterruptedException {
        test = extent.createTest("Test Case 1: My Orders - Track Order");
        test.log(Status.INFO, "Testing Track Order functionality");

        // New navigation: open app, verify, click Wellbeing Dashboard, click Profile
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        try {
            WebElement wellbeingDashboard = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name,'WELLBEING')]")
                )
            );
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked Wellbeing Dashboard");
            Thread.sleep(1000);
        } catch (Exception e) {
            test.log(Status.WARNING, "Wellbeing Dashboard not found or already on dashboard");
        }
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
        profile.click();
        test.log(Status.INFO, "✓ Clicked PROFILE");
        Thread.sleep(1000);
        ProfilePage profilePage = new ProfilePage(driver);

        // Step 5: Click MY ORDERS
            test.log(Status.INFO, "Step 5: Clicking MY ORDERS");
            profilePage.robustClick("//XCUIElementTypeStaticText[@name='MY ORDERS']", "MY ORDERS");
            test.log(Status.PASS, "✓ MY ORDERS clicked");
            Thread.sleep(2000);

        // Step 6: Click TRACK ORDER button
            test.log(Status.INFO, "Step 6: Clicking TRACK ORDER button");
            profilePage.robustClick("//XCUIElementTypeStaticText[@name='TRACK ORDER']", "TRACK ORDER");
            test.log(Status.PASS, "✓ TRACK ORDER button clicked");
            Thread.sleep(2000);

        // Step 7: Verify TRACK ORDER page is displayed
        test.log(Status.INFO, "Step 7: Verifying TRACK ORDER page is displayed");
        boolean isTrackOrderPageDisplayed = profilePage.isTrackOrderPageDisplayed();
        if (!isTrackOrderPageDisplayed) {
            test.log(Status.FAIL, "TRACK ORDER page not displayed");
            Assert.fail("TRACK ORDER page validation failed");
        }
        test.log(Status.PASS, "✓ TRACK ORDER page is displayed successfully");

        // Step 8: Navigate back to Profile page for next test
            test.log(Status.INFO, "Step 8: Navigating back to Profile page");
            profilePage.robustClick("//XCUIElementTypeButton", "Back");
            test.log(Status.PASS, "✓ Navigated back to Profile page");

        test.log(Status.PASS, "Test PASSED: Track Order functionality verified");
    }

    /**
     * ✅ TEST CASE 2: MY ORDERS - SEND SAMPLE
     * 
     * Steps:
     * 1-4: Common navigation to Profile page (via ProfileNavigationHelper)
     * 5. Click MY ORDERS
     * 6. Click SEND SAMPLE button
     * 7. Verify SEND SAMPLE page is displayed
     */
    @Test(priority = 2)
    public void testMyOrders_SendSample() throws InterruptedException {
        test = extent.createTest("Test Case 2: My Orders - Send Sample");
        test.log(Status.INFO, "Testing Send Sample functionality");

        // New navigation: open app, verify, click Wellbeing Dashboard, click Profile
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        try {
            WebElement wellbeingDashboard = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name,'WELLBEING')]")
                )
            );
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked Wellbeing Dashboard");
            Thread.sleep(1000);
        } catch (Exception e) {
            test.log(Status.WARNING, "Wellbeing Dashboard not found or already on dashboard");
        }
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
        profile.click();
        test.log(Status.INFO, "✓ Clicked PROFILE");
        Thread.sleep(1000);
        ProfilePage profilePage = new ProfilePage(driver);

        // Step 5: Click MY ORDERS
            test.log(Status.INFO, "Step 5: Clicking MY ORDERS");
            profilePage.robustClick("//XCUIElementTypeStaticText[@name='MY ORDERS']", "MY ORDERS");
            test.log(Status.PASS, "✓ MY ORDERS clicked");
            Thread.sleep(2000);

        // Step 6: Click SEND SAMPLE button
            test.log(Status.INFO, "Step 6: Clicking SEND SAMPLE button");
            profilePage.robustClick("//XCUIElementTypeStaticText[@name='SEND SAMPLE']", "SEND SAMPLE");
            test.log(Status.PASS, "✓ SEND SAMPLE button clicked");
            Thread.sleep(2000);

        // Step 7: Verify SEND SAMPLE page is displayed
        test.log(Status.INFO, "Step 7: Verifying SEND SAMPLE page is displayed");
        boolean isSendSamplePageDisplayed = profilePage.isSendSamplePageDisplayed();
        if (!isSendSamplePageDisplayed) {
            test.log(Status.FAIL, "SEND SAMPLE page not displayed");
            Assert.fail("SEND SAMPLE page validation failed");
        }
        test.log(Status.PASS, "✓ SEND SAMPLE page is displayed successfully");

        // Step 8: Click MAIL SAMPLE
            test.log(Status.INFO, "Step 8: Clicking MAIL SAMPLE");
            profilePage.robustClick("//XCUIElementTypeImage[@name='MAIL SAMPLE']", "MAIL SAMPLE");
            test.log(Status.PASS, "✓ MAIL SAMPLE clicked");
            Thread.sleep(1000);

        // Step 9: Click CONFIRM SHIPMENT
            test.log(Status.INFO, "Step 9: Clicking CONFIRM SHIPMENT");
            profilePage.robustClick("//XCUIElementTypeImage[@name='CONFIRM SHIPMENT']", "CONFIRM SHIPMENT");
            test.log(Status.PASS, "✓ CONFIRM SHIPMENT clicked");
            Thread.sleep(1000);

        // Step 10: Click RECEIVED AT LAB
            test.log(Status.INFO, "Step 10: Clicking RECEIVED AT LAB");
            profilePage.robustClick("//XCUIElementTypeImage[@name='RECEIVED AT LAB']", "RECEIVED AT LAB");
            test.log(Status.PASS, "✓ RECEIVED AT LAB clicked");
            Thread.sleep(1000);

        // Step 11: Click REPORT GENERATED
            test.log(Status.INFO, "Step 11: Clicking REPORT GENERATED");
            profilePage.robustClick("//XCUIElementTypeImage[@name='REPORT GENERATED']", "REPORT GENERATED");
            test.log(Status.PASS, "✓ REPORT GENERATED clicked");
            Thread.sleep(1000);

        // Step 12: Navigate back to Profile page for next test
            test.log(Status.INFO, "Step 12: Navigating back to Profile page");
            profilePage.robustClick("//XCUIElementTypeButton", "Back");
            test.log(Status.PASS, "✓ Navigated back to Profile page");

        test.log(Status.PASS, "Test PASSED: Send Sample functionality verified");
    }

    /**
     * ✅ TEST CASE 3: HELP & SUPPORT (4 SUB-SECTIONS)
     * 
     * This test verifies all 4 Help & Support sub-sections:
     * - Getting Started
     * - DNA Kit
     * - Subscription & Billing
     * - Troubleshooting
     * 
     * Each sub-section is tested with: Click → Verify → Back
     */
    @Test(priority = 3)
    public void testHelpSupport_AllSections() throws InterruptedException {
        test = extent.createTest("Test Case 3: Help & Support - All Sections");
        test.log(Status.INFO, "Testing all Help & Support sections");

        // New navigation: open app, verify, click Wellbeing Dashboard, click Profile
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        try {
            WebElement wellbeingDashboard = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name,'WELLBEING')]")
                )
            );
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked Wellbeing Dashboard");
            Thread.sleep(1000);
        } catch (Exception e) {
            test.log(Status.WARNING, "Wellbeing Dashboard not found or already on dashboard");
        }
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
        profile.click();
        test.log(Status.INFO, "✓ Clicked PROFILE");
        Thread.sleep(1000);
        ProfilePage profilePage = new ProfilePage(driver);

        // ========== SUB-SECTION 1: GETTING STARTED ==========
        test.log(Status.INFO, "--- Testing GETTING STARTED section ---");

        // Click HELP & SUPPORT
            test.log(Status.INFO, "Step 5.1: Clicking HELP & SUPPORT");
            profilePage.robustClick("//XCUIElementTypeStaticText[@name='HELP & SUPPORT']", "HELP & SUPPORT");
            test.log(Status.PASS, "✓ HELP & SUPPORT clicked");
            Thread.sleep(2000);

        // Click GETTING STARTED
            test.log(Status.INFO, "Step 6.1: Clicking GETTING STARTED");
            profilePage.robustClick("//XCUIElementTypeStaticText[@name='GETTING STARTED']", "GETTING STARTED");
            test.log(Status.PASS, "✓ GETTING STARTED clicked");
            Thread.sleep(2000);

        // Verify GETTING STARTED page is displayed
        test.log(Status.INFO, "Step 7.1: Verifying GETTING STARTED page is displayed");
        boolean isGettingStartedDisplayed = profilePage.isGettingStartedPageDisplayed();
        if (!isGettingStartedDisplayed) {
            test.log(Status.FAIL, "GETTING STARTED page not displayed");
            Assert.fail("GETTING STARTTED page validation failed");
        }
        test.log(Status.PASS, "✓ GETTING STARTED page is displayed");

        // Step 7.2: Click HOW DO I CREATE AN ACCOUNT? FAQ
            test.log(Status.INFO, "Step 7.2: Clicking HOW DO I CREATE AN ACCOUNT?");
            profilePage.robustClick("//XCUIElementTypeImage[@name='HOW DO I CREATE AN ACCOUNT?']", "HOW DO I CREATE AN ACCOUNT?");
            test.log(Status.PASS, "✓ FAQ clicked");
            Thread.sleep(1000);

        // Step 7.3: Click DO I NEED TO DOWNLOAD AN APP TO ACCESS MY RESULTS? FAQ
            test.log(Status.INFO, "Step 7.3: Clicking DO I NEED TO DOWNLOAD AN APP?");
            profilePage.robustClick("//XCUIElementTypeImage[@name='DO I NEED TO DOWNLOAD AN APP TO ACCESS MY RESULTS?']", "DO I NEED TO DOWNLOAD AN APP?");
            test.log(Status.PASS, "✓ FAQ clicked");
            Thread.sleep(1000);

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 7.4: Navigating back to Help & Support menu");
            profilePage.robustClick("//XCUIElementTypeButton", "Back");
            test.log(Status.PASS, "✓ Navigated back to Help & Support menu");
            Thread.sleep(1000);

        // ========== SUB-SECTION 2: DNA KIT ==========
        test.log(Status.INFO, "--- Testing DNA KIT section ---");

        // Click HELP & SUPPORT
        test.log(Status.INFO, "Step 5.2: Clicking HELP & SUPPORT");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='HELP & SUPPORT']", "HELP & SUPPORT");
        test.log(Status.PASS, "✓ HELP & SUPPORT clicked");
        Thread.sleep(2000);

        // Click DNA KIT
        test.log(Status.INFO, "Step 6.2: Clicking DNA KIT");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='DNA KIT']", "DNA KIT");
        test.log(Status.PASS, "✓ DNA KIT clicked");
        Thread.sleep(2000);

        // Verify DNA KIT page is displayed
        test.log(Status.INFO, "Step 7.2: Verifying DNA KIT page is displayed");
        boolean isDnaKitDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='DNA KIT']");
        if (!isDnaKitDisplayed) {
            test.log(Status.FAIL, "DNA KIT page not displayed");
            Assert.fail("DNA KIT page validation failed");
        }
        test.log(Status.PASS, "✓ DNA KIT page is displayed");

        // Step 7.3: Click HOW LONG DOES IT TAKE TO RECEIVE MY DNA KIT? FAQ
        test.log(Status.INFO, "Step 7.3: Clicking HOW LONG DOES IT TAKE TO RECEIVE MY DNA KIT?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='HOW LONG DOES IT TAKE TO RECEIVE MY DNA KIT?']", "HOW LONG DOES IT TAKE TO RECEIVE MY DNA KIT?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Step 7.4: Click HOW DO I ACTIVATE MY DNA KIT? FAQ
        test.log(Status.INFO, "Step 7.4: Clicking HOW DO I ACTIVATE MY DNA KIT?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='HOW DO I ACTIVATE MY DNA KIT?']", "HOW DO I ACTIVATE MY DNA KIT?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Step 7.5: Click WHAT SHOULD I DO IF MY DNA KIT IS DAMAGED OR MISSING? FAQ
        test.log(Status.INFO, "Step 7.5: Clicking WHAT SHOULD I DO IF MY DNA KIT IS DAMAGED OR MISSING?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='WHAT SHOULD I DO IF MY DNA KIT IS DAMAGED OR MISSING?']", "WHAT SHOULD I DO IF MY DNA KIT IS DAMAGED OR MISSING?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 7.6: Navigating back to Help & Support menu");
        profilePage.robustClick("//XCUIElementTypeButton", "Back");
        test.log(Status.PASS, "✓ Navigated back to Help & Support menu");
        Thread.sleep(1000);

        // ========== SUB-SECTION 3: SUBSCRIPTION & BILLING ==========
        test.log(Status.INFO, "--- Testing SUBSCRIPTION & BILLING section ---");

        // Click HELP & SUPPORT
        test.log(Status.INFO, "Step 5.3: Clicking HELP & SUPPORT");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='HELP & SUPPORT']", "HELP & SUPPORT");
        test.log(Status.PASS, "✓ HELP & SUPPORT clicked");
        Thread.sleep(2000);

        // Click SUBSCRIPTION & BILLING
        test.log(Status.INFO, "Step 6.3: Clicking SUBSCRIPTION & BILLING");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='SUBSCRIPTION & BILLING']", "SUBSCRIPTION & BILLING");
        test.log(Status.PASS, "✓ SUBSCRIPTION & BILLING clicked");
        Thread.sleep(2000);

        // Verify SUBSCRIPTION & BILLING page is displayed
        test.log(Status.INFO, "Step 7.3: Verifying SUBSCRIPTION & BILLING page is displayed");
        boolean isSubscriptionBillingDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='SUBSCRIPTION & BILLING']");
        if (!isSubscriptionBillingDisplayed) {
            test.log(Status.FAIL, "SUBSCRIPTION & BILLING page not displayed");
            Assert.fail("SUBSCRIPTION & BILLING page validation failed");
        }
        test.log(Status.PASS, "✓ SUBSCRIPTION & BILLING page is displayed");

        // Step 7.7: Click WHAT PAYMENT METHODS ARE ACCEPTED? FAQ
        test.log(Status.INFO, "Step 7.7: Clicking WHAT PAYMENT METHODS ARE ACCEPTED?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='WHAT PAYMENT METHODS ARE ACCEPTED?']", "WHAT PAYMENT METHODS ARE ACCEPTED?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Step 7.8: Click CAN I PAUSE OR CANCEL MY SUBSCRIPTION? FAQ
        test.log(Status.INFO, "Step 7.8: Clicking CAN I PAUSE OR CANCEL MY SUBSCRIPTION?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='CAN I PAUSE OR CANCEL MY SUBSCRIPTION?']", "CAN I PAUSE OR CANCEL MY SUBSCRIPTION?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Step 7.9: Click WILL I BE NOTIFIED BEFORE MY SUBSCRIPTION RENEWS? FAQ
        test.log(Status.INFO, "Step 7.9: Clicking WILL I BE NOTIFIED BEFORE MY SUBSCRIPTION RENEWS?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='WILL I BE NOTIFIED BEFORE MY SUBSCRIPTION RENEWS?']", "WILL I BE NOTIFIED BEFORE MY SUBSCRIPTION RENEWS?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 7.10: Navigating back to Help & Support menu");
        profilePage.robustClick("//XCUIElementTypeButton", "Back");
        test.log(Status.PASS, "✓ Navigated back to Help & Support menu");
        Thread.sleep(1000);

        // ========== SUB-SECTION 4: TROUBLESHOOTING ==========
        test.log(Status.INFO, "--- Testing TROUBLESHOOTING section ---");

        // Click HELP & SUPPORT
        test.log(Status.INFO, "Step 5.4: Clicking HELP & SUPPORT");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='HELP & SUPPORT']", "HELP & SUPPORT");
        test.log(Status.PASS, "✓ HELP & SUPPORT clicked");
        Thread.sleep(2000);

        // Click TROUBLESHOOTING
        test.log(Status.INFO, "Step 6.4: Clicking TROUBLESHOOTING");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='TROUBLESHOOTING']", "TROUBLESHOOTING");
        test.log(Status.PASS, "✓ TROUBLESHOOTING clicked");
        Thread.sleep(2000);

        // Verify TROUBLESHOOTING page is displayed
        test.log(Status.INFO, "Step 7.4: Verifying TROUBLESHOOTING page is displayed");
        boolean isTroubleshootingDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='TROUBLESHOOTING']");
        if (!isTroubleshootingDisplayed) {
            test.log(Status.FAIL, "TROUBLESHOOTING page not displayed");
            Assert.fail("TROUBLESHOOTING page validation failed");
        }
        test.log(Status.PASS, "✓ TROUBLESHOOTING page is displayed");

        // Step 7.11: Click I CAN’T LOG INTO MY ACCOUNT. WHAT SHOULD I DO? FAQ
        test.log(Status.INFO, "Step 7.11: Clicking I CAN’T LOG INTO MY ACCOUNT. WHAT SHOULD I DO?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='I CAN’T LOG INTO MY ACCOUNT. WHAT SHOULD I DO?']", "I CAN’T LOG INTO MY ACCOUNT. WHAT SHOULD I DO?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Step 7.12: Click MY RESULTS ARE DELAYED. HOW CAN I CHECK THE STATUS? FAQ
        test.log(Status.INFO, "Step 7.12: Clicking MY RESULTS ARE DELAYED. HOW CAN I CHECK THE STATUS?");
        profilePage.robustClick("//XCUIElementTypeImage[@name='MY RESULTS ARE DELAYED. HOW CAN I CHECK THE STATUS?']", "MY RESULTS ARE DELAYED. HOW CAN I CHECK THE STATUS?");
        test.log(Status.PASS, "✓ FAQ clicked");
        Thread.sleep(1000);

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 7.13: Navigating back to Help & Support menu");
        profilePage.robustClick("//XCUIElementTypeButton", "Back");
        test.log(Status.PASS, "✓ Navigated back to Help & Support menu");
        Thread.sleep(1000);

        // Navigate back to Profile page for next test
        test.log(Status.INFO, "Step 9: Navigating back to Profile page");
        profilePage.navigateBack();
        test.log(Status.PASS, "✓ Navigated back to Profile page");

        test.log(Status.PASS, "Test PASSED: All Help & Support sections verified successfully");
    }

    /**
     * ✅ TEST CASE 4: LEGAL INFORMATION (3 SUB-SECTIONS)
     * 
     * This test verifies all 3 Legal Information sub-sections:
     * - T&C (Terms & Conditions)
     * - Privacy Policy
     * - Open Content (Open Source Content)
     * 
     * Each sub-section is tested with: Click → Verify → Back
     */
    @Test(priority = 4)
    public void testLegalInformation_AllSections() throws InterruptedException {
        test = extent.createTest("Test Case 4: Legal Information - All Sections");
        test.log(Status.INFO, "Testing all Legal Information sections");

        // New navigation: open app, verify, click Wellbeing Dashboard, click Profile
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        try {
            WebElement wellbeingDashboard = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name,'WELLBEING')]")
                )
            );
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked Wellbeing Dashboard");
            Thread.sleep(1000);
        } catch (Exception e) {
            test.log(Status.WARNING, "Wellbeing Dashboard not found or already on dashboard");
        }
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
        profile.click();
        test.log(Status.INFO, "✓ Clicked PROFILE");
        Thread.sleep(1000);
        ProfilePage profilePage = new ProfilePage(driver);

        // ========== SUB-SECTION 1: T&C (TERMS & CONDITIONS) ==========
        test.log(Status.INFO, "--- Testing T&C section ---");

        // Click LEGAL INFORMATION
        test.log(Status.INFO, "Step 5.1: Clicking LEGAL INFORMATION");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='LEGAL INFORMATION']", "LEGAL INFORMATION");
        test.log(Status.PASS, "✓ LEGAL INFORMATION clicked");
        Thread.sleep(2000);

        // Click T&C
        test.log(Status.INFO, "Step 6.1: Clicking T&C");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='T&C']", "T&C");
        test.log(Status.PASS, "✓ T&C clicked");
        Thread.sleep(2000);

        // Verify TERMS & CONDITIONS page is displayed
        test.log(Status.INFO, "Step 7.1: Verifying TERMS & CONDITIONS page is displayed");
        boolean isTermsConditionsDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='TERMS & CONDITIONS']");
        if (!isTermsConditionsDisplayed) {
            test.log(Status.FAIL, "TERMS & CONDITIONS page not displayed");
            Assert.fail("TERMS & CONDITIONS page validation failed");
        }
        test.log(Status.PASS, "✓ TERMS & CONDITIONS page is displayed");

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 8.1: Navigating back to Legal Information menu");
        profilePage.robustClick("//XCUIElementTypeButton", "Back");
        test.log(Status.PASS, "✓ Navigated back to Legal Information menu");
        Thread.sleep(1000);

        // ========== SUB-SECTION 2: PRIVACY POLICY ==========
        test.log(Status.INFO, "--- Testing PRIVACY POLICY section ---");

        // Click LEGAL INFORMATION
        test.log(Status.INFO, "Step 5.2: Clicking LEGAL INFORMATION");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='LEGAL INFORMATION']", "LEGAL INFORMATION");
        test.log(Status.PASS, "✓ LEGAL INFORMATION clicked");
        Thread.sleep(2000);

        // Click PRIVACY POLICY
        test.log(Status.INFO, "Step 6.2: Clicking PRIVACY POLICY");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='PRIVACY POLICY']", "PRIVACY POLICY");
        test.log(Status.PASS, "✓ PRIVACY POLICY clicked");
        Thread.sleep(2000);

        // Verify PRIVACY POLICY page is displayed
        test.log(Status.INFO, "Step 7.2: Verifying PRIVACY POLICY page is displayed");
        boolean isPrivacyPolicyDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='PRIVACY POLICY']");
        if (!isPrivacyPolicyDisplayed) {
            test.log(Status.FAIL, "PRIVACY POLICY page not displayed");
            Assert.fail("PRIVACY POLICY page validation failed");
        }
        test.log(Status.PASS, "✓ PRIVACY POLICY page is displayed");

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 8.2: Navigating back to Legal Information menu");
        profilePage.robustClick("//XCUIElementTypeButton", "Back");
        test.log(Status.PASS, "✓ Navigated back to Legal Information menu");
        Thread.sleep(1000);

        // ========== SUB-SECTION 3: OPEN CONTENT (OPEN SOURCE CONTENT) ==========
        test.log(Status.INFO, "--- Testing OPEN CONTENT section ---");

        // Click LEGAL INFORMATION
        test.log(Status.INFO, "Step 5.3: Clicking LEGAL INFORMATION");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='LEGAL INFORMATION']", "LEGAL INFORMATION");
        test.log(Status.PASS, "✓ LEGAL INFORMATION clicked");
        Thread.sleep(2000);

        // Click OPEN CONTENT
        test.log(Status.INFO, "Step 6.3: Clicking OPEN CONTENT");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='OPEN CONTENT']", "OPEN CONTENT");
        test.log(Status.PASS, "✓ OPEN CONTENT clicked");
        Thread.sleep(2000);

        // Verify OPEN SOURCE CONTENT page is displayed
        test.log(Status.INFO, "Step 7.3: Verifying OPEN SOURCE CONTENT page is displayed");
        boolean isOpenSourceContentDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='OPEN SOURCE CONTENT']");
        if (!isOpenSourceContentDisplayed) {
            test.log(Status.FAIL, "OPEN SOURCE CONTENT page not displayed");
            Assert.fail("OPEN SOURCE CONTENT page validation failed");
        }
        test.log(Status.PASS, "✓ OPEN SOURCE CONTENT page is displayed");

        // Navigate back using Android system back
        test.log(Status.INFO, "Step 8.3: Navigating back to Legal Information menu");
        profilePage.robustClick("//XCUIElementTypeButton", "Back");
        test.log(Status.PASS, "✓ Navigated back to Legal Information menu");
        Thread.sleep(1000);

        // Navigate back to Profile page for next test
        test.log(Status.INFO, "Step 9: Navigating back to Profile page");
        profilePage.navigateBack();
        test.log(Status.PASS, "✓ Navigated back to Profile page");

        test.log(Status.PASS, "Test PASSED: All Legal Information sections verified successfully");
    }

    /**
     * ✅ TEST CASE 5: LOGOUT FUNCTIONALITY
     * 
     * Steps:
     * 1-4: Common navigation to Profile page (via ProfileNavigationHelper)
     * 5. Click LOG OUT
     * 6. Click NO button (cancel logout)
     * 7. Click LOG OUT again
     * 8. Click YES button (confirm logout)
     * 9. Wait and verify user is logged out (Sign In page displayed)
     */
    @Test(priority = 5)
    public void testLogout() throws InterruptedException {
        test = extent.createTest("Test Case 5: Logout Functionality");
        test.log(Status.INFO, "Testing Logout functionality");

        // New navigation: open app, verify, click Wellbeing Dashboard, click Profile
        EditProfilePage editProfilePage = new EditProfilePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        try {
            WebElement wellbeingDashboard = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//XCUIElementTypeImage[@name='WELLBEING DASHBOARD HOME'] | //XCUIElementTypeImage[contains(@name,'WELLBEING')]")
                )
            );
            wellbeingDashboard.click();
            test.log(Status.INFO, "✓ Clicked Wellbeing Dashboard");
            Thread.sleep(1000);
        } catch (Exception e) {
            test.log(Status.WARNING, "Wellbeing Dashboard not found or already on dashboard");
        }
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//XCUIElementTypeStaticText[@name='PROFILE']")));
        profile.click();
        test.log(Status.INFO, "✓ Clicked PROFILE");
        Thread.sleep(1000);
        ProfilePage profilePage = new ProfilePage(driver);

        // Step 5: Click LOG OUT (first time - will click NO)
        test.log(Status.INFO, "Step 5: Clicking LOG OUT");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='LOG OUT']", "LOG OUT");
        test.log(Status.PASS, "✓ LOG OUT clicked");
        Thread.sleep(2000);

        // Step 6: Click NO button (cancel logout)
        test.log(Status.INFO, "Step 6: Clicking NO button to cancel logout");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='NO']", "NO");
        test.log(Status.PASS, "✓ NO button clicked - Logout cancelled");
        Thread.sleep(2000);

        // Step 7: Click LOG OUT again (second time - will click YES)
        test.log(Status.INFO, "Step 7: Clicking LOG OUT again");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='LOG OUT']", "LOG OUT");
        test.log(Status.PASS, "✓ LOG OUT clicked");
        Thread.sleep(2000);

        // Step 8: Click YES button (confirm logout)
        test.log(Status.INFO, "Step 8: Clicking YES button to confirm logout");
        profilePage.robustClick("//XCUIElementTypeStaticText[@name='YES']", "YES");
        test.log(Status.PASS, "✓ YES button clicked - Logout confirmed");
        Thread.sleep(3000); // Wait for logout to complete

        // Step 9: Verify user is logged out (Sign In page is displayed)
        test.log(Status.INFO, "Step 9: Verifying user is logged out");
        boolean isSignInPageDisplayed = profilePage.isElementDisplayed("//XCUIElementTypeStaticText[@name='SIGN IN']");
        if (!isSignInPageDisplayed) {
            test.log(Status.FAIL, "Sign In page not displayed - Logout failed");
            Assert.fail("Logout validation failed - User not redirected to Sign In page");
        }
        test.log(Status.PASS, "✓ Sign In page is displayed - User successfully logged out");
        test.log(Status.PASS, "Test PASSED: Logout functionality verified successfully");
    }
}