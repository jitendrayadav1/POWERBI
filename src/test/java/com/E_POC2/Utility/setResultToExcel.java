package com.E_POC2.Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.E_POC2.TestBase.TestBase;



public class setResultToExcel extends TestBase{
	
	public static void setOutputToExcel(int row,int cell,String output) throws IOException
	{
		System.out.println("TestCaseData Path : "+config.getExcelInputData());
		System.out.println("SheetName : "+config.getTestCasesSheet());
		
		FileInputStream fis=new FileInputStream(config.getExcelInputData());//got file
		XSSFWorkbook wrbook=new XSSFWorkbook(fis);//collection of sheets..
		XSSFSheet sht=wrbook.getSheet(config.getTestCasesSheet());//BY the name..got sheet
		//XSSFSheet sht=wrbook.getSheetAt(0);
		
	//	DataFormatter dff=new DataFormatter();
		
		int rows=sht.getLastRowNum();
		int cols=sht.getRow(1).getLastCellNum();
		
		System.out.println("No of Rows : "+rows);
		System.out.println("No of Col : "+cols);
		
		sht.getRow(row).getCell(cell).setCellValue(output);
		
	/*	for(int r=0;r<=rows;r++)
		{
			XSSFRow row=sht.getRow(r); //due to index of row of array and Excel sheet 
			for(int c=0;c<cols;c++)
			{
				//System.out.print(" R="+r+" C="+c);				
				row.getCell(c).setCellValue((String)kv.getValue());
				
			}
			System.out.println();
		}*/
		
	  FileOutputStream out=new FileOutputStream(config.getExcelInputData());
	  wrbook.write(out);
	  fis.close();
	  out.close();
	System.out.println("Excel written Successfully...");
	}
	

}
