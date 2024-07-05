package com.shachi.shachihouse.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseDTO {
    @NotBlank(message = "Vui lòng nhập mã nhà")
    private String id;
    @NotBlank(message = "Vui lòng nhập tên nhà")
    private String title;
    @NotBlank(message = "Vui lòng nhập số lượng phòng ngủ")
    private String bedroom;
    @NotBlank(message = "Vui lòng nhập số lượng toilet")
    private String toilet;
    @NotBlank(message = "Vui lòng nhập địa chỉ")
    private String address;
    @NotBlank(message = "Vui lòng nhập số lượng khách hàng")
    private String customer;
    @NotNull(message = "Vui lòng nhập giá")
    private Double price;
    @NotBlank(message = "Vui lòng nhập mô tả")
    private String description;
    @NotBlank(message = "Vui lòng nhập link video </>")
    private String video;

}
