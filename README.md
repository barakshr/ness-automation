# Ness Amazon Automation — v0.1 (smoke)

A Selenium + TestNG + Java automation framework targeting Amazon.com.
**This is v0.1** — the foundation with one smoke test to prove everything wires up.

## Prerequisites

| Tool | Version |
|------|---------|
| JDK | 17 or higher (tested with 21) |
| Maven | 3.8+ |
| Chrome | Latest stable |

Selenium Manager (bundled with Selenium 4.6+) auto-downloads the correct
`chromedriver`. No WebDriverManager needed.

## Run

```bash
# From the project root
mvn clean test
```

This runs the smoke test in `testng.xml`:
- Opens `https://www.amazon.com`
- Clicks the top search box
- Asserts the search box is present and focused

## View the Allure report

```bash
mvn allure:serve
```

Opens the HTML report in your default browser.

Alternative: `mvn allure:report` generates static HTML under `target/site/allure-maven-plugin/`.

## Project layout

```
src/main/java/com/ness/automation/
├── core/
│   ├── config/ConfigManager.java     ← immutable static config
│   └── driver/
│       ├── BrowserFactory.java       ← builds a WebDriver
│       └── DriverManager.java        ← ThreadLocal lifecycle
└── pages/
    ├── BasePage.java                 ← driver pulled from DriverManager
    └── HomePage.java                 ← open(), clickSearchBox()

src/test/java/com/ness/automation/
├── base/BaseTest.java                ← driver lifecycle only
└── tests/SmokeTest.java              ← the one test

src/test/resources/
├── config.properties                 ← all runtime settings
├── log4j2.xml                        ← logging
└── testng.xml                        ← suite
```

## Configuration

All runtime settings live in `src/test/resources/config.properties`:

| Key | Default | Purpose |
|-----|---------|---------|
| `aut.base.url` | `https://www.amazon.com` | Site under test |
| `browser.type` | `chrome` | `chrome` / `firefox` / `edge` |
| `browser.headless` | `false` | Set `true` for CI |
| `timeout.explicit` | `15` | Seconds for explicit waits |
| `report.allure.results.dir` | `target/allure-results` | Allure output |

## Logging

Log level is controlled in `src/test/resources/log4j2.xml`. To change
from `INFO` to `DEBUG`, edit the `<Logger name="com.ness.automation" level="...">`.

## Known limitations / assumptions (v0.1)

- **Guest only** — no login handling yet (backlog).
- **No cookie-banner / locale-popup dismissal yet** — if Amazon throws
  a cookie consent overlay, the smoke test may fail. Will be added in v0.2.
- **USD assumed** — the `en-US` language pin in `BrowserFactory` prevents
  locale redirects for most cases.
- **CAPTCHA** — if Amazon shows a robot check, the test will fail.
  Manual bypass via Playwright-style `page.pause()` is in the backlog.

## What's next (v0.2)

- `SearchResultsPage` + `HomePage.searchFor(query)`
- Cookie / locale popup dismissal in `HomePage.open()`
- First data-driven test via `@DataProvider` reading JSON
