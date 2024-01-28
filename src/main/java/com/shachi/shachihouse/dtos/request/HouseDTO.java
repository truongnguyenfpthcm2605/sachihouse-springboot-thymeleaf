package com.shachi.shachihouse.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseDTO {

    @NotBlank(message = "id is required")
    private String id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "bedroom is required")
    private String bedroom;
    @NotBlank(message = "toilet is required")
    private String toilet;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "images is required")
    private String images;
    @NotBlank(message = "price is required")
    private Double price;
    @NotBlank(message = "description is required")
    private String description;

}
