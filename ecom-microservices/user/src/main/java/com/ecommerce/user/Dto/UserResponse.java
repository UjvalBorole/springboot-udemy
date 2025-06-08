package com.ecommerce.user.Dto;


import com.ecommerce.user.entities.UserRole;
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
