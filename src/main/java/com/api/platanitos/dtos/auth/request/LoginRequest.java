package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record LoginRequest(
    String tipo,
    String identificador,
    String passwordHash
) {} 
