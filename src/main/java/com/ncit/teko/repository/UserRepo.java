package com.ncit.teko.repository;



import com.ncit.teko.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "select * from user where id = :uId", nativeQuery = true)
    User findUserByUserId(@Param("uId") int userId);

    @Modifying(flushAutomatically = true)
    @Query(value = "update user u set u.username = :uName where u.id = :uId", nativeQuery = true)
    void updateUsername(@Param("uId") int userId, @Param("uName") String userName);

    @Modifying(flushAutomatically = true)
    @Query(value = "update user u set u.email = :e where u.id = :uId", nativeQuery = true)
    void updateEmail(@Param("uId") int userId, @Param("e") String email);
}