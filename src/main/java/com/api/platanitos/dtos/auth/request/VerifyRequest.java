package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record VerifyRequest(
    String tipo,
    String identificador
) {}
