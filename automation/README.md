# FieldForceConnect - QA Automation Project

**Author:** Vrushket Vivek Mulye

Maven + Selenium + TestNG framework for `https://test.fieldforceconnect.com/`.
Built for the Nimap Infotech QA machine test (Automation - Login, Punch In toast, Add Customer).

## Project structure (Page Object Model)

```
automation/
├── pom.xml
├── testng.xml
└── src/test/
    ├── java/com/nimap/
    │   ├── pages/       -> LoginPage, PunchInPage, AddCustomerPage (locators + actions)
    │   ├── utils/        -> ConfigReader, DriverFactory, ExcelUtils
    │   └── tests/        -> BaseTest, LoginTest, PunchInTest, AddCustomerTest
    └── resources/
        ├── config.properties  -> base URL, browser, credentials
        └── testdata.xlsx      -> LoginData sheet + CustomerData sheet (parametrization)
```

## ⚠️ Before you run this: fill in real locators

The locators inside `LoginPage.java`, `PunchInPage.java`, and `AddCustomerPage.java`
are placeholders (`By.id("username")`, etc.). You MUST:

1. Open `https://test.fieldforceconnect.com/` in Chrome.
2. Right-click each field/button (username, password, Login, Punch In, Add Customer form, Save) → **Inspect**.
3. Copy the real `id`, `name`, or a stable CSS/XPath selector.
4. Replace the placeholder `By` locators in the three Page Object classes.
5. Do the same for the toast/popup element (check the class name shown for the toast
   when Punch In / Add Customer succeeds — many frameworks use `.toast`, `.Toastify__toast`,
   `.MuiSnackbar-root`, or similar).

This step is normal and expected — no two apps have the same DOM, so a fresh
automation project always starts with a locator pass.

## How to run

```bash
mvn clean test
```

This picks up `testng.xml` (configured via the surefire plugin in `pom.xml`) and runs:
1. `LoginTest` — data-driven login (valid, invalid password, blank fields) from `testdata.xlsx`
2. `PunchInTest` — login, punch in, assert toast is shown with expected text
3. `AddCustomerTest` — login, add 2 customers from `testdata.xlsx`, validate toast + list entry

## Credentials & secrets

Don't commit real credentials to a public GitHub repo. Either:
- Set `username`/`password` as environment variables and read them in `ConfigReader`, or
- Add `config.properties` to `.gitignore` and commit a `config.properties.sample` instead.

## Parametrization technique used

`testdata.xlsx` (Apache POI) feeds TestNG `@DataProvider`s in `LoginTest` and
`AddCustomerTest`, so each test method runs once per row of data — this is the
"parametrization" the assignment asks for. (An equally valid alternative is a TestNG
`@DataProvider` with hardcoded arrays, or a CSV — Excel was chosen here so you can add/edit
data without touching code.)

## Extending to Cucumber/BDD (optional, if asked in interview)

If you want to demonstrate Cucumber knowledge, this same Page Object layer can be reused
under a `src/test/java/.../stepdefinitions` package with `.feature` files like:

```gherkin
Feature: Login
  Scenario Outline: Login with multiple credential sets
    Given user is on the login page
    When user logs in with "<username>" and "<password>"
    Then the result should be "<expectedResult>"

    Examples:
      | username               | password       | expectedResult |
      | valid_user@example.com | ValidPass@123  | success        |
      | invalid_user@example.com | WrongPass@123 | failure       |
```

## Reporting

TestNG generates a default HTML/XML report under `target/surefire-reports/`.
ExtentReports dependency is already included in `pom.xml` if you want a richer HTML report —
wire it up via a TestNG `IReporter`/listener if time allows (nice-to-have, not required).
