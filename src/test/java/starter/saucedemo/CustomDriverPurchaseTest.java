package starter.saucedemo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import starter.drivers.CustomChromeDriver;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomDriverPurchaseTest {

    private static final Logger log = LoggerFactory.getLogger(CustomDriverPurchaseTest.class);

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    // ✅ PRIDANÉ - Bridge JUL to SLF4J
    @BeforeAll
    public static void setupLogging() {
        // Remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // Add SLF4JBridgeHandler to j.u.l's root logger
        SLF4JBridgeHandler.install();

        // Optionally set JUL root level to reduce overhead
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.FINEST);

        log.info("✓ JUL to SLF4J bridge installed - Selenium logs now use Logback");
    }

    @BeforeEach
    public void setup() {
        driver = new CustomChromeDriver().createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("✓ Custom Chrome driver initialized");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
            log.info("✓ Driver closed");
        }
    }

    @Test
    @DisplayName("Complete purchase flow with custom Chrome driver")
    public void completePurchaseFlow() {
        log.info("=== COMPLETE E2E PURCHASE TEST ===");

        // STEP 1: Login
        log.info("=== STEP 1: LOGIN ===");
        driver.get("https://www.saucedemo.com");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));

        driver.findElement(By.id("user-name")).sendKeys(VALID_USERNAME);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();

        // Wait for navigation to inventory
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        String currentUrl = driver.getCurrentUrl();
        log.debug("Current URL: {}", currentUrl);
        assertThat(currentUrl).contains("inventory.html");
        log.info("✓ Successfully logged in (NO PASSWORD POPUP!)");

        // STEP 2: Add products to cart
        log.info("=== STEP 2: ADD PRODUCTS ===");
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        String cartBadge = driver.findElement(By.className("shopping_cart_badge")).getText();
        log.debug("Cart items: {}", cartBadge);
        assertThat(cartBadge).isEqualTo("2");
        log.info("✓ Added 2 products to cart");

        // STEP 3: Go to cart
        log.info("=== STEP 3: VIEW CART ===");
        driver.findElement(By.className("shopping_cart_link")).click();

        wait.until(ExpectedConditions.urlContains("cart.html"));

        int cartItems = driver.findElements(By.className("cart_item")).size();
        log.debug("Items in cart: {}", cartItems);
        assertThat(cartItems).isEqualTo(2);
        log.info("✓ Cart verified");

        // STEP 4: Checkout
        log.info("=== STEP 4: CHECKOUT ===");
        driver.findElement(By.id("checkout")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));

        driver.findElement(By.id("first-name")).sendKeys("Jan");
        driver.findElement(By.id("last-name")).sendKeys("Novak");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        log.info("✓ Checkout information entered");

        // STEP 5: Overview
        log.info("=== STEP 5: ORDER OVERVIEW ===");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));

        String subtotal = driver.findElement(By.className("summary_subtotal_label")).getText();
        String total = driver.findElement(By.className("summary_total_label")).getText();

        log.debug("Subtotal: {}", subtotal);
        log.debug("Total: {}", total);

        assertThat(subtotal).contains("$");
        assertThat(total).contains("$");
        log.info("✓ Order overview verified");

        // STEP 6: Finish
        log.info("=== STEP 6: COMPLETE ORDER ===");
        driver.findElement(By.id("finish")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("complete-header")));

        String completeHeader = driver.findElement(By.className("complete-header")).getText();
        log.info("Success message: {}", completeHeader);

        assertThat(completeHeader).containsIgnoringCase("Thank you for your order");

        log.info("✓ ORDER SUCCESSFULLY COMPLETED!");
        log.info("=== TEST PASSED ===");
    }

    @Test
    @DisplayName("Simple login test - verify no password popup")
    public void simpleLoginTest() {
        log.info("=== SIMPLE LOGIN TEST ===");

        driver.get("https://www.saucedemo.com");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));

        driver.findElement(By.id("user-name")).sendKeys(VALID_USERNAME);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();

        wait.until(ExpectedConditions.urlContains("inventory.html"));

        String currentUrl = driver.getCurrentUrl();
        log.debug("Current URL: {}", currentUrl);

        assertThat(currentUrl)
                .as("Should be on inventory page without password popup blocking")
                .contains("inventory.html");

        log.info("✓ Login successful - NO PASSWORD POPUP!");
        log.info("✓ TEST PASSED");
    }

    @Test
    @DisplayName("Purchase single item")
    public void purchaseSingleItem() {
        log.info("=== SINGLE ITEM PURCHASE ===");

        // Login
        driver.get("https://www.saucedemo.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));

        driver.findElement(By.id("user-name")).sendKeys(VALID_USERNAME);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();

        wait.until(ExpectedConditions.urlContains("inventory.html"));
        log.info("✓ Logged in");

        // Add one item
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        assertThat(driver.findElement(By.className("shopping_cart_badge")).getText()).isEqualTo("1");
        log.info("✓ Added 1 item");

        // Checkout
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));

        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));

        driver.findElement(By.id("first-name")).sendKeys("Maria");
        driver.findElement(By.id("last-name")).sendKeys("Kovacova");
        driver.findElement(By.id("postal-code")).sendKeys("54321");
        driver.findElement(By.id("continue")).click();
        log.info("✓ Checkout info entered");

        // Finish
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        driver.findElement(By.id("finish")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("complete-header")));

        String completeHeader = driver.findElement(By.className("complete-header")).getText();
        assertThat(completeHeader).containsIgnoringCase("Thank you for your order");

        log.info("✓ Single item purchase completed!");
        log.info("✓ TEST PASSED");
    }
}