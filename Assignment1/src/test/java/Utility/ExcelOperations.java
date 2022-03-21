package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {


	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static org.apache.poi.ss.usermodel.Cell Cell;
	public static HashMap <String ,String> mapTestdata  =new HashMap<String ,String>();
	public static FileInputStream ExcelFile;
	public static boolean hasData;
	public static void setExcelFile(String Path) throws Exception {
		try {
			ExcelFile= new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);

		} catch (Exception e){
			//LogMessage.fail("Class ExcelUtil | Method setExcelFile | Exception desc : "+e.getMessage());
		}
	}

	public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
		try{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			//Cell.setCellType(Cell.CELL_TYPE_STRING);
			RichTextString CellData = Cell.getRichStringCellValue();


			return CellData.toString();
		}catch (Exception e){

			//LogMessage.fail("Class Utils | Method getCellData | Exception desc : "+ RowNum +":"+ ColNum + e.getMessage());


			return"";
		}
	}



	public static int getRowCount(String SheetName){
		int iNumber=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getLastRowNum()+1;
		} catch (Exception e){
			//LogMessage.fail("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());

		}
		return iNumber;
	}

	public static int getRowContains(String strSearch, int colNum,String SheetName) throws Exception{
		int iRowNum=0;	
		try {
			int rowCount = ExcelOperations.getRowCount(SheetName);
			for (; iRowNum<rowCount; iRowNum++){
				if  (ExcelOperations.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(strSearch)){
					break;
				}
			}       			
		} catch (Exception e){
			//LogMessage.fail("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());

		}
		return iRowNum;
	}
	public static Map<String, String> setTestDataMap(String SheetName, int rowNumber) throws Exception{
		int rowCount;
		String paramName;
		String paramValue;

		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
		for (int i=0 ; i<=noOfColumns-1 ; i++){
			paramName=ExcelOperations.getCellData(0,i,SheetName);  //store column test data header
			paramValue=ExcelOperations.getCellData(rowNumber,i,SheetName); // store test data value
			mapTestdata.put(paramName, paramValue);
		} 
		return mapTestdata;
	}

	public static  String getParamValue(String strParam) throws Exception{
		return(mapTestdata.get(strParam));
	}
	public static  String getResultFolder(String SheetName) throws Exception{
		return(ExcelOperations.getCellData(1,1,SheetName));
	}
	public static  String getScreenshotFolder(String SheetName) throws Exception{
		return(ExcelOperations.getCellData(2,1,SheetName));
	}
	public static void closeworkbook() throws IOException{
		ExcelFile.close();
	}
	public static void SaveExcel(String fileName,String SheetName) throws IOException {
		String strdata;
		FileInputStream objExcelFile= new FileInputStream(fileName);
		XSSFWorkbook objExcelWBook = new XSSFWorkbook(objExcelFile);           
		XSSFSheet objExcelWSheet = objExcelWBook.getSheet(SheetName);

		XSSFCell objCell = objExcelWSheet.getRow(0).getCell(0);

		strdata=objCell.getStringCellValue();
		objCell.setCellValue(strdata);
		objExcelFile.close();


		//Open FileOutputStream to write updates
		FileOutputStream output_file =new FileOutputStream(new File(fileName));  
		//write changes
		objExcelWBook.write(output_file);
		//close the stream
		output_file.close();
	}	 


	/**
	 * @method-name: getSheet
	 * @param sheetName
	 * @param ExcelPath
	 * @return HSSFSheet
	 * @description: It gets the selected excel sheet information into the object.
	 */
	public static synchronized HSSFSheet getSheet(String sheetName, String ExcelPath) throws IOException {
		HSSFWorkbook workbook = null;
		HSSFSheet worksheet = null;
		try (
				FileInputStream fileIpStream = new FileInputStream(System.getProperty(FunctionalConstants.USER_DIRECTORY)+ExcelPath)) {
			workbook = new HSSFWorkbook(fileIpStream);
			worksheet = workbook.getSheet(sheetName);
		} finally {
			try {
				if(workbook != null) {
					workbook.close();
				}

			} catch (IOException e) {
				System.out.println(e);
			}
		}
		return worksheet;
	}

	/**
	 * @method-name: setSheet
	 * @param sheetName
	 * @param rowNumber
	 * @param cellNumber
	 * @param value
	 * @param ExcelPath
	 * @description: It updates the input excel sheet file with obtained results 
	 */
	public static synchronized void setSheet(String sheetName, int rowNumber, int cellNumber, String value,
			String ExcelPath) throws IOException {
		HSSFWorkbook moduleWorkbook = null;
		FileOutputStream fileOpStream = null;
		try (FileInputStream fis = new FileInputStream(System.getProperty(FunctionalConstants.USER_DIRECTORY) + ExcelPath)) {
			moduleWorkbook = new HSSFWorkbook(fis);
			Sheet moduleSheet = moduleWorkbook.getSheet(sheetName);
			Row rowa;
			if (moduleSheet.getRow(rowNumber) == null) {
				rowa = moduleSheet.createRow(rowNumber);
			} else {
				rowa = moduleSheet.getRow(rowNumber);
			}
			Cell cella = rowa.createCell(cellNumber);
			cella.setCellValue(value);
			fileOpStream = new FileOutputStream(System.getProperty(FunctionalConstants.USER_DIRECTORY) + ExcelPath);
			moduleWorkbook.write(fileOpStream);
		} finally {
			try {
				if(fileOpStream != null) {
					fileOpStream.close();
				}
				if(moduleWorkbook != null) {
					moduleWorkbook.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}



	/**
	 * @param sheetName
	 * @param testName
	 * @param testNumber
	 * @return
	 */
	public ArrayList<String> getValueFromExcel(HSSFSheet sheetName) {
		HSSFRow iRow = null;
		System.out.println(sheetName.getLastRowNum());
		ArrayList<String> record = new ArrayList<String>();
		DataFormatter formatter = new DataFormatter();
		Iterator<Row> itr = sheetName.iterator();    //iterating over excel file  
		while (itr.hasNext())                 
		{  
			Row row = itr.next(); 
			for(int k = 0; k <row.getLastCellNum(); k++) {
				record.add(formatter.formatCellValue(row.getCell(k)));
			}
		}

		return record;
	}

}
