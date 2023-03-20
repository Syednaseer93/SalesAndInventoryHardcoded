package adminLoginLogout;

import java.time.Duration;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import genericutilities.DatabaseUtility;
import genericutilities.ExcelUtility;
import genericutilities.FileUtility;
import genericutilities.JavaUtility;
import genericutilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_010 {

	public static void main(String[] args) {
		DatabaseUtility du = new DatabaseUtility();
		ExcelUtility eu = new ExcelUtility();
		FileUtility fu = new FileUtility();
		JavaUtility ju =  new JavaUtility();
		WebDriverUtility wu = new WebDriverUtility();
		
		ChromeOptions options = wu.setChromeOptionsDisableNotifications();
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		wu.maximizePage(driver);
		String url=fu.getPropertyValue("url");
		driver.get(url);
		wu.waitTillPageLoad(driver, 15);
		
		int row = eu.getLastRowNumber("customer","./data/getData.xlsx");
		HashMap<String,String> hm= new HashMap<String,String>();
		for(int i=0;i<=row;i++) {
			String key=eu.getCellData("customer","./data/getData.xlsx",i,0);
			String value=eu.getCellData("customer","./data/getData.xlsx",i,1);
			hm.put(key, value);
		}
		String customerFirstName=hm.get("customerFirstName");
		String customerLastName=hm.get("customerLastName");
		String customerPhoneNumber=hm.get("customerPhoneNumber")+ju.getRandomNumber(2);
		String productName=hm.get("productName");
		String productQuantity= hm.get("productQuantity");
		String expectedAlertMessage=hm.get("expectedAlertMessage");
		String userUsername=fu.getPropertyValue("userUsername");
		String userPassword=fu.getPropertyValue("userPassword");
		//LOGIN TO USER PAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);

		//CHOOSE PRODUCT CATEGORY AND ENTER PRODUCT QUANTITY AND SUBMIT
		driver.findElement(By.xpath("//a[@data-target='#keyboard']")).click();
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[1]")).sendKeys(productQuantity);
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[4]")).click();

		//CLICK ON ADD NEW CUSTOMER ICON
		driver.findElement(By.xpath("//a[@data-target='#poscustomerModal']/i")).click();

		//ADD NEW CUSTOMER DETAILS AND SUBMIT
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='firstname']")).sendKeys(customerFirstName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='lastname']")).sendKeys(customerLastName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='phonenumber']")).sendKeys(customerPhoneNumber);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/button[@type='submit']")).submit();

		wu.acceptjSAlert(driver);
		
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
		String actualAlertMessage=driver.switchTo().alert().getText();
		if(actualAlertMessage.contains(expectedAlertMessage)) {
			System.out.println("Test passed and invoice generated succesfully");
		}
		else {
			System.out.println("fail");
		}
		wu.acceptjSAlert(driver);
		

	}

}
