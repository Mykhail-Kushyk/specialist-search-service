package com.ua.searchservice.service;

import com.ua.searchservice.entity.customer.Customer;
import com.ua.searchservice.entity.user.response.UserResponse;
import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.order.request.CreateOrderRequest;
import com.ua.searchservice.entity.order.response.CreateOrderResponse;
import com.ua.searchservice.entity.order.response.OrderResponse;
import com.ua.searchservice.entity.specialist.Rating;
import com.ua.searchservice.entity.specialist.Specialist;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.entity.user.request.ChangePasswordRequest;
import com.ua.searchservice.entity.user.request.ChangeStatusRequest;
import com.ua.searchservice.exceptions.UserExceptions;
import com.ua.searchservice.repository.CustomerRepository;
import com.ua.searchservice.repository.OrderRepository;
import com.ua.searchservice.repository.RatingRepository;
import com.ua.searchservice.repository.SpecialistRepository;
import com.ua.searchservice.util.ConvertToResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    private PasswordEncoder encoder;

    private OrderRepository orderRepository;

    private SpecialistRepository specialistRepository;

    private RatingRepository ratingRepository;

    public CustomerService(CustomerRepository repository, PasswordEncoder encoder, OrderRepository orderRepository,
                           SpecialistRepository specialistRepository, RatingRepository ratingRepository) {
        this.customerRepository = repository;
        this.encoder = encoder;
        this.orderRepository = orderRepository;
        this.ratingRepository = ratingRepository;
        this.specialistRepository = specialistRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse getByUsername(String username) {
        return ConvertToResponseUtil.convertUser(
                customerRepository.findByUsername(username).orElseThrow(() -> UserExceptions.userNotFound(username)));
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return ConvertToResponseUtil.convertUser(
                customerRepository.findById(id).orElseThrow(() -> UserExceptions.userNotFound(id)));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(ConvertToResponseUtil::convertUser);
    }

    @Transactional
    public UserResponse changePassword(String username, ChangePasswordRequest request) {
        checkIfUserExists(username);
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (!encoder.matches(request.getOldPassword(), customer.get().getPassword())) {
            throw UserExceptions.wrongPassword();
        }
        customer.get().setPassword(encoder.encode(request.getNewPassword()));
        return ConvertToResponseUtil.convertUser(customer.get());
    }

    @Transactional
    public CreateOrderResponse createOrder(String username, CreateOrderRequest request) {
        checkIfUserExists(username);
        Optional<Customer> customer = customerRepository.findByUsername(username);
        Order order = new Order();
        order.setDescription(request.getDescription());
        order.setCustomer(customer.get());
        orderRepository.save(order);
        return new CreateOrderResponse(order.getDescription(), order.getCreatedAt(), order.getCustomer().getUserId());
    }

    @Transactional
    public Page<OrderResponse> getAllOrders(String username, Pageable pageable) {
        checkIfUserExists(username);
        return orderRepository.findByCustomer(customerRepository.findByUsername(username).get(), pageable).map(ConvertToResponseUtil::convertOrder);
    }

    @Transactional
    public Page<SpecialistResponse> getProposedSpecialists(Long id, Pageable pageable, String username) {

        checkIfUserExists(username);

        Order order = orderRepository.getById(id);
        if (!customerRepository.findByUsername(username).get().getOrders().contains(order)) {
            throw UserExceptions.badRequest();
        }

        return specialistRepository.findProposedSpecialistsByOrder(order.getOrderId(), pageable)
                .map(ConvertToResponseUtil::convertSpecialist);
    }

    @Transactional
    public UserResponse changeStatus(Long id, ChangeStatusRequest request) {
        Customer customer = customerRepository.getById(id);
        if (customer.getStatus() != request.getStatus()) {
            customer.setStatus(request.getStatus());
        }
        return ConvertToResponseUtil.convertUser(customer);
    }

    @Transactional
    public SpecialistResponse setOrderExecutor(Long orderId, Long specId) {
        checkIfOrderExists(orderId);
        Specialist specialist = specialistRepository.getById(specId);
        orderRepository.setSpecialist(orderId, specialist);
        return ConvertToResponseUtil.convertSpecialist(specialist);
    }

    @Transactional
    public void completeDeal(Long orderId, Integer rating) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> UserExceptions.orderNotFound(orderId));
        Rating specRating = new Rating();
        specRating.setRating(rating);
        specRating.setSpecialist(order.getSpecialist());
        ratingRepository.save(specRating);
    }

    private void checkIfUserExists(String username) {
        if (!customerRepository.existsCustomerByUsername(username)) {
            throw UserExceptions.userNotFound(username);
        }
    }

    private void checkIfOrderExists(Long id) {
        if (!orderRepository.existsById(id)) {
            throw UserExceptions.orderNotFound(id);
        }
    }
}