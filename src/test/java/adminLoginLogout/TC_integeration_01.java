package adminLoginLogout;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_integeration_01 {

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

		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();

		driver.findElement(By.xpath("//span[.='Customer']")).click();


		int totalPages=65;	
		boolean flag=false;
		String custName="";
		int count=0;
		ArrayList<String> sortedList=null;
		ArrayList<String> orgList=null;
		
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
		
			for(int r=1;r<=rows;r++) {
				List<WebElement> customersList = driver.findElements(By.xpath("//tr/td["+r+"]"));
				//SORTED LIST
				sortedList= new ArrayList();
			}
			if(!(orgList.equals(sortedList))) {
				break;
			}
			else {
				String pageNo=Integer.toString(p+1);
				driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item ']/a[.='"+pageNo+"']")).click();
			}
		}
		


	}	

}


