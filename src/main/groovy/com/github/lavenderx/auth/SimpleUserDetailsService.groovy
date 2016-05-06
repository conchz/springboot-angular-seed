package com.github.lavenderx.auth

import com.github.lavenderx.model.entity.UserAuthentication
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by lavenderx on 2016-05-05.
 */
@Service
@Slf4j
class SimpleUserDetailsService implements UserDetailsService {

    @Override
    @TypeChecked
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication user = new UserAuthentication()
        if (user != null) {
            List<GrantedAuthority> authorities = []
            user.roleList.each { role ->
                authorities.add(new SimpleGrantedAuthority(role))
            }

            new User(user.username, user.password, authorities)
        }

        log.error('User `{}` is not authorized', username)
        throw new UsernameNotFoundException('User ' + username + ' is not authorized')
    }
}
