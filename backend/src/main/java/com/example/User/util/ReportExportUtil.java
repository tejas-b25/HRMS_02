package com.example.User.util;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@Component
public class ReportExportUtil {
    public String export(List<?> data,
                         LinkedHashMap<String, String> columns,
                         String fileNamePrefix,
                         String exportType,
                         String exportPath) throws Exception {
        String title = fileNamePrefix.replace("_", " ");
        File dir = new File(exportPath);
        if (!dir.exists()) dir.mkdirs();
        String fileName = fileNamePrefix + "_" + System.currentTimeMillis();
        switch (exportType.toLowerCase()) {
            case "excel":
                fileName += ".xlsx";
                exportExcel(data, columns, exportPath + "/" + fileName);
                return fileName;
            case "csv":
                fileName += ".csv";
                exportCsv(data, columns, exportPath + "/" + fileName);
                return fileName;
            case "pdf":
                fileName += ".pdf";
                exportToPdf(data, columns, exportPath + "/" + fileName, title);
                return fileName;
            default:
                throw new IllegalArgumentException("Invalid export type! Use: excel, csv, pdf");
        }
    }
    private String exportExcel(List<?> data,
                               LinkedHashMap<String, String> columns,
                               String filePath) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");
        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderTop(BorderStyle.THICK);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setBorderLeft(BorderStyle.THICK);
        headerStyle.setBorderRight(BorderStyle.THICK);
        CellStyle dateTimeStyle  = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateTimeStyle .setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy HH:mm:ss"));
        dateTimeStyle .setAlignment(HorizontalAlignment.LEFT);
        dateTimeStyle .setBorderTop(BorderStyle.THIN);
        dateTimeStyle .setBorderBottom(BorderStyle.THIN);
        dateTimeStyle .setBorderLeft(BorderStyle.THIN);
        dateTimeStyle .setBorderRight(BorderStyle.THIN);

        CellStyle dateStyle  = workbook.createCellStyle();
        CreationHelper createHelper1 = workbook.getCreationHelper();
        dateStyle .setDataFormat(createHelper1.createDataFormat().getFormat("dd-MMM-yyyy"));
        dateStyle .setAlignment(HorizontalAlignment.LEFT);
        dateStyle .setBorderTop(BorderStyle.THIN);
        dateStyle .setBorderBottom(BorderStyle.THIN);
        dateStyle .setBorderLeft(BorderStyle.THIN);
        dateStyle .setBorderRight(BorderStyle.THIN);
        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HorizontalAlignment.LEFT);
        textStyle.setBorderTop(BorderStyle.THIN);
        textStyle.setBorderBottom(BorderStyle.THIN);
        textStyle.setBorderLeft(BorderStyle.THIN);
        textStyle.setBorderRight(BorderStyle.THIN);
        Row header = sheet.createRow(0);
        int hCol = 0;
        for (String colName : columns.values()) {
            Cell cell = header.createCell(hCol++);
            cell.setCellValue(colName);
            cell.setCellStyle(headerStyle);
        }
        int rowIdx = 1;
        for (Object rowObj : data) {
            Row row = sheet.createRow(rowIdx++);
            int colIdx = 0;
            for (String fieldPath : columns.keySet()) {
                Object value = resolveNestedField(rowObj, fieldPath);
                Cell cell = row.createCell(colIdx++);
                if (value instanceof LocalDateTime) {
                    cell.setCellValue(java.sql.Timestamp.valueOf((LocalDateTime) value));
                    cell.setCellStyle(dateTimeStyle);
                } else if (value instanceof LocalDate) {
                    cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
                    cell.setCellStyle(dateStyle);
                } else {
                    cell.setCellValue((value != null ? value.toString() : "") + "\u00A0\u00A0");
                    cell.setCellStyle(textStyle);
                }
            }
        }
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 512);
        }
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            workbook.write(out);
        }
        workbook.close();
        return filePath;
    }

    private String exportCsv(List<?> data,
                             LinkedHashMap<String, String> columns,
                             String filePath) throws Exception {
        FileWriter writer = new FileWriter(filePath);
        writer.append(String.join(",", columns.values())).append("\n");
        for (Object rowObj : data) {
            List<String> row = new ArrayList<>();
            for (String fieldPath : columns.keySet()) {
                Object value = resolveNestedField(rowObj, fieldPath);
                row.add(value != null ? value.toString() : "");
            }
            writer.append(String.join(",", row)).append("\n");
        }
        writer.flush();
        writer.close();
        return filePath;
    }
    private Object resolveNestedField(Object obj, String fieldPath) throws Exception {
        String[] parts = fieldPath.split("\\.");
        Object value = obj;
        for (String part : parts) {
            Field field = value.getClass().getDeclaredField(part);
            field.setAccessible(true);
            value = field.get(value);
            if (value == null) return null;
        }
        return value;
    }

    public static void exportToPdf(List<?> data, LinkedHashMap<String, String> columnMapping, String filePath, String title) {
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(44, 62, 80));
            Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
            Paragraph heading = new Paragraph(title, titleFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);
            Paragraph subHeading = new Paragraph("Generated on: " + new java.util.Date(), subTitleFont);
            subHeading.setAlignment(Element.ALIGN_CENTER);
            document.add(subHeading);
            document.add(Chunk.NEWLINE);
            int columnCount = columnMapping.size();
            PdfPTable table = new PdfPTable(columnCount);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            List<List<String>> allData = new java.util.ArrayList<>();
            allData.add(new java.util.ArrayList<>(columnMapping.values()));
            for (Object record : data) {
                List<String> row = new java.util.ArrayList<>();
                for (String fieldName : columnMapping.keySet()) {
                    try {
                        Field field = record.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object value = field.get(record);
                        row.add(value != null ? value.toString() : "");
                    } catch (Exception e) {
                        row.add("");
                    }
                }
                allData.add(row);
            }
            float[] colWidths = new float[columnCount];
            for (List<String> row : allData) {
                for (int i = 0; i < columnCount; i++) {
                    colWidths[i] = Math.max(colWidths[i], row.get(i).length() + 3);
                }
            }
            float total = 0;
            for (float w : colWidths) total += w;
            for (int i = 0; i < columnCount; i++) {
                colWidths[i] = (colWidths[i] / total) * 100f;
            }
            table.setWidths(colWidths);
            BaseColor headerColor = new BaseColor(52, 152, 219);
            for (String header : columnMapping.values()) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(6);
                cell.setBorderColor(BaseColor.WHITE);
                cell.setNoWrap(true);
                table.addCell(cell);
            }
            BaseColor rowColor1 = new BaseColor(245, 245, 245);
            BaseColor rowColor2 = BaseColor.WHITE;
            int rowNumber = 0;
            for (int r = 1; r < allData.size(); r++) {
                BaseColor bg = (rowNumber++ % 2 == 0) ? rowColor1 : rowColor2;
                for (String cellValue : allData.get(r)) {
                    PdfPCell cell = new PdfPCell(new Phrase(cellValue, cellFont));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(bg);
                    cell.setPadding(5);
                    cell.setNoWrap(true);
                    table.addCell(cell);
                }
            }
            document.add(table);
            document.add(Chunk.NEWLINE);
            LineSeparator separator = new LineSeparator();
            separator.setLineColor(BaseColor.LIGHT_GRAY);
            document.add(separator);
            Paragraph footer = new Paragraph("Report generated by Employee Management System © " + java.time.Year.now(), subTitleFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }


}
