package com.ua.searchservice.controller;

import com.ua.searchservice.entity.order.response.OrderResponse;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.entity.user.request.ChangePasswordRequest;
import com.ua.searchservice.entity.user.request.ChangeStatusRequest;
import com.ua.searchservice.service.SpecialistService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialists")
public class SpecialistController {

    private SpecialistService service;

    public SpecialistController(SpecialistService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public SpecialistResponse getUser(@AuthenticationPrincipal @RequestParam String username) {
        return service.getByUsername(username);
    }

    @GetMapping("/{id}")
    public SpecialistResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PatchMapping("/me/password")
    public SpecialistResponse changePassword(@RequestParam @AuthenticationPrincipal String username,
                                             @RequestBody ChangePasswordRequest request) {
        return service.changePassword(username, request);
    }

    @PatchMapping("/{id}/status")
    public SpecialistResponse changeStatusById(@PathVariable Long id, @RequestBody ChangeStatusRequest request) {
        return service.changeStatus(id, request);
    }

    @GetMapping
    @PageableAsQueryParam
    public Page<SpecialistResponse> getAll(@Parameter(hidden = true) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/available-orders")
    public List<OrderResponse> getAvailableOrders() {
        return service.getAvailableOrders();
    }

    @GetMapping("/me/orders")
    public List<OrderResponse> getMyOrders(
                                           @RequestParam @AuthenticationPrincipal String username) {
        return service.getOrdersBySpecialist(username);
    }

    @PostMapping("/me/available-orders/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse respondToOrder(@PathVariable Long id,
                                        @RequestParam @AuthenticationPrincipal String username,
                                        @RequestParam Long salary) {
        return service.respondToOrder(id, username, salary);
    }
}