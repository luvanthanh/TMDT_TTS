package com.tmdttts.backend.dto.response.account;

public record AddressResponse(Long id, String recipientName, String phone, String detail,
        String ward, String district, String city, String postalCode, boolean defaultAddress) {}
