package com.nimap.tests;

import com.nimap.pages.LoginPage;
import com.nimap.pages.PunchInPage;
import com.nimap.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Task 2: Verify the Toast/Popup message after the PunchIn
 *
 * Flow: Login (valid credentials) -> Punch In -> assert toast text/visibility.
 */
public class PunchInTest extends BaseTest {

    @Test(description = "Verify toast/popup appears with correct message after Punch In")
    public void testPunchInToastMessage() {
        // Step 1: Login with valid credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Pre-condition failed: login was not successful");

        // Step 2: Punch In
        PunchInPage punchInPage = new PunchInPage(driver);
        punchInPage.clickPunchIn();

        // Step 3: Validate toast/popup
        Assert.assertTrue(punchInPage.isToastDisplayed(), "Toast/popup did not appear after Punch In");
        String toastText = punchInPage.getToastMessageText();
        System.out.println("Toast message captured: " + toastText);

        // Update the expected text below to match the actual message on the app
        Assert.assertTrue(
                toastText.toLowerCase().contains("punch") || toastText.toLowerCase().contains("success"),
                "Toast text did not contain expected confirmation wording. Actual: " + toastText);
    }
}
