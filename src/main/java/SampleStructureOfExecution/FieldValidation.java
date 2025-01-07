package SampleStructureOfExecution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FieldValidation 
{
	
	// Define counters
    private static int totalTestCases = 60;
    private static int testCasesRun = 0;


	  public static void main(String[] args) throws Exception {
		  
			        // Read data from the Excel file
		        String filePath = "D:\\Test Case Generation.xlsx";
		        FileInputStream inputStream = new FileInputStream(filePath);
		        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		        XSSFSheet sheet = workbook.getSheet("Sheet1");

		        // Get field names
		        XSSFRow headerRow = sheet.getRow(0);
		        for (int i = 0; i < headerRow.getLastCellNum(); i++) 
		        {
		            System.out.println("Header Names: " + headerRow.getCell(i).getStringCellValue());
		        }

		        System.setProperty("webdriver.chrome.driver", "D:\\chandrakant\\Selenium\\Chrome Driver\\chromedriver.exe");
		        WebDriver driver = new ChromeDriver();

		        try {
		            driver.manage().window().maximize();
		            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		            driver.navigate().to("http://192.168.1.153:10043/#/");
		            Thread.sleep(3000);

		            driver.findElement(By.id("txtUsrName")).sendKeys("QATest");
		            Thread.sleep(500);
		            driver.findElement(By.id("txtPassword")).sendKeys("welcome@0");
		            Thread.sleep(1000);
		            driver.findElement(By.id("btnLogin")).click();
		            Thread.sleep(1000);

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

		            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		            WebElement Application = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Test Case Design App']")));
		            Thread.sleep(1000);
		            Application.click();
		            Thread.sleep(2000);
		            driver.navigate().refresh();
		            Thread.sleep(1000);
		            driver.findElement(By.xpath("//span[normalize-space()='Field Data Validation']")).click();
		            Thread.sleep(3000);

		            driver.findElement(By.xpath("//*[@id=\"runtimeMain\"]/div/app-search/div[2]/div[2]/button[1]")).click();
		            Thread.sleep(2000);

		            // Inject counter display
		            injectCounterDisplay(driver); 
		            
		            // Loop through each row in the Excel sheet starting from the second row (index 1)
		            for (int i = 1; i <= sheet.getLastRowNum(); i++) 
		            {
		                XSSFRow row = sheet.getRow(i);
		                String cellValue1 = getCellValueAsString(row.getCell(0));
		                String cellValue2 = getCellValueAsString(row.getCell(1));
		                String cellValue3 = getCellValueAsString(row.getCell(2));
		                String expectedResult = getCellValueAsString(row.getCell(3));

		                WebElement Name = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[1]/div[2]/app-textbox/div/input"));
		                Name.sendKeys(cellValue1);

		                WebElement PhoneNumber = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[2]/div[2]/app-textbox/div/input"));
		                PhoneNumber.sendKeys(cellValue2);

		                WebElement About = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[3]/div[2]/app-multiline-textbox/div/textarea"));
		                About.sendKeys(cellValue3);

		                driver.findElement(By.xpath("//div[@id='formName']")).click();
		                Thread.sleep(1000);
		                driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
		                Thread.sleep(2000);
		               
		                boolean isValErrorDisplayed = false;
		                boolean isIntegerValDisplayed = false;
		                boolean isStringValErrorDisplayed = false;
		                boolean isRecordSubmittedDisplayed = false;

		                //validation 1
						try {
						    // Locate and check if valError is displayed
						    WebElement IntigerValError = driver.findElement(By.xpath("//span[normalize-space()='Please enter integer values only.']"));
						    isIntegerValDisplayed = IntigerValError.isDisplayed();
						} 
						catch (NoSuchElementException e)
						{
						    // If valError is not found, it means it is not displayed
						    isValErrorDisplayed = false;
						}
		                
		                //validation 2
						try {
						    // Locate and check if valError is displayed
						    WebElement valError = driver.findElement(By.xpath("//div[@aria-label='Please fill all mandatory fields']"));
						    isValErrorDisplayed = valError.isDisplayed();
						} 
						catch (NoSuchElementException e)
						{
						    // If valError is not found, it means it is not displayed
						    isValErrorDisplayed = false;
						}
						
		                //validation 3
						try {
						    // Locate and check if StringvalError is displayed
						    WebElement stringval = driver.findElement(By.xpath("//span[contains(text(),'Please enter string values only,Special Characters')]"));
						    isStringValErrorDisplayed = stringval.isDisplayed();
						} 
						catch (NoSuchElementException e)
						{
						    // If valError is not found, it means it is not displayed
						    isValErrorDisplayed = false;
						}

		                //validation 4
						try {
						    // Locate and check if RecordSubmitted is displayed
						    WebElement RecordSubmitted = driver.findElement(By.xpath("//div[@aria-label='Record saved successfully']"));
						    isRecordSubmittedDisplayed = RecordSubmitted.isDisplayed();
						} 
						catch (NoSuchElementException e) 
						{
						    // If RecordSubmitted is not found, it means it is not displayed
						    isRecordSubmittedDisplayed = false;
						}
						
			
					try 
					{
		                // Take Screenshot
		                TakesScreenshot screenshot = (TakesScreenshot) driver;
		                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		                String destPath = "C:\\Users/25672/eclipse-workspace/Screenshot/integervalidation_" + i + ".png";
		                File destFile = new File(destPath);
		                FileHandler.copy(srcFile, destFile);
		                System.out.println("Screenshot saved at: " + destPath);

		                XSSFCell screenshotCell = row.createCell(6);
		                screenshotCell.setCellValue(destPath);
		                
		                System.out.println("Screenshot captured successfully!");
		                
				//After taking an screen shot displaying message in the chrome browser		
			            // Inject JavaScript to display a message
						   String script = "let messageDiv = document.createElement('div');" +
		                            "messageDiv.id = 'screenshotMessage';" +
		                            "messageDiv.innerHTML = 'Screenshot captured successfully!';" +
		                            "messageDiv.style.position = 'fixed';" +
		                            "messageDiv.style.top = '10px';" +
		                            "messageDiv.style.left = '50%';" +
		                            "messageDiv.style.transform = 'translateX(-50%)';" +
		                            "messageDiv.style.padding = '10px';" +
		                            "messageDiv.style.backgroundColor = '#0046af';" +
		                            "messageDiv.style.color = 'white';" +
		                            "messageDiv.style.zIndex = '10000';" +
		                            "document.body.appendChild(messageDiv);";
						   
			            ((JavascriptExecutor) driver).executeScript(script);
						Thread.sleep(1000);

		                // Optionally remove the message after taking the screenshot
		                String removeScript = "let messageDiv = document.getElementById('screenshotMessage');" +
                                "document.body.removeChild(messageDiv);";
		                ((JavascriptExecutor) driver).executeScript(removeScript);

		            } 
					catch (IOException| InterruptedException e)
					{
		                e.printStackTrace();
					}
		                

		                XSSFCell resultCell = row.createCell(4);
		                
//Printing  result
		                if (isIntegerValDisplayed || isStringValErrorDisplayed || isValErrorDisplayed && !isRecordSubmittedDisplayed)
		                {
		                    resultCell.setCellValue("Pass");
		                    XSSFCell actualResultCell = row.createCell(5);
		                    actualResultCell.setCellValue(expectedResult);

		                }
		                else if (isIntegerValDisplayed || isStringValErrorDisplayed || isValErrorDisplayed && isRecordSubmittedDisplayed)
		                {
		                    resultCell.setCellValue("Pass");
		                    XSSFCell actualResultCell = row.createCell(5);
		                    actualResultCell.setCellValue(expectedResult);
		                }
		                else 
		                {
		                    resultCell.setCellValue("Fail");
		                    XSSFCell actualResultCell = row.createCell(5);
		                    actualResultCell.setCellValue(expectedResult);
		                }
		                
		              
						
		           
		                // Clear the input fields for the next iteration
		                try {
		                    WebElement Name2 = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[1]/div[2]/app-textbox/div/input"));
		                    WebElement PhoneNumber2 = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[2]/div[2]/app-textbox/div/input"));
		                    WebElement About2 = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[3]/div[2]/app-multiline-textbox/div/textarea"));

		                    clearInputField(driver, Name2);
		                    clearInputField(driver, PhoneNumber2);
		                    clearInputField(driver, About2);
		                } 
		                catch (StaleElementReferenceException e) 
		                {
		                	 i--;
		                }
		                
		                // Clear the input fields for the next iteration
		                WebElement Name3 = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[1]/div[2]/app-textbox/div/input"));
		                WebElement PhoneNumber3 = driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[2]/div[2]/app-textbox/div/input"));
		                WebElement About3= driver.findElement(By.xpath("/html/body/app-root/div/app-view/div/div/form/div[1]/lib-layout-host/app-layout/div/div/div[2]/app-three-column-layout/div/lib-layout-host/app-layout/div/div[3]/div[2]/app-multiline-textbox/div/textarea"));

		                clearInputField(driver, Name3);
		                clearInputField(driver, PhoneNumber3);
		                clearInputField(driver, About3);

//		                // Update test case counter
		                testCasesRun++;
		                updateCounterDisplay(driver);

		            }

		            inputStream.close();

		            // Write the updated workbook to file
		            FileOutputStream outputStream = new FileOutputStream(filePath);
		            workbook.write(outputStream);
		            outputStream.close();

		        } finally {
		            Thread.sleep(5000);
		            // Close the workbook to save changes
		            workbook.close();
		            driver.quit();
		        }
		    }
	  
	  
	   // Method to inject counter display (for showing the count of the test cases how many fail & how many pass)
	    private static void injectCounterDisplay(WebDriver driver) 
	    {
	    	  String script = "if (!window.updateCounter) {" +
	                    "    window.updateCounter = function(run, remaining) {" +
	                    "        var counterDiv = document.getElementById('testCaseCounter');" +
	                    "        if (!counterDiv) {" +
	                    "            counterDiv = document.createElement('div');" +
	                    "            counterDiv.id = 'testCaseCounter';" +
	                    "            counterDiv.style.position = 'fixed';" +
	                    "            counterDiv.style.top = '10px';" +
	                    "            counterDiv.style.left = '150px';" +
	                    "            counterDiv.style.backgroundColor = '#0046af';" +
                        "			 counterDiv.style.color = 'white';" +
	                    "            counterDiv.style.padding = '10px';" +
	                    "            counterDiv.style.borderRadius = '5px';" +
//	                    "            counterDiv.style.height = '30px';" +
	                    "            counterDiv.style.zIndex = '9999';" +
	                    "            document.body.appendChild(counterDiv);" +
	                    "        }" +
	                    "        counterDiv.innerHTML = 'Test Cases Executions: ' + run + '/' + remaining;" +
	                    "    };" +
	                    "}" +
	                    "window.updateCounter(0, 0);"; // Initialize with default values
	    ((JavascriptExecutor) driver).executeScript(script);
	    
	    }

	    // Method to update test case counter display
	    private static void updateCounterDisplay(WebDriver driver) 
	    {
	        String script = String.format("updateCounter(%d, %d);", testCasesRun, totalTestCases - testCasesRun);
	        ((JavascriptExecutor) driver).executeScript(script);
	    }
	  

	  
	   // Method to clear input field data using JavaScript
	    private static void clearInputField(WebDriver driver, WebElement element) 
	    {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].value='';", element);
	    }

	  
			// for taking number & string values from the cell 
	  	private static String getCellValueAsString(Cell cell)
	  	{
		    if (cell == null)
		    {
		        return "";
		    }
		    switch (cell.getCellType()) 
		    {
		        case STRING:
		            return cell.getStringCellValue();
		        case NUMERIC:
		            if (DateUtil.isCellDateFormatted(cell))
		            {
		                return cell.getDateCellValue().toString();
		            } 
		            else 
		            {
		                // Use DecimalFormat to avoid scientific notation
		                DecimalFormat df = new DecimalFormat("0");
		                return df.format(cell.getNumericCellValue());
		            }
		        case BOOLEAN:
		            return String.valueOf(cell.getBooleanCellValue());
		        case FORMULA:
		            switch (cell.getCachedFormulaResultType())
		            {
		                case STRING:
		                    return cell.getRichStringCellValue().getString();
		                case NUMERIC:
		                    DecimalFormat df = new DecimalFormat("0");
		                    return df.format(cell.getNumericCellValue());
		                case BOOLEAN:
		                    return String.valueOf(cell.getBooleanCellValue());
		                default:
		                    return "";
		            }
		        default:
		            return "";
		    }
		}

		  
	  }	
	
	

