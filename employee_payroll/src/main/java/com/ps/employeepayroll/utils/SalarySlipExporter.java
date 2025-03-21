package com.ps.employeepayroll.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ps.employeepayroll.model.SalarySlip;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SalarySlipExporter {

    public static void exportSalarySlip(HttpServletResponse response, SalarySlip salarySlip)
            throws DocumentException, IOException {
        // Set response headers for PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=salary-slip.pdf");

        // Create a document object
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Add content to the PDF
        document.add(new Paragraph("Salary Slip"));
        document.add(new Paragraph("Employee ID: " + salarySlip.getEmployee().getEmployeeId()));
        document.add(new Paragraph("Tax Amount: " + salarySlip.getTaxAmount()));
        document.add(new Paragraph("Net Salary: " + salarySlip.getNetSalary()));
        document.add(new Paragraph("Generated Date: " + salarySlip.getGeneratedDate().toString()));

        // Close the document
        document.close();
    }
}
