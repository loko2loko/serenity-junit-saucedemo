package starter.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomChromeDriver {

    private static final Logger log = LoggerFactory.getLogger(CustomChromeDriver.class);

    public static WebDriver createDriver() {
        log.info("Setting up ChromeDriver...");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Disable password manager
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        // Headless mode check
        boolean headless = isHeadlessMode();
        log.info("Headless mode: {}", headless);

        if (headless) {
            log.info("🚀 Configuring HEADLESS Chrome");
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        } else {
            log.info("👁️ Configuring HEADED Chrome (visible browser)");
        }

        // Common arguments
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        log.info("✓ ChromeDriver created successfully");

        return driver;
    }

    /**
     * Priority order:
     * 1. -Dheadless=true/false (System property)
     * 2. HEADLESS=true/false (Environment variable)
     * 3. CI=true (CI environment detection)
     */
    private static boolean isHeadlessMode() {
        // 1. System property (highest priority)
        String sysProp = System.getProperty("headless");
        if (sysProp != null) {
            log.debug("Using system property: headless={}", sysProp);
            return Boolean.parseBoolean(sysProp);
        }

        // 2. Environment variable
        String envVar = System.getenv("HEADLESS");
        if (envVar != null) {
            log.debug("Using environment variable: HEADLESS={}", envVar);
            return Boolean.parseBoolean(envVar);
        }

        // 3. CI detection
        boolean ci = System.getenv("CI") != null ||
                System.getenv("GITHUB_ACTIONS") != null;
        if (ci) {
            log.debug("CI environment detected");
        }

        return ci;
    }
}