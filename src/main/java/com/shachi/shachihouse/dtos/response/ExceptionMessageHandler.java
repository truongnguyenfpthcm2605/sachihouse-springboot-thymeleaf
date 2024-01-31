package com.shachi.shachihouse.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ExceptionMessageHandler {

    @JsonProperty
    private String message;
    @JsonProperty
    private String errorCode;
}
