package com.tmdttts.backend.repository;
import com.tmdttts.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Long>{ Optional<User> findByUserEmail(String userEmail); Optional<User> findByUserEmailIgnoreCase(String userEmail); boolean existsByUserEmail(String userEmail); boolean existsByUserEmailIgnoreCase(String userEmail); }
