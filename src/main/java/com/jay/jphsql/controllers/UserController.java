package com.jay.jphsql.controllers;

import com.jay.jphsql.models.UserModel;
import com.jay.jphsql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
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

    // Delete one user by ID from SQL must make sure a user with the given id already exist
    @DeleteMapping ("/sql/{id}")
    public ResponseEntity<?> deleteOneUserById (@PathVariable String id) {
        try {
            // throws NumberFormatException if id is not an int
            int userId = Integer.parseInt(id);

            System.out.println("Getting User With ID: " + id);

            // GET data from SQL (using repo)
            Optional<UserModel> foundUser = userRepository.findById(userId);

            if (foundUser.isEmpty())   return ResponseEntity.status(404).body("User Not Found With ID: " + id);

            userRepository.deleteById(userId);

            return ResponseEntity.ok(foundUser.get());

        } catch (NumberFormatException e) {

            return ResponseEntity.status(400).body("ID: " + id + ", is not a valid id. Must be a whole number");

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Delete all users from SQL must make sure a user with the given id already exist
    @DeleteMapping("/sql/all")
    public ResponseEntity<?> deleteAllUsersSQL () {
        try {

            long count = userRepository.count();
            userRepository.deleteAll();

            return ResponseEntity.ok("Deleted Users: " + count);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Get one user by ID from SQL
    @GetMapping ("/sql/{id}")
    public ResponseEntity<?> getOneUserById (@PathVariable String id) {
        try {
            // throws NumberFormatException if id is not an int
            int userId = Integer.parseInt(id);

            System.out.println("Getting User With ID: " + id);

            // GET data from SQL (using repo)
            Optional<UserModel> foundUser = userRepository.findById(userId);

            if (foundUser.isEmpty())   return ResponseEntity.status(404).body("User Not Found With ID: " + id);

            return ResponseEntity.ok(foundUser.get());

        } catch (NumberFormatException e) {

            return ResponseEntity.status(400).body("ID: " + id + ", is not a valid id. Must be a whole number");

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

    // grab all the users from JPH and store inside our local MySQL database
    @PostMapping("/sql/all")
    public ResponseEntity<?> uploadAllUsersToSQL (RestTemplate restTemplate) {

        try {
            // retrieve data from JPH API and save to array of UserModels
            UserModel[] allUsers = restTemplate.getForObject(JPH_API_URL, UserModel[].class);

            // check that allUsers is present, otherwise an exception will be throw
            assert allUsers != null;

            // remove id from each user
            for (UserModel allUser : allUsers
                 ) {
                allUser.removeId();
            }

            // saves users to database and updates each User's id field to the saved database ID
            userRepository.saveAll(Arrays.asList(allUsers));

            // respond with the data that was just saved to the database
            return ResponseEntity.ok(allUsers);

        } catch (Exception e){

            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());

        }
    }

    // Create User
    @PostMapping
    public ResponseEntity<?> uploadOneUser (@RequestBody UserModel newUserData) {
        try {

            newUserData.removeId();

            UserModel savedUser = userRepository.save(newUserData);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
