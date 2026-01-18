package com.example.template.common.config.auth;

import com.example.template.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Map<String, Object> userMap = userMapper.findByEmail(email);

        if (userMap == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String password = (String) userMap.get("password");
        String role = (String) userMap.get("role");

        return new User(
                email,
                password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
    }
}
