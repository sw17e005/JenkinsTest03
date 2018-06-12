/*
Erstellen Sie ein Java Selenium/JUnit Testfile, das überprüft,
dass Benutzer die Contributors sind nicht auf die Userverwaltung zugreifen können. 
http://localhost/wordpress/wp-admin/users.php
Überprüfen Sie die entsprechende Fehlermeldung.
 */

import java.util.concurrent.TimeUnit;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Manuela Jung
 */
public class VerifyUserPermissionTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    public VerifyUserPermissionTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
    }

    @Before
    public void setUp() throws Exception {

//        File pathBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
//        FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
//        DesiredCapabilities desired = DesiredCapabilities.firefox();
//        FirefoxOptions options = new FirefoxOptions();
//        desired.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(firefoxBinary));
        driver = new ChromeDriver();
        baseUrl = "http://localhost//";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testVerifyUserPermission() throws Exception {
        driver.get(baseUrl + "/wordpress/wp-login.php");				//loginseite öffnen
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("User2");		//Username
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("1234");		//Passwort
        driver.findElement(By.id("wp-submit")).click();
        Thread.sleep(1000);
        driver.get("http:/localhost/wordpress/wp-admin/users.php");     //offnen der Adminseite zur Userverwaltung
        try {															//überprüfen auf Fehlermeldung
             Thread.sleep(1000);
            assertEquals("You need a higher level of permission.", driver.findElement(By.cssSelector("h1")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
