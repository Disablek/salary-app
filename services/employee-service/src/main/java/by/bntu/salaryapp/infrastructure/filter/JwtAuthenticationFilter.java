package by.bntu.salaryapp.infrastructure.filter;


import by.bntu.salaryapp.application.service.implementations.user.JwtServiceImpl;
import by.bntu.salaryapp.application.service.implementations.user.UserServiceImpl;
import by.bntu.salaryapp.application.service.interfaces.user.UserService;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            // Check for headers from gateway
            String userIdHeader = request.getHeader("X-User-Id");
            if (StringUtils.isNotEmpty(userIdHeader)) {
                UserDetails userDetails = createUserDetailsFromHeaders(request);
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
                filterChain.doFilter(request, response);
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());

        try {
            var username = jwtService.extractUserName(jwt);
            log.debug("Extracted username from JWT: {}", username);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("Creating user details from JWT claims for: {}", username);
                
                // Create UserDetails from JWT claims instead of loading from database
                UserDetails userDetails = createUserDetailsFromJwt(jwt);
                log.debug("User details created: {} with authorities: {}", userDetails.getUsername(), userDetails.getAuthorities());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    log.debug("JWT token is valid for user: {}", username);
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                    log.info("User authenticated: {} with roles: {}", username, userDetails.getAuthorities());
                } else {
                    log.warn("JWT token is invalid for user: {}", username);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().write("JWT token is expired");
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("Error processing JWT token", e);
            filterChain.doFilter(request, response);
        }
    }

    private UserDetails createUserDetailsFromJwt(String jwt) {
        String username = jwtService.extractUserName(jwt);
        String userId = jwtService.extractUserId(jwt);
        String email = jwtService.extractEmail(jwt);
        String roleName = jwtService.extractRole(jwt);
        String firstName = jwtService.extractFirstName(jwt);
        String surname = jwtService.extractSurname(jwt);

        // Create role from JWT claim
        Role role = null;
        if (roleName != null) {
            role = Role.builder()
                    .name(roleName)
                    .build();
        }

        // Create User object from JWT claims
        return User.builder()
                .username(username)
                .email(email)
                .firstName(firstName != null ? firstName : "")
                .lastName("") // Not available in JWT
                .surname(surname != null ? surname : "")
                .role(role)
                .build();
    }

    private UserDetails createUserDetailsFromHeaders(HttpServletRequest request) {
        String userId = request.getHeader("X-User-Id");
        String email = request.getHeader("X-User-Email");
        String roleName = request.getHeader("X-User-Role");

        Role role = null;
        if (roleName != null) {
            role = Role.builder()
                    .name(roleName)
                    .build();
        }

        return User.builder()
                .username(email != null ? email : userId)
                .email(email)
                .role(role)
                .build();
    }
}
