import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
    private static final Logger LOG = Logger.getLogger(BaseClass.class.getName());
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    static String driverPath = "";

    public static final WebDriver getDriver() {
        return driver;
    }

    public static final void setDriver(String webDriverType) {
        String requestedDriver = webDriverType.toUpperCase();

        if (System.getProperty("os.name").toUpperCase().contains("MAC"))
            driverPath = "./resources/webdrivers/mac/chromedriver";
        else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
            driverPath = "./resources/webdrivers/pc/chromedriver.exe";
        else
            throw new IllegalArgumentException("Unknown OS");

        if (requestedDriver.equals("SAFARI")) {
            BaseClass.driver = new SafariDriver();
            LOG.info("Browser is: Safari");
        } else if (requestedDriver.equals("CHROME")) {
            LOG.info("Browser is: Chrome;");
            System.setProperty("webdriver.chrome.driver", driverPath);
            System.setProperty("webdriver.chrome.silentOutput", "true");
            ChromeOptions option = new ChromeOptions();
            option.addArguments("disable-infobars");
            option.addArguments("--disable-notifications");

            if (System.getProperty("os.name").toUpperCase().contains("MAC"))
                option.addArguments("-start-fullscreen");
            else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
                option.addArguments("--start-maximized");
            else
                throw new IllegalArgumentException("Unknown OS");
            BaseClass.driver = new ChromeDriver(option);
        } else {
            LOG.error("Unknown browser");
            throw new IllegalArgumentException();
        }
        wait = new WebDriverWait(driver, 10);
    }
}
