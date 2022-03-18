package com.uniovi.sdientrega132.pageobjects;
import com.uniovi.sdientrega132.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {

    static public void listUsers(WebDriver driver){
        //Pinchamos en la opción de lista de notas.
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'user/list')]");
        elements.get(0).click();
    }

    static public void listPageUsers(WebDriver driver){
        //Pinchamos en la opción de lista de notas.
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'user/list')]");
        elements.get(0).click();
        //Esperamos a que se muestren los enlaces de paginación la lista de notas
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
    }

    static public void enterToMenu(WebDriver driver, String menu){
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'users-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "id", menu);
        elements.get(0).click();
    }

    static public void clickOn(WebDriver driver, String contenido, int index){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", contenido);
        elements.get(index).click();
    }

}
