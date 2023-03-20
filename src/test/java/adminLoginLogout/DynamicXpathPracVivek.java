package adminLoginLogout;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import genericutilities.DatabaseUtility;
import genericutilities.ExcelUtility;
import genericutilities.FileUtility;
import genericutilities.JavaUtility;
import genericutilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DynamicXpathPracVivek {

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
		driver.get("http://rmgtestingserver/domain/Sales_And_Inventory_System/pages/transaction.php");
		wu.waitTillPageLoad(driver, 15);
		List<WebElement> customers = driver.findElements(By.xpath("//tr/td[1])"));
		for(WebElement customer:customers) {
			String transNo = customer.getText();
			if(transNo.equals("031562146")) {
				driver.findElement(By.xpath("//a[@class='btn btn-primary bg-gradient-primary']")).click();
			}
		}

	}

}
