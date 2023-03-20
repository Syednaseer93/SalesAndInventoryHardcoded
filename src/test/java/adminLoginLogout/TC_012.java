package adminLoginLogout;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import genericutilities.DatabaseUtility;
import genericutilities.ExcelUtility;
import genericutilities.FileUtility;
import genericutilities.JavaUtility;
import genericutilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_012 {

	public static void main(String[] args) throws InterruptedException {
		DatabaseUtility du = new DatabaseUtility();
		ExcelUtility eu = new ExcelUtility();
		FileUtility fu = new FileUtility();
		JavaUtility ju =  new JavaUtility();
		WebDriverUtility wu = new WebDriverUtility();
		
		//OPEN BROWSER, MAXIMIZE PAE AND OPEN URL
		ChromeOptions options = wu.setChromeOptionsDisableNotifications();
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		wu.maximizePage(driver);
		String url=fu.getPropertyValue("url");
		driver.get(url);
		wu.waitTillPageLoad(driver, 15);
		
		//TAKE DATA FROM EXCEL AND STORE IT IN HASHMAP
		int row = eu.getLastRowNumber("customer","./data/getData.xlsx");
		HashMap<String,String> hm= new HashMap<String,String>();
		for(int i=0;i<=row;i++) {
			String key=eu.getCellData("customer","./data/getData.xlsx",i,0);
			String value=eu.getCellData("customer","./data/getData.xlsx",i,1);
			hm.put(key, value);
		}	
		String productCode=hm.get("productCode")+ju.getRandomNumber(2);
		String productName=hm.get("productName")+ju.getRandomString(2);
		String categoryName=hm.get("categoryName");
		String supplierName=hm.get("supplierName");
		String quantity=hm.get("quantity")+ju.getRandomNumber(1);
		String onHand=hm.get("onHand")+ju.getRandomNumber(3);
		String productDescription=hm.get("productDescription")+ju.getRandomString(50);
		String price=hm.get("price")+ju.getRandomNumber(10);
		String date=hm.get("date");

		String adminuserName=fu.getPropertyValue("adminuserName");
		String adminPassword=fu.getPropertyValue("adminPassword");
		String userUsername=fu.getPropertyValue("userUsername");
		String userPassword=fu.getPropertyValue("userPassword");

		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		wu.acceptjSAlert(driver);

		//CLICK ON PRODUCT 
		driver.findElement(By.xpath("//span[.='Product']")).click();

		//CREATE A NEW PRODUCT
		driver.findElement(By.xpath("//h4[@class='m-2 font-weight-bold text-primary']/a/i")).click();

		//ENTER  PRODUCT DETAILS AND SUBMIT
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='prodcode']")).sendKeys(productCode);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='name']")).sendKeys(productName);
		WebElement addProduct=driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/textarea"));
		wu.waitUntilElementClickable(driver, addProduct, 15);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/textarea")).sendKeys(productDescription);

		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='quantity']")).sendKeys(quantity);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='onhand']")).sendKeys(onHand);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/div/input[@name='price']")).sendKeys(price);

		WebElement category=driver.findElement(By.xpath("//select[@name='category']"));
		WebElement supplier = driver.findElement(By.xpath("//select[@name='supplier']"));
		Thread.sleep(1000);
		wu.selectByVisibleText(category,"Monitor");
		wu.selectByVisibleText(supplier,"google");

		driver.findElement(By.xpath("//div[@class='form-group']/input[@name='datestock']")).click();
		driver.findElement(By.xpath("//div[@class='form-group']/input[@name='datestock']")).sendKeys(date);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/button[@class='btn btn-success']")).click();

		//CLICK ON INVENTORY
		driver.findElement(By.xpath("//span[.='Inventory']")).click();

		//SEARCH FOR THE PRODUCT CREATED IN ALL THE PAGES
		int totalPages=10;	
		boolean flag=false;
		String productNameFromTable="";
		int count=0;
		for(int p=1;p<=totalPages;p++) {
			WebElement activePage = driver.findElement(By.xpath("//ul/li[@class='paginate_button page-item active']/a"));
			activePage.click();

			int rows=driver.findElements(By.xpath("//tbody/tr")).size();
			for(int r=1;r<=rows;r++) {
				productNameFromTable=driver.findElement(By.xpath("//tr["+r+"]/td[2]")).getText();
				if(productNameFromTable.equals(productName)) {
					System.out.println(productName);
					Assert.assertEquals(productNameFromTable, productName);
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
		//LOGOUT OF ADMIN PAGE
		driver.findElement(By.xpath("//ul[@class='navbar-nav ml-auto']/li[2]/a/span")).click();
		driver.findElement(By.xpath("//a[@data-target='#logoutModal']")).click();
		driver.findElement(By.linkText("Logout")).click();
  	/******************************************************************************************************/
		//LOGIN USER PAGE
		driver.findElement(By.name("user")).sendKeys(userUsername);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();

		driver.findElement(By.xpath("//a[.='"+categoryName+"']")).click();
		String actualProductName=driver.findElement(By.xpath("//h6[.='"+productName+"']")).getText();
		Assert.assertEquals(actualProductName, productName);
	}
}
