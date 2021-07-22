package com.ua.searchservice.util;

import com.ua.searchservice.entity.user.response.UserResponse;
import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.order.response.OrderResponse;
import com.ua.searchservice.entity.specialist.Specialist;
import com.ua.searchservice.entity.specialist.response.SpecialistResponse;
import com.ua.searchservice.entity.user.User;

public class ConvertToResponseUtil {

    public static UserResponse convertUser(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setStatus(user.getStatus());
        return response;
    }

    public static OrderResponse convertOrder(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getOrderId());
        response.setDescription(order.getDescription());
        response.setCreatedAt(order.getCreatedAt());
        response.setCustomerId(order.getCustomer().getUserId());
        response.setSpecialistId(order.getSpecialist().getUserId());
        return response;
    }

    public static SpecialistResponse convertSpecialist(Specialist specialist) {
        SpecialistResponse response = new SpecialistResponse();
        response.setId(specialist.getUserId());
        response.setUsername(specialist.getUsername());
        response.setEmail(specialist.getEmail());
        response.setStatus(specialist.getStatus());
        response.setWorkExperience(specialist.getWorkExperience());
        return response;
    }
}