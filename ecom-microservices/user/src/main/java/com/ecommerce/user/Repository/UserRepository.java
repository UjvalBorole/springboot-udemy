package com.ecommerce.user.Repository;


import com.ecommerce.user.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;

//public interface UserRepository extends JpaRepository<User, Long> {
//}

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
