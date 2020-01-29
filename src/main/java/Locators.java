import org.openqa.selenium.By;

public interface Locators {
    // HomePage
    By textInputField               = By.id("undefined");
    By emailInput                   = By.id("email");
    By passwordInput                = By.id("password");
    By signInButton                 = By.id("signin_btn");
    By messageLabel                 = By.xpath("//div[@class='p-rich_text_section']");
    By messageSet                   = By.xpath("//div[@class='c-message_kit__hover']");
    By starButton                   = By.xpath("//button[@aria-label='Star message']");
    By searchInput                  = By.xpath("(//button[@data-qa='legacy_search_header'])[1]");
    By searchInputOverlay           = By.xpath("(//*[@id=\"undefined\"])[2]");
    By searchResultOverlay          = By.xpath("//div[@class='c-search_message__body']/span");
    By itemSuccessAddedToStar       = By.xpath("//button[text()='Added to your starred items']");
    By closeSearchPainButton        = By.xpath("//button[@aria-label='Cancel']");
    By showFavoriteMessagesButton   = By.xpath("//button[@aria-label='Show Starred Items']");
    By favoriteItemsHeader          = By.xpath("//div[text()='Starred Items']");
    By favoriteItemLabel            = By.xpath("//div[text()='general']/../..//div[@class='p-rich_text_block']");
    By threeButtonsDropdown         = By.xpath("//button[@aria-label='More actions']");
    By deleteMessageButton          = By.xpath("//div[text()='Delete message']/..");
    By confirmDeleteButton          = By.xpath("//button[text()='Delete']");
}
