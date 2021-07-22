package com.ua.searchservice.service;

import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.order.ServiceProposal;
import com.ua.searchservice.entity.order.response.OrderResponse;
import com.ua.searchservice.entity.specialist.Specialist;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.entity.user.request.ChangePasswordRequest;
import com.ua.searchservice.entity.user.request.ChangeStatusRequest;
import com.ua.searchservice.exceptions.UserExceptions;
import com.ua.searchservice.repository.OrderRepository;
import com.ua.searchservice.repository.ServiceProposalRepository;
import com.ua.searchservice.repository.SpecialistRepository;
import com.ua.searchservice.util.ConvertToResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistService {

    private SpecialistRepository specialistRepository;

    private OrderRepository orderRepository;

    private PasswordEncoder encoder;

    private ServiceProposalRepository proposalRepository;

    public SpecialistService(SpecialistRepository specialistRepository, PasswordEncoder encoder,
                             OrderRepository orderRepository, ServiceProposalRepository proposalRepository) {
        this.specialistRepository = specialistRepository;
        this.orderRepository = orderRepository;
        this.proposalRepository = proposalRepository;
        this.encoder = encoder;
    }

    @Transactional
    public SpecialistResponse getByUsername(String username) {
        checkIfExists(username);
        Specialist specialist = specialistRepository.readByUsername(username).get();
        return ConvertToResponseUtil.convertSpecialist(specialist);
    }

    @Transactional
    public SpecialistResponse getById(Long id) {
        checkIfExists(id);
        Specialist specialist = specialistRepository.getById(id);
        return ConvertToResponseUtil.convertSpecialist(specialist);
    }

    @Transactional
    public SpecialistResponse changePassword(String username, ChangePasswordRequest request) {
        checkIfExists(username);
        Specialist specialist = specialistRepository.readByUsername(username).get();

        if (!encoder.matches(request.getOldPassword(), specialist.getPassword())) {
            throw UserExceptions.wrongPassword();
        }

        specialist.setPassword(request.getNewPassword());
        return ConvertToResponseUtil.convertSpecialist(specialist);
    }

    @Transactional
    public SpecialistResponse changeStatus(Long id, ChangeStatusRequest request) {
        checkIfExists(id);

        Specialist specialist = specialistRepository.getById(id);
        specialist.setStatus(request.getStatus());
        return ConvertToResponseUtil.convertSpecialist(specialist);
    }

    @Transactional
    public Page<SpecialistResponse> getAll(Pageable pageable) {
        return specialistRepository.findAll(pageable).map(ConvertToResponseUtil::convertSpecialist);
    }

    @Transactional
    public List<OrderResponse> getAvailableOrders() {
        return orderRepository.findAvailableOrders()
                .stream().map(ConvertToResponseUtil::convertOrder).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderResponse> getOrdersBySpecialist(String username) {
        checkIfExists(username);
        Specialist specialist = specialistRepository.readByUsername(username).get();
        return orderRepository.findBySpecialist(specialist)
                .stream().map(ConvertToResponseUtil::convertOrder).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse respondToOrder(Long orderId, String username, Long salary) {
        checkIfExists(username);
        Specialist specialist = specialistRepository.readByUsername(username).get();

        Order order = orderRepository.getById(orderId);

        ServiceProposal proposal = new ServiceProposal();

        proposal.setSpecialist(specialist);
        proposal.setOrder(order);
        proposal.setSalary(salary);

        proposalRepository.save(proposal);

        return ConvertToResponseUtil.convertOrder(order);
    }

    private void checkIfExists(String username) {
        if (!specialistRepository.existsSpecialistByUsername(username)) {
            throw UserExceptions.userNotFound(username);
        }
    }

    private void checkIfExists(Long id) {
        if (!specialistRepository.existsById(id)) {
            throw UserExceptions.userNotFound(id);
        }
    }
}