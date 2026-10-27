package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.NewsletterSubscriber;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long> {
    Optional<NewsletterSubscriber> findByNewsletterSubscriberEmailIgnoreCase(String email);
}
