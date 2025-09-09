package com.dreamportal.dream_portal_tests.tests;

import com.dreamportal.dream_portal_tests.base.BaseTest;
import com.dreamportal.dream_portal_tests.pages.DreamDiaryPage;
import com.dreamportal.dream_portal_tests.pages.DreamSummaryPage;
import com.dreamportal.dream_portal_tests.pages.HomePage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.dreamportal.dream_portal_tests.services.OpenAIService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DreamPortalTests extends BaseTest {

    @Test(priority = 1, description = "Verify Home Page loading animation and navigation")
    @Description("This test verifies the loading animation, content visibility, and navigation to other pages.")
    public void testHomePageLoadingAndNavigation() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        // 1. Verify loading animation appears
        Assert.assertTrue(homePage.isLoadingAnimationVisible(), "Loading animation did not appear.");

        // 2. Verify it disappears and main content is visible
        homePage.waitForLoadingAnimationToDisappear();
        Assert.assertTrue(homePage.isMainContentVisible(), "Main content is not visible after loading.");

        // 3. Click "My Dreams" and verify new tabs open
        String originalWindow = driver.getWindowHandle();
        homePage.clickMyDreamsButton();

        // Wait for new windows to open
        Thread.sleep(1000); // Simple wait for demo purposes

        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertEquals(allWindows.size(), 3, "Expected 3 tabs to be open.");

        // Switch to other tabs to verify their titles/content
        for (String windowHandle : allWindows) {
            if (!originalWindow.equalsIgnoreCase(windowHandle)) {
                driver.switchTo().window(windowHandle);
                String pageTitle = driver.getTitle();
                Assert.assertTrue(pageTitle.equals("Dream Diary") || pageTitle.equals("Dream Totals"), "Incorrect page opened.");
            }
        }
    }

    @Test(priority = 2, description = "Validate the content of the Dream Log Table")
    @Description("This test validates the number of entries, dream types, and data integrity of the diary table.")
    public void testDreamDiaryTableValidation() {
        driver.get("https://arjitnigam.github.io/myDreams/dreams-diary.html");
        DreamDiaryPage diaryPage = new DreamDiaryPage(driver);

        // 1. Verify exactly 10 dream entries
        Assert.assertEquals(diaryPage.getDreamEntriesCount(), 10, "Expected 10 dream entries.");

        // 2. Verify dream types are only "Good" or "Bad"
        List<String> dreamTypes = diaryPage.getDreamTypes();
        for (String type : dreamTypes) {
            Assert.assertTrue(type.equals("Good") || type.equals("Bad"), "Invalid dream type found: " + type);
        }

        // 3. Verify each row has all three columns filled
        Assert.assertTrue(diaryPage.areAllRowsFilled(), "Found rows with empty cells.");
    }

    @Test(priority = 3, description = "Verify the statistics on the Summary Page")
    @Description("This test validates the dream counts and recurring dreams logic on the summary page.")
    public void testDreamSummaryStats() {
        // First, get the data from the diary page to identify recurring dreams
        driver.get("https://arjitnigam.github.io/myDreams/dreams-diary.html");
        DreamDiaryPage diaryPage = new DreamDiaryPage(driver);
        List<String> dreamNames = diaryPage.getDreamNames();

        // Logic to find recurring dreams
        List<String> recurringDreams = dreamNames.stream()
                .filter(name -> Collections.frequency(dreamNames, name) > 1)
                .distinct()
                .collect(Collectors.toList());

        // Assert that the correct dreams are identified as recurring
        Assert.assertTrue(recurringDreams.contains("Flying over mountains"));
        Assert.assertTrue(recurringDreams.contains("Lost in maze"));

        // Now, verify the stats on the summary page
        driver.get("https://arjitnigam.github.io/myDreams/dreams-total.html");
        DreamSummaryPage summaryPage = new DreamSummaryPage(driver);

        Assert.assertEquals(summaryPage.getGoodDreamsCount(), 6, "Good dreams count is incorrect.");
        Assert.assertEquals(summaryPage.getBadDreamsCount(), 4, "Bad dreams count is incorrect.");
        Assert.assertEquals(summaryPage.getTotalDreamsCount(), 10, "Total dreams count is incorrect.");
        Assert.assertEquals(summaryPage.getRecurringDreamsCount(), 2, "Recurring dreams count is incorrect.");
    }
    // Add this to your DreamPortalTests.java

// You'll need this import

    @Test(priority = 4, description = "BONUS: Validate dream classification using AI")
    @Description("This test uses an AI model to classify dream names and compares the result with the website's classification.")
    public void testAIDreamClassification() {
        // Only run this test if the API key is available
        if (System.getenv("OPENAI_API_KEY") == null) {
            throw new SkipException("Skipping AI test: OPENAI_API_KEY not set.");
        }

        driver.get("https://arjitnigam.github.io/myDreams/dreams-diary.html");
        DreamDiaryPage diaryPage = new DreamDiaryPage(driver);
        OpenAIService aiService = new OpenAIService();

        List<String> dreamNames = diaryPage.getDreamNames();
        List<String> dreamTypes = diaryPage.getDreamTypes();

        for (int i = 0; i < dreamNames.size(); i++) {
            String dreamName = dreamNames.get(i);
            String expectedType = dreamTypes.get(i);

            String aiClassification = aiService.classifyDream(dreamName);
            System.out.printf("Dream: '%s' | Website: %s | AI: %s%n", dreamName, expectedType, aiClassification);

            Assert.assertEquals(aiClassification, expectedType, "AI classification for '" + dreamName + "' did not match.");
        }
    }
}