package nubes.booktify.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import nubes.booktify.model.User;
import nubes.booktify.repository.UserRepository;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken == null || jwtToken.isEmpty() || !jwtTokenUtil.validateStructure(jwtToken)) {
            chain.doFilter(request, response);
            return;
        }

        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user = userRepository.findByEmail(email).get();

        if(user == null || !jwtTokenUtil.validateToken(jwtToken, user)) {
            chain.doFilter(request, response);
            return;
        }

        if(user.getToken() == null || user.getToken().isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
