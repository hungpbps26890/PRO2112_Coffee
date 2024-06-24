package com.poly.coffee.dto.request;

import com.poly.coffee.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateMyInfoRequest {
    String firstName;
    String lastName;
    String phoneNumber;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}