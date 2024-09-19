package core;

import com.google.pages.HomePage;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.google.constans.Constant.GOOGLE_HOME_PAGE;

public class BaseTest {
    protected Page page;
    protected HomePage homePage;
    private Browser browser;
    private BrowserContext context;
    private Boolean isTraceEnabled = false;

    @BeforeClass
    public void setUp() {

        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));//.setChannel("chrome"));

        context = browser.newContext();

        if (isTraceEnabled) {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));
        }

        page = context.newPage();
    }


    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
    }

    @BeforeMethod
    @Step("Переход на главную страницу Google")
    public void navigateToHomePage() {
        page.navigate(GOOGLE_HOME_PAGE);
        homePage = new HomePage(page);
    }

    @AfterMethod
    public void attachFilesToFailedTest(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            String uuid = UUID.randomUUID().toString();
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("build/allure-results/screenshot_" + uuid + "screenshot.png"))
                    .setFullPage(true));

            Allure.addAttachment(uuid, new ByteArrayInputStream(screenshot));
            Allure.addAttachment("source.html", "text/html", page.content());

            if (isTraceEnabled) {
                String traceFileName = String.format("build/%s_trace.zip", uuid);
                Path tracePath = Paths.get(traceFileName);
                context.tracing()
                        .stop(new Tracing.StopOptions()
                                .setPath(tracePath));
                Allure.addAttachment("trace.zip", new ByteArrayInputStream(Files.readAllBytes(tracePath)));
            }
        }
    }
}
