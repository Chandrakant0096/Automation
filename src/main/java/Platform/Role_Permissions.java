package Platform;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public class Role_Permissions 
{
	WebDriver driver;
    
    public enum Permission 
    {
   	    ADD("xpath://button[@class='btn btn_head btn_head--add-item ng-star-inserted']//*[name()='svg']"),
//   	VIEW_ALL_RECORDS("xpath://a[@id='viewAllRecordsLink']"),
   	    VIEW_REPORT("xpath://div[@class='dropdown-menu show']//a[@class='dropdown-item vertical-line ng-star-inserted'][normalize-space()='Report']"),
   	    VIEW_HISTORY("xpath://div[@class='dropdown-menu show']//a[@class='dropdown-item vertical-line ng-star-inserted'][normalize-space()='View History']"),
   	    CLONE_RECORDS("xpath://div[@class='dropdown-menu show']//a[@class='dropdown-item vertical-line ng-star-inserted'][normalize-space()='Clone']"),
   	    EXPORT_EXCEL("xpath://*[name()='path' and contains(@d,'M1.5 14.5V')]"),
   	    DOWNLOAD_PDF("xpath://button[@class='btn btn_head btn_head--pdf ng-star-inserted']"),
   	    RECORD_AUDIT("xpath://div[@class='dropdown-menu show']//a[@class='dropdown-item vertical-line ng-star-inserted'][normalize-space()='Audit']"),
   	    BULK_APPROVAL_INITIATION_TAP("xpath:/html/body/app-root/div/app-runtime-menu/div/div[4]/div/app-search/div[2]/div[2]/button[4]"),
   	    BULK_APPROVAL("xpath://span[normalize-space()='Bulk Approval']"),
   	    BULK_INITIATION("xpath://span[normalize-space()='Bulk Initiation']");

   	    private String locator;

   	    Permission(String locator) 
   	    {
   	        this.locator = locator;
   	    }
   	    public String getLocator()
   	    {
   	        return locator;
   	    }

    }
    
    
//    // Define role and expected accessible menus
//    public static Map<String, List<String>> roleMenuMap = new HashMap<>();
//    private static Map<String, List<Permission>> rolePermissionMap = new HashMap<>();
//    private static Map<String, List<Permission>> menuPermissionMap = new HashMap<>();
    // Role -> (Menu -> List<Permissions>)
    public static Map<String, Map<String, List<Permission>>> roleMenuPermissionMap = new HashMap<>();
    public static Map<String, List<String>> roleMenuMap = new HashMap<>();

    
    
     static
    {
    	 //Role Menu Mapping 
    	 roleMenuMap.put("AppAdmin", Arrays.asList("Designation's", "Organization Hierarchy", "User Creation", "Access Request", "Master Form", "Authorization Matrix Report"));
    	 roleMenuMap.put("Admin01", Arrays.asList( "Control Panel", "Designation's", "Organization Hierarchy", "Access Request","Master Form","Department Wise User roles Report","User Status Report","Authorization Matrix Report"));
    	 roleMenuMap.put("SystmUser", Arrays.asList("Designation's","Organization Hierarchy","Root Mapping", "Master Form","Applicable Roles","Department Wise User roles Report","User Status Report", "Authorization Matrix Report"));
    	 roleMenuMap.put("EndUser", Arrays.asList("Access Request"));
    	 
    	 
         // App Admin Role Permissions
         Map<String, List<Permission>> appadminMenuPermissions = new HashMap<>();
         appadminMenuPermissions.put("Designation's", Arrays.asList(Permission.ADD, Permission.EXPORT_EXCEL, Permission.DOWNLOAD_PDF));

         appadminMenuPermissions.put("Organization Hierarchy", Arrays.asList(Permission.ADD));

         appadminMenuPermissions.put("Access Request", Arrays.asList(Permission.ADD, Permission.DOWNLOAD_PDF,Permission.BULK_APPROVAL_INITIATION_TAP));

         appadminMenuPermissions.put("User Creation", Arrays.asList(Permission.ADD, Permission.DOWNLOAD_PDF,Permission.BULK_APPROVAL_INITIATION_TAP));

         appadminMenuPermissions.put("Master Form", Arrays.asList());
         
    	 
    	  // Admin User Role Permissions
         Map<String, List<Permission>> AdminMenuPermissions = new HashMap<>();
         AdminMenuPermissions.put("Designation's", Arrays.asList(Permission.RECORD_AUDIT));
         AdminMenuPermissions.put("Organization Hierarchy", Arrays.asList(Permission.RECORD_AUDIT));
         AdminMenuPermissions.put("Access Request", Arrays.asList(Permission.VIEW_HISTORY, Permission.DOWNLOAD_PDF, Permission.EXPORT_EXCEL,Permission.RECORD_AUDIT,Permission.VIEW_REPORT,Permission.VIEW_HISTORY));
         AdminMenuPermissions.put("User Creation", Arrays.asList(Permission.ADD,Permission.VIEW_HISTORY, Permission.DOWNLOAD_PDF,Permission.BULK_APPROVAL_INITIATION_TAP,Permission.RECORD_AUDIT));
         AdminMenuPermissions.put("Master Form", Arrays.asList(Permission.RECORD_AUDIT));

         //SystemUser Role Permissions
         Map<String, List<Permission>> SystemUserMenuPermissions = new HashMap<>();
         SystemUserMenuPermissions.put("Designation's", Arrays.asList(Permission.ADD,Permission.RECORD_AUDIT));
         SystemUserMenuPermissions.put("Organization Hierarchy", Arrays.asList(Permission.ADD));
         SystemUserMenuPermissions.put("Root Mapping", Arrays.asList(Permission.ADD, Permission.RECORD_AUDIT));
         SystemUserMenuPermissions.put("Applicable Roles", Arrays.asList(Permission.ADD,Permission.VIEW_REPORT, Permission.DOWNLOAD_PDF,Permission.EXPORT_EXCEL,Permission.RECORD_AUDIT));
         SystemUserMenuPermissions.put("Master Form", Arrays.asList(Permission.ADD));

         //SystemUser Role Permissions
         Map<String, List<Permission>> EndUserMenuPermissions = new HashMap<>();
         EndUserMenuPermissions.put("Access Request", Arrays.asList());
         
         // Adding all role permissions to the main map
         roleMenuPermissionMap.put("AppAdmin", appadminMenuPermissions);
         roleMenuPermissionMap.put("Admin01", AdminMenuPermissions);
         roleMenuPermissionMap.put("SystemUser", SystemUserMenuPermissions);
         roleMenuPermissionMap.put("EndUser", SystemUserMenuPermissions);
         
    }
     
     // Method to get permissions for a role and a specific menu
     public static List<Permission> getPermissionsForRoleAndMenu(String roleName, String menuName) 
     {
         Map<String, List<Permission>> menuPermissions = roleMenuPermissionMap.get(roleName);

         if (menuPermissions != null) 
         {
             return menuPermissions.getOrDefault(menuName, Collections.emptyList());
         }

         return Collections.emptyList(); // Return empty list if role or menu doesn't exist
     }
     
//    	  // Method to get allowed menus for a role
    	    public static List<String> getAllowedMenus(String role)
    	    {
    	        return roleMenuMap.getOrDefault(role, Collections.emptyList());
    	        
    	    }
    	    

  }
     

     



