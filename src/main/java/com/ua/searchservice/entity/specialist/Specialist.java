package com.ua.searchservice.entity.specialist;

import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.order.ServiceProposal;
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
@AttributeOverride(name = "user_id", column = @Column(name = "specialist_id"))
public class Specialist extends User {

    @Column(name = "work_experience")
    private Integer workExperience;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<ServiceProposal> offers = new ArrayList<>();

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Transient
    @Override
    public List<GrantedAuthority> getRoles() {
        return List.of(new SimpleGrantedAuthority("ROLE_SPECIALIST"));
    }
}