package com.omniesoft.commerce.security.jwt;

import com.omniesoft.commerce.persistence.entity.account.AuthorityEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.security.jwt.payload.JsonWebTokenUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JsonWebTokenUserFactory {

    private JsonWebTokenUserFactory() {
    }

    public static JsonWebTokenUser create(UserEntity user) {
        return new JsonWebTokenUser(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getEncryptPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthorities(List<AuthorityEntity> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
