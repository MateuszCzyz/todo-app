package com.mateuszczyz.Todo.repository;


import com.mateuszczyz.Todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("""
            SELECT CASE WHEN count(u) > 0
            THEN true ELSE false END
            FROM User u
            WHERE u.email = :email""")
    boolean existsByEmail(String email);
}
