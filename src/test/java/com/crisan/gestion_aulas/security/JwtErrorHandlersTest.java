package com.crisan.gestion_aulas.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;

class JwtErrorHandlersTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void entryPointWritesUnauthorizedJson() throws Exception {
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint(objectMapper);
        MockHttpServletResponse response = new MockHttpServletResponse();

        entryPoint.commence(new MockHttpServletRequest(), response,
                new BadCredentialsException("nope"));

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getContentType()).isEqualTo("application/json");
        JsonNode body = objectMapper.readTree(response.getContentAsByteArray());
        assertThat(body.get("status").asInt()).isEqualTo(401);
        assertThat(body.get("message").asText()).contains("autenticarse");
    }

    @Test
    void accessDeniedHandlerWritesForbiddenJson() throws Exception {
        JwtAccesDeniedHandler handler = new JwtAccesDeniedHandler(objectMapper);
        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.handle(new MockHttpServletRequest(), response,
                new AccessDeniedException("denied"));

        assertThat(response.getStatus()).isEqualTo(403);
        assertThat(response.getContentType()).isEqualTo("application/json");
        JsonNode body = objectMapper.readTree(response.getContentAsByteArray());
        assertThat(body.get("status").asInt()).isEqualTo(403);
        assertThat(body.get("message").asText()).contains("permisos");
    }
}
