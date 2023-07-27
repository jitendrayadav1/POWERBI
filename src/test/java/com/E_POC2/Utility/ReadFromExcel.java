package com.E_POC2.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.E_POC2.TestBase.TestBase;

public class ReadFromExcel extends TestBase{
	
	public static Object[][] readExcelData(String sheetName) throws IOException
	{

		//now we are reading file stream
		System.out.println("TestCaseData Path : "+config.getExcelInputData());
		System.out.println("SheetName : "+sheetName);
		
		FileInputStream fis=new FileInputStream(config.getExcelInputData());//got file
		XSSFWorkbook wrbook=new XSSFWorkbook(fis);//collection of sheets..
		XSSFSheet sht=wrbook.getSheet(sheetName);//BY the name..got sheet
		//XSSFSheet sht=wrbook.getSheetAt(0);
		
		DataFormatter dff=new DataFormatter();
		
		int rows=sht.getLastRowNum();
		int cols=sht.getRow(1).getLastCellNum();
		
		System.out.println("No of Rows : "+rows);
		System.out.println("No of Col : "+cols);
		
		String[][] data=new String[rows+1][cols];
		 
		
		for(int r=0;r<=rows;r++)
		{
			XSSFRow row=sht.getRow(r); //due to index of row of array and Excel sheet 
			for(int c=0;c<cols;c++)
			{
				//System.out.print(" R="+r+" C="+c);
				
				data[r][c]=dff.formatCellValue(row.getCell(c));				
				System.out.print(dff.formatCellValue(row.getCell(c))+"    ");
				
			}
			System.out.println();
		}
				
		fis.close();
		wrbook.close();
       System.out.println("Done!!");
		return data;
	}

}
