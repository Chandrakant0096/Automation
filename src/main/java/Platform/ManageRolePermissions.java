package Platform;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Platform.Role_Permissions.Permission;
import ProjectDetails.Links_Credentials;


public class ManageRolePermissions extends Links_Credentials
{	
	static WebDriver driver;


    public static void main(String[] args) throws Exception
    {
        System.setProperty("webdriver.chrome.driver", "E:\\Chandrakant\\Selenium\\chrome driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.navigate().to("http://192.168.1.253:2000/#/");
            

            // Define roles and credentials in the required sequence
            Map<String, String> userCredentials = new LinkedHashMap<>(); // LinkedHashMap to maintain insertion order
            userCredentials.put("AppAdmin", "welcome@1");  
            userCredentials.put("Admin01", "welcome@1");
            userCredentials.put("SystmUser", "welcome@1"); 
            userCredentials.put("EndUser", "welcome@1");

            // Iterate through roles in sequence
            for (Map.Entry<String, String> entry : userCredentials.entrySet())
            {
                String role = entry.getKey();
                String password = entry.getValue();

                // Log in with each role	
                login(driver, role, password);
                Thread.sleep(500);
          		WebElement UserProfile = driver.findElement(By.xpath("//p[@id='user']"));
          		System.out.println("User Login the application successfully: "+UserProfile.getText());

                // Fetch expected menus for the current role
                List<String> expectedMenus = Role_Permissions.roleMenuMap.get(role);
                // Debugging statement to ensure the role and its menus are fetched correctly
                System.out.println("Role: " + role + " - Expected Menus: " + expectedMenus);

                // Ensure that expectedMenus is not null before iterating
                if (expectedMenus != null)
                {
                    for (String menu : expectedMenus)
                    {
                    	validateMenus(driver, role, expectedMenus);
                        boolean isAccessible = isMenuAccessible(driver, menu);
                        System.out.println("Role: " + role + ", Menu: " + menu + " -> " + (isAccessible ? "Accessible" : "Not Accessible"));
                        driver.findElement(By.linkText(menu)).click();
                        Thread.sleep(3000);
                        driver.navigate().back();
                        verifyPermissions(menu);

                    }
                }
	                
                else 
                {
                    System.out.println("No menus found for role: " + role);
                }
//                assertTrue(RolePermission.hasPermission("AppAdmin", Permission.ADD));
                // Log out and prepare for the next role
                logout(driver);
            }

        } 
        finally
        {
        	Thread.sleep(2000);
        	driver.close();
        }
    }
    
 //chek whether the menu is accesseble to the login  role user   
    public static void validateMenus(WebDriver driver, String role, List<String> expectedMenus) throws MenuMismatchException 
    {
        List<String> missingMenus = new ArrayList<>();

        // Iterate through the expected menus and check their visibility
        for (String menu : expectedMenus) 
        {
            if (!isMenuAccessible(driver, menu)) 
            {
                missingMenus.add(menu);
            }
        }
        // If there are any missing menus, print them
        if (!missingMenus.isEmpty()) 
        {
            System.out.println("Role: " + role + " - Missing Menus: " + missingMenus);
//            Assert.fail("Menu is Missing");
        } 
        else 
        {
            System.out.println("Role: " + role + " - All expected menus are displayed.");
        }
    }
    
    
    // Verifying menu permissions & other permissions like export PDF, Excel, etc. for the logged-in role    
    public static void verifyPermissions(String roleName) {
        Map<String, List<Permission>> menuPermissions = Role_Permissions.roleMenuPermissionMap.get(roleName);

        if (menuPermissions == null) {
            System.out.println("No permissions defined for role: " + roleName);
            return;
        }

        // Iterate through each menu and check permissions
        for (Map.Entry<String, List<Permission>> entry : menuPermissions.entrySet()) 
        {
            String menuName = entry.getKey();
            List<Permission> expectedPermissions = entry.getValue();

            // Verify that each expected permission is available on the page
            for (Permission permission : expectedPermissions) 
            {
                try
                {
                    WebElement permissionElement = driver.findElement(By.xpath(permission.getLocator()));
                    if (permissionElement.isDisplayed())
                    {
                        System.out.println("Permission " + permission.name() + " is correctly displayed for menu " + menuName);
                    } 
                    else 
                    {
                        System.out.println("Permission " + permission.name() + " is NOT displayed for menu " + menuName);
                    }
                } 
                catch (NoSuchElementException e) 
                {
                    System.out.println("Permission " + permission.name() + " is NOT found for menu " + menuName);
                }
            }
        }
      }
      
       
//verifying that the menu is accessible or not by the login user
    public static boolean isMenuAccessible(WebDriver driver, String menuName) 
    {
        try 
        {
            String xpath;
            if (menuName.contains("'")) {
                // Use concat function if there's an apostrophe ("'")
                xpath = String.format("//span[normalize-space()=concat('%s', \"'\", '%s')]", menuName.split("'")[0], menuName.split("'")[1]);
            }
            else
            {
                // Normal case without special characters
                WebElement menuElement = driver.findElement(By.xpath("//span[normalize-space()='" + menuName + "']"));
                return menuElement.isDisplayed();

            }
            
        }
        catch (NoSuchElementException e)
        {
        	
        	System.out.println(e.getMessage());
            return false;
        }
		return false;
    }
    
    
//Login to the application
    private static void login(WebDriver driver, String username, String password) throws InterruptedException
    {
        driver.findElement(By.id("txtUsrName")).sendKeys(username);
        Thread.sleep(500);
        driver.findElement(By.id("txtPassword")).sendKeys(password);
        Thread.sleep(1000);
        driver.findElement(By.id("btnLogin")).click();
        Thread.sleep(1000);

        // Handle any override pop-ups
        if (driver.findElements(By.cssSelector(".swal2-popup.swal2-modal.swal2-icon-warning.swal2-show")).size() > 0) {
            WebElement yesButton = driver.findElement(By.xpath("//button[normalize-space()='Yes, Override it!']"));
            yesButton.click();
            System.out.println("User Logged In With Override : " + driver.findElement(By.xpath("//h6[@id='user']")).getText());
        }
        else
        {
            System.out.println("User Logged in without Override : " + driver.findElement(By.xpath("//h6[@id='user']")).getText());
        }

        System.out.println("Login Successfully");
        Thread.sleep(2000);
        driver.navigate().refresh();
        Thread.sleep(1000);
    }
	
	//loging out from the application
        private static void logout(WebDriver driver) throws Exception 
        {
   		 //Logout from the application
   		 Thread.sleep(2000);
   		 driver.findElement(By.xpath("//img[@class='img-xs rounded-circle pro-user-img']")).click();
   		 Thread.sleep(500);
   		 WebElement UserProfile = driver.findElement(By.xpath("//p[@id='user']"));
   		 System.out.println("Logged out from the application successfully: "+UserProfile.getText());
   		 Thread.sleep(1000);
   		 driver.findElement(By.xpath("//span[normalize-space()='Sign Out']")).click();
   		 Thread.sleep(1000);
   		 driver.findElement(By.xpath("//button[normalize-space()='Yes, logout it!']")).click();		
   		 Thread.sleep(3000);
        }

}
