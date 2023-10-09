package com.example.employee.service;

import java.io.ByteArrayInputStream;

public interface DownloadService {
	//Generate details in PDF
	ByteArrayInputStream createPdf();
	
	//Generate details in Excel
	ByteArrayInputStream createExcel();

	
}
