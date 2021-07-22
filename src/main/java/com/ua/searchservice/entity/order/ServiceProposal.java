package com.ua.searchservice.entity.order;

import com.ua.searchservice.entity.specialist.Specialist;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "service_proposals")
@Data
public class ServiceProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_proposal")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

    private Long salary;
}