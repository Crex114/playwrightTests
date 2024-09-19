package com.google.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public interface HasAppPanel {

    Page getPage();

    @Step("Получение иконки меню приложений Google")
    default ElementHandle getApplicationIcon() {
        return getPage().querySelector("//a[contains(@href, 'https://www.google') and contains(@href, 'about/products')]");
    }

}
