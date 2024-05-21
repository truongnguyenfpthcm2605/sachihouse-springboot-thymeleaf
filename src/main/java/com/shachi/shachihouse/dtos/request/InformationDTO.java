package com.shachi.shachihouse.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationDTO {

    private String fullname;
    private String email;
    private String phone;
}
