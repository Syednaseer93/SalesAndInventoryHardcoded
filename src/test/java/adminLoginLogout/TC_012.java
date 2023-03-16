package adminLoginLogout;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_012 {

	public static void selectElement(WebElement ele, String value) {
		Select s = new Select(ele);
		s.selectByVisibleText(value);	
	}
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-notifications");
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://rmgtestingserver/domain/Sales_And_Inventory_System/pages/login.php");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));	
		String categoryName="Monitor";
		String supplierName="Zepto199";
		String productName=RandomStringUtils.randomNumeric(6);
		String description=RandomStringUtils.randomAlphabetic(15);
		String productCode=RandomStringUtils.randomNumeric(6);
		String quantity=RandomStringUtils.randomNumeric(3);
		String onHand=RandomStringUtils.randomNumeric(2);
		String price=RandomStringUtils.randomNumeric(2);
		String date="05051993";

		String adminuserName="unguardable";
		String adminPassword="admin";
		String userUsername="test";
		String userPassword="test";



		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();

		//CLICK ON PRODUCT 
		driver.findElement(By.xpath("//span[.='Product']")).click();

		//CREATE A NEW PRODUCT
		driver.findElement(By.xpath("//h4[@class='m-2 font-weight-bold text-primary']/a/i")).click();

		//ENTER  PRODUCT DETAILS AND SUBMIT
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='prodcode']")).sendKeys(productCode);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='name']")).sendKeys(productName);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@action='pro_transac.php?action=add']/div/textarea")));
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/textarea")).sendKeys(description);

		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='quantity']")).sendKeys(quantity);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='onhand']")).sendKeys(onHand);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='price']")).sendKeys(price);

		WebElement category=driver.findElement(By.xpath("//select[@name='category']"));
		WebElement supplier = driver.findElement(By.xpath("//select[@name='supplier']"));
		selectElement(category,categoryName);
		selectElement(supplier,supplierName);
		driver.findElement(By.xpath("//div[@class='form-group']/input[@name='datestock']")).click();
		driver.findElement(By.xpath("//div[@class='form-group']/input[@name='datestock']")).sendKeys(date);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/button[@class='btn btn-success']")).click();

		//CLICK ON INVENTORY
		driver.findElement(By.xpath("//span[.='Inventory']")).click();

		//SEARCH FOR THE PRODUCT CREATED IN ALL THE PAGES
		int totalPages=9;	
		boolean flag=false;
		String prodName="";
		int count=0;
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
			for(int r=1;r<=rows;r++) {
				prodName=driver.findElement(By.xpath("//tr["+r+"]/td[2]")).getText();
				if(prodName.equals(productName)) {
					System.out.println(productName);
					Assert.assertEquals(prodName, productName);
					flag=true;
					break;				
				}
			}
			if(flag) {
				break;
			}
			else {
				String pageNo=Integer.toString(p+1);
				driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item ']/a[.='"+pageNo+"']")).click();
			}
		}	
		//LOGOUT OF ADMIN PAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();

		/******************************************************************************************/
		//LOGIN USER PAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();

		driver.findElement(By.xpath("//a[.='"+categoryName+"']")).click();
		String actualProductName=driver.findElement(By.xpath("//h6[.='"+productName+"']")).getText();
		Assert.assertEquals(actualProductName, productName);
	}

}
