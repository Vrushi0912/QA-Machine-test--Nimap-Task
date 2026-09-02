# Postman API Testing — FieldForceConnect

**Candidate:** Vrushket Vivek Mulye

## 1. How the environment is set up

- **Environment**: `FieldForceConnect.postman_environment.json`
  - `baseUrl` = `https://test.fieldforceconnect.com`
  - `apiBasePath` = placeholder (`/api`) — update to the real prefix once identified (step 2)
  - `authToken` — left blank; auto-filled by the Login request's test script after a successful call
  - `email` / `password` — the credentials you signed up with
  - `customerName` / `customerEmail` / `customerPhone` — sample data for the Add Customer request

Using an environment (instead of hardcoding the URL in every request) means the whole
collection can be pointed at a different environment (dev/staging/prod) by just switching
the active environment — nothing in the requests themselves needs to change.

## 2. How to identify the real Login and Add Customer APIs

The task requires *finding* these endpoints yourself, since they aren't published:

1. Open `https://test.fieldforceconnect.com/` in Chrome.
2. Open DevTools (F12) → **Network** tab → filter by **Fetch/XHR**.
3. Sign up with your email, then perform a normal **Login** through the UI.
4. In the Network tab, find the POST request that fires when you click Login (usually
   named `login`, `signin`, `auth`, or similar). Click it and note:
   - Full request URL → this becomes `{{apiBasePath}}/<real-path>`
   - Request payload (Headers → Payload/Body tab) → these are the real field names
     (may differ from `email`/`password` — could be `username`, `userEmail`, etc.)
   - Response body → find where the auth token/session id lives (e.g. `token`,
     `accessToken`, `data.token`) and how it should be sent back (`Authorization: Bearer`,
     a custom header, or a cookie)
5. Repeat the same process while performing **Add Customer** from the dashboard, noting
   its URL and payload fields.
6. Update `apiBasePath` in the environment, and the `url`/`body`/`Authorization` header in
   the two requests inside the collection, to match exactly what you observed.

## 3. Variables for authentication (chaining requests)

`Login - Valid Credentials` has a **Tests** script that extracts the token from the
response and stores it as `{{authToken}}`:

```javascript
const json = pm.response.json();
pm.environment.set('authToken', json.token);
```

`Add Customer` then reuses it automatically via:

```
Authorization: Bearer {{authToken}}
```

This is the standard Postman pattern for auth chaining — log in once, and every
subsequent authenticated request in the collection picks up the token without
re-entering it manually.

## 4. Requests included in the collection

| Folder | Request | Method | Purpose |
|---|---|---|---|
| 01 - Auth | Login - Valid Credentials | POST | Confirms successful login (200), captures `authToken`, response-time check |
| 01 - Auth | Login - Invalid Credentials | POST | Confirms login is rejected with a client error (400/401/403) and no token returned |
| 02 - Customer | Add Customer | POST | Uses the captured `authToken` to add a customer from the dashboard, verifies success response |

Run order: **Login - Valid Credentials → Add Customer** (Postman Collection Runner will
execute folders top-to-bottom, so `authToken` is available by the time Add Customer runs).
`Login - Invalid Credentials` can run independently/anytime.

## 5. How to run

1. Import both files into Postman: `FieldForceConnect.postman_collection.json` and
   `FieldForceConnect.postman_environment.json`.
2. Select the **FieldForceConnect - Test** environment (top-right dropdown).
3. Fill in your actual `email`/`password` (the account you signed up with) in the
   environment values.
4. Update `apiBasePath` and the request URLs/bodies per Section 2 above.
5. Run requests individually, or use **Collection Runner** to execute the whole folder
   and see all `pm.test()` assertions pass/fail in one report.

## 6. Explaining the setup (for the evaluation criteria)

- **Environments & base URL**: centralizes the host so requests stay environment-agnostic.
- **Variables**: `{{baseUrl}}`, `{{apiBasePath}}`, `{{authToken}}`, and test-data variables
  keep requests reusable and avoid hardcoded, one-off values.
- **Auth chaining**: token captured once at login, reused automatically — mirrors how a
  real client (browser/app) maintains a session after authenticating.
- **Assertions (`pm.test`)**: each request validates status code, response shape, and (for
  login) that a token is/isn't present — this is what turns a manual API call into a
  repeatable automated check.
