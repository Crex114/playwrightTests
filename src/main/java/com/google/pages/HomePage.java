package com.google.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.List;


public class HomePage implements HasAppPanel {
    private final Page page;
    private final String searchLocator = "//*[@title='Поиск']";
    private final String searchListLocator = "//ul[@role='listbox']/li[@role='presentation']";
    private final String useFileCookiesCard = "//div[@id='CXQnmb']";
    private final String acceptAllButton = "//button[@id='L2AGLb']";
    private final String linksInFooter = "//a[@class='pHiOh']";


    public HomePage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    @Step("Соглашение на использование файлов Cookie")
    public void acceptingCookiesIfAppears() {
        if (page.querySelector(useFileCookiesCard) != null) {
            page.querySelector(acceptAllButton).click();
        }
    }

    @Step("Заполнение поле поиска текстом")
    public void fillSearchField(String text) {
        page.fill(searchLocator, text);
    }

    @Step("Получение списка элементов в поисковой выдаче")
    public List<ElementHandle> getListSearchElements() {
        page.waitForSelector(searchListLocator);
        return page.querySelectorAll(searchListLocator);
    }

    @Step("Получение списка элементов в футере главной страницы")
    public List<ElementHandle> getListFooterElements() {
        return page.querySelectorAll(linksInFooter);
    }

}
