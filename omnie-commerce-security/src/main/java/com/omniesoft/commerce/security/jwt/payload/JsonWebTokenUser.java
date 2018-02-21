package com.omniesoft.commerce.security.jwt.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public class JsonWebTokenUser implements UserDetails {

    private final UUID id;

    private final String username;

    private final String password;

    private final String email;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean enabled;

    private final LocalDateTime lastPasswordResetDate;


    public JsonWebTokenUser(
            UUID id,
            String username,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            LocalDateTime lastPasswordResetDate
    ) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public UUID getId() {

        return id;
    }

    public String getEmail() {

        return email;
    }

    @JsonIgnore
    public LocalDateTime getLastPasswordResetDate() {

        return lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {

        return enabled;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return enabled;
    }
}
