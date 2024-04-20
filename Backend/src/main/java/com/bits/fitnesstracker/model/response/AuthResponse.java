package com.bits.fitnesstracker.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
    int status;
    String message;
    String token;
}
