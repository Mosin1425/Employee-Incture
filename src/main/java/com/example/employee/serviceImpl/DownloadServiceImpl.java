package com.example.employee.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.entity.Employee;
import com.example.employee.service.DownloadService;
import com.example.employee.service.EmployeeService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class DownloadServiceImpl implements DownloadService{
	//private Logger logger = LoggerFactory.getLogger(PdfService.class); 
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	public ByteArrayInputStream createPdf() {
		String title = "Employee Details";
		String content = "Employee details below like salary, designation, name etc.";
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Document document = new Document();     
		
		//Anything written in document will automatically written in 'out' as a byte array output stream
		PdfWriter.getInstance(document, out);
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD,20);
		Paragraph paragraphTitle = new Paragraph(title, font);
		paragraphTitle.setAlignment(Element.ALIGN_CENTER);
		
		document.add(paragraphTitle);
		
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA,12);
		Paragraph paragraphContent = new Paragraph(content,font2);
		document.add(paragraphContent);
		
//		document.add((Element) service.allEmployees());
//		List<Employee> employees = employeeService.allEmployees();
//		for (Employee employee : employees) {
//            // Create a paragraph for each employee's details
//            Paragraph employeeDetails = new Paragraph(employee.toString());
//            document.add(employeeDetails);
//        }
		
		Table table = new Table(5);
	    table.setWidth(100); // Set the width of the table as a percentage of the page width
	    //table.setBackgroundColor(new Color(3074143));
	    table.setBorderColor(new Color(255));
	    // Add table headers
	    table.addCell("Employee ID");
	    table.addCell("Name");
	    table.addCell("Salary");
	    table.addCell("Designation");
	    table.addCell("Address");

	    List<Employee> employees = employeeService.allEmployees();
	    for (Employee employee : employees) {
	        // Add employee data to the table
	        table.addCell(String.valueOf(employee.getEmployeeId()));
	        table.addCell(employee.getEmployeeName());
	        table.addCell(String.valueOf(employee.getSalary()));
	        table.addCell(employee.getDesignation());
	        table.addCell(employee.getAddress());
	    }
	    document.add(table);
		
		document.close();
		
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ByteArrayInputStream createExcel() {
		String title = "Employee Details";
		
		String[] headers = {"Employee ID", "Name", "Salary", "Designation", "Address", "Primary Skill" };
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Employee Data");
		
		Row titleRow = sheet.createRow(0);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		
		Row headerRow = sheet.createRow(1);
		for(int i=0;i<headers.length;i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
		}
		
		List<Employee> employees = employeeService.allEmployees();
		int rowNum = 2;
		
		for(Employee employee : employees) {
			Row dataRow = sheet.createRow(rowNum++);
			dataRow.createCell(0).setCellValue(employee.getEmployeeId());
			dataRow.createCell(1).setCellValue(employee.getEmployeeName());
			dataRow.createCell(2).setCellValue(employee.getSalary());
			dataRow.createCell(3).setCellValue(employee.getDesignation());
			dataRow.createCell(4).setCellValue(employee.getAddress());
			dataRow.createCell(5).setCellValue(employee.getPrimarySkill());
		}
		
		try {
			workbook.write(out);
			workbook.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
	
}
