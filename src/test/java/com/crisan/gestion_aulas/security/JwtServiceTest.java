package com.crisan.gestion_aulas.security;

import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String SECRET =
            "MzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTA=";

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86_400_000L);
    }

    private UserDetails userDetails(String username) {
        return User.withUsername(username).password("x").authorities(List.of()).build();
    }

    @Test
    void generatedTokenContainsSubject() {
        String token = jwtService.generateToken("user@b.com");

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractUsername(token)).isEqualTo("user@b.com");
    }

    @Test
    void isTokenValidReturnsTrueForMatchingUser() {
        String token = jwtService.generateToken("user@b.com");

        assertThat(jwtService.isTokenValid(token, userDetails("user@b.com"))).isTrue();
    }

    @Test
    void isTokenValidReturnsFalseForDifferentUser() {
        String token = jwtService.generateToken("user@b.com");

        assertThat(jwtService.isTokenValid(token, userDetails("other@b.com"))).isFalse();
    }

    @Test
    void expiredTokenIsRejected() {
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1_000L);
        String token = jwtService.generateToken("user@b.com");

        assertThatThrownBy(() -> jwtService.isTokenValid(token, userDetails("user@b.com")))
                .isInstanceOf(io.jsonwebtoken.ExpiredJwtException.class);
    }

    @Test
    void tokenSignedWithDifferentKeyIsRejected() {
        JwtService other = new JwtService();
        ReflectionTestUtils.setField(other, "secret",
                "b3RoZXJzZWNyZXRvdGhlcnNlY3JldG90aGVyc2VjcmV0MDEyMzQ1Njc4OQ==");
        ReflectionTestUtils.setField(other, "jwtExpiration", 86_400_000L);
        String token = other.generateToken("user@b.com");

        assertThatThrownBy(() -> jwtService.extractUsername(token))
                .isInstanceOf(SignatureException.class);
    }
}
