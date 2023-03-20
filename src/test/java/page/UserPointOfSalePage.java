package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import genericutilities.WebDriverUtility;

public class UserPointOfSalePage extends WebDriverUtility {


	@FindBy(name="customer")
	private WebElement selectCustomerDD;

}
