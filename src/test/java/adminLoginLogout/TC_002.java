package adminLoginLogout;

import java.time.Duration;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_002 {
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
		String customerFirstName="Mr.X";
		String customerLastName=RandomStringUtils.randomAlphabetic(5);
		//String customerPhoneNumber=RandomStringUtils.randomNumeric(10);
		String custph=RandomStringUtils.randomNumeric(10);
		String productName="Newmen E120";
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

		//CLICK ON ADD CUSTOMER ICON
		driver.findElement(By.xpath("//a[@data-target='#poscustomerModal']/i")).click();

		//ADD A NEW CUSTOMER DETAILS AND SUBMIT
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='firstname']")).sendKeys(customerFirstName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='lastname']")).sendKeys(customerLastName);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/div/input[@name='phonenumber']")).sendKeys(custph);
		driver.findElement(By.xpath("//form[@action='cust_pos_trans.php?action=add']/button[@type='submit']")).submit();
		driver.switchTo().alert().accept();

		//LOGOUT OF USERPAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();

		/******************************************************************************/

		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();
		
		//CLICK ON CUSTOMER MODULE
		driver.findElement(By.xpath("//span[.='Customer']")).click();

		/*Count total pages
	    String pg=driver.findElement(By.xpath("//div[@id='dataTable_info']")).getText();
		String pg1=pg.split(" ")[5];
		int totalPages=Integer.parseInt(pg1);*/
		
		//NAVIGATE TO EVERY PAGE AND CHECK FOR ADDED CUSTOMER'S NAME
		int totalPages=55;	
		boolean flag=false;
		String phoneNo="";
		int count=0;
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
			for(int r=1;r<=rows;r++) {
				phoneNo=driver.findElement(By.xpath("//tr["+r+"]/td[3]")).getText();
				if(phoneNo.equals(custph)) {
					System.out.println(custph);
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
			System.out.println("passed"+phoneNo);
		}
		else {
			System.out.println("fail");
		}

	}
}
