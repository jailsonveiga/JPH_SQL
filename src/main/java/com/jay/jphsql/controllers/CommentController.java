package com.jay.jphsql.controllers;

import com.jay.jphsql.models.CommentModel;
import com.jay.jphsql.models.UserModel;
import com.jay.jphsql.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CommentController {

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
}
