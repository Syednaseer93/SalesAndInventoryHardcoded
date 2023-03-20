package adminLoginLogout;

import java.util.HashMap;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.GenericUtilitiy.ExcelUtility;
import com.GenericUtilitiy.FileUtility;
import com.GenericUtilitiy.IPathConstants;
import com.GenericUtilitiy.JavaUtility;
import com.GenericUtilitiy.WebdriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GenericImplementNayeem {

	public static void main(String[] args) throws Throwable {

		ExcelUtility eutil=new 	ExcelUtility();
		FileUtility futil=new FileUtility();
		JavaUtility jutil=new JavaUtility();
		WebdriverUtility wutil=new WebdriverUtility();


		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		wutil.maximizewindow(driver);
		wutil.implicitwait(driver);
		String URL = futil.propertyUtility("url");
		driver.get(URL);

		String userName = futil.propertyUtility("username");
		String passWord = futil.propertyUtility("password");
		//login to application
		driver.findElement(By.name("user")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(userName);
		driver.findElement(By.name("btnlogin")).click();
		wutil.acceptAlert(driver);

		//create product
		driver.findElement(By.xpath("//span[.='Product']")).click();
		driver.findElement(By.xpath("//i[@class='fas fa-fw fa-plus']")).click();
		
		HashMap<String,String> map = new HashMap<String,String>() ;
		for(int i=0;i<=eutil.getLastRowNo("product details");i++)
		{
			String key=eutil.readDataFromExcel("product details",i,0);
			String value=eutil.readDataFromExcel("product details",i,1);
			map.put(key, value);
		}
		String prodcode=map.get("prodcode");
		String name=map.get("name");
		String quantity=map.get("quantity");
		String onhand=map.get("onhand");
		String price=map.get("price");
		String datestock=map.get("datestock");
		
		driver.findElement(By.name("prodcode")).sendKeys(prodcode);
		driver.findElement(By.name("name")).sendKeys(name);
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']//textarea[@name='description']")).sendKeys("hgjuyjfytjgutg");
		driver.findElement(By.name("quantity")).sendKeys(quantity);
		driver.findElement(By.name("onhand")).sendKeys(onhand);
		driver.findElement(By.name("price")).sendKeys(price);
	
		//for selecting category
		WebElement cat = driver.findElement(By.name("category"));
		wutil.select("CPU", cat);

		//for selecting supplier
		WebElement sup = driver.findElement(By.name("supplier"));
		wutil.select("All is well", sup);
		
		//for selecting the date
		driver.findElement(By.name("datestock")).click();
		driver.findElement(By.name("datestock")).sendKeys("02022022");
		
		//click on save button
		driver.findElement(By.xpath("//form[@action='pro_transac.php?action=add']/button[1]")).click();
		Thread.sleep(2000);

		//type in search
		String expected_prod_name = eutil.readDataFromExcel("product details", 1, 1);
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys((eutil.readDataFromExcel(IPathConstants.ExcelSheetName, 1, 1)));
		String actual_prod_name = driver.findElement(By.xpath("//table/tbody//td[2]")).getText();

		if(actual_prod_name.equals(expected_prod_name)) 
		{
			System.out.println("added successfully");
		}


	}
}

