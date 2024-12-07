package com.backend.mainModule.repo;

import com.backend.mainModule.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    
    boolean existsByUserName(String userName);
    
    List<User> findByUserNameContaining(String partialUsername);
}