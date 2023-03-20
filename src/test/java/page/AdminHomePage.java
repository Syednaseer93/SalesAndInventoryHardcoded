package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminHomePage {

	public AdminHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[.='Customer']")
	private WebElement customerModule;

	@FindBy(xpath="//ul[@class='navbar-nav ml-auto']/li[2]/a/span")
	private WebElement profileIcon;
	
	@FindBy(xpath="//a[@data-target='#logoutModal']")
	private WebElement logoutButton;
	
	
	public void clickOnCustomerModule() {
		customerModule.click();
	}
	
	public void clickOnProfileIcon() {
		profileIcon.click();
	}
	
	public void clickOnLogoutButton() {
		logoutButton.click();
	}
	
	
	
	
	
}
