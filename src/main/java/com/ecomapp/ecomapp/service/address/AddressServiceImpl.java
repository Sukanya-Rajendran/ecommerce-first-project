package com.ecomapp.ecomapp.service.address;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class AddressServiceImpl implements  AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }


    @Override
    public Optional<Address> findById(UUID id) {

        return addressRepository.findById(id);
    }

    @Override
    public List<Address> findByUser(User user) {
        return addressRepository.findByUser(user);
    }


    @Override
    public void deleteUserAddress(UUID id) {

    }
    @Override
    public void disableAddress(UUID addressId) {
        Optional<Address> userAddress= addressRepository.findById(addressId);
        if(userAddress.isPresent()){
            Address address =userAddress.get();
            address.setDeleted(true);
            addressRepository.save(address);
        }
    }

    @Override
    public List<Address> getNonDeleteAddressByCustomer(User user) {
        return addressRepository.findByIsDeletedFalse();
    }
    @Override
    public Address getAddressById(UUID id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElse(null);
    }



    @Override
    public Address editAddress(UUID id, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(id).orElse(null);
        if (existingAddress != null) {
            // Update the fields of the existing address with the new values
            existingAddress.setFlat(updatedAddress.getFlat());
            existingAddress.setArea(updatedAddress.getArea());
            existingAddress.setTown(updatedAddress.getTown());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPin(updatedAddress.getPin());
            existingAddress.setLandmark(updatedAddress.getLandmark());

            // Save the updated address
            return addressRepository.save(existingAddress);
        }
        return null; // Address not found
    }
}


