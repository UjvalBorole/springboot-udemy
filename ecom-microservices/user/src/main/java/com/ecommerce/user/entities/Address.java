package com.ecommerce.user.entities;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Document(collation = "address")
public class Address {


//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
