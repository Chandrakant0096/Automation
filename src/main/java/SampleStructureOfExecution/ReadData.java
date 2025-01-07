package SampleStructureOfExecution;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadData
{
			
		public static void main(String args[]) throws Exception
		{
			//read Data from the excel
	        String filePath = "D:\\Sample Test Cases.xlsx";

	        // Use try-with-resources to ensure resources are closed properly
	        try (FileInputStream inputStream = new FileInputStream(filePath);
	             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) 
	        {
	        	
	        	int numberOfSheets = workbook.getNumberOfSheets();
	            System.out.println("Number of sheets: " + numberOfSheets);
	        
	            // Access the sheet by name
	            XSSFSheet sheet = workbook.getSheet("Sheet1");
	            if (sheet == null) 
	            {
	                System.out.println("Sheet 'sample' not found in the workbook.");
	                return;
	            }

	            // Access the row at index 2 (third row)
	            XSSFRow row = sheet.getRow(1);
	            if (row == null) 
	            {
	                System.out.println("Row 2 not found in the sheet.");
	                return;
	            }

	            //2nd row
	            XSSFRow row2 = sheet.getRow(2);
	            if (row2 == null) 
	            {
	                System.out.println("Row 2 not found in the sheet.");
	                return;
	            }

	            
	            // Access the cell at index 2 (third column)
	            XSSFCell cell = row.getCell(0);
	            if (cell == null)
	            {
	                System.out.println("Cell at row 2, column 2 not found.");
	                return;
	            }
	            
	            //2nd test case
	            // Access the cell at index 2 (third column)
	            XSSFCell cell02 = row2.getCell(0);
	            if (cell02 == null)
	            {
	                System.out.println("Cell at row 2, column 2 not found.");
	                return;
	            }
	            
	            
	            //Wrriting the value/Result in the excel
	            XSSFCell cell2 = row.getCell(4);
	            cell2.setCellValue("pppp");
	            
	            //Wrriting the value/Result in the excel
	            XSSFCell cell3 = row2.getCell(4);
	            cell3.setCellValue("ffff");

	            
	            XSSFCell cell03 = row.getCell(4);
	            if (cell03 == null)
	            {
	                System.out.println("Cell at row 2, column 2 not found.");
	                return;
	            }
	            
	            //Print the cell value
	            String TestCase = cell.getStringCellValue();
	            System.out.println("Test Case No:  " + TestCase);

	            //Print the cell value 2nd test cases
	            String TestCase2 = cell02.getStringCellValue();
	            System.out.println("Test Case No:  " + TestCase2);


	            // Get and print the cell value
	            String Result = cell03.getStringCellValue();
	            System.out.println("Result Of Test Cases:  " +TestCase+" - "+Result);
	            
	            // Get and print the cell value
	            String Result2 = cell3.getStringCellValue();
	            System.out.println("Result Of Test Cases:  " +TestCase2+" - "+Result2);
	            
	            // Write the updated workbook back to the file
	            try (FileOutputStream outputStream = new FileOutputStream(filePath))
	            {
	                workbook.write(outputStream);
	                outputStream.flush();
	            }

	            System.out.println("Test result written successfully!");
	            
	            workbook.close();
	        } 
	        
	        catch (IOException e)
	        {
	            System.out.println("An error occurred while reading the file: " + e.getMessage());
	        }
	        
		}

}
	