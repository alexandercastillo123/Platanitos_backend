package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record IdentificadorRequest(
    String tipo,
    String token,
    Long id
) {}
