package com.kemal.spring.service.userDetails;

import org.springframework.stereotype.Service;

/**
 * Created by Keno&Kemo on 18.02.2018..
 */
@Service
public class _UserDetailsServiceImpl /*implements UserDetailsService*/ {
    /*private _UserService userService;

    public _UserDetailsServiceImpl(_UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new _UserDetailsImpl(user);
    }*/
}
