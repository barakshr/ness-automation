# Ness Amazon Automation — v0.1 (smoke)

## Prerequisites

| Tool | Version |
|------|---------|
| JDK | 17 or higher (tested with 21) |
| Maven | 3.8+ |
| Chrome | Latest stable |


## Run (From the project root)

```bash
# From the project root
mvn clean test
```

## View the Allure report

```bash
mvn allure:serve
```

## Config file path
```bash
path:  `src/test/resources/config.properties`:
```

## Data Driven Json path
```bash
path:  `src/test/resources/testdata/shopping-scenarios.json`:
```


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







