package com.shachi.shachihouse.utils;

import com.shachi.shachihouse.entities.Account;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

public class Common {
    public static String providerId;
    public static String email_OAuth2;
    public static LocalDate dateNow = LocalDate.now();
    public static final String urlFileException = "/Error/FileException";
    public static final String urlRuntimeExceptionCustom = "/Error/RuntimeExceptionCustom";

    public static Account ACCOUNT_ACCESS ;

    public static final String Page404  = "error/404";

    public static Boolean checkAdmin() {
        return Objects.nonNull(Common.ACCOUNT_ACCESS);
    }

    public static String randomCodeMail(){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i =0 ;i<6;i++){
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
    public static Integer handlePage(String pageParam){
        int page = 0;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                page = page-1;
            } catch (NumberFormatException e) {
                page = 0;
                return page;
            }
        }
        return page;
    }

}
