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

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_001  {

	public static String getPropertyValue(String key) throws FileNotFoundException, IOException{
		Properties pro = new Properties();
		pro.load(new FileInputStream("./data/config.properties"));
		String value=pro.getProperty(key);
		return value;		
	}

	public static String getCellData(String sheet, String path, int row, int column) {
		String value = "";
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			value = wb.getSheet(sheet).getRow(row).getCell(column).getStringCellValue();
		} catch (Exception e) {
		}
		return value;
	}
	public static int getLastRowNumber(String sheet, String path) {
		int rowNum = 0;
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			rowNum = wb.getSheet(sheet).getLastRowNum();
		} catch (Exception e) {
		}
		return rowNum;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-notifications");
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		String url=getPropertyValue("url");
		driver.get(url);

		String implicitTO=getPropertyValue("ITO");
		int ITO=Integer.parseInt(implicitTO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ITO));

		String ExplicitTO=getPropertyValue("ETO");
		int ETO=Integer.parseInt(ExplicitTO);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(ETO));	

		String adminuserName=getPropertyValue("adminuserName");
		String adminPassword=getPropertyValue("adminPassword");
		String userUsername=getPropertyValue("userUsername");
		String userPassword=getPropertyValue("userPassword");


		int row = getLastRowNumber("customers","./data/getData.slsx");
		HashMap<String,String> hm= new HashMap<String,String>();
		for(int i=0;i<=row;i++) {
			String key=getCellData("customer","./data/getData.slsx",i,0);
			String value=getCellData("customer","./data/getData.slsx",i,1);
			hm.put(key, value);
		}
		String customerFirstName=hm.get("customerFirstName");
		String customerLastName=hm.get("customerLastName");
		String customerPhoneNumber=hm.get("customerPhoneNumber");
		String productName=hm.get("productName");
		String productQuantity= hm.get("productQuantity");

		
		/*	for(Entry<String, String> me :hm.entrySet()) {		

		}*/

		//LOGIN TO ADMIN PAGE
		driver.findElement(By.name("user")).sendKeys(adminuserName);
		driver.findElement(By.name("password")).sendKeys(adminPassword);
		driver.findElement(By.name("btnlogin")).click();
		driver.switchTo().alert().accept();


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
		driver.switchTo().alert().accept();

		//SELECT PRODUCT CATEGORY AND CHOOSE PRODUCT QUANTITY AND SUBMIT
		driver.findElement(By.xpath("//a[@data-target='#keyboard']")).click();
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[1]")).sendKeys(productQuantity);
		driver.findElement(By.xpath("//h6[.='"+productName+"']/following-sibling::input[4]")).click();

		//CHECK IF THE ADDED CUSTOMER NAME IS SELECTED IN DROPDOWN
		WebElement customer=driver.findElement(By.name("customer"));
		Select customers = new Select(customer);
		customers.selectByVisibleText(customerFirstName+" "+customerLastName);

		//CHECK IF THE ADDED CUSTOMER NAME IS SAME AS SHOWN IN DROP DOWN
		String actualName=customers.getFirstSelectedOption().getText();
		String expectedName=customerFirstName+" "+customerLastName;
		if(actualName.equals(expectedName)) {
			System.out.println("test passes");
		}
		else {
			System.out.println("test failed");
		}
	}
}


