package com.api.platanitos.dtos.auth.request;

import lombok.Builder;

@Builder
public record RecuperarRequest(
    String token,
    Long id,
    String password
) {
    
}
