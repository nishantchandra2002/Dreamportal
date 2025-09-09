package com.dreamportal.dream_portal_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DreamSummaryPage {
    private WebDriver driver;

    @FindBy(id = "good-dreams")
    private WebElement goodDreamsCount;

    @FindBy(id = "bad-dreams")
    private WebElement badDreamsCount;

    @FindBy(id = "total-dreams")
    private WebElement totalDreamsCount;

    @FindBy(id = "recurring-dreams")
    private WebElement recurringDreamsCount;

    public DreamSummaryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getGoodDreamsCount() {
        return Integer.parseInt(goodDreamsCount.getText());
    }

    public int getBadDreamsCount() {
        return Integer.parseInt(badDreamsCount.getText());
    }

    public int getTotalDreamsCount() {
        return Integer.parseInt(totalDreamsCount.getText());
    }

    public int getRecurringDreamsCount() {
        return Integer.parseInt(recurringDreamsCount.getText());
    }
}