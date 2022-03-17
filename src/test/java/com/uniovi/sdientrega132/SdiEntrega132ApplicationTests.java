package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.pageobjects.*;
import com.uniovi.sdientrega132.util.SeleniumUtils;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SdiEntrega132ApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver ="C:\\Users\\ANDREA DELGADO\\Documents\\CURSO 2021-2022\\CUATRI 2\\SDI\\geckodriver.exe";

    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";





    @Autowired
    private UsersRepository usersRepository;

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }



     @Test
    @Order(1)
    public void PR01() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "correo1@uniovi.es", "Josefo", "Perez", "77777", "77777");
        String checkText = "Bienvenidos a nuestra red social";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // PR02. Registro de usuario con datos inválidos (email vacío, nombre vacío,
    // apellidos vacíos)
    @Test
    @Order(2)
    public void PR02() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario, dejando vacío el campo de email
        PO_SignUpView.fillForm(driver, "hola@uniovi.es", "", "González", "123456", "123456");

        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "text",PO_View.getTimeout());

    }

    @Test
    @Order(3)
    public void PR03() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario, con la repeticion de la contraseña incorrecta
        PO_SignUpView.fillForm(driver, "andrea@uniovi.es", "Marta", "González", "123456", "1234567");

        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(4)
    public void PR04() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario, con un email ya existente
        PO_SignUpView.fillForm(driver, "uo271355@uniovi.es", "Marta", "González", "123456", "123456");

        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.email.duplicate",
                PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.email.duplicate",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(5)
    public void PR05() {
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario, como administrador
        PO_LoginView.fillLoginForm(driver,"admin@email.com","admin");
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(6)
    public void PR06() {
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario, como un usuario normal
        PO_LoginView.fillLoginForm(driver,"uo271355@uniovi.es","123456");
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(7)
    public void PR07() {
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario, como un usuario normal pero con la contraseña y email vacios
        PO_LoginView.fillLoginForm(driver,"","");
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "text",PO_View.getTimeout());
    }

    @Test
    @Order(8)
    public void PR08() {
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario, como un usuario normal pero con la contraseña incorrecta
        PO_LoginView.fillLoginForm(driver,"uo271355@uniovi.es","12345678");

        List<WebElement> result = PO_LoginView.checkElementByKey(driver, "Error.login",
                PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.login",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(9)
    public void PR09() {
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        PO_LoginView.fillLoginForm(driver,"uo271355@uniovi.es","123456");

        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

        checkText = "Identificate";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(10)
    public void PR010() {
        SeleniumUtils.textIsNotPresentOnPage(driver,"Cierra sesion");
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        PO_LoginView.fillLoginForm(driver,"uo271355@uniovi.es","123456");

        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        SeleniumUtils.textIsPresentOnPage(driver,"Cierra sesion");
        // Vamos al formulario de logeo
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

        SeleniumUtils.textIsNotPresentOnPage(driver,"Cierra sesion");
    }

    @Test
    @Order(12)
    public void PR012() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); // Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        List<WebElement> elementos = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        elementos.get(0).findElement(By.id("selected")).click();
        driver.findElement(By.id("deleteButton")).click();
        SeleniumUtils.textIsNotPresentOnPage(driver, "uo271355@uniovi.es");
    }

    @Test
    @Order(13)
    public void PR013() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); // Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        //Navegamos hasta la última página
        boolean isNextPage = false;
        do {

            // Check If There is next page
            List<WebElement> nextPageLink = driver.findElements(By.id("nextPageOfList"));
            if ( !nextPageLink.isEmpty() ) {
                nextPageLink.get(0).click();
                isNextPage = true;
            } else {
                isNextPage = false;
            }

        } while (isNextPage);


        List<WebElement> users = driver.findElements(By.id("tableUsers"));

        // Seleccionamos el último usuario y lo borramos
        WebElement checkbox = users.get( users.size()-1 ).findElement(By.id("selected"));
        checkbox.click();

        WebElement btnBorrar = driver.findElement( By.id("deleteButton"));
        btnBorrar.click();

        SeleniumUtils.textIsNotPresentOnPage(driver, "lucia@uniovi.es");
    }

    @Test
    @Order(14)
    public void PR014() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); // Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        List<WebElement> elementos = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());

        elementos.get(1).findElement(By.id("selected")).click();
        elementos.get(2).findElement(By.id("selected")).click();
        //Navegamos hasta la última página
        boolean isNextPage = false;
        do {
            List<WebElement> nextPageLink = driver.findElements(By.id("nextPageOfList"));
            // Check If There is next page
            nextPageLink = driver.findElements(By.id("nextPageOfList"));
            if ( !nextPageLink.isEmpty() ) {
                nextPageLink.get(0).click();
                isNextPage = true;
            } else {
                isNextPage = false;
            }

        } while (isNextPage);

        List<WebElement> users = driver.findElements(By.id("tableUsers"));

        // Seleccionamos el último usuario y lo borramos
        WebElement checkbox = users.get( users.size()-1 ).findElement(By.id("selected"));
        checkbox.click();



        driver.findElement(By.id("deleteButton")).click();
        SeleniumUtils.textIsNotPresentOnPage(driver, "lucia@uniovi.es");
        List<WebElement> nextPageLink = driver.findElements(By.id("nextPageOfList"));
        nextPageLink.get(0).click();
        SeleniumUtils.textIsNotPresentOnPage(driver, "sara@uniovi.es");
        SeleniumUtils.textIsNotPresentOnPage(driver, "juan@uniovi.es");

    }

/*

    @Test
    @Order(11)
    public void PR11() {
        System.out.println("TEST 11");
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver,"user");

        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

        //Comprobamos que entramos en la lista usuarios
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        //Comprobamos que lista todos los usuarios del sistema
        List<User> lista = (List<User>)usersRepository.findAll();
        int size = lista.size();
        int numPags = size/3;
        for (int i=0; i<numPags; i++) {
            PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",i+1);
            for (int j=0; j<3; j++){
                checkText = lista.get(i+j).getEmail();
                result = PO_View.checkElementBy(driver, "text", checkText);
                System.out.println(i);
                Assertions.assertEquals(checkText, result.get(0).getText());

            }
        }

        checkText = "99999990A";
        result = PO_View.checkElementBy(driver, "text", checkText);
        //System.out.println(result);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }*/

}
