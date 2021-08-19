package com.itda.apiserver.interceptor;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.jwt.TokenExtractor;
import com.itda.apiserver.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final String JWT_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final TokenExtractor tokenExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (loginRequired(handler)) {
            setUserIdOn(request);
        }
        return true;
    }

    private boolean loginRequired(Object handler) {
        return handler instanceof HandlerMethod
                && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class);
    }

    private void setUserIdOn(HttpServletRequest request) {
        String header = request.getHeader(JWT_HEADER);
        String token = tokenExtractor.extractToken(header);
        Long userId = tokenProvider.getUserId(token);
        request.setAttribute("user_id", userId);
    }
}
