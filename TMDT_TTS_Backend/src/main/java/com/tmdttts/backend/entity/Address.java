package com.tmdttts.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_user_id", nullable = false)
    private User addressUser;

    @Column(name = "address_recipient_name", nullable = false, length = 150)
    private String addressRecipientName;

    @Column(name = "address_phone", nullable = false, length = 30)
    private String addressPhone;

    @Column(name = "address_detail", nullable = false, length = 255)
    private String addressDetail;

    @Column(name = "address_ward", length = 100)
    private String addressWard;

    @Column(name = "address_district", length = 100)
    private String addressDistrict;

    @Column(name = "address_city", nullable = false, length = 100)
    private String addressCity;

    @Column(name = "address_postal_code", length = 20)
    private String addressPostalCode;

    @Column(name = "address_is_default", nullable = false)
    private boolean addressIsDefault;

    @Column(name = "address_created_at", nullable = false)
    private OffsetDateTime addressCreatedAt;

    @PrePersist
    void prePersist() {
        if (addressCreatedAt == null) {
            addressCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getAddressId() { return addressId; }
    public User getAddressUser() { return addressUser; }
    public void setAddressUser(User addressUser) { this.addressUser = addressUser; }
    public String getAddressRecipientName() { return addressRecipientName; }
    public void setAddressRecipientName(String addressRecipientName) { this.addressRecipientName = addressRecipientName; }
    public String getAddressPhone() { return addressPhone; }
    public void setAddressPhone(String addressPhone) { this.addressPhone = addressPhone; }
    public String getAddressDetail() { return addressDetail; }
    public void setAddressDetail(String addressDetail) { this.addressDetail = addressDetail; }
    public String getAddressWard() { return addressWard; }
    public void setAddressWard(String addressWard) { this.addressWard = addressWard; }
    public String getAddressDistrict() { return addressDistrict; }
    public void setAddressDistrict(String addressDistrict) { this.addressDistrict = addressDistrict; }
    public String getAddressCity() { return addressCity; }
    public void setAddressCity(String addressCity) { this.addressCity = addressCity; }
    public String getAddressPostalCode() { return addressPostalCode; }
    public void setAddressPostalCode(String addressPostalCode) { this.addressPostalCode = addressPostalCode; }
    public boolean isAddressIsDefault() { return addressIsDefault; }
    public void setAddressIsDefault(boolean addressIsDefault) { this.addressIsDefault = addressIsDefault; }
    public OffsetDateTime getAddressCreatedAt() { return addressCreatedAt; }
}
