package com.api.platanitos.dtos.auth.response;

import lombok.Builder;

@Builder
public record RegistroResponse(
    String identificador
) {}
