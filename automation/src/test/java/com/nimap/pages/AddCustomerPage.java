package com.nimap.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the "Add Customer" journey.
 * Update locators after inspecting the real DOM.
 */
public class AddCustomerPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By addCustomerMenuItem = By.xpath("//a[contains(text(),'Customer')]");
    private By addNewButton = By.xpath("//button[contains(text(),'Add')]");
    private By customerNameField = By.id("customerName");
    private By customerEmailField = By.id("customerEmail");
    private By customerPhoneField = By.id("customerPhone");
    private By saveButton = By.xpath("//button[contains(text(),'Save') or contains(text(),'Submit')]");
    private By successToast = By.cssSelector(".toast, .toast-message, .Toastify__toast, .snackbar");
    private By customerInList = By.xpath("//table//td[contains(text(),'%s')]"); // %s = customer name, filled at runtime

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToAddCustomer() {
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerMenuItem)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewButton)).click();
    }

    public void addCustomer(String name, String email, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(customerNameField)).sendKeys(name);
        driver.findElement(customerEmailField).sendKeys(email);
        driver.findElement(customerPhoneField).sendKeys(phone);
        driver.findElement(saveButton).click();
    }

    public String getSuccessToastText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).getText();
    }

    public boolean isCustomerAddedInList(String customerName) {
        By dynamicLocator = By.xpath(String.format("//table//td[contains(text(),'%s')]", customerName));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dynamicLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
