package com.shachi.shachihouse.utils;

import com.shachi.shachihouse.entities.House;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;
@RequiredArgsConstructor
@Service
public class Excel {
    private final ServletContext app;

    public void Export( List<House> houses)  {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh Sách Tài Nhà");
            String[] header = {"STT","ID","Title","Bedroom","Toilet","Address","Price","Ngày Tạo","Ngày cập nhật","View","Loại","Mô tả"};

            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for(int i =0;i<header.length;i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNum = 1;
            for (House house : houses) {
                Row row =sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum);
                row.createCell(1).setCellValue(house.getId());
                row.createCell(2).setCellValue(house.getTitle());
                row.createCell(3).setCellValue(house.getBedroom());
                row.createCell(4).setCellValue(house.getToilet());
                row.createCell(5).setCellValue(house.getAddress());
                row.createCell(6).setCellValue(house.getPrice());
                row.createCell(7).setCellValue(house.getCreatedate());
                row.createCell(8).setCellValue(house.getUpdatedate());
                row.createCell(9).setCellValue(house.getView());
                row.createCell(10).setCellValue(house.getCategory().getTitle());
                row.createCell(11).setCellValue(house.getDescription());

            }
            for (int i = 0; i < 11; i++) {
                sheet.autoSizeColumn(i);
            }
            String filePath = app.getRealPath("/document/houses.xlsx");
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
