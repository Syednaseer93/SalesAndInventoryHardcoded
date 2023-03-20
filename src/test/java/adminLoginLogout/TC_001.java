package adminLoginLogout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import genericutilities.DatabaseUtility;
import genericutilities.ExcelUtility;
import genericutilities.FileUtility;
import genericutilities.JavaUtility;
import genericutilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_001  {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		DatabaseUtility du = new DatabaseUtility();
		ExcelUtility eu = new ExcelUtility();
		FileUtility fu = new FileUtility();
		JavaUtility ju =  new JavaUtility();
		WebDriverUtility wu = new WebDriverUtility();
		//	OPEN BROWSER AND ENTER THE URL
		ChromeOptions options = wu.setChromeOptionsDisableNotifications();
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		wu.maximizePage(driver);
		String url=fu.getPropertyValue("url");
		driver.get(url);
		wu.waitTillPageLoad(driver, 15);
		//TAKE THE DATA FROM EXCEL AND STORE IT IN MAP
		String adminuserName=fu.getPropertyValue("adminuserName");
		String adminPassword=fu.getPropertyValue("adminPassword");
		String userUsername=fu.getPropertyValue("userUsername");
		String userPassword=fu.getPropertyValue("userPassword");
		
		int row = eu.getLastRowNumber("customer","./data/getData.xlsx");
		HashMap<String,String> hm= new HashMap<String,String>();
		for(int i=0;i<=row;i++) {
			String key=eu.getCellData("customer","./data/getData.xlsx",i,0);
			String value=eu.getCellData("customer","./data/getData.xlsx",i,1);
			hm.put(key, value);
		}
		//FETCH THE DATA FROM MAP AND KEEP IT IN VARIABLES
		String customerFirstName=hm.get("customerFirstName")+ju.getRandomString(3);
		String customerLastName=hm.get("customerLastName")+ju.getRandomString(3);
		String customerPhoneNumber=hm.get("customerPhoneNumber")+ju.getRandomNumber(3);
		String productName=hm.get("productName");
		String productQuantity= hm.get("productQuantity");

		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);

		//CLICK ON CUSTOMER MODULE
		driver.findElement(By.xpath("//span[.='Customer']")).click();
		driver.findElement(By.xpath("//a[@data-target='#customerModal']/i")).click();

		//ENTER CUSTOMER DETAILS AND SUBMIT
		WebElement firstName = driver.findElement(By.xpath("//form[@action='cust_transac.php?action=add']/div/input[@name='firstname']"));
		firstName.sendKeys(customerFirstName);
		WebElement lastName = driver.findElement(By.xpath("//form[@action='cust_transac.php?action=add']//input[@name='lastname']"));
		lastName.sendKeys(customerLastName);
		driver.findElement(By.name("phonenumber")).sendKeys(customerPhoneNumber);
		driver.findElement(By.xpath("//form[@action='cust_transac.php?action=add']/button[@type='submit']")).submit();

		//LOGOUT OF ADMIN PAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();

		//LOGIN USERPAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);

		//CHOOSE PRODUCT CATEGORY AND CHOOSE PRODUCT QUANTITY AND SUBMIT
		driver.findElement(By.xpath("//a[@data-target='#keyboard']")).click();
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[1]")).sendKeys(productQuantity);
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[4]")).click();

		//CHECK IF THE ADDED CUSTOMER NAME IS SELECTED IN DROPDOWN
		WebElement selectCustomer=driver.findElement(By.name("customer"));
		//Select customers = new Select(customer);
		//customers.selectByVisibleText(customerFirstName+" "+customerLastName);
		Select s=wu.selectByVisibleText(selectCustomer,customerFirstName+" "+customerLastName );

		//CHECK IF THE ADDED CUSTOMER NAME IS SAME AS SHOWN IN DROP DOWN
		String actualName=s.getFirstSelectedOption().getText();
		String expectedName=customerFirstName+" "+customerLastName;
		Assert.assertEquals(actualName, expectedName);
	}
}
	

