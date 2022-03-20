package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.pageobjects.*;
import com.uniovi.sdientrega132.repositories.PublicationsRepository;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SdiEntrega132ApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver ="C:\\nada.exe;
    //static String GeckodriverHugo ="C:\\Users\\Hugo\\Desktop\\TERCER_CURSO_INGENIERIA\\SDI\\PRACTICA\\sesion06\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String GeckodriverSergio ="C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, GeckodriverSergio);
    static String URL = "http://localhost:8090";


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PublicationsRepository publicationsRepository;

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
        //Logueo como administrador
        PO_LoginView.login(driver, "admin@email.com", "admin");

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
        //Logueo como usuario estándar
        PO_LoginView.login(driver, "user01@email.com", "user01");

        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

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
        //Logueo como usuario estándar
        PO_LoginView.login(driver, "user01@email.com", "user01");

        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

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
        //Logueo como usuario estándar
        PO_LoginView.login(driver, "user01@email.com", "user01");

        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

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
        //Logueo como usuario estándar
        PO_LoginView.login(driver, "user01@email.com", "user01");

        //Accedemos a la lista de usuarios
        PO_PrivateView.listUsers(driver);

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

    // PR19. Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario. Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente)
    @Test
    @Order(19)
    public void PR19() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Hacemos una búsqueda para el usuario 6
        PO_PrivateView.fillSearch(driver, "user06");
        PO_PrivateView.enviarAceptarPeticion(driver, "AceptButton6");

        PO_PrivateView.logout(driver);

        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user06@email.com", "user06");

        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        //Esperamos a que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/invitation')]", 0);

        String checkText = "user01@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        PO_PrivateView.logout(driver);
    }

    // PR20. Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al
    //que ya le habíamos enviado la invitación previamente.
    @Test
    @Order(20)
    public void PR20() {
        //Volvemos a enviar una petición de amistad desde el usuario 1
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Hacemos una búsqueda para el usuario 6
        PO_PrivateView.fillSearch(driver, "user06");
        PO_PrivateView.enviarAceptarPeticion(driver, "AceptButton6");

        PO_PrivateView.logout(driver);

        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user06@email.com", "user06");

        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        //Esperamos a que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/invitation')]", 0);

        //Deberíamos de tener una sola invitación, no dos.
        // Se comprueba que user06@email.com tiene 1 invitación
        List<WebElement> friends = PO_View.checkElementBy(driver, "text", "Aceptar");
        Assertions.assertEquals(1, friends.size());
    }


    // PR21. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un listado que contenga varias invitaciones recibidas.
    @Test
    @Order(21)
    public void PR21() {
        //Le enviamos otra petición de amistad al user06, que ya tenía una del user01.
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

        //Hacemos una búsqueda para el usuario 6
        PO_PrivateView.fillSearch(driver, "user06");
        //Primera invitación
        PO_PrivateView.enviarAceptarPeticion(driver, "AceptButton6");

        PO_PrivateView.logout(driver);

        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user06@email.com", "user06");

        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/invitation')]", 0);

        // Se comprueba que user06@email.com tenga 2 peticiones de amistad
        List<WebElement> friends = PO_View.checkElementBy(driver, "text", "Aceptar");
        Assertions.assertEquals(2, friends.size());
    }

    // PR22. Sobre el listado de invitaciones recibidas. Hacer clic en el botón/enlace de una de ellas y comprobar que dicha solicitud desaparece del listado de invitaciones.
    @Test
    @Order(22)
    public void PR22() {
        //Nos loggeamos con el user06, que tiene 2 invitaciones pendientes.
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user06@email.com", "user06");

        // Se despliega el menú de usuarios, y se clica en Ver peticiones de amistad
        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/invitation')]", 0);

        PO_PrivateView.click(driver, "//button[contains(@id, 'AceptButton1')]", 0);

        // Se despliega el menú de usuarios, y se clica en Ver peticiones de amistad
        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/invitation')]", 0);

        // Se comprueba que user06 tiene 1 peticion de amistad
        List<WebElement> usuarios = PO_PrivateView.checkElementBy(driver, "text", "Aceptar");
        Assertions.assertEquals(1, usuarios.size());
        PO_PrivateView.logout(driver);
    }

    // PR23. Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser
    @Test
    @Order(23)
    public void PR23() {
        //Nos loggeamos con el user06, que tiene 1 amigo (user01)
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user06@email.com", "user06");

        // Se despliega el menú de amigos, y se clica en lista de amigos
        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/list')]", 0);

        // Se comprueba que user06 tiene un nuevo amigo
        String checkText = "user01@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        PO_PrivateView.logout(driver);
    }

    // PR35. Acceder a las publicaciones de un amigo y recomendar una publicación. Comprobar que el
    //número de recomendaciones se ha incrementado en uno y que no aparece el botón/enlace recomendar.
    @Test
    @Order(35)
    public void PR35() {
        //Nos loggeamos con el user01, que tiene 1 amigo (user06) con publicaciones
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        // Se despliega el menú de amigos, y se clica en lista de amigos
        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/list')]", 0);

        //Pulsamos el enlace para ver las publicaciones del user06
        PO_PrivateView.click(driver, "//a[contains(@href, 'publication/listFriend/user06@email.com')]", 0);

        //Comprobamos que el título de la publicación es el que debería ser.
        String checkText = "Hola :P";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        //Pulsamos el enlace para ver las publicaciones del user06
        PO_PrivateView.click(driver, "//button[contains(@class, 'btn btn-success')]", 0);

        // Se despliega el menú de amigos, y se clica en lista de amigos
        PO_PrivateView.click(driver, "//li[contains(@id, 'friends-menu')]/a", 0);
        PO_PrivateView.click(driver, "//a[contains(@href, 'friend/list')]", 0);

        //Pulsamos el enlace para ver las publicaciones del user06
        PO_PrivateView.click(driver, "//a[contains(@href, 'publication/listFriend/user06@email.com')]", 0);

        //Comprobamos que el título de la publicación es el que debería ser.
        checkText = "Hola :P";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        //Comprobamos que el la publicación tiene 1 recomendación
        checkText = "1 Recs.";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        //Comprobamos que el boton ya no está y aparece el texto correspondiente
        checkText = "Ya has recomendado esta publicación";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        PO_PrivateView.logout(driver);
    }

    // PR36. Utilizando un acceso vía URL u otra alternativa, tratar de recomendar una publicación de un
    //usuario con el que no se mantiene una relación de amistad.
    @Test
    @Order(36)
    public void PR36() {
        //Nos loggeamos con el user04, que no es amigo del user06
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

        driver.get("http://localhost:8090/publication/listFriend/user06@email.com");

        //Comprobamos que no aparecen publicaciones de amigos
        String checkText = "No hay publicaciones";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());


        PO_PrivateView.logout(driver);
    }
}

