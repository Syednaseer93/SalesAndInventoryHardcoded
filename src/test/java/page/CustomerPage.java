package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerPage {

	public CustomerPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//a[@data-target='#customerModal']/i")
	private WebElement addCustomerIcon;
	
	@FindBy(xpath="//form[@action='cust_transac.php?action=add']/div/input[@name='firstname']")
	private WebElement customerFirstName;
	
	@FindBy(xpath="//form[@action='cust_transac.php?action=add']//input[@name='lastname']")
	private WebElement customerLastName;
		
	@FindBy(name="phonenumber")
	private WebElement customerPhoneNumber;
	
	@FindBy(xpath="//form[@action='cust_transac.php?action=add']/button[@type='submit']")
	private WebElement submitCustomerDetails;
	
	

}
