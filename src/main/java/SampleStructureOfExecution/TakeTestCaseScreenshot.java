package SampleStructureOfExecution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class TakeTestCaseScreenshot
{
	WebDriver driver;

	//read Data from the excel
	 public static void main(String[] args) throws Exception {
	        // Read data from the Excel file
	        String filePath = "D:\\Test Case Generation.xlsx";
	        FileInputStream inputStream = new FileInputStream(filePath);
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

	        XSSFSheet sheet = workbook.getSheet("Sheet1");
	        XSSFRow row = sheet.getRow(1);
	        XSSFCell cell0 = row.getCell(0);
	        System.out.println("Field Name: " + cell0.getStringCellValue());
	        XSSFCell cell01 = row.getCell(2);
	        System.out.println("Final Result: "+cell01.getStringCellValue());


	        System.setProperty("webdriver.chrome.driver", "D:\\chandrakant\\Selenium\\Chrome Driver\\chromedriver.exe");
	        WebDriver driver = new ChromeDriver();

	        try {
	            driver.manage().window().maximize();
	            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	            driver.navigate().to("http://192.168.1.153:10043/#/");
	            System.out.println("URL launched successfully");

	            Thread.sleep(3000);
	            driver.findElement(By.id("txtUsrName")).sendKeys("QATest");
	            Thread.sleep(500);
	            driver.findElement(By.id("txtPassword")).sendKeys("welcome@0");
	            Thread.sleep(1000);
	            driver.findElement(By.id("btnLogin")).click();
	            Thread.sleep(1000);

	            try {
	                if (driver.findElements(By.cssSelector(".swal2-popup.swal2-modal.swal2-icon-warning.swal2-show")).size() > 0) 
	                {
	                    WebElement yesButton = driver.findElement(By.xpath("//button[normalize-space()='Yes, Override it!']"));
	                    yesButton.click();
	                    System.out.println("User Logged In With Override : " + driver.findElement(By.xpath("//h6[@id='user']")).getText());
	                } 
	                else 
	                {
	                    System.out.println("User Logged in without Override : " + driver.findElement(By.xpath("//h6[@id='user']")).getText());
	                }
	                System.out.println("Login Successfully");
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	                System.out.println("Exception occurred during login : " + e.getMessage());
	            }

	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	            WebElement Application = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='User guide']")));
	            Thread.sleep(1000);
	            Application.click();
	            Thread.sleep(2000);
	            driver.navigate().refresh();
	            Thread.sleep(1000);
	            driver.findElement(By.xpath("//span[normalize-space()='TEXTBOX']")).click();
	            System.out.println("MENU NAME: " + driver.findElement(By.xpath("//span[normalize-space()='TEXTBOX']")).getText());
	            Thread.sleep(3000);

	            driver.findElement(By.xpath("//*[@id=\"runtimeMain\"]/div/app-search/div[2]/div[2]/button[1]")).click();
	            System.out.println("CURRENT FORM STATUS: " + driver.findElement(By.xpath("//div[@id='formName']")).getText());
	            System.out.println("FIELD NAME: " + driver.findElement(By.xpath("//span[contains(text(),'Textbox')]")).getText());
	            Thread.sleep(1000);

	            XSSFCell cell1 = row.getCell(1);
	            String cellValue = cell1.getStringCellValue();
	            System.out.println("Cell Value: " + cellValue);
	            Thread.sleep(2000);

	            WebElement integerval = driver.findElement(By.xpath("//input[@placeholder='Enter Name Here...']"));
	            integerval.sendKeys(cellValue);

	            driver.findElement(By.xpath("//div[@id='Textbox']//div[@class='font-label mb-1 ng-star-inserted']")).click();
	            Thread.sleep(1000);
	            System.out.println("Length Validation : " + driver.findElement(By.xpath("//span[normalize-space()='Enter 10 characters only']")).getText());
	            driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();

	            WebElement valError = driver.findElement(By.xpath("//div[@aria-label='Please fill all mandatory fields']"));
	            Thread.sleep(1000);

	            // Take Screenshot
	            TakesScreenshot screenshot = (TakesScreenshot) driver;
	            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
	            String destPath = "C:\\Users/25672/eclipse-workspace/Screenshot/integervalidation.png";
	            File destFile = new File(destPath);
	            FileUtils.copyFile(srcFile, destFile);
	            System.out.println("Screenshot saved at: " + destPath);
	            
	            XSSFCell cell4 = row.getCell(5);
	            cell4.setCellValue(destPath);

	            XSSFCell cell3 = row.getCell(3);
	            if (cell3 == null) 
	            {
	                cell3 = row.createCell(3);
	            }

	            if (valError.isDisplayed()) 
	            {
	                cell3.setCellValue("Pass");
	                System.out.println( driver.findElement(By.xpath("//span[contains(text(),'Textbox')]")).getText()+": "+ cell3.getStringCellValue());
	            } 
	            else 
	            {
	                cell3.setCellValue("Fail");
	                System.out.println( driver.findElement(By.xpath("//span[contains(text(),'Textbox')]")).getText()+": "+ cell3.getStringCellValue());

	            }
	            
	            //write the fienla result 	


	            inputStream.close();

	            // Write the updated workbook to file
	            FileOutputStream outputStream = new FileOutputStream(filePath);
	            workbook.write(outputStream);
	            outputStream.close();

	        } 
	        
	        finally 
	        {
	            // Close the workbook to save changes
	            workbook.close();
	            driver.quit();
	        }
	    }
	
}
