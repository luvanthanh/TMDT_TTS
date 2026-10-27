package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.ContactMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findByContactMessageStatusOrderByContactMessageCreatedAtDesc(String status);
}
