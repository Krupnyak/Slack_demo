import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static Util.Config.URI;
import static Util.Config.getProperty;

public class HomePage extends BaseClass implements Locators {
    private static final Logger LOG = Logger.getLogger(HomePage.class.getName());
    Common common = new Common();

    public HomePage openHomePage() {
        driver.get(getProperty(URI));
        wait.until(ExpectedConditions.presenceOfElementLocated(signInButton));
        LOG.info("Home page opened");
        return this;
    }

    public HomePage loginIntoAccount() {
        common.insertTextIntoInput(emailInput, getProperty("userName"));
        common.insertTextIntoInput(passwordInput, System.getProperty("password"));
        common.hitEnter(signInButton);
        wait.until(ExpectedConditions.presenceOfElementLocated(textInputField));
        LOG.info("Slack opened");
        deleteAllMessages();
        return this;
    }

    public HomePage sendMessage(String messageContent) {
        if (messageContent.length() < 1) {
            LOG.info("Message content should not be empty");
            throw new IllegalArgumentException();
        }
        common.insertTextIntoInput(textInputField, messageContent);
        LOG.info("Sending message \"" + messageContent + "\"");
        common.hitEnter(textInputField);
        return this;
    }

    public HomePage addMessageToFavorite() {
        wait.until(ExpectedConditions.elementToBeClickable(messageSet));
        common.clickOnElementWithLog(messageSet, "message", "item");
        wait.until(ExpectedConditions.presenceOfElementLocated(starButton));
        common.mouseOver(starButton);
        common.clickOnElementWithLog(starButton, "star", "image");
        wait.until(ExpectedConditions.elementToBeClickable(itemSuccessAddedToStar));
        // hard sleep required
        common.hardSleep(5000);
        return this;
    }

    public HomePage searchFavoriteMessages() {
        common.hardSleep(4000);
        common.clickOnElementWithLog(searchInput, "search", "input");
        wait.until(ExpectedConditions.elementToBeClickable(textInputField));
        common.insertTextIntoInput(searchInputOverlay, "has:star");
        common.hitEnter(searchInputOverlay);
        return this;
    }

    public HomePage closeSearchPain() {
        if (common.isElementVisible(closeSearchPainButton)) {
            common.clickOnElementWithLog(closeSearchPainButton, "close search pain", "");
            LOG.info("Search pain closed");
        }
        return this;
    }

    public HomePage searchForItemInStartBar() {
        common.hardSleep(2000);
        common.clickOnElementWithLog(showFavoriteMessagesButton, "show favorite messages", "image");
        wait.until(ExpectedConditions.presenceOfElementLocated(favoriteItemsHeader));
        common.hardSleep(2000);
        LOG.info("Search favorite bar opened");
        return this;
    }

    public HomePage closeSearchForItemInStartBar() {
        common.clickOnElementWithLog(showFavoriteMessagesButton, "show favorite messages", "image");
        return this;
    }

    public HomePage deleteAllMessages() {
        while (common.isElementVisible(messageSet)) {
            common.clickOnElementWithLog(messageSet, "message", "label");
            wait.until(ExpectedConditions.presenceOfElementLocated(threeButtonsDropdown));
            common.clickOnElementWithLog(threeButtonsDropdown, "more options", "dropdown");
            wait.until(ExpectedConditions.presenceOfElementLocated(deleteMessageButton));
            common.clickOnElementWithLog(deleteMessageButton, "delete message", "");
            wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton));
            common.clickOnElementWithLog(confirmDeleteButton, "delete", "");
            common.hardSleep(1000);
        }
        return this;
    }
}
