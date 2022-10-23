package selenium.pageobject.test;

import selenium.pageobject.driver.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import selenium.pageobject.listeners.TestListener;

@Listeners(value = {TestListener.class})
public class TestClass {
    protected Driver driver;

    public Driver getDriver() {
        return driver;
    }

    @AfterClass
    public void reset() {
        System.out.println("TestClass.reset - done @AfterClass");
        if (this.driver != null) {
            this.driver.reset();
        }
    }
}
