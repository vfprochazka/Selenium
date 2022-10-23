package selenium.pageobject.object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import selenium.pageobject.driver.Driver;

// single parent of all PageObjects
public abstract class PageObject {
    // protected = not public, only for child objects
    protected final Driver driver;

    public PageObject(Driver driver) {
        this.driver = driver;
        // wait until page is loaded
        this.driver.synchronization();
        // initialize all WebElement fields inside PageObject
        // ********* Required for using the notation element identifiers PageFactory
        PageFactory.initElements(driver.getDriver(), this);
    }

    // Here is called a method from driver instance, but is need this definition for all my child nodes
    // implementations in driver - here at one place..
    // Not needed implementation here.. just declare.
    protected WebElement waitForElement(WebElement element) {
        return this.driver.waitForElement(element);
    }
}
