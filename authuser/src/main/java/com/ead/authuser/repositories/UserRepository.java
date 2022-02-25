package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM UserModel u LEFT JOIN FETCH u.courses where u.id = :userId")
    Optional<UserModel> findByIdAndFetchCourses(@Param("userId") UUID userId);
}
