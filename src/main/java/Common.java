import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class Common extends BaseClass implements Locators {
    private static final Logger LOG = Logger.getLogger(Common.class.getName());

    void hightLightElement(WebElement element) {
        ((RemoteWebDriver) getDriver()).executeScript("arguments[0].setAttribute('style','border:solid 3px red');", element);
        hardSleep(100);
    }

    void unHightLightElement(WebElement element) {
        ((RemoteWebDriver) getDriver()).executeScript("arguments[0].setAttribute('style','border:solid 0px red');", element);
        hardSleep(100);
    }

    boolean isElementPresent(By by) {
        int locatorsAmount = getDriver().findElements(by).size();
        if (locatorsAmount == 1) {
            hightLightElement(getDriver().findElement(by));
            unHightLightElement(getDriver().findElement(by));
            hightLightElement(getDriver().findElement(by));
            unHightLightElement(getDriver().findElement(by));
            return true;
        } else if (locatorsAmount > 1) {
            LOG.warn("Locator \"" + by.toString() + "\" is not unique. Expected \"1\" but was \"" + locatorsAmount + "\"");
            return false;
        } else {
            LOG.warn("Locator \"" + by.toString() + "\" not exist");
            return false;
        }
    }

    boolean isElementVisible(By elementLocator) {
        if ((isElementPresent(elementLocator)) && getDriver().findElement(elementLocator).isDisplayed()) {
            return true;
        }
        return false;
    }

    void mouseOver(By elementLocator) {
        Actions actions = new Actions(getDriver());
        if (isElementPresent(elementLocator)) {
            WebElement element = getDriver().findElement(elementLocator);
            actions.moveToElement(element).build().perform();
            LOG.info("Hover over element " + elementLocator);
        } else {
            LOG.error("No able to hover over " + elementLocator);
        }
    }

    void clickOnElement(By elementLocator) {
        try {
            hightLightElement(driver.findElement(elementLocator));
            driver.findElement(elementLocator).click();
        } catch (Exception ex) {
            LOG.error("Can`t click on the element " + elementLocator.toString());
        }
    }

    void clickOnElementWithLog(By elementLocator, String elementName, String elementType) {
        clickOnElement(elementLocator);
        LOG.info("Click on \"" + elementName.toUpperCase() + "\" " + ((elementType.length() < 1) ? "button" : elementType.toLowerCase()));
    }

    void insertTextIntoInput(By inputLocator, String inputText) {
        try {
            LOG.info("Input \"" + inputText + "\" into input field\n");
            driver.findElement(inputLocator).clear();
            driver.findElement(inputLocator).sendKeys(inputText);
        } catch (NullPointerException e) {
            LOG.error("Empty input text value");
            throw new IllegalArgumentException();
        } catch (Exception e) {
            LOG.error("Cant input text into " + inputLocator.toString() + "\n" + e);
            e.printStackTrace();
        }
    }

    void hardSleep(int milieconds) {
        try {
            Thread.sleep(milieconds);
        } catch (Exception e) {
            LOG.error("Can`t sleep");
        }
    }

    void hitEnter(By elementLocator) {
        if (isElementPresent(elementLocator)) {
            driver.findElement(elementLocator).sendKeys(Keys.ENTER);
            LOG.info("Hit Enter");
        }
    }

    List<String> getAllMessagesMessages(By elementLocator) {
        List<String> listOfMessages = new ArrayList<String>();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementLocator));
        List<WebElement> listOfElements = getDriver().findElements(elementLocator);
        for (int i = 0; i < listOfElements.size(); i++) {
            listOfMessages.add(listOfElements.get(i).getText());
        }
        return listOfMessages;
    }

    String getLastMessage() {
        List<String> inputListOfMessages = getAllMessagesMessages(messageLabel);
        if (inputListOfMessages.size() < 1) return "";
        return inputListOfMessages.get(inputListOfMessages.size() - 1);
    }
}
