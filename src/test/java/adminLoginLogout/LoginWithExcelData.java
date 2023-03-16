package adminLoginLogout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginWithExcelData {

	public static int getLastRowNumber(String sheet, String path) {
		int rn = 0;
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			rn = wb.getSheet(sheet).getLastRowNum();
		} catch (Exception e) {
		}
		return rn;
	}
	
	public static int getLastColumnNumber(String sheet, String path, int r) {
		int cn = 0;
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			cn = wb.getSheet(sheet).getRow(r).getLastCellNum();
		} catch (Exception e) {
		}
		return cn;
	}

	public static String getCellData(String sheet, String path, int r, int c) {
		String dd = "";
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			dd = wb.getSheet(sheet).getRow(r).getCell(c).toString();
		} catch (Exception e) {
		}
		return dd;
	}
	public static void setCellValue(String sheet, String path, int r, int cc, String value) {
		try {
		Workbook wb = WorkbookFactory.create(new FileInputStream(path));
		wb.write(new FileOutputStream("./data/logindata.xlsx"));
		wb.getSheet(sheet).getRow(r).createCell(cc).setCellValue(value);
	} catch (Exception e) {
			
		}
	}

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("http://rmgtestingserver:8084/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.findElement(By.id("usernmae")).sendKeys(getCellData("Sheet1","./data/rmgy.xlsx",1,0));
		driver.findElement(By.id("inputPassword")).sendKeys(getCellData("Sheet1","./data/rmgy.xlsx",1,1));
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		

	}

}
