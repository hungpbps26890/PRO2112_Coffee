package com.poly.coffee.dto.request;

import com.poly.coffee.enums.AuthTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate dob;
    Boolean isActive;
    AuthTypeEnum authType;
    List<String> roles;
}