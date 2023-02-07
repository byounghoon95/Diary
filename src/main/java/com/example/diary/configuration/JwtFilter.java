package com.example.diary.configuration;

import com.example.diary.component.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = resolveToken(request);
        log.info("authorization : {}",authorization); /* Authorization */
        String requestURI = request.getRequestURI();

        if(StringUtils.hasText(authorization) && tokenProvider.validateToken(authorization)){
            String authentication = tokenProvider.getMemberId(authorization);
            log.info("authentication : {}", authentication);
            //권한 부여
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken("adf", null, List.of(new SimpleGrantedAuthority("USER")));

            //Detail을 넣어줌
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication, requestURI);
        }else{
            log.info("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
        }
        filterChain.doFilter(request, response);

//        //Token꺼내기(Bearer 삭제)
//        String token = authorization.split(" ")[1];
//        String token = "asfefzcvv.123213112.vffasdf";
//        log.info("token : {}",token);

//        //Token Expired 되었는지 여부
//        if (tokenProvider.isExpired(token)) {
//            log.error("Token이 만료 되었습니다.");
//            filterChain.doFilter(request,response);
//            return;
//        }
//
//        //Username에서 Token 꺼내기
//        String memberId = tokenProvider.getMemberId(token);
//        log.info("memberId : {}",memberId);
    }

    // Request의 Header에서 token 값을 가져옵니다. "authorization" : "token'
    /**토큰 정보 추출 */
    private String resolveToken(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
