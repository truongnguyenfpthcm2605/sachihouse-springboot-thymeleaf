package com.shachi.shachihouse.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ExceptionMessageHandler {

    private String message;
    private String errorCode;
}
