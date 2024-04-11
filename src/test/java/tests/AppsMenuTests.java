package tests;

import core.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AppsMenuTests extends BaseTest {

    @Test(testName = "Проверка наличия иконки меню приложений Google")
    public void testApplicationIconVisible() {
        assertTrue(homePage.getApplicationIcon().isVisible());
    }
}
