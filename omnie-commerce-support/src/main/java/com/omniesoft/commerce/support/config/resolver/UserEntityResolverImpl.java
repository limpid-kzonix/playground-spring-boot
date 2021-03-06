package com.omniesoft.commerce.support.config.resolver;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEntityResolverImpl implements UserEntityResolver {

    private final UserRepository userRepository;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        String userDetailsName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity user = userRepository.findByLogin(userDetailsName);

        if (null != user) {
            if (!user.getEnabled()) {
                throw new UsefulException("User account is not activate. Confirm registration please",
                        UserModuleErrorCodes.USER_NOT_ACTIVE);
            }
            return user;
        } else {
            throw new UnauthorizedUserException("UserEntity isn`t exist in security context.");
        }
    }

    @Override
    public Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public Optional<String> getCurrentUserId() {
        Optional<Authentication> authentication = getAuthentication();
        return authentication.map(a -> {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) a.getDetails();
            return (String) ((Map) details.getDecodedDetails()).get("userId");
        });
    }

}
