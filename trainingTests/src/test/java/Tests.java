import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {

    WebDriver driver;
    WebDriverWait wait;


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
    public void HomePageTest() {
        driver.navigate().to("https://www.eobuwie.com.pl");
        Assert.assertEquals(driver.getTitle(), "Modne buty damskie, męskie, dziecięce oraz torebki | eobuwie.pl");
    }

    @Ignore
    @Test
    public void NegativeLoginTest() {
        String userEmail = "kkk@gmail.com";
        String userPassword = "1234";

        driver.navigate().to("https://www.eobuwie.com.pl/customer/account/login/");
        // close popup
        driver.findElement(By.cssSelector("[data-testid='permission-popup-accept']")).click();
        WebElement loginNameInput = driver.findElement(By.id("login[username]"));
        loginNameInput.clear();
        //send userEmail to input
        loginNameInput.sendKeys(userEmail);
        WebElement passwordInput = driver.findElement(By.id("login[password]"));
        passwordInput.clear();
        //send userPassword in input
        passwordInput.sendKeys(userPassword);
        //click submit
        driver.findElement(By.cssSelector("[data-testid='login-submit-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='error-msg']//ul//li")));
        Boolean loginErrorMessageisPresent = driver.findElement(By.xpath("//li[@class='error-msg']//ul//li")).isDisplayed();
        // check if error message is displayed
        Assert.assertTrue(loginErrorMessageisPresent);
    }
    @Ignore
    @Test
    public void subPageTest(){
        driver.navigate().to("https://www.eobuwie.com.pl");
        // close popup
        driver.findElement(By.cssSelector("[data-testid='permission-popup-accept']")).click();
        // chose type "Damskie"
        driver.findElement(By.xpath("//ul[@class='e-list e-mega-menu__list']/li[1]/a[contains(.,'Damskie')]")).click();
        String checkUrl = "https://www.eobuwie.com.pl/damskie.html";
        String URL = driver.getCurrentUrl();
        //check if url is correct
        Assert.assertEquals(URL, checkUrl );
    }

    @Ignore
    @Test
    public void searchInputTest() {
        String searchWord = "klapki";

        driver.navigate().to("https://www.eobuwie.com.pl");
        // close popup
        driver.findElement(By.cssSelector("[data-testid='permission-popup-accept']")).click();
        // find searchInput
        WebElement SearchInput = driver.findElement(By.xpath("//div[@class='header-content__search-wrapper']//input[@name='q']"));
        // send word in searchInput
        SearchInput.sendKeys(searchWord);
        driver.findElement(By.xpath("//div[@class='header-content__search-wrapper']//button[@class='header-search__submit']")).click();
        String URL = driver.getCurrentUrl();
        System.out.print(URL);
        // check if currentUrl contains searched word
        Assert.assertTrue(URL.contains(searchWord));
    }

    @Test
    public void addProducttoCartTest() {
        driver.navigate().to("https://www.eobuwie.com.pl");
        // close popup
        driver.findElement(By.cssSelector("[data-testid='permission-popup-accept']")).click();
        // chose type "Damskie"
        driver.findElement(By.xpath("//ul[@class='e-list e-mega-menu__list']/li[1]/a[contains(.,'Damskie')]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".products-list .products-list__item")));
        // chose first element from displayed products
        List<WebElement> Products = driver.findElements(By.cssSelector(".products-list .products-list__item"));
        //System.out.print("Product count" + Products.size());
        Products.get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown .dropdown-toggle")));
        driver.findElement(By.cssSelector(".dropdown .dropdown-toggle")).click();
        // chose first size from available
        List<WebElement> size = driver.findElements(By.cssSelector(".dropdown-menu li"));
        //System.out.println("Size count" + size.size());
        size.get(1).click();
        //get name of chosen product
        String productName = driver.findElement(By.cssSelector(".product-left__name-first")).getText();
        // ad product to cart
        driver.findElement(By.cssSelector("[data-testid='product-add-to-cart-button']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='product-go-to-cart-link']")));
        // go to cart
        driver.findElement(By.cssSelector("[data-testid='product-go-to-cart-link']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cart-item__name-first")));
        //get name of product in cart
        String productNameinCart = driver.findElement(By.cssSelector(".cart-item__name-first")).getText();
        System.out.println(productName + " " + productNameinCart);
        //check compatibility of product name added and in cart.
        Assert.assertEquals(productName, productNameinCart);
        
    }

    @After
    public void tearDown() {
        driver.quit();

    }
}