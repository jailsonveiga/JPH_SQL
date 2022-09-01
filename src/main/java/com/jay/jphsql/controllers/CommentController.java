package com.jay.jphsql.controllers;

import com.jay.jphsql.models.CommentModel;
import com.jay.jphsql.models.UserModel;
import com.jay.jphsql.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final String JPH_API_URL = "https://jsonplaceholder.typicode.com/comments";

    @Autowired
    private CommentRepository commentRepository;

    // get all users stored in our local MySQL database
    @GetMapping("/sql/all")
    public ResponseEntity<?> getAllCommentsSQL () {
        try {

            ArrayList<CommentModel> allComments= (ArrayList<CommentModel>) commentRepository.findAll();

            return ResponseEntity.ok(allComments);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping ("/sql/all")
    public ResponseEntity<?> uploadAllCommentsSQL (RestTemplate restTemplate) {
        try {

            // retrieve data from JPH API and save to array of comments
            CommentModel[] allComments = restTemplate.getForObject(JPH_API_URL, CommentModel[].class);

            // check that allComments is present, otherwise an exception will be thrown
            assert allComments != null;

            // remove id from each comment
            for(CommentModel comment : allComments) {
                comment.removeId();
            }

            // saves comment to database and updates each comment's id to the saved database ID
            commentRepository.saveAll(Arrays.asList(allComments));

            // respond with the data that was just saved to the database
            return ResponseEntity.ok(allComments);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
