package com.shachi.shachihouse.backup;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class BackupGenerator {

    private final HouseServiceImpl houseService;
    private final ObjectMapper objectMapper;

    @Async
    @Scheduled(cron = "0 0 0 12 * ?")
    public void generateBackupSql() {
       try {
           List<House> houses = houseService.findAll();

           // Tạo chuỗi JSON từ danh sách kết quả
           StringBuilder jsonData = new StringBuilder("[");
           houses.forEach(house -> {
               try {
                   String houseJson = objectMapper.writeValueAsString(house);
                   jsonData.append(houseJson).append(",");
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });

           // Xóa dấu phẩy cuối cùng nếu có
           if (jsonData.charAt(jsonData.length() - 1) == ',') {
               jsonData.deleteCharAt(jsonData.length() - 1);
           }

           jsonData.append("]");

           // Lưu chuỗi JSON vào một tệp
           saveJsonBackupToFile(jsonData.toString(), "/datahouse.json");
       }catch (Exception e){

       }


    }

    public void saveJsonBackupToFile(String jsonData, String filePath) {
        try {
            File file = new File(filePath);
            objectMapper.writeValue(file, objectMapper.readTree(jsonData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        // Đường dẫn đến tệp JSON
//        String jsonFilePath = "/datahouse.json";
//
//        // Đọc nội dung từ tệp JSON
//        String jsonData = readJsonFromFile(jsonFilePath);
//
//        // In ra màn hình
//        System.out.println("JSON Data:");
//        System.out.println(jsonData);
//    }

    public static String readJsonFromFile(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(filePath);

            // Đọc tệp JSON và chuyển đổi thành đối tượng JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            // Chuyển đối tượng JsonNode thành chuỗi JSON
            return objectMapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
