package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adminLoginLogout.LoginWithExcelData;
import genericutilities.DynamicXpathUtils;

public class UserHomePage extends LoginWithExcelData{
	
	WebDriver driver;
	

	public UserHomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@data-target='#keyboard']")
	private WebElement KeyboardCategory;
	
	
	public String xPathProdQuantity="//h6[.='%replaceable%']/following-sibling::input[1]"; 
	public String xPathProdClick="//h6[.='%replaceable%']/following-sibling::input[4]"; 

	public void enterProductQuantity(String quantity, String productName) {
		String finalDynamicXpath = DynamicXpathUtils.getDynamicXpath(xPathProdQuantity, productName);
		driver.findElement(By.xpath(finalDynamicXpath)).sendKeys(quantity);
	}
	public void clickOnAddProduct(String productName, WebDriver driver) {
		String finalDynamicXpath1=DynamicXpathUtils.getDynamicXpath(xPathProdClick, productName);
		driver.findElement(By.xpath(finalDynamicXpath1)).click();
	}
	
	
}
