package genericutilities;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.K;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtility {

	WebDriver driver;
	public void maximizePage(WebDriver driver) {
		driver.manage().window().maximize();
	}
	public static void capturePageScreenshot(WebDriver driver, String path){
		TakesScreenshot ts= ((TakesScreenshot)driver);
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dst= new File(path);
		try {
			FileUtils.copyDirectory(src, dst);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void captureWebElementScreenshot(WebDriver driver, String path, WebElement element) {
		
		File src = element.getScreenshotAs(OutputType.FILE);
		File dst= new File(path);
		try {
			FileUtils.copyDirectory(src, dst);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCalenderDate(WebDriver driver, String day, String month, String year, WebElement nextButton,
			WebElement monthYearLabel, List<WebElement> allDays) {
		driver.findElement(By.id("onward_cal")).click();
		while(true){
			String monyr = monthYearLabel.getText();
			String arr[] = monyr.split(" ");
			String mon = arr[0];
			String yr = arr[1];
			if (mon.equalsIgnoreCase(month) && yr.equalsIgnoreCase(year)) {
				break;
			} 
			else {
				nextButton.click();	
			}	
		}
		
		for (WebElement date : allDays) {
			String tempDate = date.getText();
			if (tempDate.equals(day)) {
				date.click();
				break;
			}
		}
	}

	public void selectByVisibleText(WebElement element, String text) {
		Select s= new Select(element);
		s.selectByVisibleText(text);
	}

	public void selectByIndex(WebElement element, int index) {
		Select s= new Select(element);
		s.selectByIndex(index);
	}

	public void selectByValue(WebElement element, String value) {
		Select s= new Select(element);
		s.selectByValue(value);
	}


	public static void drawBorder(WebDriver driver,WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.border='3px solid red'",element);
	}

	public static String getTitle(WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String title=js.executeScript("return document.title;").toString();
		return title;
	}

	public static void clickElement(WebDriver driver, WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
	}		

	public static void generateAlert(WebDriver driver,String message)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("alert('" +message+ "')");	
	}

	public static void refreshPage(WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("history.go(0);");
	}

	public static void scrollPagedown(WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public static void scrollTo(WebDriver driver,int value)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,"+value+")");
	}
	public static void scrollBy(WebDriver driver,int value)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,"+value+")");
	}
	
	
	public static void scrollPageup(WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
	}
	public static void scrollUntilElementInView(WebDriver driver,WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);		
	}
	

	public static void zoomPage(WebDriver driver, int zoomPercent)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("document.body.style.zoom='"+zoomPercent+"'");
	}
	public static void flash(WebElement element, WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String bgcolor= element.getCssValue("backGroundColor");
		for(int i=0;i<100;i++)
		{
			changeColor("#FF0000",element,driver);
			changeColor(bgcolor,element,driver);
		}
	}
	public static void changeColor(String color, WebElement element, WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor='" +color+ "'", element);
		try {
			Thread.sleep(3000);
		}
		catch(Exception e)
		{

		}
	}

	public void disabledElementHandle(String value, WebElement ele, String id) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("document.getElementById('"+id+"');e.value=", ele);
	}

	public void rightClick(WebDriver driver, WebElement element) {
		Actions action = new  Actions(driver);
		action.contextClick(element).perform();
	}

	public void doubleClick(WebDriver driver, WebElement element) {
		Actions action = new  Actions(driver);
		action.doubleClick(element).perform();
	}
	public void moveToElement(WebDriver driver, WebElement element) {
		Actions action = new  Actions(driver);
		action.moveToElement(element).perform();
	}

	public void dragAndDrop(WebDriver driver, WebElement srcElement, WebElement dstElement) {
		Actions action = new  Actions(driver);
		action.dragAndDrop(srcElement, dstElement).perform();
	}

	public void dragAndDropBy(WebDriver driver, WebElement srcElement,int x, int y) {
		Actions action = new  Actions(driver);

		action.dragAndDropBy(srcElement, x, y).perform();
	}

	public  void KeyPressEnter() {
		try {
			Robot r= new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
		}
		catch(Exception e) {

		}	
	}

	public  void KeyRelease() {
		try {
			Robot r= new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
		}
		catch(Exception e) {
		}	
	}

	public void acceptjSAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void dismissjSAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public void switchBetweenFramesUsingIndex(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}
	public void switchBetweenFramesUsingName(WebDriver driver, String frameName) {
		driver.switchTo().frame(frameName);
	}

	public void switchBetweenFramesUsingWebelement(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}

	public String getChildWindowID(WebDriver driver) {
		Set<String> wIDS = driver.getWindowHandles();
		String childWindowID="";
		String parentWindowID="";
		Iterator<String> it = wIDS.iterator();
		while(it.hasNext()) {	
			parentWindowID=it.next();
			childWindowID=it.next();
		}	
		return childWindowID;
	}
	
	public String getWindowID(WebDriver driver) {
		Set<String> wIDS = driver.getWindowHandles();
		String childWindowID="";
		String parentWindowID="";
		Iterator<String> it = wIDS.iterator();
		while(it.hasNext()) {	
			parentWindowID=it.next();
			childWindowID=it.next();
		}	
		return parentWindowID;
	}
	
	public void waitUntilElementVisible(WebDriver driver, WebElement element, int duration) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	
	public void waitUntilElementClickable(WebDriver driver, WebElement element, int duration) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public void waitUntilTitleContains(WebDriver driver, WebElement element, int duration,String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
		wait.until(ExpectedConditions.titleContains(title));
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-notifications");
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.redbus.in");
		//capturePageScreenshot(driver,"c:\\ElementScreenshot.jpg");
	
		
	}
	
}
