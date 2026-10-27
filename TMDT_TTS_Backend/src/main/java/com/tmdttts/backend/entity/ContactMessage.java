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
@Table(name = "contact_messages")
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_message_id")
    private Long contactMessageId;

    @Column(name = "contact_message_full_name", nullable = false, length = 150)
    private String contactMessageFullName;

    @Column(name = "contact_message_email", nullable = false, length = 255)
    private String contactMessageEmail;

    @Column(name = "contact_message_phone", length = 30)
    private String contactMessagePhone;

    @Column(name = "contact_message_subject", length = 200)
    private String contactMessageSubject;

    @Column(name = "contact_message_content", nullable = false, columnDefinition = "text")
    private String contactMessageContent;

    @Column(name = "contact_message_status", nullable = false, length = 20)
    private String contactMessageStatus = "NEW";

    @Column(name = "contact_message_created_at", nullable = false)
    private OffsetDateTime contactMessageCreatedAt;

    @PrePersist
    void prePersist() {
        if (contactMessageCreatedAt == null) {
            contactMessageCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getContactMessageId() { return contactMessageId; }
    public String getContactMessageFullName() { return contactMessageFullName; }
    public void setContactMessageFullName(String contactMessageFullName) { this.contactMessageFullName = contactMessageFullName; }
    public String getContactMessageEmail() { return contactMessageEmail; }
    public void setContactMessageEmail(String contactMessageEmail) { this.contactMessageEmail = contactMessageEmail; }
    public String getContactMessagePhone() { return contactMessagePhone; }
    public void setContactMessagePhone(String contactMessagePhone) { this.contactMessagePhone = contactMessagePhone; }
    public String getContactMessageSubject() { return contactMessageSubject; }
    public void setContactMessageSubject(String contactMessageSubject) { this.contactMessageSubject = contactMessageSubject; }
    public String getContactMessageContent() { return contactMessageContent; }
    public void setContactMessageContent(String contactMessageContent) { this.contactMessageContent = contactMessageContent; }
    public String getContactMessageStatus() { return contactMessageStatus; }
    public void setContactMessageStatus(String contactMessageStatus) { this.contactMessageStatus = contactMessageStatus; }
    public OffsetDateTime getContactMessageCreatedAt() { return contactMessageCreatedAt; }
}
