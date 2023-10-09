package com.example.employee.controller;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.service.DownloadService;

@RestController
@RequestMapping("/downloads")
public class DownloadController {
    @Autowired
    private DownloadService downloadService;

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> generatePdf() {
        ByteArrayInputStream pdf = downloadService.createPdf();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=EmployeeDetails.pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> generateExcel() {
        ByteArrayInputStream excel = downloadService.createExcel();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=EmployeeDetails.xlsx");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excel));
    }
}