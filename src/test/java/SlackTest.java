import org.testng.annotations.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.Is.is;

public class SlackTest extends BaseClass implements Locators {

    HomePage homePage = new HomePage();
    Common common = new Common();
    final String testMessage = "Test message 6";

    @BeforeSuite
    public void beforeMethod() throws Exception {
        setDriver(System.getProperty("browser"));
        homePage.openHomePage()
                .loginIntoAccount();
    }

    @AfterSuite
    public void afterMethod() {
        getDriver().close();
    }

    @Test
    public void baseTest() throws Exception {
        homePage.sendMessage(testMessage);
        String lastMessage = common.getLastMessage();
        assertThat(lastMessage.length(), describedAs("The last message should not be empty",
                greaterThan(0)));
        assertThat(testMessage.equals(lastMessage), describedAs("Invalid last message content.\n" +
                "Expected: " + testMessage + "\nActual: " + lastMessage, is(true)));
    }

    @Test(dependsOnMethods = "favoriteTest")
    public void searchTest() throws Exception {
        homePage.searchFavoriteMessages();
        List<String> foundedMessages = common.getAllMessagesMessages(searchResultOverlay);
        String[] arrayOfMessages = foundedMessages.stream().toArray(n -> new String[n]);
        assertThat(arrayOfMessages, describedAs("\"" + testMessage + "\" should be " +
                "present in search result", hasItemInArray(testMessage)));
        homePage.closeSearchPain();
    }

    @Test(dependsOnMethods = "baseTest")
    public void favoriteTest() throws Exception {
        homePage.addMessageToFavorite()
                .searchForItemInStartBar();
        assertThat(common.isElementPresent(favoriteItemLabel), describedAs(
                "Favorite item should be visible in the view pain", is(true)));
        List<String> foundedItems = common.getAllMessagesMessages(favoriteItemLabel);
        String[] arrayOfItems = foundedItems.stream().toArray(n -> new String[n]);
        assertThat(arrayOfItems, describedAs("\"" + testMessage + "\" should be " +
                "present in the view pain", hasItemInArray(testMessage)));
        homePage.closeSearchForItemInStartBar();
    }
}
