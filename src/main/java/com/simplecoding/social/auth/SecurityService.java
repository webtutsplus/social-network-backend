package com.simplecoding.social.auth;

import com.simplecoding.social.auth.models.Credentials;
import com.simplecoding.social.auth.models.SecurityProperties;
import com.simplecoding.social.auth.models.UserDto;
import com.simplecoding.social.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


@Service
public class SecurityService {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    CookieUtils cookieUtils;

    @Autowired
    SecurityProperties securityProps;

    public UserDto getUser() {
        UserDto userDtoPrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof UserDto) {
            userDtoPrincipal = ((UserDto) principal);
        }
        return userDtoPrincipal;
    }

    public Credentials getCredentials() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (Credentials) securityContext.getAuthentication().getCredentials();
    }

    public boolean isPublic() {
        return securityProps.getAllowedPublicApis().contains(httpServletRequest.getRequestURI());
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }
}
