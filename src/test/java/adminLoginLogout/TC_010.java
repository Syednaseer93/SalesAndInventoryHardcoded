package adminLoginLogout;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_010 {

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
		String customerFirstName="Mr.Z";
		String customerLastName=RandomStringUtils.randomAlphabetic(5);
		//String customerPhoneNumber=RandomStringUtils.randomNumeric(10);
		String custph=RandomStringUtils.randomNumeric(10);
		String productName="samsung phone";
		String adminuserName="unguardable";
		String adminPassword="admin";
		String userUsername="test";
		String userPassword="test";
		
		//LOGIN TO USER PAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();

		//CHOOSE PRODUCT CATEGORY AND ENTER PRODUCT QUANTITY AND SUBMIT
		driver.findElement(By.xpath("//a[@data-target='#keyboard']")).click();
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[1]")).sendKeys("5");
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[4]")).click();

		//CLICK ON ADD NEW CUSTOMER ICON
		driver.findElement(By.xpath("//a[@data-target='#poscustomerModal']/i")).click();

		//ADD NEW CUSTOMER DETAILS AND SUBMIT
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='firstname']")).sendKeys(customerFirstName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='lastname']")).sendKeys(customerLastName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='phonenumber']")).sendKeys(custph);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/button[@type='submit']")).submit();

		driver.switchTo().alert().accept();
		
		//CHECK IF THE ADDED CUSTOMER IS SHOWING IN CUSTOMER LIST DROPDOWN AND SELECT THAT AND SUBMIT
		WebElement customersDropdown=driver.findElement(By.name("customer"));
		Select customers = new Select(customersDropdown);
		customers.selectByVisibleText(customerFirstName+" "+customerLastName);
		driver.findElement(By.xpath("//button[.='SUBMIT']")).click();
		
		//ENTER THE AMOUNT AND SUBMIT
		driver.findElement(By.xpath("//input[@id='txtNumber']")).click();
		String amount = driver.findElement(By.xpath("//h3[@class='font-weight-bold py-3 bg-light']")).getText();
		driver.findElement(By.xpath("//input[@id='txtNumber']")).sendKeys(amount);
		driver.findElement(By.xpath("//button[.='PROCEED TO PAYMENT']")).click();
		driver.switchTo().alert().accept();

	}

}
