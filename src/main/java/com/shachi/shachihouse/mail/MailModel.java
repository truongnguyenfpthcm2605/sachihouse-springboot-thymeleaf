package com.shachi.shachihouse.mail;

import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailModel {
    private String from = "travelbee@fpt.edu.vn";
    private String to;
    private String subject;
    private String content;
    private String[] cc ;
    private String[] bcc;
    private List<File> files = new ArrayList<>();
}
