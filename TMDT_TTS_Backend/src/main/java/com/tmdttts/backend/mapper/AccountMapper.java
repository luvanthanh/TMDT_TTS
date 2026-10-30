package com.tmdttts.backend.mapper;

import com.tmdttts.backend.dto.response.account.AddressResponse;
import com.tmdttts.backend.dto.response.user.UserResponse;
import com.tmdttts.backend.entity.Address;
import com.tmdttts.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public UserResponse toResponse(User user){return new UserResponse(user.getUserId(),user.getUserName(),user.getUserEmail(),user.getUserPhone(),user.getUserRole());}
    public AddressResponse toResponse(Address a){return new AddressResponse(a.getAddressId(),a.getAddressRecipientName(),a.getAddressPhone(),a.getAddressDetail(),a.getAddressWard(),a.getAddressDistrict(),a.getAddressCity(),a.getAddressPostalCode(),a.isAddressIsDefault());}
}
