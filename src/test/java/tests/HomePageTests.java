package tests;

import core.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HomePageTests extends BaseTest {

    @Test(testName = "Проверка заголовка главной страницы Google")
    public void textGoogleTitle() {
        assertEquals("Google", page.title());
    }

    @Test(testName = "Проверка количества элементов в выпадающем списке поисковой выдачи")
    public void numberOfElementsOnPage() {
        homePage.acceptingCookiesIfAppears();
        homePage.fillSearchField("playwright");
        assertEquals(homePage.getListSearchElements().size(), 10);
    }

    // @Test(testName = "Проверка количества элементов в футере главной страницы Google")
    // public void numberOfElementsInFooter() {
    //     assertEquals(homePage.getListFooterElements().size(), 8);
    // }
}
