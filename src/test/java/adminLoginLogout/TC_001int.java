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

public class TC_001int {

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

		List<WebElement> customersList = driver.findElements(By.xpath("//tr/td[1]"));

		//SORTED LIST
		ArrayList<String> sortedList= new ArrayList();
		for(WebElement customer:customersList) {
			sortedList.add(customer.getText());
		}
		Collections.sort(sortedList);

		System.out.println(sortedList);
		//ORIGINAL LIST
		ArrayList<String> orgList= new ArrayList();
		for(WebElement customer:customersList) {
			sortedList.add(customer.getText());
		}
		
		if(sortedList.equals(orgList)) {
			System.out.println("pass");
		}
		else {
			System.out.println("fail");
		}
		




	}

}
