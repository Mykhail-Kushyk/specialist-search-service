package com.ua.searchservice.controller;

import com.ua.searchservice.entity.order.request.CreateOrderRequest;
import com.ua.searchservice.entity.order.response.CreateOrderResponse;
import com.ua.searchservice.entity.user.response.UserResponse;
import com.ua.searchservice.entity.order.response.OrderResponse;
import com.ua.searchservice.entity.user.request.ChangePasswordRequest;
import com.ua.searchservice.entity.user.request.ChangeStatusRequest;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.service.CustomerService;

import io.swagger.v3.oas.annotations.Parameter;

import org.springdoc.core.converters.models.PageableAsQueryParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public UserResponse getUser(@AuthenticationPrincipal @RequestParam String username) {
        return service.getByUsername(username);
    }

    @GetMapping()
    @PageableAsQueryParam
    public Page<UserResponse> getAll(@Parameter(hidden = true) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PatchMapping("/me/password")
    public UserResponse changePassword(@AuthenticationPrincipal @RequestParam String username,
                                       @RequestBody ChangePasswordRequest request) {
        return service.changePassword(username, request);
    }

    @PatchMapping("/{id}/status")
    public UserResponse changeStatusById(@PathVariable Long id, @RequestBody ChangeStatusRequest request) {
        return service.changeStatus(id, request);
    }

    @PostMapping("/me/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request, @RequestParam @AuthenticationPrincipal String username) {
        return service.createOrder(username, request);
    }

    @GetMapping("/me/orders")
    @PageableAsQueryParam
    public Page<OrderResponse> getAllOrders(@RequestParam @AuthenticationPrincipal String username,
                                            @Parameter(hidden = true) Pageable pageable) {

        return service.getAllOrders(username, pageable);
    }

    @GetMapping("/me/orders/{id}/proposals")
    @PageableAsQueryParam
    public Page<SpecialistResponse> getProposedSpecialistsByOrder(@PathVariable Long id,
                                                                  @Parameter(hidden = true) Pageable pageable,
                                                                  @RequestParam @AuthenticationPrincipal String username
    ) {
        return service.getProposedSpecialists(id, pageable, username);
    }

    @PatchMapping("/me/orders/{id}/executor")
    public SpecialistResponse setOrderExecutor(@PathVariable Long id,
                                               @RequestParam @AuthenticationPrincipal String username,
                                               @RequestParam Long specialistId) {
        return service.setOrderExecutor(id, specialistId);
    }

    @DeleteMapping("/me/orders/{id}")
    public void completeDeal(@RequestParam @AuthenticationPrincipal String username,
                             @PathVariable Long id,
                             @RequestParam Integer rating) {
        service.completeDeal(id, rating);
    }
}