package com.tmdttts.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "newsletter_subscribers")
public class NewsletterSubscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsletter_subscriber_id")
    private Long newsletterSubscriberId;

    @Column(name = "newsletter_subscriber_email", nullable = false, unique = true, length = 255)
    private String newsletterSubscriberEmail;

    @Column(name = "newsletter_subscriber_status", nullable = false, length = 20)
    private String newsletterSubscriberStatus = "SUBSCRIBED";

    @Column(name = "newsletter_subscriber_subscribed_at", nullable = false)
    private OffsetDateTime newsletterSubscriberSubscribedAt;

    @Column(name = "newsletter_subscriber_unsubscribed_at")
    private OffsetDateTime newsletterSubscriberUnsubscribedAt;

    @PrePersist
    void prePersist() {
        if (newsletterSubscriberSubscribedAt == null) {
            newsletterSubscriberSubscribedAt = OffsetDateTime.now();
        }
    }

    public Long getNewsletterSubscriberId() { return newsletterSubscriberId; }
    public String getNewsletterSubscriberEmail() { return newsletterSubscriberEmail; }
    public void setNewsletterSubscriberEmail(String newsletterSubscriberEmail) { this.newsletterSubscriberEmail = newsletterSubscriberEmail; }
    public String getNewsletterSubscriberStatus() { return newsletterSubscriberStatus; }
    public void setNewsletterSubscriberStatus(String newsletterSubscriberStatus) { this.newsletterSubscriberStatus = newsletterSubscriberStatus; }
    public OffsetDateTime getNewsletterSubscriberSubscribedAt() { return newsletterSubscriberSubscribedAt; }
    public OffsetDateTime getNewsletterSubscriberUnsubscribedAt() { return newsletterSubscriberUnsubscribedAt; }
    public void setNewsletterSubscriberUnsubscribedAt(OffsetDateTime newsletterSubscriberUnsubscribedAt) { this.newsletterSubscriberUnsubscribedAt = newsletterSubscriberUnsubscribedAt; }
}
