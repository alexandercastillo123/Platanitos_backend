package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record RegisterRequest(
    String tipo,
    String identificador,
    String dni,
    String nombres,
    String apellidos
) {}
