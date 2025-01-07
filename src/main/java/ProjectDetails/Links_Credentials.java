package ProjectDetails;

import org.openqa.selenium.WebDriver;

public class Links_Credentials
{
	 WebDriver driver;

	
	//Project Link
	public static String url = "http://192.168.1.253:2000/#/";
	
	//Credentials
	public static String username  = "Aladmin";
	public static String  password  = "Welcome@8";
	
	public String changePassword = "welcome@3";

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
