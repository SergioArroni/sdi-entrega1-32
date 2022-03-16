package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.pageobjects.PO_HomeView;
import com.uniovi.sdientrega132.pageobjects.PO_Properties;
import com.uniovi.sdientrega132.pageobjects.PO_SignUpView;
import com.uniovi.sdientrega132.pageobjects.PO_View;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.pageobjects.PO_NavView;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

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

    //[Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema
    @Test
    @Order(1)
    public void PR01() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "correo1@uniovi.es", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la vista concreta
        //PO_ListUsersView.checkKey(driver, "usersInSystem.message", PO_Properties.getSPANISH());
    }

    // PR02. Registro de usuario con datos inválidos (email vacío, nombre vacío,
    // apellidos vacíos)
    @Test
    public void PR02() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario, dejando vacío el campo de email
        PO_SignUpView.fillForm(driver, "", "Marta", "González", "holas", "hola");
        PO_View.checkElementBy(driver, "text", "Regístrate como usuario");
        PO_SignUpView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

        // Rellenamos el formulario, dejando vacío el campo de nombre
        PO_SignUpView.fillForm(driver, "marta@uniovi.es", "", "González", "holas", "hola");
        PO_View.checkElementBy(driver, "text", "Regístrate como usuario");
        PO_SignUpView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

        // Rellenamos el formulario, dejando vacío el campo de apellido
        PO_SignUpView.fillForm(driver, "marta@uniovi.es", "Marta", "", "holas", "hola");
        PO_View.checkElementBy(driver, "text", "Regístrate como usuario");
        PO_SignUpView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

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
    }

}
