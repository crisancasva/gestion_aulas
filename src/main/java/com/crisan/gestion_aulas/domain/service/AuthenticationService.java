package com.crisan.gestion_aulas.domain.service;

import com.crisan.gestion_aulas.web.dto.AuthenticationRequest;
import com.crisan.gestion_aulas.web.dto.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse autenthicate(AuthenticationRequest request);
}
