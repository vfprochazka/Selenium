package selenium.objects;

import selenium.pageobject.driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.pageobject.object.PageObject;

public class HomePage extends PageObject {
    @FindBy(css = "body > div.overall-wrapper > div:nth-child(6) > div > main > div.homepage-group-title.homepage-products-heading-4.h4")
    private WebElement content_title;
    @FindBy(xpath = "//a[contains(@href,'kategorie/lokomotivy')]")
    private WebElement menu_locomotives;
    @FindBy(xpath = "//label[@for=\"order3\"]")
    private WebElement items_top_order_most_expensive;
    private String item_BuyButtonPath = "//div[@class=\"product\"][<index>]//button[@type=\"submit\"]";

    public HomePage(Driver driver) {
        super(driver);
        System.out.println("HomePage.constructor");
        System.out.println();
    }

    public HomePage checkPage() {
        System.out.println("HomePage.checkPage");
        String welcomeText = this.waitForElement(content_title).getText();
        System.out.println("HomePage.CheckPage.welcomeText: " + welcomeText);

        // verify the URL
        System.out.println("HomePage.CheckPage URL: " + this.driver.getDriver().getCurrentUrl());
        if (this.driver.getDriver().getCurrentUrl().matches(".*www.itvlaky.cz/")) {
            System.out.println("Match!");
        } else {
            System.out.println("NOT Match!");
        }

        // verify the TITLE of the page
        System.out.println("HomePage.CheckPage title: " + this.driver.getDriver().getTitle());
        if (this.driver.getDriver().getTitle().matches("itvlaky.cz - TT, H0, N.*")) {
            System.out.println("Match!");
        } else {
            System.out.println("NOT Match!");
        }

        return this;
    }

    public HomePage navigateToLocomotives() {
        this.driver.waitForElement(menu_locomotives).click();
        this.driver.synchronization();
        return this;
    }

    public HomePage selectMostExpencive() {
        this.driver.waitForElement(items_top_order_most_expensive).click();
        this.driver.synchronization();
        return this;
    }

    public HomePage buyItem(int indexOfItem) {
        String itemBuyButtonPath = item_BuyButtonPath.replace("<index>", String.valueOf(indexOfItem));
        this.driver.waitForElement(itemBuyButtonPath).click();
        return this;
    }
}
