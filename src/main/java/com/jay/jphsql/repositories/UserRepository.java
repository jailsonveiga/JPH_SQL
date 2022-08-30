package com.jay.jphsql.repositories;


import com.jay.jphsql.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer > {
}
