package com.ua.searchservice.service;

import com.ua.searchservice.entity.admin.Admin;
import com.ua.searchservice.entity.admin.request.CreateAdminRequest;
import com.ua.searchservice.entity.user.response.UserResponse;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.entity.user.UserStatus;
import com.ua.searchservice.repository.AdminRepository;
import com.ua.searchservice.repository.CustomerRepository;
import com.ua.searchservice.repository.SpecialistRepository;
import com.ua.searchservice.util.ConvertToResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    private CustomerRepository customerRepository;

    private SpecialistRepository specialistRepository;

    private PasswordEncoder encoder;

    public AdminService(AdminRepository adminRepository, CustomerRepository customerRepository,
                        SpecialistRepository specialistRepository, PasswordEncoder encoder) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
        this.encoder = encoder;
    }

    @Transactional
    public Page<UserResponse> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable).map(ConvertToResponseUtil::convertUser);
    }

    @Transactional
    public Page<SpecialistResponse> getAllSpecialists(Pageable pageable) {
        return specialistRepository.findAll(pageable).map(ConvertToResponseUtil::convertSpecialist);
    }

    @Transactional
    public UserResponse createAdmin(CreateAdminRequest request) {
        Admin admin = new Admin();
        admin.setStatus(UserStatus.SUSPENDED);
        admin.setEmail(request.getEmail());
        admin.setUsername(request.getUsername());
        admin.setPassword(encoder.encode(request.getPassword()));
        adminRepository.save(admin);
        return ConvertToResponseUtil.convertUser(admin);
    }

    @Transactional
    public void deleteSpecialist(Long id) {
        specialistRepository.deleteById(id);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
