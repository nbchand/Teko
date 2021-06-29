package com.ncit.teko.repository;

import com.ncit.teko.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsUserByUsernameOrEmail(String username, String email);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    @Query(value = "select * from user where  email = :em and password = :psw ", nativeQuery = true)
    User checkUser(@Param("em") String email, @Param("psw") String password);

    @Query(value = "select * from user where verification_code = :c ", nativeQuery = true)
    User checkByVerificationCode(@Param("c") String code);

}