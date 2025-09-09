package com.dreamportal.dream_portal_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DreamDiaryPage {
    private WebDriver driver;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> dreamRows;

    @FindBy(xpath = "//tbody/tr/td[1]")
    private List<WebElement> dreamNames;

    @FindBy(xpath = "//tbody/tr/td[3]")
    private List<WebElement> dreamTypes;

    public DreamDiaryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getDreamEntriesCount() {
        return dreamRows.size();
    }

    public List<String> getDreamTypes() {
        return dreamTypes.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getDreamNames() {
        return dreamNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean areAllRowsFilled() {
        for (WebElement row : dreamRows) {
            List<WebElement> cells = row.findElements(org.openqa.selenium.By.tagName("td"));
            if (cells.size() != 3) return false;
            for (WebElement cell : cells) {
                if (cell.getText().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}