package com.codefish.keeilzhanstd.ecommerce;

import com.codefish.keeilzhanstd.ecommerce.customer.Customer;
import com.codefish.keeilzhanstd.ecommerce.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(CustomerAuthenticationProvider.class);

    private CustomerRepository repository;

    private PasswordEncoder encoder;

    public CustomerAuthenticationProvider(CustomerRepository repository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Customer> c = repository.findByUsername(username);
        if (c == null) {
            throw new BadCredentialsException("Details not found");
        }

        if (encoder.matches(password, c.get().getPassword())) {
            logger.info(authentication.getName());
            return new UsernamePasswordAuthenticationToken(username, password, getCustomerRoles(c.get().getRole()));
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }

    private List<GrantedAuthority> getCustomerRoles(String studentRoles) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String[] roles = studentRoles.split(",");
        for (String role : roles) {
            logger.info("Role: " + role);
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.replaceAll("\\s+", "")));
        }

        return grantedAuthorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}