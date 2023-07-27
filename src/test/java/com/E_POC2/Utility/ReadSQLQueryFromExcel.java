package com.E_POC2.Utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.E_POC2.TestBase.TestBase;

public class ReadSQLQueryFromExcel extends TestBase{
	
	public String query;

	public String readSQLQuery() throws IOException
	{

		//now we are reading file stream
		FileInputStream fis=new FileInputStream(config.getExcelInputQuerry());//got file
		XSSFWorkbook wrbook=new XSSFWorkbook(fis);//collection of sheets..
		XSSFSheet sht=wrbook.getSheet(config.getSheet_Name());//BY the name..got sheet
		
		//By for loop
		 query=dataReadingByForLoop(sht);
		 System.out.println("Query : "+query); 
		fis.close();
		return query;
	}

	public String dataReadingByForLoop(XSSFSheet sht)
	{
		int rows=sht.getLastRowNum();//rows size from sheet
		int cols=sht.getRow(1).getLastCellNum();//column size from particular row(1)
		String query = null;

		System.out.println("rows :"+rows);
		System.out.println("cols :"+cols+"\n");

		for(int r=1;r<=rows;r++) //this for row
		{
			XSSFRow row=sht.getRow(r);//reading row 0....so on

			for(int c=0;c<cols;c++) //this for col
			{
				XSSFCell cell=row.getCell(c); //reading cells of row(0)...so on

				//due to hybrid data..datatype will be hybrid..so capture cell datatype
				if(r==1 && c==2) {
					switch(cell.getCellType())
					{
					case STRING:System.out.print(cell.getStringCellValue());query=cell.getStringCellValue();break;
					case NUMERIC:System.out.print(cell.getNumericCellValue());break;
					case BOOLEAN:System.out.print(cell.getBooleanCellValue());break;
					}

				}

				System.out.print(" | ");
			}
			System.out.println();
		}
 
		return query;

	}

}
