package com.api.platanitos.dtos.api;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ResponseError(
    Boolean success,
    String message,
    LocalDateTime timestamp
) {}
