package com.ua.searchservice.entity.admin;

import com.ua.searchservice.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@AttributeOverride(name = "user_id", column = @Column(name = "admin_id"))
public class Admin extends User {

    @Transient
    @Override
    public List<GrantedAuthority> getRoles() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}