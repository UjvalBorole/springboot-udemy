package com.ecom.application.Dto;

import com.ecom.application.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String  email;
    private UserRole role;
    private String phone;
    private AddressDto address;
}
