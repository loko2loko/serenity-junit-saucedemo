# Serenity JUnit SauceDemo Test Automation

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat&logo=apache-maven)
![Selenium](https://img.shields.io/badge/Selenium-4.25.0-green?style=flat&logo=selenium)
![Serenity BDD](https://img.shields.io/badge/Serenity%20BDD-4.2.9-brightgreen?style=flat)

E2E test automation for [SauceDemo](https://www.saucedemo.com) using **Serenity BDD**, **JUnit 5**, and **Selenium WebDriver**.

---

## 🚀 Key Features

- Custom Chrome driver with disabled password manager (no popups)
- Complete E2E purchase flow automation
- Explicit waits and stable element interactions
- Professional logging (SLF4J + Logback)
- Serenity BDD HTML reports

## 🛠️ Tech Stack

- **Java 17** | **Maven** | **Serenity BDD 4.2.9** | **Selenium 4.25.0** | **JUnit 5** | **AssertJ** | **Logback**

## 📋 Prerequisites

- Java 17+
- Maven 3.6+
- Chrome browser

## 🏃 Quick Start

```bash
# Clone repository
git clone https://github.com/yourusername/serenity-junit-saucedemo.git
cd serenity-junit-saucedemo

# Run tests
mvn clean test

# Generate Serenity report
mvn clean verify
open target/site/serenity/index.html
```

## 📁 Project Structure

```
serenity-junit-saucedemo/
├── src/main/java/starter/drivers/
│   └── CustomChromeDriver.java          # Custom Chrome config
├── src/test/java/starter/saucedemo/
│   └── CustomDriverPurchaseTest.java    # Test cases
├── src/test/resources/
│   ├── logback.xml
│   └── serenity.conf
└── pom.xml
```

## 🧪 Test Cases

### TC_001: Complete E2E Purchase Flow
**Priority:** HIGH | **Type:** End-to-End

| Step | Action | Validation |
|------|--------|------------|
| 1 | Login with valid credentials | URL contains `inventory.html` |
| 2 | Add 2 products to cart | Cart badge shows "2" |
| 3 | Navigate to cart | Cart contains 2 items |
| 4 | Complete checkout form | Navigate to overview page |
| 5 | Finish purchase | "Thank you for your order" displayed |

**Test Data:**
- Username: `standard_user`
- Password: `secret_sauce`
- Products: Backpack, Bike Light
- Checkout: Jan Novak, 12345

**Result:** ✅ PASS

---

### TC_002: Simple Login Test
**Priority:** HIGH | **Type:** Smoke Test

| Step | Action | Validation |
|------|--------|------------|
| 1 | Navigate to login page | Page loads |
| 2 | Enter credentials | Fields populated |
| 3 | Click Login | Redirected to inventory |
| 4 | Verify no popup | No password manager popup appears |

**Result:** ✅ PASS

---

### TC_003: Single Item Purchase
**Priority:** MEDIUM | **Type:** Functional

| Step | Action | Validation |
|------|--------|------------|
| 1 | Login | Inventory page displayed |
| 2 | Add 1 product | Cart badge shows "1" |
| 3 | Checkout with form data | Maria Kovacova, 54321 |
| 4 | Complete order | Success message shown |

**Result:** ✅ PASS

---

## 🔧 Custom Chrome Driver

**Problem:** Chrome password popups interrupt test execution

**Solution:**
```java
Map<String, Object> prefs = new HashMap<>();
prefs.put("credentials_enable_service", false);
prefs.put("profile.password_manager_enabled", false);
options.setExperimentalOption("prefs", prefs);
```

**Benefits:** No popups, stable tests, no manual handling

---

## 📊 What I Learned

**Technical Skills:**
- Selenium WebDriver 4.x with custom browser configuration
- JUnit 5 lifecycle and assertions with AssertJ
- SLF4J/Logback logging configuration
- Explicit waits and stable element handling

**Best Practices:**
- Browser popup handling
- Test isolation and cleanup
- Structured logging for debugging

---

## 🚀 Future Enhancements

- [ ] CI/CD integration (GitHub Actions)
- [ ] Cross-browser testing (Firefox, Edge)
- [ ] Parallel test execution

---

## 👤 Author

**loko2loko**

- LinkedIn: [Your Profile](https://linkedin.com/in/yourprofile)
- GitHub: [@loko2loko](https://github.com/loko2loko)

---

## 📄 License

Educational and portfolio purposes.

---

**⭐ Star this project if you find it useful!**
