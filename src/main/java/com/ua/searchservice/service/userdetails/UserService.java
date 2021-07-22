package com.ua.searchservice.service.userdetails;

import com.ua.searchservice.entity.admin.Admin;
import com.ua.searchservice.entity.customer.Customer;
import com.ua.searchservice.entity.specialist.Specialist;
import com.ua.searchservice.entity.user.MyUserDetails;
import com.ua.searchservice.repository.AdminRepository;
import com.ua.searchservice.repository.CustomerRepository;
import com.ua.searchservice.repository.SpecialistRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private CustomerRepository customerRepository;
    private SpecialistRepository specialistRepository;
    private AdminRepository adminRepository;

    public UserService(AdminRepository adminRepository,
                       CustomerRepository customerRepository,
                       SpecialistRepository specialistRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (customerRepository.existsCustomerByUsername(username)) {

            Optional<Customer> user = customerRepository.findByUsername(username);
            return new MyUserDetails(user.get(), user.get().getRoles());

        } else if (specialistRepository.existsSpecialistByUsername(username)) {

            Optional<Specialist> user = specialistRepository.readByUsername(username);
            return new MyUserDetails(user.get(), user.get().getRoles());

        } else if (adminRepository.existsByUsername(username)) {
            Admin user = adminRepository.findByUsername(username);
            return new MyUserDetails(user, user.getRoles());
        }
        throw new UsernameNotFoundException("User not found by username: " + username);
    }
}