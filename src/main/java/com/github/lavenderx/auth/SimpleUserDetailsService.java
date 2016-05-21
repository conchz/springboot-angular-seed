package com.github.lavenderx.auth;

import com.github.lavenderx.model.entity.UserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavenderx on 2016-05-05.
 */
@Service
@Slf4j
public class SimpleUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication user = new UserAuthentication();
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            user.getRoleList().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

            return new User(user.getUsername(), user.getPassword(), authorities);
        }

        log.error("User `{}` is not authorized", username);
        throw new UsernameNotFoundException("User " + username + " is not authorized");
    }
}
