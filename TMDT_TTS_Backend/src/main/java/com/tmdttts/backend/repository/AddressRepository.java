package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByAddressUser_UserIdOrderByAddressIsDefaultDescAddressCreatedAtDesc(Long userId);

    Optional<Address> findByAddressIdAndAddressUser_UserId(Long addressId, Long userId);

    boolean existsByAddressUser_UserIdAndAddressIsDefaultTrue(Long userId);
}
