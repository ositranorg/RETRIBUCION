package com.kemal.spring.service.userDetails;

/**
 * Created by Keno&Kemo on 04.11.2017..
 */


public class _UserDetailsImpl /*implements UserDetails*/ {
    /**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;
	private User user;
    public _UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        List<Role> roles = user.getRoles();
        for( Role role : roles ) {
            authorities.add( new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;

    }
    
    public User getUser() {
        return user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }*/
}
