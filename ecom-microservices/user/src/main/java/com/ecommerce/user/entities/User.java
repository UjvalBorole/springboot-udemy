package com.ecommerce.user.entities;


//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "users")  // this is used for sql
@Document(collection = "users")
public class User {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    private Long id;
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String  email;
    private String phone;

//    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

//    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
//    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;

//    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdAt;

//    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updateAt;
}
