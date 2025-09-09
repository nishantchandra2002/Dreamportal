package com.dreamportal.dream_portal_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "loader")
    private WebElement loadingAnimation;

    @FindBy(id = "main-content")
    private WebElement mainContent;

    @FindBy(xpath = "//a[contains(text(), 'My Dreams')]")
    private WebElement myDreamsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        driver.get("https://arjitnigam.github.io/myDreams/");
    }

    public boolean isLoadingAnimationVisible() {
        return loadingAnimation.isDisplayed();
    }

    public void waitForLoadingAnimationToDisappear() {
        wait.until(ExpectedConditions.invisibilityOf(loadingAnimation));
    }

    public boolean isMainContentVisible() {
        return mainContent.isDisplayed();
    }

    public void clickMyDreamsButton() {
        myDreamsButton.click();
    }
}