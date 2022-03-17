package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.pageobjects.*;
import com.uniovi.sdientrega132.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SdiEntrega132ApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\ANDREA DELGADO\\Documents\\CURSO 2021-2022\\CUATRI 2\\SDI\\geckodriver.exe";

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
    public void setUp() {
        driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

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

    @Test
    @Order(11)
    public void PR11() {
        System.out.println("TEST 11");
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");

        //Entramos en el menú de usuarios
        PO_PrivateView.enterToMenu(driver, "user");

        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

        //Comprobamos que entramos en la lista usuarios
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        //Comprobamos que lista todos los usuarios del sistema
        List<User> lista = (List<User>) usersRepository.findAll();
        int size = lista.size();
        int numPags = size / 3;
        for (int i = 0; i < numPags; i++) {
            PO_PrivateView.clickOn(driver, "//a[contains(@class, 'page-link')]", i + 1);
            for (int j = 0; j < 3; j++) {
                checkText = lista.get(i + j).getEmail();
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

    // PR19. Envíar petición de amistad a alguien y que le aparezca
    @Test
    @Order(19)
    public void PR19B() {
        System.out.println("TEST 11");
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
    }

    // PR19. Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario. Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente)
    @Test
    @Order(19)
    public void PR19() {

    }

    // PR20. Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario. Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente)
    @Test
    @Order(20)
    public void PR20() {

    }


    // PR21. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un listado que contenga varias invitaciones recibidas.
    @Test
    @Order(21)
    public void PR21() {
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "alex@uniovi.es", "123456");

        // Se despliega el menú de amigos, y se clica en invitationFriends
        PO_NavView.desplegarAmigos(driver, "invitationFriends");

        // Se comprueba que alex@uniovi.es tenga mas de una 1 peticion de amistad
        List<WebElement> friends = PO_View.checkElementBy(driver, "text", "Aceptar");
        Assertions.assertTrue(friends.size() >= 1);
    }

    // PR22. Sobre el listado de invitaciones recibidas. Hacer clic en el botón/enlace de una de ellas y comprobar que dicha solicitud desaparece del listado de invitaciones.
    @Test
    @Order(22)
    public void PR22() {
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "juan@uniovi.es", "123456");

        // Se despliega el menú de usuarios, y se clica en Ver peticiones de amistad
        PO_NavView.desplegarAmigos(driver, "invitationFriends");

        // Se comprueba que Pablo tiene 1 peticion de amistad
        List<WebElement> usuarios = PO_PrivateView.checkElementBy(driver, "text", "Aceptar");
        Assertions.assertTrue(usuarios.size() == 1);

        // Se acepta la petición del usuario "Mongo"
        PO_PrivateView.enviarAceptarPeticion(driver, "Sara");

        // Se comprueba que ahora ya se ha aceptado la petición, por tanto no hay peticiones pendientes
        PO_PrivateView.clickCheck(driver, "No dispones de ninguna peticion de amistad, :(",0);
    }

    // PR23. Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser
    @Test
    @Order(23)
    public void PR23() {
        // Rellenamos el formulario de login con datos válidos
        // Inicio sesión con Pablo, que tiene varios amigos
        PO_LoginView.fillLoginForm(driver, "alex@uniovi.es", "123456");

        // Se despliega el menú de usuarios, y se clica en listFriends
        PO_NavView.desplegarAmigos(driver, "listFriends");

        // Se comprueba que alex@uniovi.es tiene 6 amigos
        List<WebElement> usuarios = SeleniumUtils.waitLoadElementsBy(driver, "text", "Email",
                PO_View.getTimeout());
        Assertions.assertTrue(usuarios.size() == 6);
    }

}
