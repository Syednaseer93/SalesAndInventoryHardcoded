package adminLoginLogout;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_003 {
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-notifications");
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://rmgtestingserver/domain/Sales_And_Inventory_System/pages/login.php");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));	
		String customerFirstName="Navi";
		String customerLastName="Soft";
		String customerName=customerFirstName+" "+customerLastName;
		//String customerPhoneNumber=RandomStringUtils.randomNumeric(10);
		String custph=RandomStringUtils.randomNumeric(10);
		String productName="Newmen E120";
		String adminuserName="unguardable";
		String adminPassword="admin";
		String userUsername="test";
		String userPassword="test";

		//LOGIN TO USERPAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();

		//CHOOSE PRODUCT CATEGORY AND ENTER PRODUCT QUANTITY AND SUBMIT
		driver.findElement(By.xpath("//a[@data-target='#keyboard']")).click();
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[1]")).sendKeys("5");
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[4]")).click();

		//SELECT ANY CUSTOMER FROM LIST AND SUBMIT
		WebElement customer=driver.findElement(By.name("customer"));
		Select customers = new Select(customer);
		customers.selectByVisibleText(customerFirstName+" "+customerLastName);
		driver.findElement(By.xpath("//button[.='SUBMIT']")).click();

		//ENTER THE AMOUNT TO BE PAID AND SUBMIT
		driver.findElement(By.xpath("//input[@id='txtNumber']")).click();
		String amount = driver.findElement(By.xpath("//h3[@class='font-weight-bold py-3 bg-light']")).getText();
		driver.findElement(By.xpath("//input[@id='txtNumber']")).sendKeys(amount);
		driver.findElement(By.xpath("//button[.='PROCEED TO PAYMENT']")).click();
		driver.switchTo().alert().accept();

		//LOGOUT OF USER PAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();
		
		/******************************************************************/
		
		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();
		
		//CLICK ON TRANSACTION MODULE AND IT SHOWS LIST OF TRANSACTIONS
		driver.findElement(By.xpath("//span[.='Transaction']")).click();
		
		//NAVIGATE TO EVERY TRANSACTION PAGE AND SEARCH FOR CUSTOMER NAME IN INVOICE FOR WHOM TRANSACTION WAS MADE
		int totalPages=14;	
		boolean flag=false;
		String custName="";
		int count=0;
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
			for(int r=1;r<=rows;r++) {
				custName=driver.findElement(By.xpath("//tr["+r+"]/td[2]")).getText();
				if(custName.equals(customerName)) {
					System.out.println(customerName);
					flag=true;
					driver.findElement(By.xpath("//td[.='"+custName+"']/../td[4]/a")).click();
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
		//CHECK IF THE NAME IS PRESENT IN INVOICE
		String actualName=driver.findElement(By.xpath("//h6[normalize-space()='"+custName+"']")).getText();
		String expectedName="Navi Soft";
		Assert.assertEquals(actualName, expectedName);
		
		
		
		
	}
}
