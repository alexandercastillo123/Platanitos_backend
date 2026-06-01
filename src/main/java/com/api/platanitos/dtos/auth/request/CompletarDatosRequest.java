package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record CompletarDatosRequest(
    String tipo,
    String token,
    Long id,
    String identificador,
    String password
) {}
