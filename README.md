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
├── assertions/
│   └── AssertBudgetInCart.java
├── core/
│   ├── config/ConfigManager.java
│   ├── driver/
│   │   ├── BrowserFactory.java
│   │   └── DriverManager.java
│   ├── report/AllureAttachments.java
│   └── DataReader.java
├── models/
│   ├── TestDataFile.java
│   └── TestScenario.java
├── pages/
│   ├── BasePage.java
│   ├── HomePage.java
│   ├── SearchResultsPage.java
│   ├── ItemPage.java
│   ├── CartPage.java
│   └── components/
│       ├── TopBarComponent.java
│       ├── PaginationComponent.java
│       └── ItemData.java
├── testng/listeners/
│   ├── DriverLifecycleListener.java
│   └── TestListener.java
├── utils/
│   ├── generalUtils/
│   │   ├── NumberParser.java
│   │   └── ScreenshotUtils.java
│   └── seleniumUtils/
│       ├── SeleniumOprationsUtil.java
│       └── SeleniumWaitUtil.java
└── workflows/
    ├── SearchWorkFlowScenarios.java
    └── ItemsWorkFlows.java

src/test/java/com/ness/automation/
├── base/BaseTest.java
└── tests/ShoppingTest.java

src/test/resources/
├── config.properties
├── log4j2.xml
├── testng.xml
└── testdata/
    └── shopping-scenarios.json
```

## Configuration

All runtime settings live in `src/test/resources/config.properties`:

| Key | Default | Purpose |
|-----|---------|---------|
| `aut.base.url` | `https://www.amazon.com` | Site under test |
| `browser.type` | `chrome` | `chrome` / `firefox` / `edge` |
| `browser.headless` | `false` | Set `true` for CI |
| `timeout.explicit` | `15` | Seconds for explicit waits |
| `report.allure.results.dir` | `allure-results` (optional) | Documented default; Allure path is set by Maven (`pom.xml`) to `./allure-results` at project root |

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
