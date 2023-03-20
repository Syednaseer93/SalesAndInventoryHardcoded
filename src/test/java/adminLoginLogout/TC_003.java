package adminLoginLogout;

import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import genericutilities.DatabaseUtility;
import genericutilities.ExcelUtility;
import genericutilities.FileUtility;
import genericutilities.JavaUtility;
import genericutilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_003 {
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
		String productName=hm.get("productName");
		String productQuantity= hm.get("productQuantity");
		
		String adminuserName=fu.getPropertyValue("adminuserName");
		String adminPassword=fu.getPropertyValue("adminPassword");
		String userUsername=fu.getPropertyValue("userUsername");
		String userPassword=fu.getPropertyValue("userPassword");
		//LOGIN TO USERPAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);

		//CHOOSE PRODUCT CATEGORY AND ENTER PRODUCT QUANTITY AND SUBMIT
		driver.findElement(By.xpath("//a[@data-target='#keyboard']")).click();
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[1]")).sendKeys(productQuantity);
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
		wu.acceptjSAlert(driver);

		//LOGOUT OF USER PAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();
		
		/*****************************************************************************************/
		
		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);
		
		//CLICK ON TRANSACTION MODULE AND IT SHOWS LIST OF TRANSACTIONS
		driver.findElement(By.xpath("//span[.='Transaction']")).click();
		
		//NAVIGATE TO EVERY TRANSACTION PAGE AND SEARCH FOR CUSTOMER NAME IN INVOICE FOR WHOM TRANSACTION WAS MADE
		int totalPages=14;	
		boolean flag=false;
		String custNameFromTable="";
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
			for(int r=1;r<=rows;r++) {
				custNameFromTable=driver.findElement(By.xpath("//tr["+r+"]/td[2]")).getText();
				if(custNameFromTable.equals(customerFirstName+" "+customerLastName)) {
					System.out.println(custNameFromTable);
					flag=true;
					driver.findElement(By.xpath("//td[.='"+custNameFromTable+"']/../td[4]/a")).click();
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
		String actualName=driver.findElement(By.xpath("//h6[normalize-space()='"+custNameFromTable+"']")).getText();
		String expectedName="Navi Soft";
		Assert.assertEquals(actualName,customerFirstName+" "+customerLastName);
			
	}
}
