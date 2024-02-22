package com.shachi.shachihouse.utils;

import java.time.LocalDate;

public class Common {
    public static String providerId;
    public static String email_OAuth2;
    public static LocalDate dateNow = LocalDate.now();
    public static final String ACCOUNT_SESSION  = "account";
    public static final String urlFileException = "/Error/FileException";
    public static final String urlRuntimeExceptionCustom = "/Error/RuntimeExceptionCustom";

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
