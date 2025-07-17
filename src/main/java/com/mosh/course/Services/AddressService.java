package com.mosh.course.Services;


import com.mosh.course.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public void showAddressEagerAndLazyLoading(){
        var address = addressRepository.findById(1L).orElseThrow();
        System.out.println(address.getStreet());
    }
}
