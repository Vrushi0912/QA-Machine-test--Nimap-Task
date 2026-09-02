package com.nimap.tests;

import com.nimap.pages.AddCustomerPage;
import com.nimap.pages.LoginPage;
import com.nimap.utils.ConfigReader;
import com.nimap.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Task 3: Add Customer (using parametrization and Validate it)
 *
 * Data pulled from testdata.xlsx -> sheet "CustomerData" so the same
 * flow adds multiple customers and validates each was created successfully.
 */
public class AddCustomerTest extends BaseTest {

    @DataProvider(name = "customerData")
    public Object[][] customerData() {
        return ExcelUtils.getData("src/test/resources/testdata.xlsx", "CustomerData");
    }

    @Test(dataProvider = "customerData", description = "Add customer with multiple data sets and validate")
    public void testAddCustomer(String name, String email, String phone) {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Pre-condition failed: login was not successful");

        // Step 2: Navigate & add customer
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);
        addCustomerPage.navigateToAddCustomer();
        addCustomerPage.addCustomer(name, email, phone);

        // Step 3: Validate success toast
        String toastText = addCustomerPage.getSuccessToastText();
        Assert.assertTrue(toastText.toLowerCase().contains("success") || toastText.toLowerCase().contains("added"),
                "Unexpected toast after adding customer: " + toastText);

        // Step 4: Validate customer now appears in the list/table
        Assert.assertTrue(addCustomerPage.isCustomerAddedInList(name),
                "Newly added customer '" + name + "' was not found in the customer list");
    }
}
