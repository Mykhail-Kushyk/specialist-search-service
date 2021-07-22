package com.ua.searchservice.controller;

import com.ua.searchservice.entity.admin.request.CreateAdminRequest;
import com.ua.searchservice.entity.user.response.UserResponse;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.service.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("/customers")
    @PageableAsQueryParam
    public Page<UserResponse> getAllCustomers(@Parameter(hidden = true) Pageable pageable) {
        return service.getAllCustomers(pageable);
    }

    @GetMapping("/specialists")
    @PageableAsQueryParam
    public Page<SpecialistResponse> getAllSpecialists(@Parameter(hidden = true) Pageable pageable) {
        return service.getAllSpecialists(pageable);
    }

    @PostMapping()
    public UserResponse createNewAdmin(CreateAdminRequest request) {
        return service.createAdmin(request);
    }

    @DeleteMapping("/specialists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialist(@PathVariable Long id) {
        service.deleteSpecialist(id);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomers(@PathVariable Long id) {
        service.deleteCustomer(id);
    }
}