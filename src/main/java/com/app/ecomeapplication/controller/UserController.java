package com.app.ecomeapplication.controller;

import com.app.ecomeapplication.dto.UserRequest;
import com.app.ecomeapplication.dto.UserResponse;
import com.app.ecomeapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;



    @GetMapping
    public ResponseEntity< List<UserResponse>> getAllUsers(){

        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id){



        return  userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


    @PostMapping
    public String createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return "User Added Successfully";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest updatedUserRequest, @PathVariable long id){



       boolean updated =  userService.updateUser(id, updatedUserRequest);

       if(updated){
           return ResponseEntity.ok("User Updated Successfully");
       }
       return ResponseEntity.notFound().build();

    }

}
