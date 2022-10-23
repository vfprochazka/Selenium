package selenium.gemalto.coesys.mbi.seleniumTests.tests.selenium.login;

import org.testng.annotations.Test;
import selenium.objects.HomePage;
import selenium.pageobject.driver.Driver;
import selenium.pageobject.test.TestClass;


// tests for login page
public class buy_items extends TestClass {

    // https://jira.com/browse/TEST-1
    // go to shop and buy item
    @Test
    public void buy_item() {
        if (this.driver == null) {
            this.driver = new Driver();
        }
        HomePage homePage = new HomePage(this.driver);

        homePage.checkPage()
                .navigateToLocomotives()
                .selectMostExpencive()
                .buyItem(1)
                .buyItem(2);
    }


}
