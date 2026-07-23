package com.crisan.gestion_aulas.security;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private UserDetails userDetails() {
        return User.withUsername("a@b.com").password("x").authorities(List.of()).build();
    }

    @Test
    void skipsWhenNoAuthorizationHeader() throws Exception {
        FilterChain chain = org.mockito.Mockito.mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void skipsWhenHeaderNotBearer() throws Exception {
        request.addHeader("Authorization", "Basic abc");
        FilterChain chain = org.mockito.Mockito.mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void authenticatesWithValidToken() throws Exception {
        request.addHeader("Authorization", "Bearer good-token");
        UserDetails details = userDetails();
        when(jwtService.extractUsername("good-token")).thenReturn("a@b.com");
        when(customUserDetailsService.loadUserByUsername("a@b.com")).thenReturn(details);
        when(jwtService.isTokenValid("good-token", details)).thenReturn(true);
        FilterChain chain = org.mockito.Mockito.mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .isEqualTo(details);
        verify(chain).doFilter(request, response);
    }

    @Test
    void doesNotAuthenticateWithInvalidToken() throws Exception {
        request.addHeader("Authorization", "Bearer bad-token");
        UserDetails details = userDetails();
        when(jwtService.extractUsername("bad-token")).thenReturn("a@b.com");
        when(customUserDetailsService.loadUserByUsername("a@b.com")).thenReturn(details);
        when(jwtService.isTokenValid("bad-token", details)).thenReturn(false);
        FilterChain chain = org.mockito.Mockito.mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(request, response);
    }

    @Test
    void skipsUserLookupWhenTokenHasNoUsername() throws Exception {
        request.addHeader("Authorization", "Bearer empty");
        when(jwtService.extractUsername("empty")).thenReturn(null);
        FilterChain chain = org.mockito.Mockito.mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        verify(customUserDetailsService, never()).loadUserByUsername(org.mockito.ArgumentMatchers.any());
        verify(chain).doFilter(request, response);
    }
}
