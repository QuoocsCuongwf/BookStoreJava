package com.example.demo.BUS.services;

import com.example.demo.model.SanPham;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel {

    public static void exportSanPhamToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SanPham");
        SanPhamServices sanPhamServices = new SanPhamServices();
        List<SanPham> sanPhamList=sanPhamServices.getListSanPham();
        String filePath = "D:\\code\\BookStoreJava\\src\\main\\resources\\Output\\Excel\\Output.xls";
        // Header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"MASP", "TENSP", "SL", "MATL", "MATG", "MANXB", "NAMXB", "DONGIA", "SOTRANG", "ANHBIA"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Data rows
        int rowIdx = 1;
        for (SanPham sp : sanPhamList) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(sp.getMasp());
            row.createCell(1).setCellValue(sp.getTensp());
            row.createCell(2).setCellValue(sp.getSl());
            row.createCell(3).setCellValue(sp.getMatl());
            row.createCell(4).setCellValue(sp.getMatg());
            row.createCell(5).setCellValue(sp.getManxb());
            row.createCell(6).setCellValue(sp.getNamxb());
            row.createCell(7).setCellValue(sp.getDongia());
            row.createCell(8).setCellValue(sp.getSotrang());
            row.createCell(9).setCellValue(sp.getAnhbia());
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    public static void main(String[] args) throws IOException {
        Excel.exportSanPhamToExcel();
    }
}
