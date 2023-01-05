package com.mateuszczyz.Todo.repository;


import com.mateuszczyz.Todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u
            FROM User u
            WHERE u.email = :email
            """)
    Optional<User> findByEmail(@Param("email") String email);

    @Query("""
            SELECT CASE WHEN count(u) > 0
            THEN true ELSE false END
            FROM User u
            WHERE u.email = :email
            """)
    boolean existsByEmail(@Param("email") String email);
}
