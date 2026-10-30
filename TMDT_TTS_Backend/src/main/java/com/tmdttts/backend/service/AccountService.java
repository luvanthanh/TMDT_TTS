package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.entity.Address;
import com.tmdttts.backend.entity.User;
import com.tmdttts.backend.mapper.ApiMapper;
import com.tmdttts.backend.repository.AddressRepository;
import com.tmdttts.backend.repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {
    private final UserRepository users; private final AddressRepository addresses; private final ApiMapper mapper;
    public AccountService(UserRepository users,AddressRepository addresses,ApiMapper mapper){this.users=users;this.addresses=addresses;this.mapper=mapper;}
    @Transactional(readOnly=true) public Responses.User profile(Long id){return mapper.user(user(id));}
    @Transactional public Responses.User updateProfile(Long id,Requests.Profile body){User u=user(id);u.setUserName(body.name().trim());u.setUserPhone(body.phone());return mapper.user(u);}
    @Transactional(readOnly=true) public List<Responses.Address> addresses(Long id){user(id);return addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id).stream().map(mapper::address).toList();}
    @Transactional public Responses.Address addAddress(Long id,Requests.Address body){User u=user(id);var existing=addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id);
        if(body.defaultAddress()||existing.isEmpty())existing.forEach(a->a.setAddressIsDefault(false));
        Address a=new Address();a.setAddressUser(u);apply(a,body);a.setAddressIsDefault(body.defaultAddress()||existing.isEmpty());return mapper.address(addresses.save(a));}
    @Transactional public Responses.Address updateAddress(Long id,Long addressId,Requests.Address body){Address a=ownedAddress(id,addressId);
        if(body.defaultAddress())addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id).forEach(other->{if(!other.getAddressId().equals(addressId))other.setAddressIsDefault(false);});
        apply(a,body);return mapper.address(a);}
    @Transactional public void deleteAddress(Long id,Long addressId){Address a=ownedAddress(id,addressId);boolean wasDefault=a.isAddressIsDefault();addresses.delete(a);
        if(wasDefault)addresses.findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(id).stream().findFirst().ifPresent(next->next.setAddressIsDefault(true));}
    private void apply(Address a,Requests.Address b){a.setAddressRecipientName(b.recipientName().trim());a.setAddressPhone(b.phone().trim());a.setAddressDetail(b.detail().trim());a.setAddressWard(b.ward());a.setAddressDistrict(b.district());a.setAddressCity(b.city().trim());a.setAddressPostalCode(b.postalCode());a.setAddressIsDefault(b.defaultAddress());}
    private User user(Long id){return users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Tài khoản không tồn tại"));}
    private Address ownedAddress(Long userId,Long addressId){return addresses.findByAddressIdAndAddressUser_UserId(addressId,userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy địa chỉ"));}
}
