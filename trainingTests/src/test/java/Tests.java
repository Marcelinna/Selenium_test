import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Tests {

    WebDriver driver;
    WebDriverWait wait;
    String userEmail = "kkk@gmail.com";
    String userPassword = "1234";

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
    }
    @Ignore
    @Test
    public void HomePage() {
        driver.navigate().to("https://www.eobuwie.com.pl");
        Assert.assertEquals(driver.getTitle(), "Modne buty damskie, męskie, dziecięce oraz torebki | eobuwie.pl");
    }

    @Ignore
    @Test
    public void NegativeLoginTest() {
        driver.navigate().to("https://www.eobuwie.com.pl/customer/account/login/");
        driver.findElement(By.cssSelector("[data-testid='permission-popup-accept']")).click();
        WebElement loginNameInput = driver.findElement(By.id("login[username]"));
        loginNameInput.clear();
        loginNameInput.sendKeys(userEmail);
        WebElement passwordInput = driver.findElement(By.id("login[password]"));
        passwordInput.clear();
        passwordInput.sendKeys(userPassword);
        driver.findElement(By.cssSelector("[data-testid='login-submit-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='error-msg']//ul//li")));
        Boolean loginErrorMessageisPresent = driver.findElement(By.xpath("//li[@class='error-msg']//ul//li")).isDisplayed();
        Assert.assertTrue(loginErrorMessageisPresent);
    }

    @Test
    public void subPage(){
        driver.navigate().to("https://www.eobuwie.com.pl");
        driver.findElement(By.cssSelector("[data-testid='permission-popup-accept']")).click();
        driver.findElement(By.xpath("//ul[@class='e-list e-mega-menu__list']/li[1]/a[contains(.,'Damskie')]")).click();
        String checkUrl = "https://www.eobuwie.com.pl/damskie.html";
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, checkUrl );


    }

    @After
    public void tearDown() {
        driver.quit();

    }
}