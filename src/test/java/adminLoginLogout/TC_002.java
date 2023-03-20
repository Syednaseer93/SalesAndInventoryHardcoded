package adminLoginLogout;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import genericutilities.DatabaseUtility;
import genericutilities.ExcelUtility;
import genericutilities.FileUtility;
import genericutilities.JavaUtility;
import genericutilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_002 {
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
		String customerFirstName=hm.get("customerFirstName")+ju.getRandomString(3);
		String customerLastName=hm.get("customerLastName")+ju.getRandomString(3);
		String customerPhoneNumber=hm.get("customerPhoneNumber")+ju.getRandomNumber(2);
		String productName=hm.get("productName");
		String productQuantity= hm.get("productQuantity");
		
		String adminuserName=fu.getPropertyValue("adminuserName");
		String adminPassword=fu.getPropertyValue("adminPassword");
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

		//CLICK ON ADD CUSTOMER ICON
		driver.findElement(By.xpath("//a[@data-target='#poscustomerModal']/i")).click();

		//ADD A NEW CUSTOMER DETAILS AND SUBMIT
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='firstname']")).sendKeys(customerFirstName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='lastname']")).sendKeys(customerLastName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='phonenumber']")).sendKeys(customerPhoneNumber);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/button[@type='submit']")).submit();
		wu.acceptjSAlert(driver);

		//LOGOUT OF USERPAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();

		/******************************************************************************/

		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);
		
		//CLICK ON CUSTOMER MODULE
		driver.findElement(By.xpath("//span[.='Customer']")).click();

		/*Count total pages
	    String pg=driver.findElement(By.xpath("//div[@id='dataTable_info']")).getText();
		String pg1=pg.split(" ")[5];
		int totalPages=Integer.parseInt(pg1);*/
		
		//NAVIGATE TO EVERY PAGE AND CHECK FOR ADDED CUSTOMER'S NAME
		int totalPages=65;	
		boolean flag=false;
		String phoneNo="";
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
			for(int r=1;r<=rows;r++) {
				phoneNo=driver.findElement(By.xpath("//tr["+r+"]/td[3]")).getText();
				if(phoneNo.equals(customerPhoneNumber)) {
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
		//CHECK IF THE ADDED CUSTOMER'S NAME IS PRESENT IN CUSTOMER'S TABLE
		if(flag==true) {
			System.out.println("Test passed, and customer's phone number matched is: "+phoneNo);
		}
		else {
			System.out.println("fail");
		}
	}
}
