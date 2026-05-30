package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record CompletarDatosRequest(
    String tipo,
    String token,
    Integer id,
    String identificador,
    String passwordHash
) {}
