package adminLoginLogout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mysql.cj.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RmgYantraTest {

	public static void main(String[] args) throws SQLException {


		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://rmgtestingserver:8084");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));	

		String expProjName="ZIVAINDUSTRIES6";
		WebElement userName = driver.findElement(By.id("usernmae"));
		userName.sendKeys("rmgyantra");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[.='Sign in']")).click();

		driver.findElement(By.linkText("Projects")).click();
		driver.findElement(By.xpath("//span[.='Create Project']")).click();

		WebElement projectName = driver.findElement(By.name("projectName"));
		projectName.sendKeys(expProjName);

		WebElement projectManager = driver.findElement(By.name("createdBy"));
		projectManager.sendKeys("Syed Naseer");
		WebElement projectStatus = driver.findElement(By.name("status"));
		Select status = new Select(projectStatus);
		status.selectByVisibleText("Created");

		driver.findElement(By.xpath("//input[@value='Add Project']")).click();

		Driver driverDB= new Driver();
		DriverManager.registerDriver(driverDB);
		Connection con = DriverManager.getConnection("jdbc:mysql://rmgtestingserver:3333/projects","root@%","root");
		Statement stmt=con.createStatement();
		String s="SELECT * FROM PROJECT";
		ResultSet rs=stmt.executeQuery(s);
		boolean flag=false;
		while(rs.next()) {
			String actualProjName=rs.getString(4);
			System.out.println(actualProjName);

			if(actualProjName.equalsIgnoreCase(expProjName)) {
				flag=true;
				break;
			}

		}
		if(flag) {
			System.out.println("Project present in DB");
		}
		else {
			System.out.println("Not");
		}




	}
}
