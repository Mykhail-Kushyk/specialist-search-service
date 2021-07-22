package com.ua.searchservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ua.searchservice.config.Properties;
import com.ua.searchservice.entity.specialist.Specialist;
import com.ua.searchservice.entity.specialist.request.AuthSpecialistRequest;
import com.ua.searchservice.entity.specialist.response.AuthSpecialistResponse;
import com.ua.searchservice.entity.user.UserStatus;
import com.ua.searchservice.entity.user.request.SignInRequest;
import com.ua.searchservice.entity.customer.Customer;
import com.ua.searchservice.entity.customer.request.AuthCustomerRequest;
import com.ua.searchservice.entity.customer.response.AuthCustomerResponse;
import com.ua.searchservice.entity.user.MyUserDetails;
import com.ua.searchservice.entity.user.User;
import com.ua.searchservice.exceptions.UserExceptions;
import com.ua.searchservice.repository.CustomerRepository;
import com.ua.searchservice.repository.SpecialistRepository;
import com.ua.searchservice.service.userdetails.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class AuthService {

    private CustomerRepository customerRepository;

    private SpecialistRepository specialistRepository;

    private Algorithm algorithm;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private UserService userService;

    public AuthService(CustomerRepository customerRepository, UserService userService,
                       SpecialistRepository specialistRepository) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.specialistRepository = specialistRepository;
        this.algorithm = Algorithm.HMAC512(new String(Properties.secret.getBytes()));
    }

    public AuthCustomerResponse loginCustomer(AuthCustomerRequest request) {

        Customer customer = new Customer();
        customer.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        customer.setEmail(request.getEmail());
        customer.setUsername(request.getUsername());

        customerRepository.save(customer);

        String token = createJwtToken(customer.getUsername(), customer.getRoles());

        return new AuthCustomerResponse(customer, token);
    }

    public AuthSpecialistResponse loginSpecialist(AuthSpecialistRequest request) {

        Specialist specialist = new Specialist();
        specialist.setPassword((bCryptPasswordEncoder.encode(request.getPassword())));
        specialist.setStatus(UserStatus.ACTIVE);
        specialist.setEmail(request.getEmail());
        specialist.setWorkExperience(request.getWorkExperience());
        specialist.setUsername(request.getUsername());

        specialistRepository.save(specialist);

        String token = createJwtToken(specialist.getUsername(), specialist.getRoles());

        return new AuthSpecialistResponse(specialist, token);
    }

    public AuthCustomerResponse signIn(SignInRequest request) {
        MyUserDetails userDetails = (MyUserDetails) userService.loadUserByUsername(request.getUsername());
        User user = userDetails.getSource();
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw UserExceptions.badCredentials();
        }

        String token = createJwtToken(user.getUsername(), user.getRoles());

        return new AuthCustomerResponse(user, token);
    }

    private String createJwtToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        long issuedAt = System.currentTimeMillis();
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date(issuedAt))
                .withExpiresAt(new Date(issuedAt + validityInMilliseconds))
                .withArrayClaim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .sign(algorithm);
    }
}