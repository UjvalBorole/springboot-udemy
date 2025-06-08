package com.ecommerce.user.services;


import com.ecommerce.user.Dto.AddressDto;
import com.ecommerce.user.Dto.UserRequest;
import com.ecommerce.user.Dto.UserResponse;
import com.ecommerce.user.Repository.UserRepository;
import com.ecommerce.user.entities.Address;
import com.ecommerce.user.entities.User;
import com.ecommerce.user.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
//public class UserService {
//     List<User> list = new ArrayList<>();
//
//    public List<User> getData(){
//        return list;
//    }
//
//
//    public List<User>CreateUser(@RequestBody User user){
//        list.add(user);
//        return list;
//    }
//
//    //using stream
//    public Optional<User> fetchUser(Long id) {
//        return list.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
//    }
//
//    public boolean updateUser(Long id, User updateUser){
//        return list.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst()
//                .map(exist->{
//                    exist.setId(updateUser.getId());
//                    exist.setFirstName(updateUser.getFirstName());
//                    return true;
//                }).orElse(false);
//    }
//}
//
//


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getAllUsers() {

        return  userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void  createUser(UserRequest userReq) {

        User user = new User();
        mapToUserRequest(user,userReq);
         userRepository.save(user);
    }

    private void  mapToUserRequest(User user,UserRequest userReq) {


        // Set basic user fields

        user.setFirstName(userReq.getFirstName());
        user.setLastName(userReq.getLastName());
        user.setEmail(userReq.getEmail());
        if(userReq.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);  // Set default here if role not sent
        } else {
            user.setRole(userReq.getRole());
        }
        user.setPhone(userReq.getPhone());
        // Map address if present
        if (userReq.getAddress() != null) {
            Address address = new Address();
            AddressDto addressDto = userReq.getAddress();

            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setCountry(addressDto.getCountry());
            address.setStreet(addressDto.getStreet());
            address.setZipcode(addressDto.getZipcode());
            address.setStreet(addressDto.getStreet()); // assuming street is part of DTO

            user.setAddress(address);
        }


    }



    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest updatedUser) {

        return userRepository.findById(id).map(user -> {
             mapToUserRequest(user,updatedUser);

            userRepository.save(user);
            return true;
        }).orElse(false);
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setEmail(user.getEmail());
       response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        if(user.getAddress() != null){
            AddressDto address = new AddressDto();
            Address ad = user.getAddress();
            address.setCity(ad.getCity());
            address.setState(ad.getState());
            address.setStreet(ad.getStreet());
            address.setCountry(ad.getCountry());
            address.setZipcode(ad.getZipcode());
            response.setAddress(address);
        }
        return response;
    }
}
