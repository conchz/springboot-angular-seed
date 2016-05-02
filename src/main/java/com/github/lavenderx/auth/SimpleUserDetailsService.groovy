package com.github.lavenderx.auth

import com.github.lavenderx.model.entity.UserAuthentication
import com.google.common.collect.Lists
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by lavenderx on 2016-05-01.
 */
@Service
class SimpleUserDetailsService implements UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication user = new UserAuthentication()
        if (user != null) {
            List<GrantedAuthority> authorities = Lists.newLinkedList()
            user.roleList.each { role ->
                authorities.add(new SimpleGrantedAuthority(role))
            }

            new User(user.username, user.password, authorities)
        }

        throw new UsernameNotFoundException("Username " + username + " is not authorized.")
    }
}
