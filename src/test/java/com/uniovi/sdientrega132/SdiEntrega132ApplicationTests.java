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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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
    //[Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema
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


        checkText = "99999990A";
        result = PO_View.checkElementBy(driver, "text", checkText);
        //System.out.println(result);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }*/

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
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

        // Se despliega el menú de usuarios, y se clica en listUser
        //PO_NavView.desplegarUsuarios(driver, "listUser");
        PO_PrivateView.listUsers(driver);

        // Se acepta la petición del usuario "Sara"
        PO_PrivateView.enviarAceptarPeticion(driver, "Sara");

    }

    // PR20. Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario. Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente)
    @Test
    @Order(20)
    public void PR20() {
        //Vamos al formulario de logueo
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

        // Se despliega el menú de usuarios, y se clica en listUser
        PO_NavView.desplegarUsuarios(driver, "listUser");

        // Se acepta la petición del usuario "Sara"
        PO_PrivateView.enviarAceptarPeticion(driver, "Sara");
    }


    // PR21. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un listado que contenga varias invitaciones recibidas.
    @Test
    @Order(21)
    public void PR21() {
        // Rellenamos el formulario de login con datos válidos
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

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
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

        // Se despliega el menú de usuarios, y se clica en Ver peticiones de amistad
        PO_NavView.desplegarAmigos(driver, "invitationFriends");

        // Se comprueba que juan tiene 1 peticion de amistad
        List<WebElement> usuarios = PO_PrivateView.checkElementBy(driver, "text", "Aceptar");
        Assertions.assertTrue(usuarios.size() == 1);

        // Se acepta la petición del usuario "Sara"
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
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");

        // Se despliega el menú de usuarios, y se clica en listFriends
        PO_NavView.desplegarAmigos(driver, "listFriends");

        // Se comprueba que alex@uniovi.es tiene 6 amigos
        List<WebElement> usuarios = SeleniumUtils.waitLoadElementsBy(driver, "text", "Email",
                PO_View.getTimeout());
        Assertions.assertTrue(usuarios.size() == 6);
    }

    // PR24. Crear una publicación y comprobar que se ha creado correctamente
    @Test
    @Order(24)
    public void PR24() {
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        // Entramos en la ventana de creación
        PO_NavView.desplegarPublicaciones(driver, "addPublication");

        // Añadimos un título
        List<WebElement> title = SeleniumUtils.waitLoadElementsBy(driver, "id", "title", PO_View.getTimeout());
        title.get(0).sendKeys("Prueba título");

        // Añadimos el contenido
        List<WebElement> content = PO_View.checkElementBy(driver, "id", "text");
        content.get(0).sendKeys("Prueba contenido");

        // Confirmamos
        driver.findElement(By.id("post")).click();

        List<WebElement> nuevaPublicacion = SeleniumUtils.waitLoadElementsBy(driver, "text", "Prueba título", PO_View.getTimeout());

        Assertions.assertTrue(nuevaPublicacion.get(0)!=null);
    }

    // PR25. Probar que si se intenta crear una publicación sin título, la aplicación no lo permite
    @Test
    @Order(25)
    public void PR25() {
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        // Entramos en la ventana de creación
        PO_NavView.desplegarPublicaciones(driver, "addPublication");

        // Añadimos el contenido
        List<WebElement> content = PO_View.checkElementBy(driver, "id", "text");
        content.get(0).sendKeys("Prueba contenido");

        // Confirmamos
        driver.findElement(By.id("post")).click();

        // Comprobamos que no ha pasado a ninguna otra ventana
        List<WebElement> paginaAgregar = SeleniumUtils.waitLoadElementsBy(driver, "text", "Subir publicación", PO_View.getTimeout());

        Assertions.assertTrue(paginaAgregar.get(0)!=null);
    }

    // PR26. Probar que se muestran todas las publicaciones de un usuario
    @Test
    @Order(26)
    public void PR26() {
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        // Entramos en la ventana de creación
        PO_NavView.desplegarPublicaciones(driver, "listPublication");

        // Comprobamos que están todas las publicaciones
        List<WebElement> pub1 = SeleniumUtils.waitLoadElementsBy(driver, "text", "publicacion 1 de Andrea", PO_View.getTimeout());
        Assertions.assertTrue(pub1.get(0)!=null);

        List<WebElement> pub2 = SeleniumUtils.waitLoadElementsBy(driver, "text", "publicacion 2 de Andrea", PO_View.getTimeout());
        Assertions.assertTrue(pub2.get(0)!=null);
    }

    // PR27. Probar que se muestran todas las publicaciones de un amigo
    @Test
    @Order(27)
    public void PR27() {
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");

        // Entramos en la ventana de amigos
        PO_NavView.desplegarAmigos(driver, "listFriends");

        SeleniumUtils.waitLoadElementsBy(driver, "text", "Ver publicaciones", PO_View.getTimeout()).get(0).click();

        // Comprobamos que están todas las publicaciones
        List<WebElement> pub1 = SeleniumUtils.waitLoadElementsBy(driver, "text", "publicacion 1 de Andrea", PO_View.getTimeout());
        Assertions.assertTrue(pub1.get(0)!=null);

        List<WebElement> pub2 = SeleniumUtils.waitLoadElementsBy(driver, "text", "publicacion 2 de Andrea", PO_View.getTimeout());
        Assertions.assertTrue(pub2.get(0)!=null);
    }

    // PR28. Probar que se muestran todas las publicaciones de un amigo
    @Test
    @Order(28)
    public void PR28() {
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");

        // Entramos en la ventana de amigos
        PO_NavView.desplegarAmigos(driver, "listFriends");

        SeleniumUtils.waitLoadElementsBy(driver, "text", "Ver publicaciones", PO_View.getTimeout()).get(0).click();

        // Comprobamos que están todas las publicaciones
        List<WebElement> pub1 = SeleniumUtils.waitLoadElementsBy(driver, "text", "publicacion 1 de Andrea", PO_View.getTimeout());
        Assertions.assertTrue(pub1.get(0)!=null);

        List<WebElement> pub2 = SeleniumUtils.waitLoadElementsBy(driver, "text", "publicacion 2 de Andrea", PO_View.getTimeout());
        Assertions.assertTrue(pub2.get(0)!=null);
    }

}
