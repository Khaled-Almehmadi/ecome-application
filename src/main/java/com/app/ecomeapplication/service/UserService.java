package com.app.ecomeapplication.service;

import com.app.ecomeapplication.dto.AddressDTO;
import com.app.ecomeapplication.dto.UserRequest;
import com.app.ecomeapplication.dto.UserResponse;
import com.app.ecomeapplication.model.Address;
import com.app.ecomeapplication.model.User;
import com.app.ecomeapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;






    public List<UserResponse> fetchAllUsers(){

        return  userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }

    public Optional<UserResponse> fetchUser(long id){


        return  userRepository.findById(id).map(this::mapToUserResponse);

    }


    public void addUser(UserRequest userRequest){

        User user = new User();
        updateUserFromRequest(user,userRequest);
       userRepository.save(user);
    }



    public Boolean updateUser(long id ,UserRequest updatedUserRequesst) {

        return  userRepository.findById(id)
                .map(exsistingUser->{
                   updateUserFromRequest(exsistingUser,updatedUserRequesst);
                   userRepository.save(exsistingUser);
                    return true;
                }).orElse(false);
    }


    private void updateUserFromRequest(User user, UserRequest userRequest) {


        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null){

            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());

            user.setAddress(address);
        }




    }




    private UserResponse mapToUserResponse(User user){

        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null){

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);


        }

        return response;
    }
}

