package com.api.platanitos.dtos.auth.response;

import lombok.Builder;

@Builder
public record LoginResponse(
    Long id,
    String email,
    String nombres,
    String apellidos,
    String rol
) {}