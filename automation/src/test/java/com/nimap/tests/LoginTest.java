package com.nimap.tests;

import com.nimap.pages.LoginPage;
import com.nimap.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Task 1: Automate Login Journey (by parametrization technique and Validate it)
 *
 * Data is pulled from testdata.xlsx -> sheet "LoginData" so the same test
 * runs for multiple username/password/expectedResult combinations
 * (valid login, invalid password, blank username, blank password, etc.)
 */
public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelUtils.getData("src/test/resources/testdata.xlsx", "LoginData");
    }

    @Test(dataProvider = "loginData", description = "Validate login with multiple data sets")
    public void testLogin(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(loginPage.isLoginSuccessful(),
                    "Expected login to succeed for user: " + username);
        } else {
            Assert.assertFalse(loginPage.isLoginSuccessful(),
                    "Expected login to fail for user: " + username);
            // Optionally also assert the error toast/message text, e.g.:
            // Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid"));
        }
    }
}
