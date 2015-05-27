package com.rsternal.mhbl.web.security;

import com.rsternal.mhbl.main.service.Service;
import com.rsternal.mhbl.main.service.exceptions.AddServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.ServiceDataNotFoundException;
import com.rsternal.mhbl.web.utilities.DefaultTemporaryAdminUser;
import dao.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class MhblAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("userService")
    private Service<User> service;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = String.valueOf(authentication.getName());
        String password = String.valueOf(authentication.getCredentials());
        User user = new User();
        try {
            user = service.findById(login);
        } catch (ServiceDataNotFoundException e) {
            e.printStackTrace();
        }

        if (!login.equals(user.getLogin()) || !password.equals(user.getPassword()) || !user.isActive()) {
            addTemporaryAdminUser();
            throw new AuthenticationException("Unable authenticate user") {};
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password, grantedAuthorities);
        return token;
    }

    public boolean supports(Class<?> authenticate) {
        return authenticate.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void addTemporaryAdminUser() {
        try {
            if (service.findAll().size() == 0) {
                service.add(new DefaultTemporaryAdminUser().getDefaultuser());
            }
        } catch (AddServiceOperationException | ServiceDataNotFoundException e) {
        }
    }
}
