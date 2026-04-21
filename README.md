# Ness Amazon Automation 

## Project 
This is a Java Maven project with TestNg framework for testing ,Selenium for UI operations, And allure as report.
It has uses:  Data driven file, Object-Oriented Programming, Page object model 

1.  in the data driven file "shopping-scenarios.json"  you can add more JSON blocks to the list
    Each one  is a different test case.
    And they will all be executed in a single run test suite run 

2. if you have  more  then a single test case in the file , then whey will run in parallel (maximum threads is 3)
    in the same run. 

3. I didn't test the "headless = true" in config file , so i am not sure it will work 

4. Only after you run the test suite with the maven command (seen below) , open the allure report (command bellow) 
   the report ( for some reason) only show a single run  
   So if you run the  test suite a second time , you will need to close the report process and reopen it  

5. There are screen shots in the report, you need to drill down the tree of report logs to see them (that's how allure build it)



## About the exercise 

1. I tried to follow the instructions , hope i was correct 

2. The Ecommerce site is Amazon.
   I didnt filter the results with the amazon price filter because  sometimes it doesn't appear in the search results there

3. In section 4.1 of the assignment, you requested to use xpath in order to extract the items with a price lower then the max 
   Unfortunately i only saw this after i already implemented a different way to filter the prices and extract the items 



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

##






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


## Project Extrenal




