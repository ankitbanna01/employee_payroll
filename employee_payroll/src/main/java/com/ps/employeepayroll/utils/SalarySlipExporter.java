package com.ps.employeepayroll.utils;

import jakarta.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ps.employeepayroll.model.SalarySlip;

import java.io.IOException;

public class SalarySlipExporter {

    public static void exportSalarySlip(HttpServletResponse response, SalarySlip salarySlip)
            throws IOException, DocumentException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=salary_slip_" + salarySlip.getEmployee().getId() + ".pdf";
        response.setHeader(headerKey, headerValue);

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

        document.add(new Paragraph("Salary Slip", titleFont));

        // Add more content to the PDF document here
        document.add(new Paragraph("Employee ID: " + salarySlip.getEmployee().getId(), normalFont));
        document.add(new Paragraph("Base Salary: " + salarySlip.getBaseSalary(), normalFont));
        document.add(new Paragraph("Tax Amount: " + salarySlip.getTaxAmount(), normalFont));
        document.add(new Paragraph("Net Salary: " + salarySlip.getNetSalary(), normalFont));

        document.close();
    }
}