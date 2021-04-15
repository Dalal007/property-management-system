package com.narola.property.repository;


import com.narola.property.entity.User;
import com.narola.property.model.PropertySearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User getUserByUsername(@Param("userName") String userName);
}
