package com.api.platanitos.dtos.api;

import lombok.Builder;

@Builder
public record Response<T>(
    Boolean success,
    String message,
    T  data
) {}
