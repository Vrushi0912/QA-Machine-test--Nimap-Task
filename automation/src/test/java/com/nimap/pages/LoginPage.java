package com.nimap.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Login screen.
 *
 * IMPORTANT: The By locators below are PLACEHOLDERS.
 * Open the site in Chrome -> right click each field -> Inspect,
 * and replace the locator strings with the real id/name/css/xpath
 * you find in the DOM. That is expected as part of the assignment -
 * every real QA project needs locators updated per-application.
 */
public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---- Locators (update after inspecting the real page) ----
    private By usernameField = By.id("username");          // e.g. By.name("email") / By.xpath("//input[@formcontrolname='username']")
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//button[contains(text(),'Login') or contains(text(),'Sign In')]");
    private By errorMessage = By.className("error-message"); // toast/inline error for invalid login
    private By dashboardMarker = By.xpath("//*[contains(text(),'Dashboard')]"); // proof login succeeded

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String username) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        el.clear();
        el.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement el = driver.findElement(passwordField);
        el.clear();
        el.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isLoginSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardMarker)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
