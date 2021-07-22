package com.ua.searchservice.entity.order;

import com.ua.searchservice.entity.customer.Customer;
import com.ua.searchservice.entity.specialist.Specialist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<ServiceProposal> offers = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    private String description;

    public Order() {
        this.createdAt = OffsetDateTime.now();
    }
}