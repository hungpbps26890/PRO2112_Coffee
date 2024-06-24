package com.poly.coffee.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String streetNumber;

    @Column(columnDefinition = "nvarchar(255)")
    private String ward;

    @Column(columnDefinition = "nvarchar(255)")
    private String district;

    @Column(columnDefinition = "nvarchar(255)")
    private String province;
}
