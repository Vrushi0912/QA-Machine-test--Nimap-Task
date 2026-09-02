# Nimap Infotech — QA Machine Test Submission

**Candidate:** Vrushket Vivek Mulye
**Application under test:** https://test.fieldforceconnect.com/

This repository is organized into three parts, matching the three sections of the assignment:

```
nimap-qa-project/
├── automation/        -> Maven + Selenium + TestNG framework (Login, Punch In toast, Add Customer)
├── manual-testing/     -> Manual_Test_Cases_FieldForceConnect.xlsx (Sign Up, Forgot Password, OTP, Login)
└── postman/            -> Postman collection + environment + API testing guide
```

## 1. Automation (`/automation`)

Page Object Model framework in Java using Selenium + TestNG + Maven.
- `LoginTest` — parametrized login validation (valid/invalid/blank data via Excel `@DataProvider`)
- `PunchInTest` — verifies the toast/popup message after Punch In
- `AddCustomerTest` — parametrized Add Customer flow with validation

**Before running:** open `automation/README.md` — locators must be updated to match the
real DOM (this is expected; every fresh automation project starts with a locator pass).

Run with: `cd automation && mvn clean test`

## 2. Manual Testing (`/manual-testing`)

`Manual_Test_Cases_FieldForceConnect.xlsx` contains:
- **Read Me** — how to use the workbook
- **Test Cases** — 47 test cases across Sign Up, Forgot Password, Sign with OTP, Login
- **Field Validations** — validation rules per field, per module, with valid/invalid examples
- **Bug Report** — standard bug template with one filled example row

Execute each test case against the live site, fill in Actual Result/Status, and log any
defects found on the Bug Report sheet.

## 3. Postman API Testing (`/postman`)

- `FieldForceConnect.postman_collection.json` — Login (valid/invalid) + Add Customer requests
  with built-in `pm.test()` assertions and auth-token chaining
- `FieldForceConnect.postman_environment.json` — base URL and variables
- `API_TESTING_GUIDE.md` — step-by-step on identifying the real Login/Add Customer API
  endpoints via Chrome DevTools, plus an explanation of the setup (per the evaluation criteria)

## Next steps before submission

1. Sign up on https://test.fieldforceconnect.com/ with your own email to get valid credentials.
2. Inspect the real DOM/API and replace the placeholder locators/endpoints noted above.
3. Execute the manual test cases and log any bugs found.
4. Run the automation suite and the Postman collection; capture screenshots/reports if useful.
5. Push to your GitHub repo (see commands below) and share the link before the
   **04-Sept-26, 2 PM** deadline.

## Pushing to GitHub

```bash
cd nimap-qa-project
git init
git add .
git commit -m "Nimap QA machine test submission - Vrushket Vivek Mulye"
git branch -M main
git remote add origin https://github.com/Vrushi0912/QA-Machine-test--Nimap-Task.git
git push -u origin main
```

If the repo already has a README/commit from creating it on GitHub, pull first to avoid a
rejected push:
```bash
git pull origin main --allow-unrelated-histories
git push -u origin main
```
