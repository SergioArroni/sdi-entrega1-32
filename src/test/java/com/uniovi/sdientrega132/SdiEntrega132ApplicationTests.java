package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SdiEntrega132ApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver ="C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

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
        //Comprobamos que entramos en la vista concreta
        //PO_ListUsersView.checkKey(driver, "usersInSystem.message", PO_Properties.getSPANISH());
    }

    // PR02. Registro de usuario con datos inválidos (email vacío, nombre vacío,
    // apellidos vacíos)
    @Test
    @Order(2)
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
    }
    //[Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema
    @Test
    @Order(11)
    public void PR11() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver,"users-menu");
        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

        //Comprobamos que lista todos los usuarios del sistema
        List<User> lista = (List<User>)usersRepository.findAll();
        String checkText;
        List<WebElement> result;

        for (int i=0; i<lista.size(); i++) {
            checkText = lista.get(i).getEmail();
            result = PO_View.checkElementBy(driver, "text", checkText);
            Assertions.assertEquals(checkText, result.get(0).getText());
        }

    }

    //[Prueba15] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema,
    //excepto el propio usuario y aquellos que sean Administradores
    @Test
    @Order(15)
    public void PR15() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver,"users-menu");
        //Accedemos a la lista de usuarios
        PO_PrivateView.listPageUsers(driver);

        //Comprobamos que lista todos los usuarios del sistema menos los admin y él mismo
        User activeUser = usersRepository.findByEmail("user01@email.com");

        List<WebElement> result = PO_View.checkElementBy(driver, "class", "usersTable");
        String checkText;
        int pageSize = result.size();
        List<User> lista = usersRepository.findAllStandard(activeUser);
        int size = lista.size();
        int numPags = size/pageSize;
        int usersInLastPage = size%pageSize;
        for (int i=0; i<numPags; i++) {
            PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",i+1);
            for (int j=0; j<pageSize; j++) {
                checkText = lista.get(i*pageSize+j).getEmail();
                result = PO_View.checkElementBy(driver, "text", checkText);
                Assertions.assertEquals(checkText, result.get(0).getText());
            }
        }
        //Nos movemos a la última página
        PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",numPags+1);
        for (int j=0; j<usersInLastPage; j++) {
            checkText = lista.get(numPags*pageSize+j).getEmail();
            result = PO_View.checkElementBy(driver, "text", checkText);
            Assertions.assertEquals(checkText, result.get(0).getText());
        }
    }

    //[Prueba16] Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
    //corresponde con el listado usuarios existentes en el sistema.
    @Test
    @Order(16)
    public void PR16() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver,"users-menu");
        //Accedemos a la lista de usuarios
        PO_PrivateView.listPageUsers(driver);

        //Hacemos una búsqueda con el campo vacío
        PO_PrivateView.fillSearch(driver, "");

        //Comprobamos que lista todos los usuarios del sistema menos los admin y él mismo
        User activeUser = usersRepository.findByEmail("user01@email.com");

        List<WebElement> result = PO_View.checkElementBy(driver, "class", "usersTable");
        String checkText;
        int pageSize = result.size();
        List<User> lista = usersRepository.findAllStandard(activeUser);
        int size = lista.size();
        int numPags = size/pageSize;
        int usersInLastPage = size%pageSize;
        for (int i=0; i<numPags; i++) {
            PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",i+1);
            for (int j=0; j<pageSize; j++) {
                checkText = lista.get(i*pageSize+j).getEmail();
                result = PO_View.checkElementBy(driver, "text", checkText);
                Assertions.assertEquals(checkText, result.get(0).getText());
            }
        }
        //Nos movemos a la última página
        PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",numPags+1);
        for (int j=0; j<usersInLastPage; j++) {
            checkText = lista.get(numPags*pageSize+j).getEmail();
            result = PO_View.checkElementBy(driver, "text", checkText);
            Assertions.assertEquals(checkText, result.get(0).getText());
        }
    }

    //[Prueba17] Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
    //muestra la página que corresponde, con la lista de usuarios vacía.
    @Test
    @Order(17)
    public void PR17() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver,"users-menu");
        //Accedemos a la lista de usuarios
        PO_PrivateView.listPageUsers(driver);

        //Hacemos una búsqueda escribiendo un texto que no exista
        PO_PrivateView.fillSearch(driver, "hola");

        //Comprobamos que aparece la lista de usuarios vacía
        List<WebElement> result = PO_View.checkElementBy(driver, "id", "tbody");

        int numUsers = result.get(0).getSize().getHeight();
        Assertions.assertEquals(0, numUsers);

    }

    //[Prueba18] Hacer una búsqueda con un texto específico y comprobar que se muestra la página que
    //corresponde, con la lista de usuarios en los que el texto especificado sea parte de su nombre, apellidos o
    //de su email.
    @Test
    @Order(18)
    public void PR18() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver,"users-menu");
        //Accedemos a la lista de usuarios
        PO_PrivateView.listPageUsers(driver);

        //Hacemos una búsqueda con un texto específico
        PO_PrivateView.fillSearch(driver, "n");

        //Comprobamos que lista todos los usuarios del sistema menos los admin y él mismo
        User activeUser = usersRepository.findByEmail("user01@email.com");

        List<WebElement> result = PO_View.checkElementBy(driver, "class", "usersTable");
        String checkText = "N";
        int pageSize = result.size();
        List<User> lista = usersRepository.findAllStandard(activeUser);
        List<User> lista2 = new ArrayList<>();
        for (User user : lista) {
            if (user.getName().contains(checkText)||user.getEmail().contains(checkText)){
                lista2.add(user);
            }
        }
        int size = lista2.size();
        int numPags = size/pageSize;
        int usersInLastPage = size%pageSize;
        for (int i=0; i<numPags; i++) {
            PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",i+1);
            for (int j=0; j<pageSize; j++) {
                checkText = lista2.get(i*pageSize+j).getEmail();
                result = PO_View.checkElementBy(driver, "text", checkText);
                Assertions.assertEquals(checkText, result.get(0).getText());
            }
        }
        //Nos movemos a la última página
        PO_PrivateView.clickOn(driver,"//a[contains(@class, 'page-link')]",numPags+1);
        for (int j=0; j<usersInLastPage; j++) {
            checkText = lista2.get(numPags*pageSize+j).getEmail();
            result = PO_View.checkElementBy(driver, "text", checkText);
            Assertions.assertEquals(checkText, result.get(0).getText());
        }
        
    }
}
