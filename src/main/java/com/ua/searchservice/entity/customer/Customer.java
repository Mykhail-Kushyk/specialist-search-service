package com.ua.searchservice.entity.customer;

import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AttributeOverride(name = "user_id", column = @Column(name = "customer_id"))
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Transient
    @Override
    public List<GrantedAuthority> getRoles() {
        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }
}