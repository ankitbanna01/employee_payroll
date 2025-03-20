package com.ps.employeepayroll.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ps.employeepayroll.model.SalarySlip;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SalarySlipExporter {

    /**
     * Exports a salary slip as a PDF file.
     *
     * @param response   HTTP response object
     * @param salarySlip SalarySlip object to be exported
     * @throws IOException       If an error occurs while writing the PDF
     * @throws DocumentException If an error occurs while creating the PDF document
     */
    public static void exportSalarySlip(HttpServletResponse response, SalarySlip salarySlip)
            throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=SalarySlip_" + salarySlip.getEmployee().getEmployeeId() + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Salary Slip", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Employee Information Table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table, "Employee ID", salarySlip.getEmployee().getEmployeeId());
        addTableHeader(table, "Employee Name", salarySlip.getEmployee().getName());
        addTableHeader(table, "Designation", salarySlip.getEmployee().getDesignation());
        addTableHeader(table, "Generated Date", salarySlip.getGeneratedDate().toString());
        addTableHeader(table, "Base Salary", "₹" + salarySlip.getBaseSalary());
        addTableHeader(table, "Tax Amount (10%)", "₹" + salarySlip.getTaxAmount());
        addTableHeader(table, "Net Salary", "₹" + salarySlip.getNetSalary());

        document.add(table);
        document.close();
    }

    /**
     * Helper method to add table headers and values.
     */
    private static void addTableHeader(PdfPTable table, String header, String value) {
        PdfPCell headerCell = new PdfPCell(new Phrase(header));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setPadding(5);
        table.addCell(headerCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value));
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }
}
