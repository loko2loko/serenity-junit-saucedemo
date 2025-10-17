# Serenity JUnit SauceDemo Test Automation

🎯 **E2E test automation** project for [SauceDemo](https://www.saucedemo.com) using **Serenity BDD**, **JUnit 5**, and **Selenium WebDriver**.

## 🚀 Features

- ✅ Custom Chrome driver with **password manager disabled** (no annoying popups!)
- ✅ Complete E2E purchase flow tests
- ✅ Clean test structure with proper logging (SLF4J + Logback)
- ✅ Multiple test scenarios (login, single item, complete purchase)
- ✅ Explicit waits and stable element interactions

## 🛠️ Tech Stack

- **Java 17**
- **Serenity BDD 4.2.9** (includes Selenium 4.25.0)
- **JUnit 5** (Jupiter 5.10.1)
- **WebDriverManager** (automatic driver management)
- **AssertJ** (fluent assertions)
- **SLF4J + Logback** (logging)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Chrome browser installed

## 🏃 Running Tests

### Run all tests:
```bash
mvn clean test
```

### Run specific test:
```bash
mvn test -Dtest=CustomDriverPurchaseTest#completePurchaseFlow
```

### Run with Serenity reports:
```bash
mvn clean verify
```

After running, open the report:
```bash
open target/site/serenity/index.html
```

## 📁 Project Structure
```
serenity-junit-saucedemo/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── starter/
│   │           └── drivers/
│   │               └── CustomChromeDriver.java
│   └── test/
│       ├── java/
│       │   └── starter/
│       │       └── saucedemo/
│       │           └── CustomDriverPurchaseTest.java
│       └── resources/
│           ├── logback.xml
│           └── serenity.conf
├── pom.xml
├── .gitignore
└── README.md
```

## 🧪 Test Scenarios

### 1. Complete Purchase Flow
- Login with valid credentials
- Add multiple items to cart
- Navigate through checkout process
- Complete order
- Verify success message

### 2. Simple Login Test
- Verify login without password popup interference

### 3. Single Item Purchase
- Simplified purchase flow with one item

## 🔧 Custom Chrome Driver

This project uses a **custom Chrome driver configuration** to completely disable Chrome's password manager popups:
```java
prefs.put("credentials_enable_service", false);
prefs.put("profile.password_manager_enabled", false);
prefs.put("profile.password_manager_leak_detection", false);
```

This ensures tests run smoothly without manual popup handling! 🎉

## 📊 Logging

Configured with **Logback** for clean, structured logging:
- Framework logs: `WARN` level
- Test logs: `INFO` level
- Debug mode available for troubleshooting

## 👤 Author

Created by loko2loko

## 📄 License

This project is for educational purposes.