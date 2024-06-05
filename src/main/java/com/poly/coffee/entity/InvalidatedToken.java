package com.poly.coffee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invalidated_tokens")
public class InvalidatedToken {

    @Id
    private String id;

    private Date expiryTime;
}
