package selenium.pageobject.listeners;

import selenium.pageobject.test.TestClass;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        StringBuilder destination = new StringBuilder("reports/").append(new SimpleDateFormat("yyyyMMdd").format(new Date()))
                                                                 .append(File.separator)
                                                                 .append(result.getTestClass().getRealClass().getName())
                                                                 .append(File.separator)
                                                                 .append(testName)
                                                                 .append(".log");

        ThreadContext.put("testName", testName);
        ThreadContext.put("fileName", destination.toString());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TestClass testConfiguration = (TestClass) result.getInstance();
        testConfiguration.getDriver().takeScreenshot();
    }
}
