package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public LoginPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	@FindBy(name="user")
	private WebElement loginUsername;

	@FindBy(name="password")
	private WebElement loginPassword;

	@FindBy(name="btnlogin")
	private WebElement loginButton;


	public void setUsername(String un) {
		loginUsername.sendKeys(un);
	}

	public void setPassword(String pw) {
		loginPassword.sendKeys(pw);
	}

	public void clickLoginButton() {
		loginButton.click();
	}

}
