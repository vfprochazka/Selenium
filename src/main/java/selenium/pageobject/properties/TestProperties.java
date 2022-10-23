package selenium.pageobject.properties;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class TestProperties {
    // init of object for specific settings per browser
    private MutableCapabilities capabilities = new MutableCapabilities();

    public Execution getRun() {
        return Execution.valueOf(System.getProperty("run", "LOCAL").toUpperCase());
    }

    public Browser getBrowser() {
        return Browser.valueOf(System.getProperty("browser", "CHROME").toUpperCase());
    }

    public MutableCapabilities getCapabilities() {
        return this.getBrowser().getOptions(this.capabilities);
    }

    public URL getGridUrl() {
        try {
            return new URL(System.getProperty("gridUrl", "http://www.itvlaky.cz"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getApplicationUrl() {
        return System.getProperty("appUrl", "http://www.itvlaky.cz");
    }

    public void setCapabilities(MutableCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    public enum Execution {
        LOCAL, REMOTE
    }

    public enum Browser {

        CHROME() {
            @Override
            public ChromeOptions getOptions(MutableCapabilities capabilities) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(true);
                return chromeOptions.merge(capabilities);
            }
        },
        FIREFOX() {
            @Override
            public FirefoxOptions getOptions(MutableCapabilities capabilities) {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setProxy(new Proxy().setProxyType(Proxy.ProxyType.DIRECT));
                firefoxOptions.setAcceptInsecureCerts(true);
                return firefoxOptions.merge(capabilities);
            }
        },
        IE() {
            @Override
            public InternetExplorerOptions getOptions(MutableCapabilities capabilities) {
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                return internetExplorerOptions.merge(capabilities);
            }
        };

        public abstract MutableCapabilities getOptions(MutableCapabilities capabilities);
    }
}
