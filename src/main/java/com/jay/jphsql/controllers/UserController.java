package com.jay.jphsql.controllers;

import com.jay.jphsql.models.UserModel;
import com.jay.jphsql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final String JPH_API_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private UserRepository userRepository;

    // getting all user directly from JPH API
    @GetMapping("/jph/all")
    public ResponseEntity<?> getAllUsersAPI (RestTemplate restTemplate) {

        try {

            UserModel[] allUsers = restTemplate.getForObject(JPH_API_URL, UserModel[].class);

            return ResponseEntity.ok(allUsers);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // get all users stored in our local MySQL database
    @GetMapping("/sql/all")
    public ResponseEntity<?> getAllUsersSQL () {
        try {

            ArrayList<UserModel> allUsers = (ArrayList<UserModel>) userRepository.findAll();

            return ResponseEntity.ok(allUsers);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // grab all the users and store inside our local MySQL database
    @PostMapping("/all")
    public ResponseEntity<?> uploadAllUsersToSQL (RestTemplate restTemplate) {

        try {
            UserModel[] allUsers = restTemplate.getForObject(JPH_API_URL, UserModel[].class);

            assert allUsers != null;
            List<UserModel> savedUsers = userRepository.saveAll(Arrays.asList(allUsers));

            return ResponseEntity.ok(savedUsers);
        } catch (Exception e){

            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());

        }
    }

}
