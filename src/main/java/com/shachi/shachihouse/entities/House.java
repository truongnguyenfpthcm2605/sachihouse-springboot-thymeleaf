package com.shachi.shachihouse.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.web.JsonPath;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "house")
public class House implements Serializable {
    @Id
    private String id;
    private String title;
    private String bedroom;
    private String toilet;
    private String address;
    private String images;
    private String customer;
    private Double price;
    private String description;
    @Temporal(TemporalType.DATE)
    private LocalDate createdate;
    @Temporal(TemporalType.DATE)
    private LocalDate updatedate;
    private Long view;
    private Boolean isactive;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @JsonBackReference
    private Category category;


}
