package com.jay.jphsql.repositories;

import com.jay.jphsql.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <CommentModel, Integer> {
}
