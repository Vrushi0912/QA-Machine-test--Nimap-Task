package com.nimap.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Punch In action and its toast/popup confirmation.
 * Update locators after inspecting the real DOM.
 */
public class PunchInPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By punchInButton = By.xpath("//button[contains(text(),'Punch In')]");
    // Toasts are often short-lived - use a broad, resilient locator
    private By toastMessage = By.cssSelector(".toast, .toast-message, .Toastify__toast, .snackbar");

    public PunchInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickPunchIn() {
        wait.until(ExpectedConditions.elementToBeClickable(punchInButton)).click();
    }

    /**
     * Toasts disappear quickly, so we wait for it to become visible
     * immediately after triggering the action, and capture the text
     * before it fades out.
     */
    public String getToastMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).getText();
    }

    public boolean isToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
