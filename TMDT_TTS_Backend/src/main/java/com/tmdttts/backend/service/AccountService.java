package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.request.account.AddressRequest;
import com.tmdttts.backend.dto.request.account.UpdateProfileRequest;
import com.tmdttts.backend.dto.response.user.UserResponse;
import com.tmdttts.backend.dto.response.account.AddressResponse;

import com.tmdttts.backend.entity.Address;
import com.tmdttts.backend.entity.User;
import com.tmdttts.backend.mapper.AccountMapper;
import com.tmdttts.backend.repository.AddressRepository;
import com.tmdttts.backend.repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {
    private final UserRepository users; private final AddressRepository addresses; private final AccountMapper mapper;
    public AccountService(UserRepository users,AddressRepository addresses,AccountMapper mapper){this.users=users;this.addresses=addresses;this.mapper=mapper;}
    @Transactional(readOnly=true) public UserResponse profile(Long id){return mapper.toResponse(user(id));}
    @Transactional public UserResponse updateProfile(Long id,UpdateProfileRequest body){User u=user(id);u.setUserName(body.name().trim());u.setUserPhone(body.phone());return mapper.toResponse(u);}
    @Transactional(readOnly=true) public List<AddressResponse> addresses(Long id){user(id);return addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id).stream().map(address->mapper.toResponse(address)).toList();}
    @Transactional public AddressResponse addAddress(Long id,AddressRequest body){User u=user(id);var existing=addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id);
        if(body.defaultAddress()||existing.isEmpty())existing.forEach(a->a.setAddressIsDefault(false));
        Address a=new Address();a.setAddressUser(u);apply(a,body);a.setAddressIsDefault(body.defaultAddress()||existing.isEmpty());return mapper.toResponse(addresses.save(a));}
    @Transactional public AddressResponse updateAddress(Long id,Long addressId,AddressRequest body){Address a=ownedAddress(id,addressId);
        if(body.defaultAddress())addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id).forEach(other->{if(!other.getAddressId().equals(addressId))other.setAddressIsDefault(false);});
        apply(a,body);return mapper.toResponse(a);}
    @Transactional public void deleteAddress(Long id,Long addressId){Address a=ownedAddress(id,addressId);boolean wasDefault=a.isAddressIsDefault();addresses.delete(a);
        if(wasDefault)addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id).stream().findFirst().ifPresent(next->next.setAddressIsDefault(true));}
    private void apply(Address a,AddressRequest b){a.setAddressRecipientName(b.recipientName().trim());a.setAddressPhone(b.phone().trim());a.setAddressDetail(b.detail().trim());a.setAddressWard(b.ward());a.setAddressDistrict(b.district());a.setAddressCity(b.city().trim());a.setAddressPostalCode(b.postalCode());a.setAddressIsDefault(b.defaultAddress());}
    private User user(Long id){return users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Tài khoản không tồn tại"));}
    private Address ownedAddress(Long userId,Long addressId){return addresses.findByAddressIdAndAddressUser_UserId(addressId,userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy địa chỉ"));}
}
